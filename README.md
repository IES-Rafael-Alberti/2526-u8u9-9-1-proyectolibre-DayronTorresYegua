[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/P3WX0DWh)
# Actividad: Proyecto Software en Kotlin con Persistencia

- **ID actividad:** `2526_PRO_u9_proyecto`
- **Agrupamiento:** Individual
- **Módulo:** Programación
- **Curso:** 1.º DAW
- **Lenguaje principal:** Kotlin

**Archivo de solución:** [`SOLUCION_2526_PRO_u9_proyecto.md`](SOLUCION_2526_PRO_u9_proyecto.md)

Esta actividad consiste en desarrollar un proyecto software completo en Kotlin, de temática libre, que integre diseño, implementación, pruebas, documentación, persistencia y defensa oral.

La aplicación debe ser pequeña pero seria: bien estructurada, explicada, ejecutable y con decisiones técnicas justificadas. No se busca una aplicación enorme, sino un proyecto coherente que demuestre dominio real de los contenidos del módulo.

## Archivo obligatorio de documentación

La documentación del proyecto y las respuestas a los criterios de evaluación deberán ir en:

```text
SOLUCION_2526_PRO_u9_proyecto.md
```

El README debe servir como enunciado y guía de entrega. El archivo `SOLUCION_2526_PRO_u9_proyecto.md` debe contener la explicación concreta del proyecto entregado, evidencias y enlaces al código relevante.

## Resultados de aprendizaje

Esta actividad trabaja especialmente:

| RA | Evidencia principal |
|----|---------------------|
| RA5 | Entrada/salida de información mediante ficheros. |
| RA8 | Persistencia con MongoDB u otros mecanismos NoSQL. |
| RA9 | Persistencia con bases de datos relacionales mediante SQL. |

También permite evidenciar programación estructurada, POO, colecciones, herencia, interfaces, genéricos, expresiones regulares, excepciones, pruebas, documentación, uso de IDE y control de versiones.

Para Entornos de Desarrollo también podrán evidenciarse KDoc/Dokka/Javadoc, refactorización, code smells, Git, IntelliJ IDEA, pruebas con Kotest/MockK, UML, kLint, depuración y logs.

## Temática del proyecto

La temática es libre, pero debe permitir trabajar de forma realista con entidades, validaciones, operaciones CRUD y tres mecanismos de persistencia:

1. Ficheros.
2. MongoDB.
3. Base de datos relacional.

Ejemplos válidos: biblioteca, tareas, incidencias, reservas, inventario, alumnado y calificaciones, agenda, seguimiento deportivo, colecciones, pedidos o control de gastos.

La temática debe incluir, como mínimo:

- Varias entidades del dominio.
- Una entidad principal.
- Alta, consulta, modificación y borrado.
- Validación de datos.
- Persistencia en ficheros.
- Persistencia en MongoDB.
- Persistencia en base de datos relacional.

Ejemplo: en una biblioteca pueden existir `Libro`, `Usuario`, `Préstamo` y `Categoría`; los libros podrían guardarse en SQL, el histórico en MongoDB y la configuración inicial en ficheros.

## Planificación inicial

Antes de programar, define en `SOLUCION_2526_PRO_u9_proyecto.md`:

- Nombre del proyecto.
- Problema que resuelve.
- Usuarios a los que va dirigido.
- Funcionalidades principales.
- Entidades principales.
- Información almacenada en ficheros.
- Información almacenada en MongoDB.
- Información almacenada en base de datos relacional.
- Estructura inicial prevista.

## Estructura recomendada

La estructura puede variar si está justificada, pero se recomienda separar responsabilidades:

```text
src/main/kotlin/
├── app/          -> punto de entrada
├── model/        -> clases del dominio
├── service/      -> lógica de negocio
├── repository/   -> acceso a ficheros, MongoDB y SQL
├── validator/    -> validaciones
├── exception/    -> excepciones propias
└── util/         -> utilidades
```

## Requisitos mínimos de programación y Entornos de Desarrollo

Estos requisitos salen directamente de las preguntas que hay que responder en `SOLUCION_2526_PRO_u9_proyecto.md`. Si una pregunta debe contestarse con evidencias y enlaces al código, entonces ese aspecto debe haberse diseñado, implementado o utilizado realmente en la aplicación.

