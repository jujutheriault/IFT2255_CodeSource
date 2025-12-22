import os, re, json, datetime, pathlib
from io import BytesIO

import discord
from discord.ext import commands
from dotenv import load_dotenv
import aiohttp  # NEW

load_dotenv()
TOKEN = os.getenv("DISCORD_TOKEN")
print(f"DEBUG: Token = {TOKEN[:20] if TOKEN else 'NONE'}...")
GUILD_ID = os.getenv("GUILD_ID")


OPINION_API_URL = os.getenv("OPINION_API_URL")      # URL API
AVIS_JSON_PATH = os.getenv("AVIS_JSON_PATH", "../rest-api/src/main/java/com/diro/ift2255/data/avis_etudiants.json")

TARGET_CHANNEL_IDS = set()

intents = discord.Intents.default()
intents.guilds = True
intents.members = True
intents.message_content = True

bot = commands.Bot(command_prefix="!", intents=intents)

COURSE_RE = re.compile(r"\b([A-Z]{3}\d{4})\b")

PROF_NAME_RE = re.compile(
    r"\b(?:prof(?:esseur)?|dr\.?|doctor|teacher|instructor)\s+"
    r"([A-Z][a-zÀ-ÖØ-öø-ÿ\-]+(?:\s+[A-Z][a-zÀ-ÖØ-öø-ÿ\-]+)?)",
    re.IGNORECASE,
)

OPINION_HINTS = {
    "fr": {"prof", "professeur", "cours", "matière", "classe", "bon", "mauvais", "difficile", "facile"},
    "en": {"professor", "teacher", "instructor", "course", "class", "good", "bad", "difficult", "easy"},
}

OPINION_KEYWORDS = set().union(*OPINION_HINTS.values())


def extract_course_codes(text: str):
    return sorted({m.group(1).upper() for m in COURSE_RE.finditer(text or "")})


def extract_professor_name(text: str):
    m = PROF_NAME_RE.search(text or "")
    return m.group(1) if m else None


def looks_like_opinion(text: str) -> bool:
    # heuristique simple
    if extract_course_codes(text):
        return True
    t = (text or "").lower()
    return any(k in t for k in OPINION_KEYWORDS)


def record_from_message(msg: discord.Message):
    text = msg.content or ""
    courses = extract_course_codes(text)
    prof = extract_professor_name(text)

    base = {
        "message_id": str(msg.id),
        "guild_id": str(msg.guild.id) if msg.guild else None,
        "channel_id": str(msg.channel.id),
        "channel_name": getattr(msg.channel, "name", None),
        "author_id": str(msg.author.id),
        "author_name": f"{msg.author.name}#{msg.author.discriminator}",
        "created_at": msg.created_at.isoformat(),
        "text": text,
    }

    if courses:
        return [{**base, "course_code": c, "professor_name": prof} for c in courses]
    else:
        return []


def group_by_course(flat_records):
    grouped = {}
    for r in flat_records:
        code = r.get("course_code")
        if not code:
            continue
        if code not in grouped:
            grouped[code] = []

        grouped[code].append({
            "professor_name": r.get("professor_name"),
            "text": r.get("text"),
            "message_id": r.get("message_id"),
            "author_id": r.get("author_id"),
            "author_name": r.get("author_name"),
            "created_at": r.get("created_at"),
            "channel_id": r.get("channel_id"),
            "channel_name": r.get("channel_name"),
        })
    return grouped

# la fonciton post_opinion_to_api est celle qui poste à l'API ================

async def post_opinion_to_api(record: dict):
    """ 
    ceci est la fonction principale qui poste à l'API. le concept derrière est simple: 
    si l'URL de l'API est définie, nous envoyons un POST avec les données de l'opinion. 
    dans notre cas, on envoie cahque opinion à L'API Javalin le code qu on avait définit 
    dans le projet web_api.
    """

    if not OPINION_API_URL:
        print("OPINION_API_URL is not set, skipping POST.")
        return

    try:
        async with aiohttp.ClientSession() as session:
            async with session.post(OPINION_API_URL, json=record, timeout=5) as resp:
                if resp.status >= 400:
                    body = await resp.text()
                    print(f"[OPINION API] Error {resp.status}: {body}")
    except Exception as e:
        print("[OPINION API] Exception while posting opinion:", e)

