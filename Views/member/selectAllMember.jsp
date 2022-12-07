<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체회원정보</h1>
	<hr>
		<table border="1">
			<tr>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
				<th>나이</th>
			</tr>
			<c:forEach var="list" items="${memberList }">
				<tr>
					<td>${list.userId }</td>
					<td>${list.userPwd }</td>
					<td>${list.userName }</td>
					<td>${list.age }</td>		
				</tr>
			</c:forEach>
		</table>
</body>
</html>