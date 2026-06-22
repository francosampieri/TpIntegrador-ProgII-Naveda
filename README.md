# Food Store – Sistema de Gestión de Pedidos

Trabajo Práctico Integrador de Programación 2 – UTN  
Tecnicatura Universitaria en Programación a Distancia

\---

## ¿De qué se trata?

Es un sistema de consola hecho en Java para gestionar un negocio de comidas. Permite crear y administrar categorías, productos, usuarios y pedidos, todo guardado en una base de datos MySQL.

No tiene interfaz gráfica ni login, todo se maneja desde el menú de la consola.

\---

## ¿Qué tecnologías usé?

* Java
* MySQL
* JDBC
* Maven

\---

## Estructura del proyecto

```
src/main/java/
Main/          → Main.java y MenuHandler (el menú de consola)

Entity/        → Las clases del modelo (Categoria, Producto, Usuario, Pedido, etc.)
DAO/           → Acceso a la base de datos con JDBC
Service/       → Lógica de negocio
Config/        → Conexión a la base de datos
```

\---

## Cómo ejecutarlo

### 1\. Crear la base de datos

Abrí MySQL y ejecutá:

Schema.sql



### 2\. Configurar la conexión

Abrí el archivo `src/main/java/Config/DatabaseConnection.java` y cambiá los datos según tu configuración de BD:

```
private static final String URL = "jdbc:mysql://localhost:3306/food\_store";
private static final String USER = "root";
private static final String PASSWORD = ""; // ponele tu contraseña acá
```

### 3\. Ejecutar el proyecto

Desde el IDE corré directamente la clase `Main.java`.

\---

## Funcionalidades

El menú principal tiene estas opciones:

```
=== FOOD STORE ===
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
```

Cada opción tiene su submenú con: listar, crear, editar y eliminar.

Las eliminaciones son lógicas (no se borra nada de la base, solo se marca como eliminado).

\---

## Links

* Documentación PDF: https://docs.google.com/document/d/1zo1ChFDLzEOYB0OPJs13FIGx3lnr1PpmwkNAReLpWzA/edit?usp=sharing

\---

