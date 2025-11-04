"use strict";

document.addEventListener("DOMContentLoaded", () => {
    const carousel = document.querySelector(".carousel-track");
    const prevBtn = document.querySelector(".carousel-control.prev");
    const nextBtn = document.querySelector(".carousel-control.next");

    if (!carousel || !prevBtn || !nextBtn) {
        return;
    }

    const getStep = () => {
        const firstCard = carousel.querySelector(".recipe-card");
        if (!firstCard) {
            return 0;
        }
        const rect = firstCard.getBoundingClientRect();
        const styles = getComputedStyle(carousel);
        const gap = parseFloat(styles.columnGap || "0");
        return rect.width + gap;
    };

    const scrollCarousel = (direction) => {
        const offset = getStep() * direction;
        carousel.scrollBy({ left: offset, behavior: "smooth" });
    };

    const updateButtons = () => {
        const maxScroll = carousel.scrollWidth - carousel.clientWidth;
        prevBtn.disabled = carousel.scrollLeft <= 0;
        nextBtn.disabled = carousel.scrollLeft >= maxScroll - 1;
        prevBtn.classList.toggle("is-disabled", prevBtn.disabled);
        nextBtn.classList.toggle("is-disabled", nextBtn.disabled);
    };

    prevBtn.addEventListener("click", () => {
        scrollCarousel(-1);
    });

    nextBtn.addEventListener("click", () => {
        scrollCarousel(1);
    });

    carousel.addEventListener("scroll", () => {
        window.requestAnimationFrame(updateButtons);
    });

    window.addEventListener("resize", () => {
        window.requestAnimationFrame(updateButtons);
    });

    updateButtons();
});
