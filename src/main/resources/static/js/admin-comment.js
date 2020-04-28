$(document).ready(function () {
 $(".btn-delete-comment").on("click", function () {
        var id = $(this).attr("cateid");

        if (confirm("Are you sure you want to delete!")) {
        axios.delete("http://localhost:8080/admins/comments/" + id)
            .then(function (data) {
            swal(data.data.message);
            location.reload();
            }).catch(function (err) {
            swal(err.data.message);
        });
        }
    });
});
