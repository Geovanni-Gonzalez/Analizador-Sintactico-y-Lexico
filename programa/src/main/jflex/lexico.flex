/* Definiciones de tokens basadas en la gramática */
"int"       { return symbol(sym.INT, "INT"); }
"float"     { return symbol(sym.FLOAT, "FLOAT"); }
"<-"        { return symbol(sym.ASSIGN, "ASSIGN"); }
"~"         { return symbol(sym.SEPARATOR, "SEPARATOR"); }
"!"         { return symbol(sym.END_EXPR, "END_EXPR"); }
"|:"        { return symbol(sym.L_BLOCK, "L_BLOCK"); }
":|"        { return symbol(sym.R_BLOCK, "R_BLOCK"); }


[0-9]+  {
    return new Symbol(sym.NUMBER,
        yyline + 1,   //Symbol.left
        yycolumn + 1, //Symbol.right
        Integer.parseInt(yytext())
    );
}

/* Regla "comodín" para capturar cualquier carácter no reconocido */
[^] { 
    // Reportar el error indicando la línea fundamental [cite: 210]
    System.err.println("Error Léxico: Carácter <" + yytext() + 
                       "> en Línea " + (yyline + 1));
    // Al no retornar nada, el scanner simplemente ignora el carácter
    // ilegal y continúa con el siguiente (Modo Pánico).
}