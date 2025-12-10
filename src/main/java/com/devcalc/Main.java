package com.devcalc;

import io.javalin.Javalin;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Aplicação rodando no Kubernetes!"));
        app.get("/add", ctx -> {
            int a = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("a")));
            int b = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("b")));
            ctx.result(String.valueOf(a + b));
        });
    }
}
