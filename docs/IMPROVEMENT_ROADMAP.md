# IMPROVEMENT_ROADMAP — Analizador-Sintactico-y-Lexico

Repo de valor histórico (P1 de la serie de compiladores). Solo higiene; no invertir esfuerzo técnico.

## Quick Wins

| # | Mejora | Impacto | Esfuerzo | Prioridad |
|---|---|---|---|---|
| 1 | Untrackear `parser.java` y `sym.java` (generados) y hacer que Maven los genere al build, como en P2/P3 | Medio | Bajo | P1 |
| 2 | Añadir al README un bloque "Parte 1 de 3" con links a P2 y P3 (y recíprocamente) — convierte tres repos sueltos en una narrativa | Alto | Bajo | P0 |
| 3 | Mover los jars de CUP a `src/lib/`; eliminar `.github/java-upgrade/` | Bajo | Bajo | P2 |
| 4 | GitHub Topics: `compiler`, `lexer`, `parser`, `jflex`, `cup`, `java` + descripción | Medio | Bajo | P1 |

## Mejoras técnicas / arquitectónicas

Ninguna recomendada: la evolución de este código vive en P2/P3.

## Mejoras de GitHub

Ya presentes: CI, LICENSE, enunciado en docs. Faltan: Topics, badge CI en README, links de la serie (item 2).
