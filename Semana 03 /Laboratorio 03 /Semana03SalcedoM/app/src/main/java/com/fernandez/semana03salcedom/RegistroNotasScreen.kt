package com.fernandez.semana03salcedom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen() {
    var notaFundamentos by remember { mutableStateOf(0f) }
    var notaPoo by remember { mutableStateOf(0f) }
    var notaMoviles by remember { mutableStateOf(0f) }
    var notaBd by remember { mutableStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Notas", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE8EAF6), Color(0xFFF3E5F5))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CursoRow("Fundamentos de Programación (20%)", notaFundamentos) { notaFundamentos = it }
                CursoRow("Programación Orientada a Objetos (25%)", notaPoo) { notaPoo = it }
                CursoRow("Programación en Móviles (30%)", notaMoviles) { notaMoviles = it }
                CursoRow("Base de Datos (25%)", notaBd) { notaBd = it }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Redondear promedio final", fontSize = 14.sp)
                    Switch(checked = redondear, onCheckedChange = { redondear = it })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = confirmado, onCheckedChange = { confirmado = it })
                    Text("Confirmo que las notas son correctas", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { calculado = true },
                    enabled = confirmado,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("CALCULAR PROMEDIO")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!calculado) {
                    Text(
                        text = "Asigna las notas y confirma para calcular",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                } else {
                    val promPonderado = (notaFundamentos * 0.20f) + (notaPoo * 0.25f) + (notaMoviles * 0.30f) + (notaBd * 0.25f)
                    val promFinal = if (redondear) promPonderado.roundToInt().toFloat() else promPonderado

                    val (observacion, colorChip) = when {
                        promFinal >= 17f -> "EXCELENTE" to Color(0xFF1B5E20)
                        promFinal >= 13f -> "APROBADO" to Color(0xFF4CAF50)
                        promFinal >= 10f -> "EN RECUPERACIÓN" to Color(0xFFFFB300)
                        else -> "DESAPROBADO" to Color(0xFFE53935)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Promedio Ponderado: %.2f".format(promPonderado), fontSize = 15.sp)

                            val textoFinal = if (redondear) {
                                "Promedio Final: ${promFinal.toInt()} (redondeado)"
                            } else {
                                "Promedio Final: %.2f".format(promFinal)
                            }

                            Text(textoFinal, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(color = colorChip, shape = RoundedCornerShape(16.dp)) {
                                Text(
                                    text = observacion,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("✓ Confirmación registrada", color = Color(0xFF2E7D32), fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Desarrollado por: [Coloca Tu Nombre Completo]",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CursoRow(
    nombreCurso: String,
    valorNota: Float,
    onNotaChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = nombreCurso, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                value = valorNota,
                onValueChange = onNotaChange,
                valueRange = 0f..20f,
                steps = 19,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = valorNota.toInt().toString(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}