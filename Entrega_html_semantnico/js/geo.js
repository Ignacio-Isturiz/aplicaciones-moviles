/* ExploraLog: Geolocation Radar */
let currentMetadata = { lat: null, lon: null };

function obtenerUbicacion() {
    const ubiElement = document.getElementById("ubicacion");
    const locStatus = document.getElementById("location-status");
    const mapContainer = document.getElementById("map-container");
    const mapa = document.getElementById("mapa");

    if (!navigator.geolocation) {
        if (ubiElement) ubiElement.textContent = "Geolocalización no soportada";
        return;
    }

    if (ubiElement) ubiElement.textContent = "Rastreando señal...";

    navigator.geolocation.getCurrentPosition(
        pos => {
            const lat = pos.coords.latitude;
            const lon = pos.coords.longitude;
            currentMetadata = { lat, lon };

            if (ubiElement) ubiElement.textContent = `LAT: ${lat.toFixed(4)} | LON: ${lon.toFixed(4)}`;
            if (locStatus) locStatus.textContent = `📍 Ubicación vinculada (${lat.toFixed(2)}, ${lon.toFixed(2)})`;

            if (mapContainer && mapa) {
                mapContainer.style.display = "block";
                mapa.src = `https://www.openstreetmap.org/export/embed.html?bbox=${lon - 0.01}%2C${lat - 0.01}%2C${lon + 0.01}%2C${lat + 0.01}&layer=mapnik&marker=${lat}%2C${lon}`;
            }
        },
        error => {
            if (ubiElement) ubiElement.textContent = "Error: " + error.message;
        }
    );
}

// Auto-track on load
document.addEventListener("DOMContentLoaded", obtenerUbicacion);
