package carrito

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    var cantidad: Int
)

data class Cliente(
    val nombre: String,
    val email: String,
    val esMiembro: Boolean
)

fun calcularSubtotal(productos: List<Producto>): Double {
    return productos.sumOf { it.precio * it.cantidad }
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun calcularDescuento(total: Double, esMiembro: Boolean): Double {
    return when {
        total > 5000 && esMiembro -> total * 0.15
        total > 5000              -> total * 0.10
        total > 3000 && esMiembro -> total * 0.08
        total > 3000              -> total * 0.05
        esMiembro                 -> total * 0.03
        else                      -> 0.0
    }
}

fun calcularSubtotalPorCategoria(productos: List<Producto>): Map<String, Double> {
    val resultado = mutableMapOf<String, Double>()
    for (p in productos) {
        val acumulado = resultado.getOrDefault(p.categoria, 0.0)
        resultado[p.categoria] = acumulado + (p.precio * p.cantidad)
    }
    return resultado
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun productoMasCaro(productos: List<Producto>): Producto? {
    return productos.maxByOrNull { it.precio }
}

fun productoMasBarato(productos: List<Producto>): Producto? {
    return productos.minByOrNull { it.precio }
}

fun productosOrdenadosPorPrecio(productos: List<Producto>): List<Producto> {
    return productos.sortedByDescending { it.precio }
}

fun mostrarEncabezado() {
    println("==============================================")
    println("      TIENDA TECSUP - SISTEMA DE VENTAS      ")
    println("             Carrito de Compras               ")
    println("==============================================")
    println()
}

fun mostrarInfoCliente(cliente: Cliente) {
    println("----------------------------------------------")
    println(" DATOS DEL CLIENTE")
    println(" Nombre : ${cliente.nombre}")
    println(" Email  : ${cliente.email}")
    println(" Tipo   : ${if (cliente.esMiembro) "Miembro Premium" else "Cliente Regular"}")
    println("----------------------------------------------")
    println()
}

fun mostrarDetalle(productos: List<Producto>) {
    println("----------------------------------------------")
    println(" DETALLE DEL CARRITO")
    println(String.format(" %-4s %-22s %-13s %4s %10s", "N.", "Producto", "Categoria", "Cant", "Importe"))
    println(" " + "-".repeat(56))
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format(" %-4d %-22s %-13s %4d S/%8.2f", i, p.nombre, p.categoria, p.cantidad, importe))
        i++
    }
    println("----------------------------------------------")
}

fun mostrarResumenCategorias(categorias: Map<String, Double>) {
    println("----------------------------------------------")
    println(" RESUMEN POR CATEGORIA")
    for ((cat, total) in categorias) {
        println(String.format(" %-25s S/ %8.2f", cat, total))
    }
    println("----------------------------------------------")
    println()
}

fun mostrarTotales(subtotal: Double, igv: Double, total: Double, descuento: Double, totalFinal: Double, cliente: Cliente) {
    println("----------------------------------------------")
    println(" RESUMEN DE PAGO")
    println(String.format(" %-30s S/ %8.2f", "Subtotal:", subtotal))
    println(String.format(" %-30s S/ %8.2f", "IGV (18%):", igv))
    println(String.format(" %-30s S/ %8.2f", "Total antes de descuento:", total))
    if (descuento > 0) {
        val pct = when {
            total > 5000 && cliente.esMiembro -> 15
            total > 5000                      -> 10
            total > 3000 && cliente.esMiembro -> 8
            total > 3000                      -> 5
            cliente.esMiembro                 -> 3
            else                              -> 0
        }
        println(String.format(" %-30s-S/ %8.2f", "Descuento ($pct%):", descuento))
    } else {
        println(" Sin descuento aplicado")
    }
    println(" " + "-".repeat(42))
    println(String.format(" %-30s S/ %8.2f", "TOTAL A PAGAR:", totalFinal))
    println("----------------------------------------------")
    println()
}

fun mostrarEstadisticas(productos: List<Producto>) {
    val masCaro      = productoMasCaro(productos)
    val masBarato    = productoMasBarato(productos)
    val ordenados    = productosOrdenadosPorPrecio(productos)
    val totalUnidades = productos.sumOf { it.cantidad }

    println("----------------------------------------------")
    println(" ESTADISTICAS DEL CARRITO")
    println(" Productos distintos : ${productos.size}")
    println(" Total de unidades   : $totalUnidades")
    if (masCaro != null)
        println(" Producto mas caro   : ${masCaro.nombre} (S/ ${String.format("%.2f", masCaro.precio)})")
    if (masBarato != null)
        println(" Producto mas barato : ${masBarato.nombre} (S/ ${String.format("%.2f", masBarato.precio)})")
    println()
    println(" Ranking por precio (mayor a menor):")
    for ((idx, p) in ordenados.withIndex()) {
        println("  ${idx + 1}. ${p.nombre} - S/ ${String.format("%.2f", p.precio)}")
    }
    println("----------------------------------------------")
    println()
}

fun main() {
    mostrarEncabezado()

    val cliente = Cliente(
        nombre    = "Amore Salcedo",
        email     = "amore@tecsup.edu.pe",
        esMiembro = true
    )
    mostrarInfoCliente(cliente)

    val carrito = mutableListOf<Producto>()
    carrito.add(Producto(1, "Laptop HP Pavilion",  2500.00, "Computadoras",   1))
    carrito.add(Producto(2, "Mouse Logitech MX3",    89.90, "Perifericos",    2))
    carrito.add(Producto(3, "Teclado Mecanico HK",  210.00, "Perifericos",    1))
    carrito.add(Producto(4, "Monitor Samsung 24",   899.00, "Monitores",      1))
    carrito.add(Producto(5, "Webcam Logitech C920", 350.00, "Perifericos",    1))
    carrito.add(Producto(6, "SSD Kingston 1TB",     280.00, "Almacenamiento", 2))

    println(" Productos agregados al carrito:")
    for (p in carrito) {
        println("  + ${p.nombre} (x${p.cantidad})")
    }
    println()

    mostrarDetalle(carrito)
    println()

    val categorias = calcularSubtotalPorCategoria(carrito)
    mostrarResumenCategorias(categorias)

    val subtotal   = calcularSubtotal(carrito)
    val igv        = calcularIGV(subtotal)
    val total      = calcularTotal(subtotal, igv)
    val descuento  = calcularDescuento(total, cliente.esMiembro)
    val totalFinal = total - descuento

    mostrarTotales(subtotal, igv, total, descuento, totalFinal, cliente)
    mostrarEstadisticas(carrito)

    println(" Busqueda: 'Webcam Logitech C920'")
    val encontrado = buscarProducto(carrito, "Webcam Logitech C920")
    if (encontrado != null) {
        println(" Encontrado -> ${encontrado.nombre} | ${encontrado.categoria} | S/ ${String.format("%.2f", encontrado.precio)}")
    }
    println()

    carrito.removeIf { it.nombre == "Mouse Logitech MX3" }
    println(" 'Mouse Logitech MX3' eliminado del carrito.")
    println()

    mostrarDetalle(carrito)
    println()

    val sub2   = calcularSubtotal(carrito)
    val igv2   = calcularIGV(sub2)
    val tot2   = calcularTotal(sub2, igv2)
    val desc2  = calcularDescuento(tot2, cliente.esMiembro)
    val final2 = tot2 - desc2
    mostrarTotales(sub2, igv2, tot2, desc2, final2, cliente)

    println("==============================================")
    println("          GRACIAS POR SU COMPRA              ")
    println("        Tienda Tecsup - 2026                 ")
    println("==============================================")
}
