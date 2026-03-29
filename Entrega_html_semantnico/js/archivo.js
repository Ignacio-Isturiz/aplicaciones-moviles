/* ExploraLog: Data Import (File API) */
document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("archivo");
    const fileInfo = document.getElementById("file-info");

    if (fileInput) {
        fileInput.addEventListener("change", e => {
            const file = e.target.files[0];
            if (!file) return;

            const reader = new FileReader();
            
            reader.onloadstart = () => {
                if (fileInfo) fileInfo.textContent = "Analizando archivo...";
            };

            reader.onload = (event) => {
                const content = event.target.result;
                if (fileInfo) fileInfo.textContent = `Importado: ${file.name}`;
                
                // For this project, we'll treat imported .txt files as a new entry
                // but pre-fill the form instead of silent saving
                const titleInput = document.getElementById("entry-title");
                const textInput = document.getElementById("entry-text");
                
                if (titleInput && textInput) {
                    titleInput.value = `Importado: ${file.name}`;
                    textInput.value = content;
                }
            };

            reader.onerror = () => {
                if (fileInfo) fileInfo.textContent = "Error al leer el archivo.";
            };

            reader.readAsText(file);
        });
    }
});
