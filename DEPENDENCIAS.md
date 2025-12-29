# Dependencias del proyecto

Este documento describe los requisitos técnicos necesarios para **compilar y ejecutar** la aplicación **OnlineStore**.

---

## 1. Requisitos del sistema

La aplicación es **multiplataforma** y puede ejecutarse en los siguientes sistemas operativos:

- Windows  
- macOS  
- Linux  

---

## 2. Java

**Versión requerida:**
- Java JDK 17 o superior

**Distribuciones recomendadas:**
- OpenJDK  
- Microsoft Build of OpenJDK  

Para comprobar la versión instalada:
```
java -version
```

---

## 3. JavaFX

**Versión utilizada:**
- JavaFX SDK 21

JavaFX **no está incluido por defecto** en el JDK y debe descargarse y configurarse manualmente.

Para ejecutar la aplicación es necesario añadir los siguientes parámetros:
```
–module-path <ruta_javafx_lib>
–add-modules javafx.controls,javafx.fxml
```

---

## 4. Base de datos

**Sistema gestor:**
- MySQL 8.x

**Requisitos importantes:**
- La base de datos debe estar creada previamente
- El servicio MySQL debe estar iniciado antes de ejecutar la aplicación

**Parámetros habituales:**
- Host: `localhost`  
- Puerto: `3306`  

⚠️ **Si la base de datos no está activa, la aplicación no funcionará correctamente.**

---

## 5. Librerías externas

Incluidas en la carpeta `/lib` del proyecto:

- Hibernate Core 5.6.x  
- Hibernate Commons Annotations  
- javax.persistence-api  
- MySQL Connector/J 8.x  
- JAXB API  
- JAXB Runtime  
- Log4j  

No es necesario descargar estas librerías manualmente si el proyecto se clona completo.

---

## 6. Observaciones

- La base de datos debe estar activa antes de lanzar la aplicación.
- No se requieren herramientas adicionales para la ejecución.
- Las dependencias de desarrollo (IDE, editores visuales, etc.) no forman parte de este documento.

---

## Navegación

- [Volver al README principal del proyecto](README.md)
- [Volver al README de documentación en español](README_es.md)
