/* Definiciones de tokens basadas en la gramática */
"int"       { return symbol(sym.INT, "INT"); }
"float"     { return symbol(sym.FLOAT, "FLOAT"); }
"<-"        { return symbol(sym.ASSIGN, "ASSIGN"); }
"~"         { return symbol(sym.SEPARATOR, "SEPARATOR"); }
"!"         { return symbol(sym.END_EXPR, "END_EXPR"); }
"|:"        { return symbol(sym.L_BLOCK, "L_BLOCK"); }
":|"        { return symbol(sym.R_BLOCK, "R_BLOCK"); }