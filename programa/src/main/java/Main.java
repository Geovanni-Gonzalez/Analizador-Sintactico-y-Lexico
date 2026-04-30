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

            MiLexer lexer = new MiLexer(reader);
 
            parser p = new parser(lexer);
 
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
 

