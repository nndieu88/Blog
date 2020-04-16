$(document).ready(function () {
    var dataUser = {};

    $(".close, #btn-close").on("click", function () {
        location.reload();
    });


    //  add
    $("#new-category").on("click", function () {
        $("#modal-create-cate").modal();

        $(".btn-save-category").on("click", function () {
            dataUser.name = $("#input-category-name").val();
            dataUser.email = $("#input-category-email").val();
            dataUser.password = $("#input-category-password").val();

            axios.post("http://localhost:8080/admins/users", dataUser)
                .then(function (data) {
                    swal("successful");
                }).catch(function (err) {
                swal("error")
            });
        });
    });

    //  update
    $(".btn-edit-cate").on("click", function () {
        $("#modal-update-cate").modal();
        var id = $(this).attr("cateid");
        var img;

        axios.get("http://localhost:8080/admins/users/" + id)
            .then(function (data) {
                $("#input-cate-name-update").val(data.data.name);
                $("#input-cate-address-update").val(data.data.address);
                $("#input-cate-phone-update").val(data.data.phone);
                // $('#img').attr('src', data.data.avatar);
                img = data.data.avatar;
                $("#input-cate-email-update").val(data.data.email);
            });


        $("#btn-update-cate").on("click", function () {
            dataUser.name = $("#input-cate-name-update").val();
            dataUser.address = $("#input-cate-address-update").val();
            dataUser.phone = $("#input-cate-phone-update").val();
            dataUser.email = $("#input-cate-email-update").val();

            if ($("#file")[0].files.length === 0) {
                dataUser.avatar = img;
                update(id, dataUser);
            } else {
                var form = new FormData();
                var files = $('#file')[0].files[0];
                form.append('file', files);

                axios.post("http://localhost:8080/upload", form)
                    .then(function (data) {
                        dataUser.avatar = data.data;
                        update(id, dataUser);
                    }).catch(function (err) {
                })
            }

            function update(id, dataUser) {
                axios.put("http://localhost:8080/admins/users/" + id, dataUser)
                    .then(function (data) {
                        swal("successfull");
                        location.reload();
                    })
            }
        })
    });

    // delete
    $(".btn-delete-cate").on("click", function () {
        var id = $(this).attr("cateid");
        if (confirm("bạn có muốn xóa không")) {
            axios.delete("http://localhost:8080/admins/users/" + id)
                .then(function (data) {
                    swal("successful");
                    location.reload();
                }).catch(function (err) {
                swal("Bạn không thể xóa")
            });
            return;
        }
        location.reload();
    });


});
