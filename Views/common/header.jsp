<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- 부트스트랩에서 제공하고 있는 스타일 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- 부트스트랩에서 제공하고 있는 스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        div {box-sizing:border-box;}
        #header {
            width:80%;
            height:100px;
            padding-top:20px;
            margin:auto;
        }
        #header>div {width:100%; margin-bottom:10px;}
        #header_1 {height:40%;}
        #header_2 {height:60%;}
        #header_1>div{
            height:100%;
            float:left;
        }
        #header_1_left {width:30%; position:relative;}
        #header_1_center {width:40%;}
        #header_1_right {width:30%;}
        #header_1_left>img {height:80%; position:absolute; margin:auto; top:0px; bottom:0px; right:0px; left:0px;}
        #header_1_right {text-align:center; line-height:35px; font-size:12px; text-indent:35px;}
        #header_1_right>a {margin:5px;}
        #header_1_right>a:hover {cursor:pointer;}
        #header_2>ul {width:100%; height:100%; list-style-type:none; margin:auto; padding:0;}
        #header_2>ul>li {float:left; width:25%; height:100%; line-height:55px; text-align:center;}
        #header_2>ul>li a {text-decoration:none; color:black; font-size:18px; font-weight:900;}
        #header_2 {border-top:1px solid lightgray;}
        #header a {text-decoration:none; color:black;}
        /* 세부페이지마다 공통적으로 유지할 style */
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
	<c:if test="${not empty alertMsg}">
         <script>
            alert("${alertMsg}");
         </script>
         <c:remove var="alertMsg" scope="session"/>
      </c:if>
      
    <div id="header">
        <div id="header_1">
            <div id="header_1_left">
            <a href="${pageContext.request.contextPath}">
            <img src="data:image/png;base64,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" width="80px" height="100%" alt="">
            </a>
<%--                 <img src="${pageContext.request.contextPath}/resources/images/common/top_logo_s.jpg" alt=""> --%>
            </div>
            <div id="header_1_center"></div>
            <div id="header_1_right">
            <c:choose>
            	<c:when test="${empty loginUser }">
                <!-- 로그인 전 -->
                <a href="insert.me">회원가입</a>
                <a data-toggle="modal" data-target="#loginModal">로그인</a> <!-- 모달의 원리 : 이 버튼 클릭시 data-targer에 제시되어있는 해당 아이디의 div요소를 띄워줌 -->
            	</c:when>
            	<c:otherwise>            	
                <!-- 로그인 후 -->
                <lable>${loginUser.userName }님 환영합니다</label> &nbsp;&nbsp;
                <a href="myPage.me">마이페이지</a>
                <a href="logOut.me">로그아웃</a>
            	</c:otherwise>
            </c:choose>
            </div>
        </div>
        <div id="header_2">
            <ul>
                <li><a href="${pageContext.request.contextPath}">HOME</a></li>
                <li><a href="">공지사항</a></li>
                <li><a href="list.bo">자유게시판</a></li>
                <li><a href="">사진게시판</a></li>
            </ul>
        </div>
    </div>
    <!-- 로그인 클릭 시 뜨는 모달 (기존에는 안보이다가 위의 a 클릭 시 보임) -->
    <div class="modal fade" id="loginModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Login</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action="login.me" method="post">
                    <!-- Modal body -->
                    <div class="modal-body">
                        <label for="userId" class="mr-sm-2">ID : </label>
                        <input type="text" class="form-control mb-2 mr-sm-2" placeholder="ID" id="userId" name="userId"> <br>
                        <label for="userPwd" class="mr-sm-2">Password : </label>
                        <input type="password" class="form-control mb-2 mr-sm-2" placeholder="Password" id="userPwd" name="userPwd">
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">로그인</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br clear="both">
</body>
</html>

















