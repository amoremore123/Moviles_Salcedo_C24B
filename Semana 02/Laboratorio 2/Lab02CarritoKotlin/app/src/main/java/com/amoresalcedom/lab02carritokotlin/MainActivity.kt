package com.amoresalcedom.lab02carritokotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amoresalcedom.lab02carritokotlin.ui.theme.Lab02CarritoKotlinTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab02CarritoKotlinTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    CarritoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun CarritoScreen(
    modifier: Modifier = Modifier
) {

    val carrito = remember {
        mutableStateListOf<Producto>()
    }

    var nombreCliente by remember {
        mutableStateOf("")
    }

    var nombreProducto by remember {
        mutableStateOf("")
    }

    var precio by remember {
        mutableStateOf("")
    }

    var cantidad by remember {
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    var compraRealizada by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Carrito de Compras",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Tienda TECSUP",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = nombreCliente,
            onValueChange = {
                nombreCliente = it
            },
            label = {
                Text("Nombre del cliente")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Agregar producto",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = nombreProducto,
            onValueChange = {
                nombreProducto = it
            },
            label = {
                Text("Nombre del producto")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = precio,
                onValueChange = {
                    precio = it
                },
                label = {
                    Text("Precio")
                },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it
                },
                label = {
                    Text("Cantidad")
                },
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {

                val precioProducto = precio.toDoubleOrNull()
                val cantidadProducto = cantidad.toIntOrNull()

                if (
                    nombreProducto.isNotBlank() &&
                    precioProducto != null &&
                    cantidadProducto != null &&
                    precioProducto > 0 &&
                    cantidadProducto > 0
                ) {

                    carrito.add(
                        Producto(
                            nombre = nombreProducto,
                            precio = precioProducto,
                            cantidad = cantidadProducto
                        )
                    )

                    mensaje = "Producto agregado correctamente"

                    nombreProducto = ""
                    precio = ""
                    cantidad = ""

                } else {

                    mensaje = "Complete correctamente los datos del producto"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar producto")
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje
            )
        }

        Text(
            text = "Cliente: ${
                if (nombreCliente.isBlank()) "Sin nombre"
                else nombreCliente
            }",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Productos en el carrito: ${carrito.size}"
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            items(carrito) { producto ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Precio: S/ ${
                                String.format(
                                    "%.2f",
                                    producto.precio
                                )
                            }"
                        )

                        Text(
                            text = "Cantidad: ${producto.cantidad}"
                        )

                        Text(
                            text = "Importe: S/ ${
                                String.format(
                                    "%.2f",
                                    producto.precio * producto.cantidad
                                )
                            }"
                        )

                        Button(
                            onClick = {
                                carrito.remove(producto)
                                mensaje = "Producto eliminado"
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }

        if (carrito.isNotEmpty()) {

            val subtotal = calcularSubtotal(carrito)
            val igv = calcularIGV(subtotal)
            val total = calcularTotal(subtotal, igv)
            val descuento = calcularDescuento(total)
            val totalFinal = total - descuento

            Text(
                text = "Subtotal: S/ ${
                    String.format("%.2f", subtotal)
                }"
            )

            Text(
                text = "IGV (18%): S/ ${
                    String.format("%.2f", igv)
                }"
            )

            if (descuento > 0) {
                Text(
                    text = "Descuento: S/ ${
                        String.format("%.2f", descuento)
                    }"
                )
            }

            Text(
                text = "TOTAL: S/ ${
                    String.format("%.2f", totalFinal)
                }",
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = {

                    if (nombreCliente.isBlank()) {
                        mensaje = "Ingrese el nombre del cliente antes de comprar"
                    } else {
                        compraRealizada = true
                        mensaje = "Compra realizada correctamente"
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("COMPRAR AHORA")
            }
        }

        if (compraRealizada) {

            Text(
                text = "¡Gracias por tu compra, $nombreCliente!",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tu pedido ha sido registrado correctamente."
            )
        }
    }
}