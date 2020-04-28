$(document).ready(function () {
  var data = {};

  $(".register-user").on("click", function () {
    data.name = $(".name").val();
    data.email = $(".email").val();
    data.password = $(".password").val();

    if (
      $(".name").val().trim() == "" ||
      $(".email").val().trim() == "" ||
      $(".password").val().trim() == ""
    ) {
      swal("Enter full information");
      return;
    }

    axios
      .post("http://localhost:8080/admins/users", data)
      .then(function (data) {
        dataLogin = {
          email: $(".email").val(),
          password: $(".password").val(),
        };
        axios.post("http://localhost:8080/login", dataLogin).then(function (res) {
          swal(res.data.message);
          location.replace("/");
        });
      })
      .catch(function (err) {
        swal("wrong account");
      });
  });
});
