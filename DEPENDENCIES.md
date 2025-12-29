# Project Dependencies

This document describes the technical requirements needed to **build and run** the **OnlineStore** application.

---

## 1. System Requirements

The application is **cross-platform** and can be executed on the following operating systems:

- Windows  
- macOS  
- Linux  

---

## 2. Java

**Required version:**
- Java JDK 17 or higher

**Recommended distributions:**
- OpenJDK  
- Microsoft Build of OpenJDK  

To check the installed version:
```
java -version
```

---

## 3. JavaFX

**Version used:**
- JavaFX SDK 21

JavaFX is **not included by default** in the JDK and must be downloaded and configured separately.

To run the application, the following parameters must be added:
```
–module-path <javafx_lib_path>
–add-modules javafx.controls,javafx.fxml
```

---

## 4. Database

**Database management system:**
- MySQL 8.x

**Important requirements:**
- The database must be created beforehand
- The MySQL service must be running before launching the application

**Common parameters:**
- Host: `localhost`  
- Port: `3306`  

⚠️ **If the database is not running, the application will not work correctly.**

---

## 5. External Libraries

Included in the project’s `/lib` directory:

- Hibernate Core 5.6.x  
- Hibernate Commons Annotations  
- javax.persistence-api  
- MySQL Connector/J 8.x  
- JAXB API  
- JAXB Runtime  
- Log4j  

There is no need to download these libraries manually if the project is cloned completely.

---

## 6. Notes

- The database must be running before starting the application.
- No additional tools are required for execution.
- Development dependencies (IDEs, visual editors, etc.) are not part of this document.

---

## Navigation

- [Back to the main project README](README.md)
- [Back to the Spanish documentation README](README_es.md)