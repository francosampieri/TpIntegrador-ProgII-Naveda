package Main;

import Entity.Categoria;
import Entity.Estado;
import Entity.FormaPago;
import Entity.Pedido;
import Entity.Producto;
import Entity.Usuario;
import Service.CategoriaService;
import Service.PedidoService;
import Service.ProductoService;
import Service.UsuarioService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private final Scanner sc;
    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    
    public MenuHandler(Scanner sc, CategoriaService categoriaService, ProductoService productoService, UsuarioService usuarioService, PedidoService pedidoService) {
        this.sc = sc;
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }
    
    //METODOS
    
    public void listarCategorias() {        
        try {
            List<Categoria> categorias = categoriaService.listarAll();
            
            if (categorias.isEmpty()) { 
                System.out.println("No hay categorias registradas");
                return;
            }
            
            for(Categoria c : categorias) {
                imprimirCategoria(c);
            }
        } catch(Exception e) {
            System.out.println("Ocurrio un error al listar las categorias: " + e.getMessage());
        }
    }
    
    public void crearCategoria() {
        try {
            System.out.print("Ingresa el nombre de la nueva categoria: ");
            String nombre = sc.nextLine().trim();
            
            System.out.print("Ingresa la descripcion de la nueva categoria: ");
            String descripcion = sc.nextLine().trim();
            
            long id = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoria " + nombre + " creada correctamente con ID: " + id);
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al crear la categoria: " + e.getMessage());
        }
    }
    
    public void editarCategoria() {
        try {
            Categoria cat = seleccionarCategoriaById();
            
            System.out.println("Categoria Encontrada: ");
            imprimirCategoria(cat);
                       
            System.out.println("Desea editar el nombre?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo nombre de la categoria " + cat.getNombre() + ": ");
                String nombre = sc.nextLine().trim();
                categoriaService.editarNombre(cat.getId(), nombre);
                
                System.out.println("Categoria actualizada correctamente");
            }
            
            System.out.println("Desea editar la descripcion?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa la nueva descripcion de la categoria " + cat.getNombre() + ": ");
                String descripcion = sc.nextLine().trim();
                categoriaService.editarDescripcion(cat.getId(), descripcion);
                
                System.out.println("Categoria actualizada correctamente");
            }  
        } catch(Exception e) {
            System.out.println("Ocurrio un error al editar la categoria: " + e.getMessage());
        }        
    }
    
    public void eliminarCategoria() {
        try {
            Categoria cat = seleccionarCategoriaById();
            
            System.out.println("Quieres eliminar la categoria " + cat.getNombre() + "?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                categoriaService.eliminar(cat.getId());
                System.out.println("Categoria eliminada correctamente");
                
                List<Producto> productos = productoService.listarByCategoria(cat.getId());
                for (Producto p : productos) { eliminarProductosSinConsultar(p); }
            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al eliminar la categoria: " + e.getMessage());
        }
    }
   
    public void listarProductos() {
        try {
            List<Producto> productos = productoService.listarAll();
            
            if (productos.isEmpty()) { 
                System.out.println("No hay productos registrados");
                return;
            }
            
            for(Producto p : productos) {
                imprimirProducto(p);
            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al listar los productos: " + e.getMessage());
        }
    }
    
    public void crearProducto() {
        try {
            System.out.print("Ingresa el nombre del nuevo producto: ");
            String nombre = sc.nextLine().trim();
            
            System.out.print("Ingresa la descripcion del nuevo producto: ");
            String descripcion = sc.nextLine().trim();
            
            System.out.print("Ingresa el precio del nuevo producto: ");
            double precio = Double.parseDouble(sc.nextLine().trim());
            
            System.out.print("Ingresa el stock del nuevo producto: ");
            int stock = Integer.parseInt(sc.nextLine().trim());
            
            System.out.print("Ingresa la URL o nombre de imagen: ");
            String imagen = sc.nextLine().trim();
            
            System.out.println("El nuevo producto esta disponible?");
            boolean disponible = elegirSN().equals("S");
            
            System.out.println("Seleccione una categoria");
            long id_categoria = seleccionarCategoriaById().getId();
            
            long id = productoService.crear(nombre, descripcion, precio, stock, imagen, disponible, id_categoria);
            System.out.println("Producto " + nombre + " creado correctamente con ID: " + id);
                        
        } catch(Exception e) {
            System.out.println("Ocurrio un error al crear el producto: " + e.getMessage());
        }
    }
    
    public void editarProducto() {
        try {
            
            Producto prod = seleccionarProductoById();

            System.out.println("Producto Encontrado: ");
            imprimirProducto(prod);

            System.out.println("Desea editar el precio?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo precio del producto " + prod.getNombre() + ": ");
                double precio = Double.parseDouble(sc.nextLine().trim());

                productoService.editarPrecio(prod.getId(), precio);

                System.out.println("Producto actualizado correctamente");
            }

            System.out.println("Desea editar el stock?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo stock del producto " + prod.getNombre() + ": ");
                int stock = Integer.parseInt(sc.nextLine().trim());

                productoService.editarStock(prod.getId(), stock);

                System.out.println("Producto actualizado correctamente");
            }

            System.out.println("Desea editar la categoria?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.println("Seleccione la nueva categoria:");

                Categoria categoria = seleccionarCategoriaById();

                productoService.editarCategoria(
                    prod.getId(),
                    categoria.getId()
                );
                System.out.println("Producto actualizado correctamente");
            }

        } catch(Exception e) {
                System.out.println("Ocurrio un error al editar el producto: " + e.getMessage());
            }
    }
    
    public void eliminarProducto() {
        try {
            Producto producto = seleccionarProductoById();
            
            System.out.println("Quieres eliminar el producto " + producto.getNombre() + "?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                categoriaService.eliminar(producto.getId());
                System.out.println("Producto " + producto.getNombre() + " eliminado correctamente");

            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al eliminar el producto: " + e.getMessage());
        }
    }
    
    private void eliminarProductosSinConsultar(Producto producto) throws SQLException {
        categoriaService.eliminar(producto.getId());
        System.out.println("Producto " + producto.getNombre() + " eliminado correctamente");
    }
   
    public void listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarAll();
            
            if (usuarios.isEmpty()) { 
                System.out.println("No hay usuarios registrados");
                return;
            }
            
            for(Usuario u : usuarios) {
                imprimirUsuario(u);
            }
        } catch(Exception e) {
            System.out.println("Ocurrio un error al listar los usuarios: " + e.getMessage());
        }
    }
    
    public void crearUsuario() {
        try {
            System.out.print("Ingresa el nombre del nuevo usuario: ");
            String nombre = sc.nextLine().trim();
            
            System.out.print("Ingresa el apellido del nuevo usuario: ");
            String apellido = sc.nextLine().trim();
            
            System.out.print("Ingresa el mail del nuevo usuario: ");
            String mail = sc.nextLine().trim();
            
            System.out.print("Ingresa el celular del nuevo usuario: ");
            String celular = sc.nextLine().trim();
            
            System.out.println("Quieres cargar el nuevo usuario como eliminado?");
            boolean eliminado = elegirSN().equals("S");
            
            long id = usuarioService.crear(nombre, apellido, mail, celular, eliminado);
            System.out.println("Usuario " + nombre + " creado correctamente con ID: " + id);
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al crear el usuario: " + e.getMessage());
        }
    }
    
    public void editarUsuario() {
        try {            
            Usuario usuario = seleccionarUsuarioById();

            System.out.println("Usuario Encontrado: ");
            imprimirUsuario(usuario);

            System.out.println("Desea editar el nombre?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo nombre del usuario " + usuario.getNombre() + " " + usuario.getApellido() + ": ");
                String nombre = sc.nextLine().trim();

                usuarioService.editarNombre(usuario.getId(), nombre);

                System.out.println("Usuario actualizado correctamente");
            }

            System.out.println("Desea editar el apellido?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo apellido del usuario " + usuario.getNombre() + " " + usuario.getApellido() + ": ");
                String apellido = sc.nextLine().trim();

                usuarioService.editarApellido(usuario.getId(), apellido);

                System.out.println("Usuario actualizado correctamente");
            }

            System.out.println("Desea editar el mail?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo mail del usuario " + usuario.getNombre() + " " + usuario.getApellido() + ": ");
                String mail = sc.nextLine().trim();

                usuarioService.editarMail(usuario.getId(), mail);

                System.out.println("Usuario actualizado correctamente");
            }

            System.out.println("Desea editar el celular?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                System.out.print("Ingresa el nuevo celular del usuario " + usuario.getNombre() + " " + usuario.getApellido() + ": ");
                String celular = sc.nextLine().trim();

                usuarioService.editarCelular(usuario.getId(), celular);

                System.out.println("Usuario actualizado correctamente");
            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al editar el usuario: " + e.getMessage());
        }
    }
    
    public void eliminarUsuario() {
        try {
            Usuario usuario = seleccionarUsuarioById();
            
            System.out.println("Quieres eliminar el usuario " + usuario.getNombre() + " " + usuario.getApellido() + "?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                usuarioService.eliminar(usuario.getId());
                System.out.println("Usuario " + usuario.getNombre() + " " + usuario.getApellido() + " eliminado correctamente");

            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al eliminar el usuario: " + e.getMessage());
        }
    }
    
    public void listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.listarAll();
            
            if (pedidos.isEmpty()) { 
                System.out.println("No hay pedidos registradas");
                return;
            }
            
            for(Pedido p : pedidos) {
                imprimirPedido(p);
            }
        } catch(Exception e) {
            System.out.println("Ocurrio un error al listar los pedidos: " + e.getMessage());
        }
    }
    
    public void crearPedido() {
        try {
            Usuario usuario = seleccionarUsuarioById();
            
            Estado estado = elegirEstado();
            
            FormaPago formaPago = elegirFormaPago();
            
            System.out.println("Quieres cargar el nuevo pedido como eliminado?");
            boolean eliminado = elegirSN().equals("S");
            
            long id = pedidoService.crear(usuario, estado, formaPago, eliminado);
            System.out.println("Pedido creado correctamente con ID: " + id);
            
            System.out.println("Quieres agregar"); gr
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al crear el pedido: " + e.getMessage());
        }
    }
    
    public void editarPedido() {
        try {            
            Pedido pedido = seleccionarPedidoById();

            System.out.println("Pedido Encontrado: ");
            imprimirPedido(pedido);

            System.out.println("Desea editar el estado?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                pedidoService.editarEstado(pedido.getId(), elegirEstado());
                System.out.println("Pedido actualizado correctamente");
            } 

            System.out.println("Desea editar la Forma de Pago?");
            seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                pedidoService.editarFormaPago(pedido.getId(), elegirFormaPago());
                System.out.println("Pedido actualizado correctamente");
            } 
        } catch(Exception e) {
            System.out.println("Ocurrio un error al editar el pedido: " + e.getMessage());
        }
    }
    
    public void eliminarPedido() {
        try {
            Pedido pedido = seleccionarPedidoById();
            
            System.out.println("Quieres eliminar el pedido?");
            String seleccion = elegirSN();
            if (seleccion.equalsIgnoreCase("S")) {
                pedidoService.eliminar(pedido.getId());
                System.out.println("Pedido eliminado correctamente");

            }
            
        } catch(Exception e) {
            System.out.println("Ocurrio un error al eliminar el pedido: " + e.getMessage());
        }
    }
    
    private void imprimirCategoria(Categoria categoria) {
        System.out.println("ID: " + categoria.getId());
        System.out.println("Nombre: " + categoria.getNombre());
        System.out.println("Descripcion: " + categoria.getDescripcion());
        System.out.println("----------------------------------");
    }
    
    private void imprimirProducto(Producto producto) {
        System.out.println("ID: " + producto.getId());
        System.out.println("Nombre: " + producto.getNombre());
        System.out.println("Precio: " + producto.getPrecio());
        System.out.println("Descripcion: " + producto.getDescripcion());
        System.out.println("Stock: " + producto.getStock());
        System.out.println("Categoria: " + producto.getCategoria().getId());
        System.out.println("----------------------------------");
    }
    
    private void imprimirUsuario(Usuario usuario) {
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Apellido: " + usuario.getApellido());
        System.out.println("Mail: " + usuario.getMail());
        System.out.println("----------------------------------");
    }
    
    private void imprimirPedido(Pedido pedido) {
        System.out.println("ID: " + pedido.getId());
        System.out.println("Nombre Usuario: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido());
        System.out.println("Estado: " + pedido.getEstado().name());
        System.out.println("Forma de Pago: " + pedido.getFormaPago().name());
        System.out.println("Total: " + pedido.getTotal());
        System.out.println("Fecha: " + pedido.getCreatedAt());
        System.out.println("----------------------------------");
    }
    
    private Categoria seleccionarCategoriaById() throws SQLException {
        listarCategorias();
        
        System.out.print("Ingresa el id de la categoria: ");
        long id = Long.parseLong(sc.nextLine());        
        return categoriaService.listarById(id);            
    }
    
    private Producto seleccionarProductoById() throws SQLException {
        listarProductos();
        
        System.out.print("Ingresa el id del producto: ");
        long id = Long.parseLong(sc.nextLine());

        return productoService.listarById(id);
    }
    
    private Usuario seleccionarUsuarioById() throws SQLException {
        listarUsuarios();
        
        System.out.print("Ingresa el id del usuario: ");
        long id = Long.parseLong(sc.nextLine());

        return usuarioService.listarById(id);
    }
    
    private Pedido seleccionarPedidoById() throws SQLException {
        listarPedidos();
        
        System.out.print("Ingresa el id del pedido: ");
        long id = Long.parseLong(sc.nextLine());

        return pedidoService.listarById(id);
    }
    
    private String elegirSN() {
        System.out.print("[S/N]: ");
        String opcion = sc.nextLine().trim().toUpperCase();
        while (!opcion.equals("S") && !opcion.equals("N")){
            System.out.println("Error. Ingresa un valor valido entre [S] o [N]");
            System.out.print("[S/N]: ");
            opcion = sc.nextLine().trim().toUpperCase();
        }
        return opcion;
    }
    
    private Estado elegirEstado() {
        int eleccion = 0;
        boolean valido = false;

        while (!valido) {
            System.out.println("--Estados--");
            System.out.println("[1] - Pendiente\n[2] - Confirmado\n[3] - Terminado\n[4] - Cancelado");
            System.out.print("Selecciona un estado [1-4]: ");

            eleccion = Integer.parseInt(sc.nextLine());

            if (eleccion >= 1 && eleccion <= 4) {
                valido = true;
            } else {
                System.out.println("Opción fuera de rango. Intente nuevamente.\n");
            }
        }        
        return Estado.values()[eleccion-1];
    }
    
    private FormaPago elegirFormaPago() {
        int eleccion = 0;
        boolean valido = false;

        while (!valido) {
            System.out.println("--Formas de Pago--");
            System.out.println("[1] - Tarjeta\n[2] - Transferencia\n[3] - Efectivo");
            System.out.print("Selecciona un estado [1-3]: ");
 
            eleccion = Integer.parseInt(sc.nextLine());

            if (eleccion >= 1 && eleccion <= 3) {
                valido = true;
            } else {
                System.out.println("Opción fuera de rango. Intente nuevamente.\n");
            }
        }        
        return FormaPago.values()[eleccion-1];
    }
    
}
