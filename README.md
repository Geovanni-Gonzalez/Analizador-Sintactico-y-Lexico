# Analizador Sintáctico y Léxico

## Descripción
Proyecto Java/Maven para analizar un lenguaje mediante JFlex y CUP. Incluye gramática, archivo de prueba y documentación.

## Objetivo
Construir un analizador léxico-sintáctico como base para cursos de compiladores.

## Tecnologías utilizadas
- Java 11
- Maven
- JFlex
- CUP

## Funcionalidades principales
- Scanner desde léxico.flex
- Parser desde sintáctico.cup
- Archivo test.txt
- Manual y diseño

## Mi rol
Configuré el analizador, definí reglas léxicas/sintácticas y documenté el flujo.

## Aprendizajes clave
- Parsers con CUP
- JFlex en Maven
- Lectura de entradas
- Documentación técnica

## Instalación y ejecución
```bash
cd Analizador-Sintactico-y-Lexico/programa
mvn clean package
java -jar target/proyecto-compiladores-1.0-SNAPSHOT.jar test.txt
```
Si varia, revisar `Main.java` y el JAR en `target/`.

## Estructura del proyecto
- programa/pom.xml: build
- programa/src/main/jflex/: lexer
- programa/src/main/cup/: parser
- programa/src/main/java/: clases
- documentación/: manual

## Capturas o demo
Por documentar. Se recomienda agregar capturas de la pantalla principal o un GIF corto de uso.

## Estado del proyecto
Proyecto académico funcional con ejecución exacta por confirmar.

## Valor técnico demostrado
Evidencia dominio inicial de herramientas de compiladores.

## Mejoras futuras
- Normalizar paquetes
- Agregar pruebas
- Ampliar ejemplos

## Autor
Geovanni González  
Estudiante de Ingeniería en Computación  
GitHub: [Geovanni-Gonzalez](https://github.com/Geovanni-Gonzalez)










