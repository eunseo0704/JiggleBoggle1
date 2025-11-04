window.addEventListener('DOMContentLoaded',function (){
    const msg = document.getElementById('msg')?.value;
    if (msg) {
        alert(msg);
    }

    const moveMainPage = document.getElementById("moveMainPage");
    const moveIcon = document.getElementById("moveIcon");

    if (moveMainPage) {
        moveMainPage.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });
    }

    if (moveIcon) {
        moveIcon.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            window.location.href = "/logout"; // 메인 페이지로 이동
        });
    }
});

