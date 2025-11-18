window.addEventListener('DOMContentLoaded',function (){

    const moveMainPage = document.getElementById("moveMainPage");
    const moveIcon = document.getElementById("moveIcon");
    const circleUserIcon = document.getElementById("circle-user");
    const signUpBtn = document.getElementById("signUpBtn");
    const logoImg = document.getElementById("logoImg");

    if (moveMainPage) {
        moveMainPage.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });
    }

    if (moveIcon || logoImg) {
        moveIcon.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });

        logoImg.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });
    }

    if (circleUserIcon) {
        circleUserIcon.addEventListener("click", function () {
            window.location.href = "/MainPage"; // 메인 페이지로 이동
        });
    }
    if (signUpBtn) {
        signUpBtn.addEventListener("click", function () {
            window.location.href = "/signup"; // 메인 페이지로 이동
        });
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            window.location.href = "/logout"; // 메인 페이지로 이동
        });
    }



});
