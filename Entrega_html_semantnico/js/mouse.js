/* ExploraLog: Mouse Trail & Tracker */
let movementsCount = 0;

document.addEventListener("mousemove", (e) => {
    // 1. Update counter (if element exists)
    movementsCount++;
    const movElement = document.getElementById("movimientos");
    if (movElement) movElement.textContent = movementsCount;

    // 2. Create Trail Particle at exact cursor position
    // Use clientX/Y for position: fixed elements
    crearRastro(e.clientX, e.clientY);
});

function crearRastro(x, y) {
    const trail = document.createElement("div");
    trail.className = "trail";
    
    // Set initial position (centering the 10px circle)
    trail.style.left = `${x - 5}px`;
    trail.style.top = `${y - 5}px`;
    
    document.body.appendChild(trail);

    // Trigger animation slightly after creation for the transition to work
    requestAnimationFrame(() => {
        trail.style.transform = "scale(0.2)";
        trail.style.opacity = "0";
    });

    // Remove element after animation finishes
    setTimeout(() => {
        trail.remove();
    }, 500);
}

// Subtle parallax effect for entries
document.addEventListener("scroll", () => {
    const panels = document.querySelectorAll(".panel");
    const scrolled = window.scrollY;
    panels.forEach((p, idx) => {
        p.style.transform = `translateY(${scrolled * 0.02 * (idx % 2 === 0 ? 1 : -1)}px)`;
    });
});
