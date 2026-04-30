# Manual de Usuario - Analizador Léxico y Sintáctico

Este manual describe el funcionamiento, la instalación, y el uso del compilador diseñado para el lenguaje de configuración de chips en sistemas empotrados (Proyecto #1).

---

## 1. Requisitos Previos

Para poder compilar y ejecutar el proyecto, se requiere tener instalado:
* **Java Development Kit (JDK):** Versión 11 o superior.
* **Librerías de JFlex y CUP:** `jflex-full-1.x.x.jar` y `java-cup-11b.jar` (en caso de compilación manual) o alternativamente **Maven** si se desea compilar usando el archivo `pom.xml` incluido.

---

## 2. Compilación y Ejecución

El proyecto puede compilarse de dos maneras: manual o mediante Maven.

### Opción A: Compilación Manual (Según README)
1. **Generar el Scanner (Léxico):**
   ```bash
   jflex scanner.flex
   ```
2. **Generar el Parser (Sintáctico):**
   ```bash
   java -jar java-cup-11b.jar -parser Parser -symbols Sym parser.cup
   ```
3. **Ejecutar el Compilador:**
   ```bash
   java -cp ".:lib/*" Main archivo_fuente.txt
   ```

### Opción B: Uso de Maven (Recomendado)
El proyecto incluye un archivo `pom.xml` configurado para automatizar la generación y construcción:
1. **Compilar y empaquetar:**
   ```bash
   mvn clean package
   ```
2. **Ejecutar el programa generado:**
   ```bash
   java -jar target/proyecto-compiladores-1.0-SNAPSHOT.jar [archivo_fuente.txt]
   ```

> **Nota:** Si se ejecuta sin especificar un `archivo_fuente.txt`, el programa leerá la entrada directamente desde el teclado (presione `Ctrl+D` para terminar la entrada).

---

## 3. Estructura y Sintaxis del Lenguaje

El compilador procesa un lenguaje imperativo ligero con las siguientes características:

### 3.1. Tipos de Datos y Literales
* **Primitivos:** `int`, `float`, `bool`, `char`, `string`
* **Booleanos:** `true`, `false`
* **Formatos Numéricos Especiales:**
  * **Fracciones:** `entero/entero` (Ej: `3/4`)
  * **Exponenciales:** `enteroeentero` (Ej: `5e2`)
* **Caracteres y Cadenas:**
  * Char: `'a'`
  * String: `"texto"`

### 3.2. Operadores
* **Aritméticos:** `+`, `-`, `*`, `/`, `%` (módulo), `^` (potencia).
* **Lógicos:** `@` (AND), `#` (OR).
* **Relacionales (mediante funciones):**
  * `greather_t(a, b)`: Mayor que
  * `less_t(a, b)`: Menor que
  * `greather_te(a, b)`: Mayor o igual que
  * `less_te(a, b)`: Menor o igual que
  * `equal(a, b)`: Igual
  * `n_equal(a, b)`: Diferente

### 3.3. Símbolos Clave
* **Asignación:** `<-`
* **Separador de Declaraciones:** `~` (Ej: `int ~ var <- 5`)
* **Bloques de Código:** `|:` (Inicio) y `:|` (Fin)
* **Paréntesis:** `<|` y `|>` (Léxicamente definidos, pero en expresiones se permiten `(` y `)` según contexto, mapeados a L_PAREN/R_PAREN)
* **Arreglos:** `<<` y `>>` para acceder a índices (Ej: `matriz<<0, 1>>`).
* **Fin de Expresión/Sentencia:** `!`
* **Comas:** `,` para separar parámetros.

### 3.4. Estructuras de Control
* **Condicionales:**
  ```text
  if ( condicion ) |: sentencias :|
  if ( condicion ) |: sentencias :| else |: sentencias :|
  ```
* **Bucles (Do-While):**
  ```text
  do |: sentencias :| while ( condicion )
  ```
* **Switch-Case:**
  ```text
  switch ( variable ) |:
      case 1 ~ |: sentencias :|
      default ~ |: sentencias :|
  :|
  ```

### 3.5. Estructura de Funciones y Función Principal
* Toda función (incluyendo `main`) debe definir su tipo de retorno, un separador `~` y su nombre.
* La función principal obligatoria es `__main__` de tipo `empty`:
  ```text
  empty ~ __main__() |:
      ¡¡ Cuerpo de la función principal
  :|
  ```
* Definición de función genérica:
  ```text
  int ~ sumar(int ~ a, int ~ b) |:
      return ~ a + b !
  :|
  ```

### 3.6. Entrada y Salida (I/O)
* **Lectura:** `cin ~ variable !`
* **Escritura:** `cout ~ variable !` (o concatenando: `cout ~ var1, var2 !`)

### 3.7. Comentarios
* **Línea:** Iniciando con `¡¡` (Ej: `¡¡ Esto es un comentario`)
* **Bloque:** Encerrado entre `{-` y `-}` (Ej: `{- Comentario multilinea -}`)

---

## 4. Gestión de Errores

El compilador incluye rutinas de recuperación de errores (Panic Mode):
* **Léxicos:** Notifica el carácter no reconocido junto con su línea y continúa el escaneo.
* **Sintácticos:** 
  * Descarta tokens hasta encontrar delimitadores clave (como `!`, `:|` o de bloque).
  * Informa la línea, columna y el token inesperado.
  * Si el error es irremediable o de estructura mayor, indicará que el análisis no puede continuar.
Al finalizar la compilación, se reportará por consola el total de errores detectados.
