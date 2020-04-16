$(document).ready(function () {
    $(".closes, #btn-close").on("click", function () {
        location.reload();
    });

    //  update
    $(".btn-update").on("click", function () {
        $("#modal-update-cate").modal();
        var id ;
        var img;
        var dataUser={};

        axios.get("http://localhost:8080/current-user")
            .then(function (data) {
                $("#input-cate-name-update").val(data.data.name);
                $("#input-cate-address-update").val(data.data.address);
                $("#input-cate-phone-update").val(data.data.phone);
                // $('#img').attr('src', data.data.avatar);
                img = data.data.avatar;
                id=data.data.id;
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

});