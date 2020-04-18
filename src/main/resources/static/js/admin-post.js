$(document).ready(function () {
    var dataPost = {};
    var dataCate = {};

    $(".close, #btn-close").on("click", function () {
        location.reload();
    });

    // content
    $(".show-content").on("click", function () {
        var id = $(".btn-edit-product").attr("cateid");
        $("#modal-context").modal();

        axios.get("http://localhost:8080/admins/posts/" + id)
            .then(function (data) {
                $(".context").val(data.data.content);
            });
    });

    // add
    $("#new-product").on("click", function () {
        $("#modal-create-cate").modal();

        $("#save-cate").on("click", function () {
            dataPost.title = $("#input-title").val();
            dataPost.content = $("#input-content").val();
            dataPost.categoryID = parseInt($(".category-id-create").val());

            var form = new FormData;
            form.append("file", $("#files")[0].files[0]);
            axios.post("http://localhost:8080/upload", form)
                .then(function (data) {
                    dataPost.image = data.data;
                    axios.post("http://localhost:8080/admins/posts", dataPost)
                        .then(function (data) {
                            swal("successful");
                        }).catch(function (err) {
                        swal("err");
                    });
                }).catch(function (err) {
                swal("Nhap hinh vao!");
            })
        });
    });

    // update
    $(".btn-edit-product").on("click", function () {
        $("#modal-update-cate").modal();
        var id = $(this).attr("cateid");
        var img;

        axios.get("http://localhost:8080/admins/posts/" + id)
            .then(function (data) {
                $("#input-title-update").val(data.data.title);
                $("#input-content-update").val(data.data.content);
                img = data.data.image;
            });

        $("#btn-update-cate").on("click", function () {
            dataPost.title = $("#input-title-update").val();
            dataPost.content = $("#input-content-update").val();
            dataPost.categoryID = parseInt($(".category-id-update").val());

            if ($("#file")[0].files.length === 0) {
                dataPost.image = img;
                update(id, dataPost);
            } else {
                var form = new FormData;
                form.append("file", $("#file")[0].files[0]);
                axios.post("http://localhost:8080/upload", form)
                    .then(function (data) {
                        dataPost.image = data.data;
                        update(id, dataPost);
                    })
            }

            function update(id, dataPost) {
                axios.put("http://localhost:8080/admins/posts/" + id, dataPost)
                    .then(function (data) {
                        swal("successful");
                        location.reload();
                    }).catch(function (err) {
                    swal("err");
                })
            }
        })

    });

    // delete
    $(".btn-delete-product").on("click", function () {
        var id = $(this).attr("cateid");
        if (confirm("bạn có muốn xóa không")) {
            axios.delete("http://localhost:8080/admins/posts/" + id)
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
