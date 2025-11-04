window.addEventListener('DOMContentLoaded',function (){
    const msg = document.getElementById('msg')?.value;
    if (msg) {
        alert(msg);
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            window.location.href = "/logout"; // 메인 페이지로 이동
        });
    }
});

