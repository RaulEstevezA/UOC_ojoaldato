package ojoaldato.modelo;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import java.math.BigDecimal;

import java.time.LocalDateTime;

public class CargarDatos {

    /**
     * Carga 2 clientes premium, 2 estándar, 3 artículos y 2 pedidos
     * usando los MISMOS flujos que la consola (controladores).
     */
    public static void CargarDatos(
            ClienteControlador clienteCtl,
            ArticuloControlador articuloCtl,
            PedidoControlador pedidoCtl,
            Datos datos
    ) {

        // === CLIENTES (mismo ctor que usa Consola: sin BigDecimal) ===
        Cliente c1 = new ClientePremium("Juan Pérez", "Calle Luna 10", "12345678A", "juan@correo.com");
        Cliente c2 = new ClientePremium("María López", "Av. Sol 5", "87654321B", "maria@correo.com");
        Cliente c3 = new ClienteEstandar("Luis García", "Calle Mar 8", "11111111C", "luis@correo.com");
        Cliente c4 = new ClienteEstandar("Ana Torres", "Calle Nube 2", "22222222D", "ana@correo.com");

        System.out.println(clienteCtl.addCliente(c1));
        System.out.println(clienteCtl.addCliente(c2));
        System.out.println(clienteCtl.addCliente(c3));
        System.out.println(clienteCtl.addCliente(c4));

        // === ARTÍCULOS (mismo ctor que usa Consola: codigo, desc, pvp, gastosEnvio, tiempoPrep) ===
        Articulo a1 = new Articulo(
                "A001", "Silla ergonómica",
                new BigDecimal("59.99"),
                new BigDecimal("4.90"),
                2
        );

        Articulo a2 = new Articulo(
                "A002", "Mesa de oficina",
                new BigDecimal("129.50"),
                new BigDecimal("9.90"),
                3
        );

        Articulo a3 = new Articulo(
                "A003", "Pantalla 24\"",
                new BigDecimal("149.90"),
                new BigDecimal("7.90"),
                1
        );

        System.out.println(articuloCtl.addArticulo(a1));
        System.out.println(articuloCtl.addArticulo(a2));
        System.out.println(articuloCtl.addArticulo(a3));

        // === PEDIDOS (idéntico a Consola: buscar cliente por email y artículo por código) ===
        // Pedido 1: numPedido, cliente, articulo, cantidad, fechaHora
        Pedido p1 = new Pedido(
                1001,
                clienteCtl.buscarCliente("juan@correo.com"),
                articuloCtl.buscarArticulo("A001"),
                2,
                LocalDateTime.now()
        );
        System.out.println(pedidoCtl.addPedido(p1.getCliente(), p1));

        // Pedido 2:
        Pedido p2 = new Pedido(
                1002,
                clienteCtl.buscarCliente("luis@correo.com"),
                articuloCtl.buscarArticulo("A003"),
                1,
                LocalDateTime.now()
        );
        System.out.println(pedidoCtl.addPedido(p2.getCliente(), p2));

        System.out.println("Datos de ejemplo cargados.");
    }
}
