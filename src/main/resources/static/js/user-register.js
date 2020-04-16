$(document).ready(function () {
    var data = {};

    $(".register-user").on("click", function () {
        data.name = $(".name").val();
        data.email = $(".email").val();
        data.password = $(".password").val();

        if ($(".name").val().trim() == '' || $(".email").val().trim() == '' || $(".password").val().trim() == '') {
            swal("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        axios.post("http://localhost:8080/admins/users", data)
            .then(function (data) {
                dataLogin = {
                    email: $(".email").val(),
                    password: $(".password").val()
                };
                axios.post("http://localhost:8080/login", dataLogin)
                    .then(function () {
                        swal("Đăng ký thành công");
                        location.replace("/mobile")
                    })
            }).catch(function (err) {
            swal("Lỗi đăng ký. Vui lòng nhập lại thông tin");
        })
    });

});