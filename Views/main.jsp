<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="common/header.jsp"/>
	<h1>Spring Test</h1>
	<hr>
	<a href="selectAllMember.do">전체회원 조회하기</a>
	<div class="rankContent">
		<br><br>
		<div class="innerOuter">
			<h4>게시글 TOP5</h4>
			<br>
			<a href="list.bo">더보기</a>
			
			<table id="boardList" class="table table-hover" align="center">
				<thead>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>첨부파일</th>
					</tr>
				</thead>
				<tbody>
					<!-- 조회수 높은 순으로 조회하여 5개 게시글 데이터 뿌리기 -->
				</tbody>
				
			</table>
		</div>
	</div>
	<script>
		$(function(){
			viewTopBoardList();
			
			setInterval(viewTopBoardList, 10000);
			
// 			$("#boardList tr").click(function(){
// 				console.log("클릭됨");
// 			})
			
			//동적요소 이벤트 걸기
			$(document).on("click","#boardList > tbody > tr",function(){
				console.log($(this).children().eq(0).text());
				var bno = $(this).children().eq(0).text();
				location.href = "detail.bo?bno="+bno;
			})
		})
		
		function viewTopBoardList(){
			$.ajax({
				url: "viewTopBoard.bo",
				success: function(list){
					console.log(list);
					var str="";
					if(list != null){
						for(var i in list){
							str += "<tr>"
								 + "<td>" + list[i].boardNo + "</td>"
								 + "<td>" + list[i].boardTitle + "</td>"
								 + "<td>" + list[i].boardWriter + "</td>"
								 + "<td>" + list[i].count + "</td>"
								 + "<td>" + list[i].createDate + "</td>"
								 + "</tr>";
							if(list[i].originName != null){
								str += "★";
							}
							str += "</td></tr>";
							$("#boardList > tbody").html(str);
						}
					}
					if(list == ""){
						$("#boardList > tbody").html("<tr><td>아직 게시물이 없습니다!!</td></td>");
					}
				},
				error: function(){
					console.log("통신실패");
				}
			})
		}
		
	</script>
<%-- 	<jsp:include page="common/footer.jsp"/> --%>
	<%@ include file="common/footer.jsp" %>
</body>
</html>





















