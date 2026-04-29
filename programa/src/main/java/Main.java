import java.io.FileReader;
import java.io.Reader;
 
public class Main {
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
 
            // 2. Crear lexer
            MiLexer lexer = new MiLexer(reader);
 
            // 3. Crear parser (CUP)
            parser p = new parser(lexer);
 
            // 4. Ejecutar análisis
            p.parse();
 
            // 5. Resultado final
            if (p.numErrores > 0) {
                System.err.println("\nAnálisis finalizado con " + p.numErrores + " error(es).");
            } else {
                System.out.println("Análisis completado sin errores.");
            }
 
        } catch (Exception e) {
            System.err.println("Error durante la ejecución:");
            e.printStackTrace();
        }
    }
}
 
