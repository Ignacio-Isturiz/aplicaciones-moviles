package com.example.random

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.random.ui.theme.RandomTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "pantalla_menu"
                    ) {
                        composable("pantalla_menu") {
                            MenuPrincipal(
                                onIrAJuego = { navController.navigate("pantalla_juego") },
                                onIrAComparador = { navController.navigate("pantalla_comparador") },
                                onIrABaloto = { navController.navigate("pantalla_baloto") }
                            )
                        }

                        composable("pantalla_juego") {
                            JuegoRandom(onVolver = { navController.popBackStack() })
                        }

                        composable("pantalla_comparador") {
                            PantallaComparador(onVolver = { navController.popBackStack() })
                        }

                        composable("pantalla_baloto") {
                            PantallaBaloto(onVolver = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

// --- VISTA 1: EL MENÚ ---
@Composable
fun MenuPrincipal(onIrAJuego: () -> Unit, onIrAComparador: () -> Unit, onIrABaloto: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "MOVILES", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(40.dp))

        val buttonModifier = Modifier.fillMaxWidth(0.8f).height(60.dp)

        Button(onClick = onIrAJuego, modifier = buttonModifier) {
            Text("1. JUEGO NÚMERO RANDOM")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onIrAComparador, modifier = buttonModifier, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
            Text("2. ¿CUÁL ES MAYOR?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onIrABaloto, modifier = buttonModifier, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
            Text("3. GENERADOR BALOTO")
        }
    }
}

// --- VISTA 2: JUEGO NÚMERO RANDOM ---
@Composable
fun JuegoRandom(onVolver: () -> Unit) {
    var number by remember { mutableStateOf<Double?>(null) }
    var mostrarAlerta by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = onVolver, modifier = Modifier.align(Alignment.Start)) { Text("< VOLVER") }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = number?.toString() ?: "Presiona el botón", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { number = Random.nextInt(0, 100).toDouble() }, modifier = Modifier.fillMaxWidth(0.6f)) { Text("GENERAR") }
        Button(onClick = { val current = number ?: 0.0; if (current < 100.0) number = current + 1 }, modifier = Modifier.fillMaxWidth(0.6f)) { Text("AUMENTAR +1") }
        Button(onClick = { val current = number ?: 0.0; if (current == 0.0 || number == null) mostrarAlerta = true else number = current / 2 }, modifier = Modifier.fillMaxWidth(0.6f)) { Text("DIVIDIR MITAD") }
        Spacer(modifier = Modifier.weight(1.2f))
    }
    if (mostrarAlerta) {
        AlertDialog(onDismissRequest = { mostrarAlerta = false }, confirmButton = { Button(onClick = { mostrarAlerta = false }) { Text("OK") } }, title = { Text("Valor no válido") }, text = { Text("Debes generar un número primero.") })
    }
}

// --- VISTA 3: COMPARADOR ---
@Composable
fun PantallaComparador(onVolver: () -> Unit) {
    var numA by remember { mutableStateOf<Int?>(null) }
    var numB by remember { mutableStateOf<Int?>(null) }
    var resultado by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = onVolver, modifier = Modifier.align(Alignment.Start)) { Text("< VOLVER") }
        Text(text = "Comparador", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("Número A", fontWeight = FontWeight.Bold); Text(text = numA?.toString() ?: "-", fontSize = 40.sp) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("Número B", fontWeight = FontWeight.Bold); Text(text = numB?.toString() ?: "-", fontSize = 40.sp) }
        }
        Spacer(modifier = Modifier.height(40.dp))
        if (resultado.isNotEmpty()) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Text(text = resultado, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val a = Random.nextInt(0, 100); val b = Random.nextInt(0, 100)
            numA = a; numB = b
            resultado = when { a > b -> "El mayor es A ($a)"; b > a -> "El mayor es B ($b)"; else -> "¡Son iguales!" }
        }, modifier = Modifier.fillMaxWidth(0.7f)) { Text("GENERAR Y COMPARAR") }
    }
}

// --- VISTA 4: GENERADOR BALOTO (NUEVA) ---
@Composable
fun PantallaBaloto(onVolver: () -> Unit) {
    var balotas by remember { mutableStateOf<List<Int>>(emptyList()) }
    var superBalota by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = onVolver, modifier = Modifier.align(Alignment.Start)) { Text("< VOLVER") }

        Text(text = "BALOTO", style = MaterialTheme.typography.displayMedium, color = Color(0xFF2E7D32), fontWeight = FontWeight.ExtraBold)
        Text(text = "Sorteo Automático", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(40.dp))

        // Fila de Balotas principales (1-43)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (balotas.isEmpty()) {
                repeat(5) { BalotaUI(null, Color.LightGray) }
            } else {
                balotas.forEach { num -> BalotaUI(num, Color(0xFFFFEB3B)) }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text("SÚPER BALOTA", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))

        // Súper Balota (1-16)
        BalotaUI(superBalota, Color(0xFFF44336))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Lógica de sorteo: 5 números únicos del 1 al 43
                val tempSet = mutableSetOf<Int>()
                while (tempSet.size < 5) {
                    tempSet.add(Random.nextInt(1, 44))
                }
                balotas = tempSet.toList().sorted()
                // Súper balota del 1 al 16
                superBalota = Random.nextInt(1, 17)
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("GENERAR SORTEO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun BalotaUI(numero: Int?, color: Color) {
    Surface(
        modifier = Modifier.size(55.dp),
        shape = CircleShape,
        color = color,
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = numero?.toString() ?: "?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (color == Color.LightGray) Color.DarkGray else Color.Black
            )
        }
    }
}