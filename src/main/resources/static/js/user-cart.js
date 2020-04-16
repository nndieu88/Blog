$(document).ready(function () {
    var dataOrder = {};

    $(".closes, #btn-close").on("click", function () {
        location.reload();
    });

    $("#btn-order").on("click", function () {
        dataOrder.nameProd = $(this).attr("name");
        dataOrder.price = $(".total-price").attr("price");

        axios.get("http://localhost:8080/current-user")
            .then(function (data) {
                if (data.data.phone == null || data.data.address == null) {
                    swal("Vui lòng nhập đầy đủ thông tin để mua hàng!");

                    //  update

                    $("#modal-update-cate").modal();
                    var id;
                    var img;
                    var dataUser = {};

                    $("#input-cate-name-update").val(data.data.name);
                    $("#input-cate-address-update").val(data.data.address);
                    $("#input-cate-phone-update").val(data.data.phone);
                    // $('#img').attr('src', data.data.avatar);
                    img = data.data.avatar;
                    id = data.data.id;
                    $("#input-cate-email-update").val(data.data.email);


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
                } else {
                    dataOrder.nameUser = data.data.name;
                    dataOrder.phone = data.data.phone;
                    dataOrder.address = data.data.address;
                    axios.post("http://localhost:8080/admins/order", dataOrder)
                        .then(function (data) {
                            axios.get("http://localhost:8080/mobile/cart/delete")
                                .then(function () {
                                    swal({
                                        text: "Đặt hàng thành công!",
                                        timer: 5000
                                    });
                                    location.replace("/mobile");
                                })
                        }).catch(function (err) {
                        swal("Đặt hàng lỗi. Xin vui lòng đặt lại. Cảm ơn!");
                        location.replace("/mobile/register")
                    })
                }
            }).catch(function (err) {
            swal("Vui lòng đăng nhập để thanh toán",
                "http://localhost:8080/mobile/register");
        })
    });


    $(".close").on("click", function () {
        var id = $(this).attr("prodid");
        if (confirm("Bạn có muốn xóa không ?")) {
            axios.get("http://localhost:8080/mobile/cart/" + id)
                .then(function () {
                    swal("successful");
                    location.reload();
                })
        }
    })
});