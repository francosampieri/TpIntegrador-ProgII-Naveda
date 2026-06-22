package Main;

import DAO.CategoriaDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.UsuarioDAO;
import Service.CategoriaService;
import Service.PedidoService;
import Service.ProductoService;
import Service.UsuarioService;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static CategoriaDAO categoriaDAO = new CategoriaDAO();
    public static PedidoDAO pedidoDAO = new PedidoDAO();
    public static ProductoDAO productoDAO = new ProductoDAO();
    public static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static CategoriaService categoriaService = new CategoriaService(categoriaDAO);
    public static ProductoService productoService = new ProductoService(productoDAO, categoriaService);
    public static UsuarioService usuarioService = new UsuarioService(usuarioDAO);
    public static PedidoService pedidoService = new PedidoService(pedidoDAO);

    public static MenuHandler menuHandler = new MenuHandler(sc, categoriaService, productoService, usuarioService, pedidoService);

    public static void main(String[] args) {
        iniciarMenu();
        
    }
    
    public static void iniciarMenu() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
        System.out.println("1. Categorias");
        System.out.println("2. Productos");
        System.out.println("3. Usuarios");
        System.out.println("4. Pedidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción fuera de rango.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
        }
    }
}

    private static void menuCategorias() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n--- CATEGORIAS ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> menuHandler.listarCategorias();
                case 2 -> menuHandler.crearCategoria();
                case 3 -> menuHandler.editarCategoria();
                case 4 -> menuHandler.eliminarCategoria();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion fuera de rango.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
        }
    }
}
    
    private static void menuProductos() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n--- PRODUCTOS ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> menuHandler.listarProductos();
                case 2 -> menuHandler.crearProducto();
                case 3 -> menuHandler.editarProducto();
                case 4 -> menuHandler.eliminarProducto();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion fuera de rango.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
        }
    }
}
    
    private static void menuUsuarios() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n--- USUARIOS ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> menuHandler.listarUsuarios();
                case 2 -> menuHandler.crearUsuario();
                case 3 -> menuHandler.editarUsuario();
                case 4 -> menuHandler.eliminarUsuario();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion fuera de rango.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
        }
    }
}
    
    private static void menuPedidos() {

    int opcion = -1;

    while (opcion != 0) {

        System.out.println("\n--- PEDIDOS ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> menuHandler.listarPedidos();
                case 2 -> menuHandler.crearPedido();
                case 3 -> menuHandler.editarPedido();
                case 4 -> menuHandler.eliminarPedido();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion fuera de rango.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
        }
    }
}
    
}   