### Requisitos mínimos de Programación

| Requisito | Qué debe demostrarse |
|-----------|----------------------|
| Diseño general | Temática definida, problema que resuelve, entidades principales, funcionalidades y estructura del código justificada. |
| POO | Clases propias, objetos, constructores, propiedades, métodos, visibilidad, encapsulación y `data class` cuando tenga sentido. |
| Colecciones | Uso justificado de `List`, `Set`, `Map` u otras colecciones de Kotlin. |
| Genéricos | Al menos una clase, interfaz o función genérica, preferiblemente útil para repositorios o servicios. |
| Herencia, interfaces o clases abstractas | Uso con sentido de abstracción, polimorfismo o contratos de comportamiento. |
| Expresiones regulares | Validación de email, DNI, teléfono, código, matrícula, ISBN, fecha, usuario u otro dato. |
| Excepciones | Gestión de errores de entrada, ficheros, MongoDB, SQL, búsquedas sin resultado y operaciones CRUD fallidas. |
| Buenas prácticas y SOLID | Al menos dos principios SOLID aplicados y explicados con ejemplos reales del código. |
| Librerías externas | Librerías necesarias declaradas, configuradas y usadas de forma justificada. |
| Pruebas y evidencias | Pruebas manuales o automatizadas, datos de prueba y evidencias de funcionamiento. |

No se valorará crear clases, jerarquías o abstracciones artificiales solo para marcar una casilla. El diseño debe mejorar el sistema, no decorarlo.

### Requisitos mínimos de Entornos de Desarrollo

| Requisito | Qué debe demostrarse |
|-----------|----------------------|
| Refactorización | Al menos una mejora real del código: extracción de funciones, reducción de duplicidad, simplificación de clases, mejora de nombres o reorganización de responsabilidades. |
| Código limpio | Código legible, nombres claros, funciones con responsabilidad concreta, ausencia de duplicidades graves y estructura mantenible. |
| Patrones de diseño | Uso justificado de al menos un patrón de diseño o patrón arquitectónico cuando resuelva un problema real del proyecto. |
| Documentación técnica | Documentación del proyecto, explicación de decisiones, instrucciones de ejecución y, cuando proceda, KDoc/Dokka/Javadoc o diagramas. |
| Control de versiones | Uso de Git con historial de commits descriptivos, repositorio accesible y, si se usan ramas o merges, explicación de cómo se han gestionado. |
| Evidencias enlazadas | Enlaces al repositorio, historial, documentación generada, fragmentos de código relevantes o capturas que demuestren el trabajo realizado. |

## Requisitos de persistencia

### Ficheros

La aplicación debe leer y escribir información en ficheros. Usos válidos:

- Carga de datos iniciales.
- Configuración.
- Exportación de informes.
- Logs.
- Importación o exportación en texto, CSV o JSON.

Debe explicarse qué fichero se usa, formato, contenido, clase responsable y comportamiento ante errores.

### MongoDB

MongoDB debe usarse como persistencia NoSQL para información menos estructurada o histórica: logs, eventos, auditoría, comentarios, valoraciones, mensajes, configuración flexible o datos complementarios.

Debe incluir conexión, al menos una colección, inserción, consulta, modificación o eliminación de documentos, y explicación del modelo de documentos.

### Base de datos relacional

La aplicación debe usar una base de datos relacional. Se recomienda H2 o SQLite para facilitar la corrección, salvo justificación de MySQL, MariaDB, PostgreSQL u otra.

Debe incluir:

- Script SQL de creación.
- Al menos dos tablas relacionadas como mínimo.
- Claves primarias y foráneas cuando proceda.
- Operaciones CRUD.
- Consultas parametrizadas.
- Capa DAO o Repository.
- Gestión correcta de conexión y cierre de recursos.

No se admiten consultas SQL construidas concatenando texto introducido por el usuario.

## Arquitectura, diseño y librerías

El proyecto debe separar modelo, lógica de negocio, acceso a ficheros, acceso a MongoDB, acceso SQL, validaciones e interfaz de usuario.

Además, debe explicar al menos dos principios SOLID aplicados, por ejemplo:

