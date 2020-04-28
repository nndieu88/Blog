$(document).ready(function () {
    var dataCategory = {};
    $("#input-category-name").val("");

    $(".close, #btn-close").on("click", function () {
        location.reload();
    });

    // add
    $("#new-category").on("click", function () {
        $("#modal-create-cate").modal();

        $("#save-cate").on("click", function () {
            if($("#input-category-name").val().trim()===''){
                swal('Enter full information');
                return;
            }

            dataCategory.categoryName = $("#input-category-name").val();

            axios.post("http://localhost:8080/admins/categories", dataCategory)
                .then(function (data) {
                    $("#input-category-name").val("");
                    swal(data.data.message)
                }).catch(function (err) {
                    swal(err.data.message)
                });
        });
    });

    // update
    $(".btn-edit-cate").on("click", function () {
        $("#modal-update-cate").modal();

        var id = $(this).attr("cateid");

        axios.get("http://localhost:8080/admins/categories/" + id)
            .then(function (data) {
                $("#input-cate-name-update").val(data.data.data.categoryName);
            });

        $("#btn-update-cate").on("click", function () {
            if($("#input-cate-name-update").val().trim()===''){
                swal('Enter full information');
                return;
            }
            dataCategory.categoryName = $("#input-cate-name-update").val();

            axios.put("http://localhost:8080/admins/categories/" + id, dataCategory)
                .then(function(data){
                    swal(data.data.message)
                    location.reload();
                }).catch(function (err) {
                    swal(err.data.message)
            })
        })
    });

    //delete
    $(".btn-delete-cate").on("click", function () {
        var id = $(this).attr("cateid");

        if (confirm("Are you sure you want to delete!")) {
        axios.delete("http://localhost:8080/admins/categories/" + id)
            .then(function (data) {
            swal(data.data.message);
            location.reload();
            }).catch(function (err) {
            swal(err.data.message);
        });
        }
    });
});