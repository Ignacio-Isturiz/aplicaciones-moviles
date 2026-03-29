/* ExploraLog: Journal Entry Persistence (LocalStorage) */
function guardarEntrada() {
    const titleInput = document.getElementById("entry-title");
    const textInput = document.getElementById("entry-text");
    const container = document.getElementById("logs-container");

    if (!titleInput.value || !textInput.value) {
        alert("Por favor, completa el título y el contenido.");
        return;
    }

    const entry = {
        id: Date.now(),
        title: titleInput.value,
        text: textInput.value,
        date: new Date().toLocaleString(),
        location: currentMetadata // From geo.js
    };

    let entries = JSON.parse(localStorage.getItem("exploralog_entries") || "[]");
    entries.unshift(entry); // Newest first
    localStorage.setItem("exploralog_entries", JSON.stringify(entries));

    titleInput.value = "";
    textInput.value = "";
    cargarEntradas();
}

function cargarEntradas() {
    const container = document.getElementById("logs-container");
    if (!container) return;

    let entries = JSON.parse(localStorage.getItem("exploralog_entries") || "[]");
    
    if (entries.length === 0) {
        container.innerHTML = `<p class="entry-meta" style="text-align:center; padding: 2rem;">No hay registros aún. Empieza documentando tu entorno.</p>`;
        return;
    }

    container.innerHTML = entries.map(e => `
        <article class="log-entry" id="entry-${e.id}">
            <div class="entry-meta">${e.date} ${e.location.lat ? `• 📍 ${e.location.lat.toFixed(2)}, ${e.location.lon.toFixed(2)}` : ""}</div>
            <h2>${e.title}</h2>
            <p>${e.text}</p>
            <button onclick="borrarEntrada(${e.id})" style="width: auto; padding: 0.4rem 0.8rem; background: rgba(255,71,87,0.1); color: #ff4757; font-size: 0.7rem;">Eliminar Registro</button>
        </article>
    `).join("");
}

function borrarEntrada(id) {
    let entries = JSON.parse(localStorage.getItem("exploralog_entries") || "[]");
    entries = entries.filter(e => e.id !== id);
    localStorage.setItem("exploralog_entries", JSON.stringify(entries));
    cargarEntradas();
}

// Initial load
document.addEventListener("DOMContentLoaded", cargarEntradas);
