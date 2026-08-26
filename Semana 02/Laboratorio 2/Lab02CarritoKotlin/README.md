# Lab02 - Carrito de Compras en Kotlin

**Nombre:** Amore Salcedo  
**Curso:** Programación en Móviles — 4to Ciclo  
**Docente:** Juan José León Suiyon  

---

## Descripción

Programa de consola desarrollado en Kotlin que simula un carrito de compras.  
El usuario tiene una lista de productos con nombre, precio y cantidad.  
El programa calcula el subtotal, el IGV (18 %), el total a pagar y aplica un descuento automático del 5 % si el total supera S/ 3 000, o del 10 % si supera S/ 5 000.

### Funciones implementadas

| Función | Descripción |
|---|---|
| `calcularSubtotal` | Suma `precio × cantidad` de todos los productos |
| `calcularIGV` | Devuelve el 18 % del subtotal |
| `calcularTotal` | Suma subtotal + IGV |
| `calcularDescuento` | Aplica 5 % o 10 % según el monto total (usando `when`) |
| `mostrarDetalle` | Imprime el carrito con columnas alineadas y 2 decimales |
| `buscarProducto` | Busca un producto por nombre usando `find` (reto adicional) |
| Eliminar con `removeIf` | Elimina un producto del carrito y recalcula los totales (reto adicional) |

---

## Captura de la consola

```
=========================================
   CARRITO DE COMPRAS - TIENDA TECSUP   
=========================================
Cliente: Amore Salcedo

Producto agregado: Laptop HP
Producto agregado: Mouse Logitech
Producto agregado: Teclado Mecanico
Producto agregado: Monitor Samsung

--------- DETALLE DEL CARRITO ---------
1. Laptop HP             x1  S/  2500.00
2. Mouse Logitech        x2  S/    91.00
3. Teclado Mecanico      x1  S/   150.00
4. Monitor Samsung       x1  S/   899.90
---------------------------------------

Cantidad de productos: 4

Subtotal:                  S/  3640.90
IGV (18%):                 S/   655.36
TOTAL A PAGAR:             S/  4296.26

Producto mas caro: Laptop HP (S/ 2500.00)

Descuento aplicado (5%): - S/ 214.81
TOTAL CON DESCUENTO:       S/  4081.45
```

---

## val vs var — Pregunta de la Parte 2

**`val`** declara un valor **inmutable**: una vez asignado no puede cambiar.  
**`var`** declara una variable **mutable**: su valor puede reasignarse en cualquier momento.

En la `data class Producto`:

```kotlin
data class Producto(
    val nombre: String,   // no cambia: el nombre del producto es fijo
    val precio: Double,   // no cambia: el precio no se modifica en el carrito
    var cantidad: Int     // SÍ cambia: el usuario puede agregar o quitar unidades
)
```

Si intentaras escribir `producto.precio = 999.0` después de crear el objeto, el compilador lanzaría el error:
> **Val cannot be reassigned**

Usar `val` siempre que sea posible es una buena práctica: hace el código más predecible y seguro frente a modificaciones accidentales.

---

## Historial de commits

| # | Mensaje |
|---|---|
| 1 | Proyecto inicial con menú del carrito |
| 2 | Agrega data class Producto y funciones de subtotal, IGV y total |
| 3 | Agrega reporte de detalle con columnas alineadas |
| 4 | Agrega producto mas caro y descuento por monto con when |
