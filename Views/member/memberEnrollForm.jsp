<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }
    </style>
</head>
<body>
    
    <!-- 메뉴바 -->
    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId2" placeholder="Please Enter ID" name="userId" required><button id="btnz">중복확인</button><br>
					<div id="checkResult" style="font-size:0.8em;"></div>
                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary">회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <script>
//     	$(function(){
//     		$("input[value=${loginUser.gender}]").attr("checked",true);
//     	})
    	
// 		$(function(){
// 			$("#btnz").click(function(){
// 				$.ajax({
// 					url:"validate.me",
// 					data: {userId: $("#userId").val()},
// 					success: function(result){
// 						if(result == "FF"){
// 							$("#checkResult").html("이미 존재하는 아이디입니다.");
// 						}
// 						if(result == "TT"){
// 							$("#checkResult").html("사용 가능한 아이디입니다.");
// 						}
// 					},
// 					error: function(){
// 						console.log("통신실패");
// 					}
// 				})
// 			})
// 		})
		
		const $idInput = $("#userId2");
		//input요소 객체에 키가 눌렸다가 떼어질때 발생시키는
		$idInput.keyup(function(){
			//최소 5글자 이상일 경우에만 중복체크가 진행되게 작업
			if($idInput.val().length >= 5){
				$.ajax({
					url: "validate.me",
					data: {userId:$idInput.val()},
					success: function(result){
						if(result == "FF"){
							$("#checkResult").css("color","red").text("이미 존재하는 아이디입니다.");
						}
						if(result == "TT"){
							$("#checkResult").css("color","green").text("사용 가능한 아이디입니다.");
						}
						return;
					},
					error: function(){
						console.log("통신 실패");
					}
				})
			}
			//5글자 미만
			$("#checkResult").html("5글자 이상 입력해주세요").show();
		})
		
	</script>

    <!-- 푸터바 -->
    <jsp:include page="../common/footer.jsp" />

</body>
</html>

















