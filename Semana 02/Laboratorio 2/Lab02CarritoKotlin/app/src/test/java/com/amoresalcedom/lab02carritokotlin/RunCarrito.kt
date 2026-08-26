package com.amoresalcedom.lab02carritokotlin

import org.junit.Test

class RunCarrito {

    @Test
    fun correrCarrito() {
        println("=========================================")
        println("   CARRITO DE COMPRAS - TIENDA TECSUP   ")
        println("=========================================")

        val nombreCliente = "Amore Salcedo"
        val carrito = mutableListOf<Producto>()
        println("Cliente: $nombreCliente")
        println()

        carrito.add(Producto("Laptop HP",        2500.00, 1))
        carrito.add(Producto("Mouse Logitech",     45.50, 2))
        carrito.add(Producto("Teclado Mecanico",  150.00, 1))
        carrito.add(Producto("Monitor Samsung",   899.90, 1))

        for (producto in carrito) {
            println("Producto agregado: ${producto.nombre}")
        }
        println()

        mostrarDetalle(carrito)
        println()
        println("Cantidad de productos: ${carrito.size}")
        println()

        val subtotal = calcularSubtotal(carrito)
        val igv      = calcularIGV(subtotal)
        val total    = calcularTotal(subtotal, igv)

        println(String.format("%-26s S/ %8.2f", "Subtotal:",      subtotal))
        println(String.format("%-26s S/ %8.2f", "IGV (18%):",     igv))
        println(String.format("%-26s S/ %8.2f", "TOTAL A PAGAR:", total))
        println()

        val masCaro = carrito.maxByOrNull { it.precio }
        if (masCaro != null) {
            println("Producto mas caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio))
        }
        println()

        val descuento  = calcularDescuento(total)
        val totalFinal = total - descuento

        if (descuento > 0) {
            val porcentaje = if (total > 5000) 10 else 5
            println("Descuento aplicado ($porcentaje%): " +
                    String.format("- S/ %.2f", descuento))
        } else {
            println("Sin descuento (total no supera S/ 3000)")
        }
        println(String.format("%-26s S/ %8.2f", "TOTAL CON DESCUENTO:", totalFinal))
        println()

        println("=========================================")
        println(" RETO: BUSQUEDA Y ELIMINACION")
        println("=========================================")

        val encontrado = buscarProducto(carrito, "Mouse Logitech")
        if (encontrado != null) {
            println("Producto encontrado: ${encontrado.nombre} " +
                    String.format("(S/ %.2f)", encontrado.precio))
        } else {
            println("Producto no encontrado.")
        }

        carrito.removeIf { it.nombre == "Mouse Logitech" }
        println("Mouse Logitech eliminado del carrito.")
        println()
        println("Carrito actualizado:")
        mostrarDetalle(carrito)
        println()

        val subtotal2  = calcularSubtotal(carrito)
        val igv2       = calcularIGV(subtotal2)
        val total2     = calcularTotal(subtotal2, igv2)
        val descuento2 = calcularDescuento(total2)
        val totalFinal2 = total2 - descuento2

        println(String.format("%-26s S/ %8.2f", "Subtotal:",          subtotal2))
        println(String.format("%-26s S/ %8.2f", "IGV (18%):",         igv2))
        println(String.format("%-26s S/ %8.2f", "TOTAL A PAGAR:",     total2))
        if (descuento2 > 0) {
            println(String.format("%-26s S/ %8.2f", "Descuento:", descuento2))
        }
        println(String.format("%-26s S/ %8.2f", "TOTAL CON DESCUENTO:", totalFinal2))
        println()
        println("=========================================")
        println("          FIN DEL PROGRAMA               ")
        println("=========================================")
    }
}
