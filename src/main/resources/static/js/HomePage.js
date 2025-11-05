document.addEventListener("DOMContentLoaded", () => {
    const recipeList = document.getElementById("recipeList");
    const prevBtn = document.querySelector(".slide-btn.prev");
    const nextBtn = document.querySelector(".slide-btn.next");

    if (!recipeList || !prevBtn || !nextBtn) return;

    const scrollAmount = 250; // 한 번에 이동할 거리

    prevBtn.addEventListener("click", () => {
        recipeList.scrollBy({ left: -scrollAmount, behavior: "smooth" });
    });

    nextBtn.addEventListener("click", () => {
        recipeList.scrollBy({ left: scrollAmount, behavior: "smooth" });
    });
});
