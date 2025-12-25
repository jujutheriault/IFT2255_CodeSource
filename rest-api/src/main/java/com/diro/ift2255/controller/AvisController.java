package com.diro.ift2255.controller;

import java.util.List;
import java.util.Map;

import com.diro.ift2255.model.Avis;
import com.diro.ift2255.service.AvisService;
import com.diro.ift2255.util.ResponseUtil;

import io.javalin.http.Context;

public class AvisController {

    private final AvisService store;

    public AvisController(AvisService store) {
        this.store = store;
    }

    // POST /avis
    public void createAvis(Context ctx) {
        Avis avis = ctx.bodyAsClass(Avis.class);

        // validations minimales (évite crash sur mauvaise entrée)
        if (avis.getSigle() == null || avis.getSigle().isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("sigle est requis (ex: IFT2255)."));
            return;
        }
        if (avis.getNivDifficulte() < 1 || avis.getNivDifficulte() > 5) {
            ctx.status(400).json(ResponseUtil.formatError("nivDifficulte doit être entre 1 et 5."));
            return;
        }
        if (avis.getVolumeTravail() < 1 || avis.getVolumeTravail() > 5) {
            ctx.status(400).json(ResponseUtil.formatError("volumeTravail doit être entre 1 et 5."));
            return;
        }
        if (avis.getTrimestre() == null || avis.getTrimestre().isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("trimestre est requis (ex: A25, H25, E25)."));
            return;
        }
        if (avis.getAnnee() <= 0) {
            ctx.status(400).json(ResponseUtil.formatError("annee doit être valide (ex: 2025)."));
            return;
        }

        avis.setSigle(avis.getSigle().trim().toUpperCase());
        store.add(avis);
        ctx.status(201).json(avis);
    }

    // GET /avis/{sigle}
    public void getAvisBySigle(Context ctx) {
        String sigle = ctx.pathParam("sigle");
        List<Avis> list = store.getBySigle(sigle);

        if (list.isEmpty()) {
            ctx.status(404).json(ResponseUtil.formatError("Aucun avis trouvé pour " + sigle.toUpperCase()));
            return;
        }
        ctx.status(200).json(list);
    }

    // GET /avis/{sigle}/resume
    public void getResume(Context ctx) {
        String sigle = ctx.pathParam("sigle");
        Map<String, Object> resume = store.getResume(sigle);

        Number countNum = (Number) resume.getOrDefault("count", 0);
        int count = countNum.intValue();

        if (count == 0) {
            ctx.status(404).json(ResponseUtil.formatError("Aucun avis trouvé pour " + sigle.toUpperCase()));
            return;
        }

        ctx.status(200).json(resume);
    }
}
