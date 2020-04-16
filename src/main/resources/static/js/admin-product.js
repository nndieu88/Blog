$(document).ready(function () {
    var dataProduct = {};
    var dataCate = {};

    $(".close, #btn-close").on("click", function () {
        location.reload();
    });

    // add
    $("#new-product").on("click", function () {
        $("#modal-create-cate").modal();

        $("#save-cate").on("click", function () {
            dataProduct.nameProd = $("#input-category-name").val();
            dataProduct.price = $("#input-category-price").val();
            dataProduct.description = $("#input-category-decription").val();
            dataProduct.cate_id = parseInt($(".category-id-create").val());

            var form = new FormData;
            form.append("file", $("#files")[0].files[0]);
            axios.post("http://localhost:8080/upload", form)
                .then(function (data) {
                    dataProduct.image = data.data;
                    axios.post("http://localhost:8080/admins/products", dataProduct)
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

        axios.get("http://localhost:8080/admins/products/" + id)
            .then(function (data) {
                $("#input-cate-name-update").val(data.data.nameProd);
                $("#input-cate-price-update").val(data.data.price);
                $("#input-cate-decription-update").val(data.data.description);
                img = data.data.image;
                console.log($("#input-cate-name-update"))
            });

        $("#btn-update-cate").on("click", function () {
            dataProduct.nameProd = $("#input-cate-name-update").val();
            dataProduct.price = $("#input-cate-price-update").val();
            dataProduct.description = $("#input-cate-decription-update").val();
            dataProduct.cate_id = parseInt($(".category-id-update").val());

            if ($("#file")[0].files.length === 0) {
                dataProduct.image = img;
                update(id, dataProduct);
            } else {
                var form = new FormData;
                form.append("file", $("#file")[0].files[0]);
                axios.post("http://localhost:8080/upload", form)
                    .then(function (data) {
                        dataProduct.image = data.data;
                        update(id, dataProduct);
                    })
            }

            function update(id, dataProduct) {
                axios.put("http://localhost:8080/admins/products/" + id, dataProduct)
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
            axios.delete("http://localhost:8080/admins/products/" + id)
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
