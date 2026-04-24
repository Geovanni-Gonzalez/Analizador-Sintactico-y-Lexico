package main.java;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import cr.ac.itcr.Parser;
import cr.ac.itcr.sym;
import cr.ac.itcr.Lexer;      
import java_cup.runtime.Symbol;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // Verificar que se pasó un archivo como argumento
        if (args.length < 1) {
            System.err.println("Uso: java Main <archivo_fuente>");
            System.exit(1);
        }

        try {
            // Crear el lexer apuntando al archivo de entrada
            FileReader     fileReader = new FileReader(args[0]);
            Lexer          lexer      = new Lexer(fileReader);

            // Crear el archivo de reporte de tokens
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream("tokens_reporte.txt"), "UTF-8"));

            writer.println("=================================================");
            writer.println("          REPORTE DE TOKENS");
            writer.println("=================================================");

            Symbol token;
            while ((token = lexer.next_token()).sym != sym.EOF) {
                String info = "Token: " + sym.terminalNames[token.sym] +
                              " | Lexema: [" + token.value + "]" +
                              " | Línea: " + (token.left + 1) +
                              " | Columna: " + (token.right + 1);
                writer.println(info);
                writer.println("   -> Tabla: " + clasificarTabla(token.sym));
            }

            writer.println("=================================================");
            writer.println("Análisis léxico completado.");
            writer.close();

            System.out.println("Reporte generado en: tokens_reporte.txt");

            // También correr el parser completo desde el inicio
            FileReader fileReader2 = new FileReader(args[0]);
            Lexer      lexer2      = new Lexer(fileReader2);
            Parser     parser      = new Parser(lexer2);
            parser.parse();
            System.out.println("Análisis sintáctico completado sin errores fatales.");

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error durante el análisis: " + e.getMessage());
        }
    }

    /**
     * Clasifica un token en su tabla de símbolos correspondiente.
     */
    private static String clasificarTabla(int symId) {
        switch (symId) {
            // Palabras reservadas
            case sym.INT:
            case sym.FLOAT:
            case sym.BOOL:
            case sym.CHAR_TYPE:
            case sym.STRING:
            case sym.IF:
            case sym.ELSE:
            case sym.DO:
            case sym.WHILE:
            case sym.SWITCH:
            case sym.CASE:
            case sym.DEFAULT:
            case sym.RETURN:
            case sym.BREAK:
            case sym.CIN:
            case sym.COUT:
            case sym.EMPTY:
            case sym.MAIN:
            case sym.TRUE:
            case sym.FALSE:
            case sym.GREATHER_T:
            case sym.LESS_T:
            case sym.GREATHER_TE:
            case sym.LESS_TE:
            case sym.EQUAL_OP:
            case sym.N_EQUAL:
                return "Palabras Reservadas";

            // Identificadores
            case sym.ID:
                return "Identificadores";

            // Literales numéricos
            case sym.LIT_ENTERO:
            case sym.LIT_FLOTANTE:
            case sym.LIT_FRACCION:
            case sym.LIT_EXPONENTE:
                return "Literales Numéricos";

            // Literales de texto
            case sym.LIT_CHAR:
            case sym.LIT_STRING:
                return "Literales de Texto";

            // Operadores
            case sym.PLUS:
            case sym.MINUS:
            case sym.TIMES:
            case sym.DIV:
            case sym.MOD:
            case sym.POW:
            case sym.AND_LOG:
            case sym.OR_LOG:
            case sym.INC:
            case sym.DEC:
            case sym.DEREF:
            case sym.ASSIGN:
                return "Operadores";

            // Delimitadores / Símbolos especiales
            case sym.L_BLOCK:
            case sym.R_BLOCK:
            case sym.L_PAREN:
            case sym.R_PAREN:
            case sym.L_ARRAY_ACCESS:
            case sym.R_ARRAY_ACCESS:
            case sym.SEPARATOR:
            case sym.END_EXPR:
            case sym.COMMA:
                return "Delimitadores";

            default:
                return "Desconocido";
        }
    }
}