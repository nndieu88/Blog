$(document).ready(function () {
  var data = {};
  $(".login-user").on("click", function () {
    data.email = $(".email").val();
    data.password = $(".password").val();

    if ($(".email").val().trim() == "" || $(".password").val().trim() == "") {
      swal("Enter full information");
      return;
    }

    axios
      .post("http://localhost:8080/api/login", data)
      .then(function (data) {
        swal(data.data.message);
        location.replace("/");
      })
      .catch(function (err) {
        swal("wrong account");
      });
  });
});