- **SRP:** una clase tiene una única responsabilidad.
- **OCP:** se puede ampliar el sistema sin modificar todo el código.
- **DIP:** los servicios dependen de interfaces, no de implementaciones concretas.

También deben documentarse las librerías externas usadas para MongoDB, SQL, JSON y pruebas: nombre, finalidad, configuración y lugar del código donde se utilizan.

## Pruebas y evidencias

Como mínimo, deben aportarse evidencias de:

- Ejecución correcta del programa.
- Pruebas manuales de funcionalidades principales.
- Lectura o escritura de ficheros.
- Inserción y consulta en MongoDB.
- Inserción, consulta, modificación y borrado en SQL.
- Errores controlados.
- Salidas de consola o capturas si son necesarias.
- Resto de cosas que se hayan implementado y que se quieran destacar.

Se valorará positivamente el uso de pruebas automatizadas con Kotest u otra herramienta similar.

## Entrega

El repositorio de GitHub debe contener:

- Código fuente completo.
- `README.md` como guía de la actividad.
- `SOLUCION_2526_PRO_u9_proyecto.md` con documentación, respuestas, evidencias y enlaces al código.
- Script SQL de creación de la base de datos relacional.
- Ficheros de ejemplo necesarios para probar la aplicación.
- Fichero de configuración de ejemplo, si procede.
- Instrucciones de instalación y ejecución.
- Evidencias de funcionamiento.

La entrega debe permitir que el profesor clone el repositorio, siga las instrucciones y ejecute el proyecto.

## Contenido mínimo del archivo de respuestas

`SOLUCION_2526_PRO_u9_proyecto.md` debe contener:

| Apartado | Contenido mínimo |
|----------|------------------|
| Descripción del proyecto | Título, problema, usuarios, funcionalidades y estructura. |
| Modelo de clases | Clases principales, relaciones y diagrama si procede. |
| Persistencia en ficheros | Ficheros, formato, datos, clases responsables y errores. |
| Persistencia en MongoDB | Base de datos, colecciones, documentos, operaciones y ejemplo. |
| Persistencia relacional | SGBD, tablas, relaciones, script SQL, CRUD y consultas parametrizadas. |
| Instalación y ejecución | Requisitos, configuración, comandos y datos de prueba. |
| Ejemplos de uso | Salidas de consola, capturas o evidencias generadas. |
| Problemas y soluciones | Dificultades técnicas reales y cómo se resolvieron. |
| Preguntas de evaluación | Respuestas claras, justificadas y con enlaces permanentes al código. |
| Autoevaluación | Reflexión sobre el trabajo realizado, dificultades, aprendizajes y mejoras. |
| Conclusiones | Qué se ha aprendido y qué se mejoraría. |

## Preguntas de evaluación

Las respuestas deben estar en `SOLUCION_2526_PRO_u9_proyecto.md`, no dispersas por el README.

### Programación

1. **Diseño general:** temática, problema, entidades, funcionalidades, organización del código y justificación de la estructura.
2. **Clases y objetos:** clases principales, propiedades, métodos, constructores, objetos instanciados, ejemplos y enlaces al código.
3. **Encapsulación y visibilidad:** propiedades públicas o privadas, métodos de modificación, validaciones y decisiones tomadas.
4. **Colecciones:** tipo de colección, información almacenada, motivo de elección y enlace al código.
5. **Genéricos:** clase, interfaz o función genérica, problema que resuelve, ventaja y enlace al código.
6. **Herencia, interfaces o clases abstractas:** participantes, relación, ventaja, polimorfismo si existe y enlace al código.
7. **Expresiones regulares:** dato validado, regex, ejemplo válido, ejemplo no válido, clase o función y enlace al código.
8. **Ficheros:** ficheros usados, lectura/escritura, formato, contenido, clases responsables, errores y enlace al código.
9. **MongoDB:** base de datos, colecciones, documentos, operaciones, clase responsable, ejemplo y enlace al código.
10. **Base de datos relacional:** SGBD, tablas, relaciones, script SQL, CRUD, conexión, cierre de recursos, consultas parametrizadas y enlace al código.
11. **Excepciones:** excepciones controladas, excepciones propias, comportamiento ante error, ejemplos y enlace al código.
12. **SOLID y buenas prácticas:** principios aplicados, clases donde aparecen, problema que evitan, mejora aportada y enlace al código.
13. **Librerías externas:** nombre, finalidad, configuración, uso en código y motivo.
14. **Pruebas y evidencias:** pruebas, datos, salidas, capturas si procede, ficheros generados, MongoDB y SQL.

