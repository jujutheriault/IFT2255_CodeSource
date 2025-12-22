package com.diro.ift2255.controller;

import com.diro.ift2255.model.Program;
import com.diro.ift2255.service.ProgramService;
import com.diro.ift2255.util.ResponseUtil;
import io.javalin.http.Context;

import java.util.Optional;

public class ProgramController {

    private final ProgramService service;

    public ProgramController(ProgramService service) {
        this.service = service;
    }

    /**
     * GET /programs/{id}
     */
    public void getProgramById(Context ctx) {
        String id = ctx.pathParam("id");
        boolean includeDetails = "true".equalsIgnoreCase(ctx.queryParam("include_courses_detail"));

        Optional<Program> program = service.getProgramById(id, includeDetails);
        
        if (program.isPresent()) {
            ctx.json(program.get());
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Programme introuvable: " + id));
        }
    }
}