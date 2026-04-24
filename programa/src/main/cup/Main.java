package main.cup;
// Crear el archivo de salida solicitado
PrintWriter writer = new PrintWriter(new FileWriter("tokens_reporte.txt"));
Symbol token;
while ((token = lexer.next_token()).sym != sym.EOF) {
    String info = "Token: " + sym.terminalNames[token.sym] + 
                  " | Lexema: [" + token.value + "]";
    writer.println(info);
    // Indicar la tabla de símbolos (ej: IDs, Reservadas, Literales)
    writer.println("   -> Tabla: " + clasificarTabla(token.sym));
}
writer.close();