$(document).ready(function () {
    var data = {};
    $(".login-user").on("click", function () {
        data.email = $(".email").val();
        data.password = $(".password").val();

        if ($(".email").val().trim() == '' || $(".password").val().trim() == '') {
            swal("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        axios.post("http://localhost:8080/api/login", data)
            .then(function (data) {
                if (data.data == "ROLE_ADMIN") {
                    location.replace("/admin")
                } else {
                    location.replace("/");
                }
            }).catch(function (err) {
            swal("Tài khoản sai, vui lòng nhập lại")
        })
    })
});