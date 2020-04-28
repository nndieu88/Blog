$(document).ready(function(){
    var dataUser = {};
    var data = {};

//    function addNewComment(comment){
//        $(".actionBox").append(`
//            <ul class="commentList">
//                <li>
//                    <div class="commenterImage">
//                        <h5 th:text="${comment.user}"></h5>
//                    </div>
//                    <div>
//                    <br>
//                    </div>
//                    <div class="commentText">
//                    <p class="" th:text="${comment.content}" style="padding-left:100px; font-size: 12px"></p>
//                    <span class="date sub-text" th:text="${#dates.format(comment.dateCreated, 'dd-MM-yyyy')}"></span>
//                    </div>
//                </li>
//            </ul>
//            <form class="form-inline" role="form">
//                                    <div class="form-group">
//                                        <textarea class="form-control btn-input-comment" type="text"
//                                                  placeholder="Your comments"></textarea>
//                                    </div>
//                                    <div class="form-group">
//                                        <button type="button"
//                                                class="btn btn-primary"
//                                                id="btn-comment" >
//                                            Add
//                                        </button>
//
//
//                                    </div>
//                                </form>
//        `)
//    }

//    function loadComment(){
//        $(".actionBox").html("");
//        axios.get("http://localhost:8080/admins/comments")
//            .then(function(res){
//                for(comment of res.data){
//                    addNewComment(comment);
//                }
//            })
//    }

    $("#btn-comment").on("click",function(){

        axios.get("http://localhost:8080/check-user")
            .then(function(res){

                data.content = $(".btn-input-comment").val();
                data.postID = $("#save-cate").attr("postID");

                    axios.post("http://localhost:8080/admins/comments", data)
                        .then(function(res){
                            location.reload();
                        }).catch(function(err){
                            swal(err.data.message);
                        })


            }).catch(function(err){
                swal("Please register!");
                $("#modal-create-user").modal();

                    $("#save-cate").on("click",function(){
                        dataUser.name = $("#input-user-name").val();
                        dataUser.email = $("#input-user-email").val();
                        dataUser.password = $("#input-user-password").val();

                        if($("#input-user-name").val().trim()==""||$("#input-user-email").val().trim()==""||$("#input-user-password").val().trim()==""){
                            swal("Enter full information");
                            return;
                        }

                        axios.post("http://localhost:8080/admins/users", dataUser)
                            .then(function(res){
                                swal(res.data.message);
                                location.replace("/login");
                            }).catch(function(err){
                                swal("wrong account");
                            })
                    })
            })
    })
})