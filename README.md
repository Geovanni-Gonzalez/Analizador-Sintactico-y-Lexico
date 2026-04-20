# Proyecto #1: Análisis Léxico y Sintáctico - Compiladores e Intérpretes

## 📋 Descripción del Proyecto
[cite_start]Este proyecto consiste en el desarrollo de las fases iniciales de un compilador para un nuevo lenguaje imperativo ligero, orientado a la configuración de chips en sistemas empotrados[cite: 10, 11]. [cite_start]El sistema realiza un análisis léxico y sintáctico completo basado en una gramática original descrita en el Anexo I[cite: 13, 26].

**Tecnologías utilizadas:**
* [cite_start]**Java:** Lenguaje base del proyecto[cite: 41].
* [cite_start]**JFlex:** Herramienta para la generación del scanner (análisis léxico)[cite: 29].
* [cite_start]**Cup:** Herramienta para la generación del parser (análisis sintáctico)[cite: 33].
* [cite_start]**GitHub:** Control de versiones y gestión de tareas mediante Kanban[cite: 44].

---

## 🏗️ Estructura del Entregable
[cite_start]De acuerdo con los requerimientos administrativos[cite: 73]:
* [cite_start]📂 **`programa/`**: Código fuente Java y archivos de especificación `.flex` y `.cup`[cite: 43].
* [cite_start]📂 **`documentacion/`**: Manual de usuario, diseño del programa y pruebas de funcionalidad[cite: 52, 54, 55, 57].
* [cite_start]📄 **`info.txt`**: Datos administrativos del curso, semestre y equipo[cite: 74, 75, 76, 77].

---

## 🚀 Instalación y Ejecución
> *Nota: Consulte el Manual de Usuario en la carpeta de documentación para detalles técnicos adicionales.*

1. [cite_start]**Prerrequisitos:** Contar con el JDK de Java y las librerías `jflex-full-1.x.x.jar` y `java-cup-11b.jar`[cite: 41, 42].
2. **Generar el Scanner:**
   ```bash
   jflex scanner.flex
3. **Generar el Parser:**
   ```bash
   java -jar java-cup-11b.jar -parser Parser -symbols Sym parser.cup

4. **Ejecutar el Compilador:**
   ```bash
   java -cp ".:lib/*" Main archivo_fuente.txt
