package com.diro.ift2255.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.diro.ift2255.model.Avis;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AvisService {

    private final Path filePath;
    private final ObjectMapper mapper = new ObjectMapper();

    // sigle -> liste avis
    private final Map<String, List<Avis>> bySigle = new HashMap<>();

    public AvisService(String filePath) {
        this.filePath = Paths.get(filePath);
        ensureParentDir();
        loadFromFile(); // recharge au démarrage
        System.out.println("[AvisService] fichier avis = " + this.filePath.toAbsolutePath());
    }

    public synchronized void add(Avis avis) {
        String sigle = normalizeSigle(avis.getSigle());
        avis.setSigle(sigle);

        bySigle.computeIfAbsent(sigle, k -> new ArrayList<>()).add(avis);
        appendToFile(avis);
    }

    public synchronized List<Avis> getBySigle(String sigle) {
        String s = normalizeSigle(sigle);
        return new ArrayList<>(bySigle.getOrDefault(s, Collections.emptyList()));
    }

    public synchronized Map<String, Object> getResume(String sigle) {
        List<Avis> list = getBySigle(sigle);
        int n = list.size();

        if (n == 0) {
            return Map.of(
                    "sigle", normalizeSigle(sigle),
                    "count", 0
            );
        }

        double avgDiff = list.stream().mapToInt(Avis::getNivDifficulte).average().orElse(0);
        double avgWork = list.stream().mapToInt(Avis::getVolumeTravail).average().orElse(0);

        return Map.of(
                "sigle", normalizeSigle(sigle),
                "nombreAvis", n,
                "avgNivDifficulte", avgDiff,
                "avgVolumeTravail", avgWork
        );
    }

    /* ---------------- helpers ---------------- */

    private void ensureParentDir() {
        try {
            Path parent = filePath.getParent();
            if (parent != null) Files.createDirectories(parent);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer le dossier data/: " + e.getMessage(), e);
        }
    }

    private void appendToFile(Avis avis) {
        try (BufferedWriter w = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND
        )) {
            w.write(mapper.writeValueAsString(avis));
            w.newLine();
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'écrire avis.ndjson: " + e.getMessage(), e);
        }
    }

    private void loadFromFile() {
        if (!Files.exists(filePath)) return;

        try (BufferedReader r = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Avis avis = mapper.readValue(line, Avis.class);
                if (avis.getSigle() == null || avis.getSigle().isBlank()) continue;

                String sigle = normalizeSigle(avis.getSigle());
                avis.setSigle(sigle);

                bySigle.computeIfAbsent(sigle, k -> new ArrayList<>()).add(avis);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible de relire data/avis.ndjson: " + e.getMessage(), e);
        }
    }

    private String normalizeSigle(String s) {
        if (s == null) return null;
        return s.replaceAll("\\s+", "").toUpperCase();
    }
}
