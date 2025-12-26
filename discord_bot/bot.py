import os, re, json, datetime, pathlib
from io import BytesIO

import discord
from discord.ext import commands
from dotenv import load_dotenv
import aiohttp  # NEW

load_dotenv()
TOKEN = os.getenv("DISCORD_TOKEN")
GUILD_ID = os.getenv("GUILD_ID")


OPINION_API_URL = os.getenv("OPINION_API_URL")      # URL API

TARGET_CHANNEL_IDS = set()

intents = discord.Intents.default()
intents.guilds = True
intents.members = True
intents.message_content = True

bot = commands.Bot(command_prefix="!", intents=intents)

COURSE_RE = re.compile(r"\b([A-Z]{3}\d{4})\b")
SEM_RE = re.compile(r"\b(?:trimestre|sem|session)?\s*([HAEhae]\d{2})\b", re.IGNORECASE)
DIFF_RE = re.compile(r"\b(?:d|diff|difficulte|difficulté)\s*(?:[:=]\s*|\s+)([1-5])\b", re.IGNORECASE)
WORK_RE = re.compile(r"\b(?:t|travail|charge|work)\s*(?:[:=]\s*|\s+)([1-5])\b", re.IGNORECASE)


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

def parse_diff_work(text: str):
    d = DIFF_RE.search(text or "")
    w = WORK_RE.search(text or "")
    diff = int(d.group(1)) if d else None
    work = int(w.group(1)) if w else None
    return diff, work

def parse_semester(text: str):
    m = SEM_RE.search(text or "")
    return m.group(1).upper() if m else None

def semester_to_year(semester: str):
    # A25 -> 2025
    yy = int(semester[1:])
    return 2000 + yy

def extract_comment(text: str) -> str:
    t = (text or "").strip()

    t = COURSE_RE.sub("", t)
    t = SEM_RE.sub("", t)
    t = DIFF_RE.sub("", t)
    t = WORK_RE.sub("", t)
    t = PROF_NAME_RE.sub("", t)

    t = re.sub(r"\b(prof|professeur|d|diff|difficulte|difficulté|t|travail|charge|work|sem|session|trimestre)\b", "", t, flags=re.IGNORECASE)

    t = re.sub(r"^[\s:;,\-–—]+", "", t)
    t = re.sub(r"\s+", " ", t).strip()

    return t


#def infer_trimester_and_year():
    #now = datetime.datetime.now()
   # m, y = now.month, now.year
    # Jan-Apr = H, May-Aug = E, Sep-Dec = A
    #if 1 <= m <= 4:
    #    tri = f"H{str(y)[2:]}"
    #elif 5 <= m <= 8:
    #    tri = f"E{str(y)[2:]}"
    #else:
    #    tri = f"A{str(y)[2:]}"
    #return tri, y


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
    
    print("OPINION_API_URL =", OPINION_API_URL)
    print("Payload envoyé =", record)


    try:
        async with aiohttp.ClientSession() as session:
            async with session.post(OPINION_API_URL, json=record, timeout=5) as resp:
                body = await resp.text()
                print("[OPINION API] status =", resp.status)
                print("[OPINION API] body =", body)

                if resp.status >= 400:
                    print(f"[OPINION API] Error {resp.status}: {body}")

    except Exception as e:
        print("[OPINION API] Exception while posting opinion:", e)


############################################################################# 

@bot.event
async def on_message(message: discord.Message):
    await bot.process_commands(message)

    if message.author.bot or not message.guild:
        return
    if TARGET_CHANNEL_IDS and message.channel.id not in TARGET_CHANNEL_IDS:
        return

    text = (message.content or "").strip()
    if not text:
        return

    courses = extract_course_codes(text)
    if not courses:
        return

    diff, work = parse_diff_work(text)
    semester = parse_semester(text)  

    if semester is None or diff is None or work is None:
        await message.reply(
            "⚠️ Format d’avis non valide.\n"
            "Veuillez écrire votre avis suivant le format suivant:\n"
            "- `<sigle du cours> <trimestre> diff <1–5> travail <1–5> prof <nom>`\n"
            "Exemples:\n"
            "- `IFT2255 A25 diff 4 travail 5 prof Tremblay`\n"
            "- `IFT2255 H25 difficulte:3 charge:4`\n"
            "\n"
            "Règles: sigle + trimestre (A25/H25/E24) + diff(1-5) + travail(1-5)."
            "\n"
            "Note : Les avis partagés reflètent uniquement l’expérience personnelle des étudiants."

        )
        return


    prof = extract_professor_name(text) or ""
    commentaire = extract_comment(text)  
    trimestre = semester              # ex: A25
    annee = semester_to_year(semester) # ex: 2025

    pathlib.Path("exports").mkdir(exist_ok=True)

    print("Message reçu:", text)
    print("Cours détectés:", courses, "diff/work:", diff, work)


    for sigle in courses:
        avis_payload = {
            "sigle": sigle,
            "session": sigle,
            "trimestre": trimestre,
            "annee": annee,
            "nivDifficulte": diff,
            "volumeTravail": work,
            "professeur": prof,
            "commentaire": commentaire,
            "nombreAvis": 1
        }

        # Trace locale du bot (fichier)
        with open("exports/avis_stream.ndjson", "a", encoding="utf-8") as f:
            f.write(json.dumps(avis_payload, ensure_ascii=False) + "\n")

        # POST vers ton API Javalin
        await post_opinion_to_api(avis_payload)


@bot.event
async def on_ready():
    print(f"Logged in as {bot.user} (ID: {bot.user.id})")


if __name__ == "__main__":
    if not TOKEN:
        raise SystemExit("Missing DISCORD_TOKEN in .env")
    bot.run(TOKEN)
