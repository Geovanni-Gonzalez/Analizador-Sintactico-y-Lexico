import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<Integer, String> symNames = new HashMap<>();

    static {
        for (Field f : sym.class.getFields()) {
            try {
                if (f.getType() == int.class) {
                    symNames.put(f.getInt(null), f.getName());
                }
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        try {
            // 1. Fuente de entrada
            Reader reader;
            if (args.length > 0) {
                reader = new FileReader(args[0]);
            } else {
                System.out.println("Leyendo desde teclado (Ctrl+D para terminar):");
                reader = new java.io.InputStreamReader(System.in);
            }

            // Leer todo el contenido a un String
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            String input = sb.toString();

            // -------------------------------------------------------------
            // FASE 1: Análisis Léxico y Generación de tokens.txt
            // -------------------------------------------------------------
            MiLexer lexerPass1 = new MiLexer(new StringReader(input));
            PrintWriter pw = new PrintWriter("tokens.txt", "UTF-8");
            pw.println("Tokens Encontrados:");
            pw.println("-------------------------------------------------------------------------------------------------------------------");
            pw.printf("%-20s | %-25s | %-20s | %s%n", "Token", "Lexema", "Tabla de Símbolos", "Información a Almacenar");
            pw.println("-------------------------------------------------------------------------------------------------------------------");

            java_cup.runtime.Symbol s;
            while ((s = lexerPass1.next_token()).sym != sym.EOF) {
                String tokenName = symNames.getOrDefault(s.sym, "UNKNOWN");
                String lexema = (s.value != null) ? s.value.toString() : "";
                
                // Si el lexema tiene saltos de línea limpiarlo para no romper la tabla
                lexema = lexema.replace("\n", "\\n").replace("\r", "");

                String tabla = "No aplica";
                String info = "Ninguna";

                if (s.sym == sym.ID) {
                    tabla = "Tabla de Símbolos";
                    info = "Nombre, Tipo, Ámbito, Dirección Memoria";
                } else if (tokenName.startsWith("LIT_") || s.sym == sym.TRUE || s.sym == sym.FALSE) {
                    tabla = "Tabla de Literales";
                    info = "Valor, Tipo";
                }

                pw.printf("%-20s | %-25s | %-20s | %s%n", tokenName, "'" + lexema + "'", tabla, info);
            }
            pw.close();
            System.out.println("Reporte de tokens generado en 'tokens.txt'.");

            // -------------------------------------------------------------
            // FASE 2: Análisis Sintáctico
            // -------------------------------------------------------------
            MiLexer lexerPass2 = new MiLexer(new StringReader(input));
            parser p = new parser(lexerPass2);
            p.parse();

            if (p.numErrores > 0) {
                System.err.println("\nEl archivo fuente no puede ser generado por la gramática.");
                System.err.println("Se encontraron " + p.numErrores + " error(es).");
            } else {
                System.out.println("El archivo fuente puede ser generado por la gramática.");
            }

        } catch (Exception e) {
            System.err.println("Error durante la ejecución:");
            e.printStackTrace();
        }
    }
}
