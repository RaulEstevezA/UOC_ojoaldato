# OnlineStore — Documentación del proyecto

## 1. Introducción

**OnlineStore** es una aplicación de escritorio desarrollada en Java que simula el funcionamiento de una tienda online, permitiendo la gestión de clientes, artículos y pedidos.

El proyecto ha sido desarrollado por el grupo **OjoAlDato** y está orientado a la aplicación de buenas prácticas de programación y a la creación de una base sólida sobre la que construir un proyecto real, utilizando patrones de diseño y tecnologías ampliamente utilizadas en el sector.

---

## 2. Descripción general de la aplicación

La aplicación permite:

- Gestión de clientes (alta, consulta y clasificación)
- Gestión de artículos y stock
- Gestión de pedidos (creación, consulta, cancelación)
- Persistencia de datos en base de datos MySQL
- Interfaz gráfica de escritorio mediante JavaFX
- Ejecución alternativa en modo consola

El sistema está organizado siguiendo el patrón **MVC (Modelo–Vista–Controlador)**, separando claramente la lógica de negocio, la persistencia de datos y la presentación.

---

## 3. Tecnologías utilizadas

Las principales tecnologías empleadas en el desarrollo son:

- Java (JDK 17+)
- JavaFX (interfaz gráfica)
- MySQL (base de datos)
- Hibernate / JPA (ORM)
- JDBC
- JUnit (pruebas unitarias)
- Git / GitHub (control de versiones)

El detalle completo de dependencias puede consultarse en el archivo  
[`DEPENDENCIAS.md`](./DEPENDENCIAS.md).

---

## 4. Evolución del proyecto

El desarrollo de la aplicación se ha realizado de forma **iterativa**, siguiendo un proceso de mejora progresiva dividido en varias fases.

Cada fase ha supuesto una ampliación o mejora del sistema, permitiendo:

- Definir y consolidar el modelo de dominio
- Implementar la persistencia de datos
- Añadir lógica de negocio y controladores
- Incorporar una interfaz gráfica
- Refinar la experiencia de usuario y la estructura del código

Este enfoque ha permitido evolucionar la aplicación desde una versión inicial funcional hasta una solución completa y mantenible, con una arquitectura clara y fácilmente extensible.

---

## 5. Fases principales del desarrollo

### Fase 1 — Diseño UML

- Diagramas de casos de uso
- Diagramas de clases
- Definición del comportamiento del sistema
- Diseño del modelo estático

### Fase 2 — Implementación en Java

- Implementación del modelo de clases
- Uso de estructuras dinámicas de datos
- Aplicación del patrón MVC
- Pruebas unitarias con JUnit
- Control de versiones con Git

### Fase 3 — Persistencia en base de datos

- Implementación del patrón DAO
- Uso del patrón Factory
- Acceso a datos mediante JDBC
- Separación de la lógica de persistencia

### Fase 4 — Persistencia mediante ORM

- Integración de Hibernate
- Mapeo de entidades mediante JPA
- Mantenimiento del patrón MVC
- Simplificación del acceso a datos

### Fase 5 — Interfaz gráfica de escritorio

- Desarrollo de la interfaz con JavaFX
- Navegación entre vistas
- Formularios, tablas y validaciones
- Convivencia de interfaz gráfica y modo consola

---

## 6. Base de datos

El proyecto utiliza **MySQL** como sistema gestor de base de datos.

En la carpeta `resources/sql` se incluyen los siguientes scripts:

- `crear_tablas.sql`  
  Script para la creación de todas las tablas necesarias.

- `data.sql`  
  Inserción de datos de ejemplo para pruebas.

- `procedimientos_almacenados.sql`  
  Contiene procedimientos almacenados, como la actualización segura del stock de artículos, evitando valores negativos y validando la existencia del producto.

Estos scripts permiten reproducir el entorno completo de base de datos de forma controlada.

⚠️ Es imprescindible que la base de datos esté creada y el servicio MySQL iniciado antes de ejecutar la aplicación.

---

## 7. Configuración de la base de datos

El proyecto incluye un archivo de ejemplo:
```
db.properties.example
```

Este archivo debe renombrarse a:
```
db.properties
```

y configurarse con las credenciales correctas del sistema MySQL antes de ejecutar la aplicación.

---

## 8. Interfaz gráfica

La aplicación dispone de una interfaz gráfica de escritorio desarrollada con **JavaFX**, que permite gestionar la tienda de forma visual e intuitiva, manteniendo la lógica de negocio desacoplada mediante el patrón MVC.

---

### Menú principal

<p align="center">
  <img src="images/menu_principal.png" alt="Menú principal" width="600">
</p>

---

### Gestión de clientes

<p align="center">
  <img src="images/gestion_clientes.png" alt="Gestión de clientes" width="600">
</p>

---

### Añadir artículo

<p align="center">
  <img src="images/anadir_articulo.png" alt="Añadir artículo" width="600">
</p>

---

### Gestión de pedidos

<p align="center">
  <img src="images/gestion_pedidos.png" alt="Gestión de pedidos" width="600">
</p>

---

### Nuevo pedido

<p align="center">
  <img src="images/nuevo_pedido.png" alt="Nuevo pedido" width="600">
</p>

---

## 9. Licencia

El proyecto incluye un archivo [`LICENSE`](./LICENSE) que define los términos de uso y distribución del código.

---

## 10. Estado del proyecto

El proyecto se encuentra **funcional y estable**, con todas las funcionalidades principales implementadas.

Puede servir como:
- Proyecto académico avanzado
- Base para ampliaciones futuras
- Ejemplo de aplicación Java con arquitectura MVC y persistencia

---


## Volver al README principal

[Volver al README principal del proyecto](README.md)
