# Diseño del Sistema: Análisis Léxico

Este documento detalla el diseño, la estructura y el funcionamiento de la fase de análisis léxico del compilador del proyecto. El objetivo de esta fase es leer el código fuente como un flujo de caracteres, agruparlos en secuencias lógicas con significado y generar una secuencia de componentes léxicos (tokens) para ser procesados posteriormente por el analizador sintáctico.

---

## 1. Herramientas Utilizadas

* **JFlex (Versión 1.9.1):** Es un generador de analizadores léxicos (scanners) para Java. JFlex toma como entrada un archivo de especificación (`.flex`) que contiene expresiones regulares y genera una clase en Java (`MiLexer.java`) capaz de identificar los tokens definidos.
* **Java CUP:** Aunque se encarga del análisis sintáctico, la clase generada por JFlex interactúa directamente con CUP devolviendo objetos de la clase `java_cup.runtime.Symbol`.

---

## 2. Estructura de la Especificación Léxica (`lexico.flex`)

El archivo de configuración de JFlex se divide en tres secciones principales:

### 2.1. Código de Usuario y Paquetes
En la parte superior, se importa el paquete necesario de CUP para la creación de los símbolos.
```java
import java_cup.runtime.*;
```

### 2.2. Declaraciones y Opciones de JFlex
Se definen directivas clave para configurar el escáner generado:
* `%class MiLexer`: Nombra a la clase generada como `MiLexer`.
* `%unicode`: Permite el soporte completo para caracteres Unicode.
* `%cup`: Habilita la compatibilidad con el analizador sintáctico generado por Java CUP.
* `%line` y `%column`: Instruye al analizador a llevar un seguimiento automático de la línea y la columna actuales (empezando desde 0), lo que resulta vital para el reporte de errores.

Adicionalmente, se insertan métodos auxiliares en el código generado mediante el bloque `%{ ... %}`. Se definieron funciones sobrecargadas `symbol(...)` para facilitar el retorno de tokens hacia CUP, encapsulando el tipo de token, la línea (ajustada a base 1), la columna (ajustada a base 1) y su valor léxico real si aplica.

### 2.3. Macros (Expresiones Regulares Base)
Para simplificar la escritura de las reglas, se definieron macros comunes que representan las bases léxicas del lenguaje:

* **Básicos:**
  * `DIGITO`: `[0-9]`
  * `LETRA`: `[a-zA-Z]`
* **Identificadores (`ID`):** Empiezan con una letra, seguida de cualquier combinación de letras, dígitos o guiones bajos `_`.
  * `{LETRA}({LETRA}|{DIGITO}|"_")*`
* **Numéricos:**
  * `ENTERO`: Un cero aislado o números que no empiezan con cero `0|[1-9][0-9]*`.
  * `FLOTANTE`: Consiste en un entero seguido de un punto y uno o más dígitos `{ENTERO}"."{DIGITO}+`.
  * `FRACCION`: Expresada como `{ENTERO}"/"{ENTERO}` (ej. `3/4`).
  * `EXPONENTE`: Expresada como `{ENTERO}"e"{ENTERO}` (ej. `2e4`).
* **Textuales:**
  * `CHAR`: Carácter individual encerrado entre comillas simples `\'([a-zA-Z0-9_])\'`.
  * `STRING`: Cadena de texto encerrada entre comillas dobles, que excluye saltos de línea internos `\"([^\"\n]*)\"`.

---

## 3. Tokens Identificados y Expresiones de Reconocimiento

El analizador empareja el flujo de entrada contra patrones específicos. Dependiendo del patrón emparejado, se retorna un identificador de terminal predefinido por el parser (clase `sym`).

### 3.1. Palabras Reservadas (Keywords)
Se reconocen directamente por su secuencia de caracteres exacta. Estas palabras no pueden ser utilizadas como identificadores.
* **Tipos de datos:** `int`, `float`, `bool`, `char`, `string`, `empty`
* **Valores Booleanos:** `true`, `false` (Retornan el terminal y además su valor de tipo `Boolean`).
* **Control de flujo:** `if`, `else`, `do`, `while`, `switch`, `case`, `default`, `break`, `return`
* **Entrada/Salida:** `cin`, `cout`
* **Función Principal:** `__main__`

### 3.2. Símbolos Especiales y Operadores
Estos símbolos dictan la estructura del código y ejecutan operaciones:
* **Estructurales:**
  * Asignación: `<-`
  * Separador de sentencia: `~`
  * Delimitadores de bloque: `|:` y `:|`
  * Delimitadores de paréntesis: `<|` y `|>`
  * Delimitadores de arreglos: `<<` y `>>`
  * Fin de expresión: `!`
  * Separador de lista: `,`
* **Aritméticos:** `+` (Suma), `-` (Resta), `*` (Multiplicación), `/` (División), `%` (Módulo), `^` (Potencia).
* **Lógicos:** `@` (AND lógico), `#` (OR lógico).
* **Relacionales:** Se evalúan como palabras reservadas en formato de función: `greather_t` (>), `less_t` (<), `greather_te` (>=), `less_te` (<=), `equal` (==), `n_equal` (!=).

### 3.3. Manejo de Literales
Para los literales que poseen un valor semántico asociado (además de su propio tipo de token), se parsea o captura el valor exacto introducido en el código fuente:
* `LIT_FLOTANTE`: Convierte la cadena emparejada a un tipo `Double` de Java.
* `LIT_ENTERO`: Convierte la cadena emparejada a un tipo `Integer` de Java.
* `LIT_CHAR`: Captura el carácter exacto de la cadena ignorando las comillas simples.
* `LIT_STRING`, `LIT_FRACCION`, `LIT_EXPONENTE`: Retornan el texto en bruto recuperado por `yytext()`.

### 3.4. Identificadores (Variables y Nombres de Funciones)
Se recogen mediante la macro `{ID}`. Si una palabra en el código no es una palabra reservada pero cumple con la regla de construcción del identificador, se cataloga como un terminal `sym.ID` y se guarda el nombre específico recuperado con `yytext()`.

---

## 4. Ignorados (Blancos y Comentarios)

El análisis léxico también se encarga de limpiar el código de caracteres no funcionales para la gramática:
* **Espacios en blanco:** Los espacios ` `, tabulaciones `\t`, retornos de carro `\r` y saltos de línea `\n` son simplemente ignorados por la regla `[ \t\r\n]+ { /* ignorar */ }`.
* **Comentarios de una sola línea:** Todos los caracteres que sigan a `¡¡` hasta el final de esa línea son omitidos: `"¡¡".*`.
* **Comentarios multi-línea:** El texto encapsulado entre `{-` y `-}` es totalmente omitido usando la expresión `"{-"([^])*"-}"`.

---

## 5. Gestión y Reporte de Errores Léxicos

El analizador está dotado de un mecanismo "atrapa-todo" como última regla de emparejamiento. Si el carácter en evaluación (indicado por un punto `.`) no coincide con ninguna de las reglas válidas estipuladas anteriormente, se dispara la regla de error.

```java
. {
    System.err.println("Error léxico: '" + yytext() +
                       "' en línea " + (yyline + 1));
}
```

Este acercamiento permite que, aunque se encuentre un símbolo inválido o un error ortográfico en el código, el analizador léxico no interrumpa de inmediato su ejecución total. Reporta el error por salida estándar de error (`System.err`) junto con su ubicación exacta, y luego descarta dicho carácter, procediendo con el siguiente token en el flujo de entrada (modo *Panic Mode Recovery* a nivel de caracteres).
