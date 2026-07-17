# TECHNICAL_REVIEW — Analizador-Sintactico-y-Lexico

Fecha de revisión: 2026-07-16
Método: análisis estático, enunciado (`docs/Proyecto 1 Compiladores.md`), CI y git. CI compila (`mvn clean package`).

## 1. Contexto en el portafolio

**Proyecto #1 de Compiladores (TEC)**: front-end léxico-sintáctico del lenguaje `.chip` — gramática CUP (274 líneas) + spec JFlex (127 líneas) + Main de prueba. Es el primer eslabón de la cadena que evoluciona a `Analisis-Semantico-y-Generacion-de-Codigo-Intermedio` (P2) y culmina en `Generacion-Codigo-Destino-MIPS` (P3). Su valor es **histórico/evolutivo**; la evidencia técnica de compiladores vive en P3.

## 2. Cumplimiento del enunciado

🟦 El enunciado P1 exige scanner + parser de la gramática del lenguaje con reporte de tokens y aceptación. La estructura lo implementa (`lexico.flex`, `sintactico.cup`, `Main.java`, `tokens.txt`, `test.txt`); no re-verificado por ejecución.

## 3. Hallazgos de higiene

| Hallazgo | Severidad | Nota |
|---|---|---|
| `parser.java` y `sym.java` **generados** trackeados en `src/main/java/` | Media | En P2/P3 se generan al build (correcto); aquí deberían salir de git y generarse vía Maven |
| Jars de CUP dentro de `src/main/java/` (en vez de `lib/`) | Baja | Mover a `src/lib/` como en P2/P3 |
| Scaffolding `.github/java-upgrade/` residual | Baja | Eliminar (mismo caso que P2) |
| Sin tests | Baja | La suite de P3 cubre el pipeline completo; invertir allí |

## 4. Evaluación profesional

- Nivel demostrado: **Junior** en esta pieza aislada (configuración de herramientas + gramática inicial).
- Valor para reclutador: mostrar los 3 repos juntos evidencia **evolución sostenida** en un dominio difícil — P1 (front-end) → P2 (+semántica/IR, smoke test) → P3 (+backend MIPS, 36 tests, CI, arquitectura modular). Esa narrativa vale más que el repo individual.

## 5. Recomendaciones

Ver `IMPROVEMENT_ROADMAP.md`. No invertir esfuerzo técnico aquí más allá de la higiene; enlazar P1→P2→P3 en los tres READMEs.