### Entornos de Desarrollo

15. **Refactorización y código limpio:** partes refactorizadas, técnicas aplicadas, mejoras conseguidas, ejemplos y enlaces.
16. **Patrones de diseño:** patrón aplicado, ubicación, problema que resuelve, ventajas y enlace al código.
17. **Documentación:** herramientas, partes documentadas, formato, enlace a documentación generada y ejemplo.
18. **Control de versiones:** sistema usado, organización de commits, ramas, conflictos, enlace al repositorio y ejemplo de commit.

## Criterios generales de calificación

Se valorará especialmente:

- Que el proyecto compile y funcione.
- Que el diseño sea coherente y el código esté organizado.
- Que se apliquen correctamente los contenidos de programación.
- Que ficheros, MongoDB y SQL estén realmente implementados.
- Que existan operaciones CRUD y consultas parametrizadas.
- Que se gestionen errores.
- Que las respuestas estén justificadas con enlaces al código.
- Que haya buenas prácticas, patrones, código limpio, documentación y uso correcto de Git.

No se valorará positivamente:

- Código copiado sin entender.
- Funcionalidades no explicadas.
- Documentación incompleta.
- Repositorio desordenado.
- Código que no compila.
- Base de datos no documentada.
- Ficheros necesarios no incluidos.
- Ausencia de evidencias.
- Respuestas genéricas sin enlaces al código.

## Condiciones mínimas para evaluar

La práctica podrá considerarse no evaluable o incompleta si falta alguna condición mínima:

- Repositorio accesible.
- Proyecto compilable.
- Documentación completa en `SOLUCION_2526_PRO_u9_proyecto.md`.
- Acceso a ficheros.
- Acceso a MongoDB.
- Acceso a base de datos relacional.
- Evidencias de funcionamiento, del resto de cosas que se piden.
- Preguntas de evaluación respondidas.
- Enlaces al código relevante.

## Presentación y defensa

Durante la defensa, el alumno o alumna debe poder:

- Explicar la temática.
- Ejecutar la aplicación.
- Mostrar el código principal.
- Explicar el modelo de clases.
- Mostrar ficheros, MongoDB y SQL.
- Justificar decisiones de diseño.
- Responder preguntas sobre su propio código.

No basta con que el código exista: hay que demostrar que se entiende.

## Guía de evaluación

La calificación de cada criterio usará esta escala:

| Nota | Significado |
|------|-------------|
| 0 | No se ha cumplido el criterio. |
| 2.5 | Se ha cumplido de forma insatisfactoria. |
| 5 | Se ha cumplido de forma básica. |
| 7.5 | Se ha cumplido de forma satisfactoria. |
| 10 | Se ha cumplido de forma excelente. |

### Programación
- Completitud de requisitos mínimos.
- Acceso a ficheros.
- Integración de MongoDB.
- Base de datos relacional y operaciones CRUD.
- Preguntas de evaluación de Programación.

### Entornos de Desarrollo

- Refactorización y código limpio.
- Patrones de diseño.
- Documentación.
- Control de versiones.
- Preguntas de evaluación de Entornos de Desarrollo.

## Checklist final

Antes de entregar, comprueba:

- [ ] El proyecto compila y se ejecuta.
- [ ] Hay clases propias, objetos, colecciones, genéricos, regex y excepciones.
- [ ] Hay lectura o escritura de ficheros.
- [ ] Hay conexión con MongoDB.
- [ ] Hay conexión con base de datos relacional.
- [ ] Hay operaciones CRUD y script SQL.
- [ ] Hay instrucciones de ejecución.
- [ ] Hay evidencias de funcionamiento.
- [ ] `SOLUCION_2526_PRO_u9_proyecto.md` está completo.
- [ ] Las respuestas incluyen enlaces al código.
- [ ] El código está limpio y documentado.
- [ ] El historial de Git muestra el desarrollo del proyecto.
