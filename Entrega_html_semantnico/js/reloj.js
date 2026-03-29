/* ExploraLog: Reloj & Cronómetro de Sesión */
function actualizarReloj() {
    const relojElement = document.getElementById("reloj");
    if (relojElement) {
        const ahora = new Date();
        const opciones = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
        relojElement.textContent = ahora.toLocaleDateString('es-ES', opciones).toUpperCase();
    }
}

// Actualizar cada segundo
setInterval(actualizarReloj, 1000);
actualizarReloj();