############################################################################# 

def append_avis_to_json(sigle: str, auteur: str, note: int, commentaire: str):
    """Ajoute un avis au fichier JSON"""
    date = datetime.datetime.now().strftime("%Y-%m-%d")
    
    # Créer le dossier si nécessaire
    pathlib.Path(AVIS_JSON_PATH).parent.mkdir(parents=True, exist_ok=True)
    
    # Lire les avis existants
    if pathlib.Path(AVIS_JSON_PATH).exists():
        with open(AVIS_JSON_PATH, "r", encoding="utf-8") as f:
            avis_list = json.load(f)
    else:
        avis_list = []
    
    # Ajouter le nouvel avis
    nouvel_avis = {
        "sigle": sigle,
        "auteur": auteur,
        "note": note,
        "commentaire": commentaire,
        "date": date
    }
    avis_list.append(nouvel_avis)
    
    # Écrire dans le fichier
    with open(AVIS_JSON_PATH, "w", encoding="utf-8") as f:
        json.dump(avis_list, f, ensure_ascii=False, indent=2)
    
    print(f"✅ Avis ajouté: {sigle} - {auteur} - {note}/5")

@bot.command(name="avis")
async def avis_command(ctx, sigle: str = None, note: str = None, *, commentaire: str = None):
    """Commande pour poster un avis sur un cours"""
    
    if not sigle or not note or not commentaire:
        await ctx.send(
            "❌ **Format invalide!**\n"
            "Usage: `!avis [SIGLE] [NOTE] [Commentaire]`\n"
            "Exemple: `!avis IFT2255 5 Excellent cours!`"
        )
        return
    
    # Valider le sigle
    if not re.match(r"^[A-Z]{3}\d{4}$", sigle.upper()):
        await ctx.send(f"❌ **Sigle invalide:** `{sigle}` (Format: IFT2255)")
        return
    
    # Valider la note
    try:
        note_int = int(note)
        if note_int < 1 or note_int > 5:
            raise ValueError
    except ValueError:
        await ctx.send(f"❌ **Note invalide:** `{note}` (entre 1 et 5)")
        return
    
    # Valider le commentaire
    if len(commentaire) < 10:
        await ctx.send("❌ **Commentaire trop court** (min 10 caractères)")
        return
    
    auteur = ctx.author.name
    
    try:
        append_avis_to_json(sigle.upper(), auteur, note_int, commentaire)
        
        stars = "⭐" * note_int
        await ctx.send(
            f"✅ **Avis enregistré!**\n\n"
            f"📚 Cours: `{sigle.upper()}`\n"
            f"{stars} Note: **{note_int}/5**\n"
            f"💬 {commentaire}\n"
            f"👤 Par: {auteur}"
        )
        
    except Exception as e:
        await ctx.send(f"❌ **Erreur:** {e}")
        print(f"[ERROR] Erreur lors de l'ajout d'avis: {e}")









@bot.event
async def on_message(message: discord.Message):
    await bot.process_commands(message)

    if message.author.bot or not message.guild:
        return
    if TARGET_CHANNEL_IDS and message.channel.id not in TARGET_CHANNEL_IDS:
        return

    text = (message.content or "").strip()
    if not text or not looks_like_opinion(text):
        return

    recs = record_from_message(message)
    if not recs:
        return

    pathlib.Path("exports").mkdir(exist_ok=True)
    with open("exports/opinions_stream.ndjson", "a", encoding="utf-8") as f:
        for r in recs:
            f.write(json.dumps(r, ensure_ascii=False) + "\n")

    for r in recs:
        await post_opinion_to_api(r)

@bot.event
async def on_ready():
    print(f"Logged in as {bot.user} (ID: {bot.user.id})")


if __name__ == "__main__":
    if not TOKEN:
        raise SystemExit("Missing DISCORD_TOKEN in .env")
    bot.run(TOKEN)
