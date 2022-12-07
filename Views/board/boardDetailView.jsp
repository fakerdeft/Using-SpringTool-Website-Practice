<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
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

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
    	
    <jsp:include page="../common/header.jsp" />
	
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
			
            <a class="btn btn-secondary" style="float:right;" href="#" onClick="history.go(-1);">목록으로</a>
            <br><br>
			
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${Board.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${Board.boardWriter }</td>
                    <th>작성일</th>
                    <td>${Board.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                        <a href="${Board.changeName }" download="${Board.originName }">${Board.originName }</a>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${Board.boardContent }</p></td>
                </tr>
            </table>
            <br>
			
            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                <c:if test="${loginUser.userId eq Board.boardWriter }">
	                <a class="btn btn-primary" onclick="submitPostForm(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="submitPostForm(0);">삭제하기</a>
                </c:if>
            </div>
            <br><br>
            
            <form id="postForm">
            	<input type="hidden" name="boardNo" value="${Board.boardNo }">
            	<input type="hidden" name="filePath" value="${Board.changeName }">
            </form>
            
            <script>
            	function submitPostForm(num){
            		
//             		//동적으로 DOM요소 만들어서 이벤트(submit)해보기
//             		var form = $("<form>");
//             		var cBno = $("<input>").prop("type","hidden").prop("name","bno").prop("value","${Board.boardNo}");
//             		var cFp = $("<input>").prop("type","hidden").prop("name","filePath").prop("value","${Board.changeName}");
            		
//             		form.append(cBno).append(cFp);
//             		if(num == 1){//수정 버튼 get타입
//             			form.prop("action","update.bo").prop("method","GET");
//             		}
//             		if(num != 1){//삭제 버튼 post타입
//             			form.prop("action","delete.bo").prop("method","POST");
//             		}
//             		//만들어놓은 form 요소를 body에 추가하기
//             		$("body").append(form);
            		
//             		form.submit();
            		
            		if(num == 1){
            			$("#postForm").attr("action","update.bo").submit();
            		}
            		if(num != 1){
            			$("#postForm").attr("action","delete.bo").submit();
            		}
            	}
            </script>
			
            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
            	<c:if test="${not empty loginUser}">
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="replyContent" id="replyContent" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle">
                        	<button class="btn btn-secondary" onclick="insertReply()">등록하기</button>
                        </th>
                    </tr>
                </c:if>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount"></span>)</td>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <br><br>
	
    </div>
    
    <script>
			$(function() {
				viewReplyList();
				
				//1초마다 갱신
				setInterval(viewReplyList, 1000);
			});
			
			function viewReplyList(){
				$.ajax({
					url: "viewReplyList.bo",
					data: {boardNo: ${Board.boardNo}},
					success: function(list) {
						console.log(list);
						if(list != null){
							var str = "";
							for(var i in list) {
								str += "<tr>"
										+ "<td>"+list[i].replyWriter+"</td>"
										+ "<td>"+list[i].replyContent+"</td>"
										+ "<td>"+list[i].createDate+"</td>"
										+ "</tr>";
							}
							$("#replyArea tbody").html(str);
						}
						if(list == ""){
							$("#replyArea tbody").html("<tr><td>아직 댓글이 없습니다!!</td></td>");
						}
						$("#rcount").text(list.length);
					},
					error: function() {
						console.log("통신 실패");
					}
				})
			}
			
			function insertReply() {
				$.ajax({
					url: "insertReply.bo",
					data: {
						boardNo: ${Board.boardNo},
						replyContent: $("#replyContent").val()
					},
					success: function(result){
						if(result == "TT") {
							viewReplyList(); //댓글목록 갱신
							$("#replyContent").val("");
						} 
						if(result == "FF"){
							console.log("댓글작성 실패");
						}
					},
					error: function(){
						console.log("통신 실패");
					}
				});
			}
			
			
	</script>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>

















