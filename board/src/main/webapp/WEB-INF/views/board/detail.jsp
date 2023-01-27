<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../includes/header.jsp" %>
<div class="container">
  <h2>${board.writer} 글보기</h2>
  
    <div class="form-group">
      <label for="id">글번호</label>
      <input type="text" class="form-control" id="id" name="id" value="${board.id}" readonly="readonly">
    </div>
    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" class="form-control" id="title" name="title" value="${board.title}" readonly="readonly">
    </div>
    <div class="form-group">
      <label for="writer">작성자</label>
      <input type="text" class="form-control" id="writer"name="writer" value="${board.writer}" readonly="readonly">
    </div>
    
         <%--
    <div class="form-group">
      <label for="file">file</label>

     <div>
      <ul>
       <c:forEach items="${board.fileList}" var="fileInfo">
	      	<li style="list-style:nome">
	      		<c:choose>
	      			<c:when test="${fileInfo.filetype=='image'}">
	      				<img src="/resources/upload/${fileInfo.savefolder}/${fileInfo.savefile}" height="50">
	      			</c:when>
	      			<c:otherwise>
	      				<img src="/resources/upload/file.png" height="50">
	      			</c:otherwise>
	      		</c:choose>
	      		${fileInfo.originfile}
	      		<a class="filedown" href="#" fno="${fileInfo.fno}" 
	      		sfolder="${fileInfo.savefolder}"
	      		ofile="${fileInfo.originfile}" 
	      		sfile="${fileInfo.savefile}">[다운로드]</a>
	      	</li><br>
      	</c:forEach> 
      </ul>
      </div>
      --%> 	
      
    </div>
    <div class="form-group">
      <label for="content">내용</label>
      <textarea class="form-control" rows="5" id="content" name="content" readonly="readonly">${board.content}</textarea>
    </div>
    <div class="form-group">
      <label for="writer">등록일</label>
      <input type="text" class="form-control" id="writer"name="writer" value="${board.regdate}" readonly="readonly">
    </div>
    <%-- 
    <div class="form-group">
      <label for="writer">수정일</label>
      <input type="text" class="form-control" id="writer"name="writer" value="${board.updatedate}" readonly="readonly">
    </div>
    --%>
    <div class="form-group">
      <label for="writer">조회수</label>
      <input type="text" class="form-control" id="writer"name="writer" value="${board.hitcount}" readonly="readonly">
    </div>
    
    <div class="form-group text-right">
    <c:if test="${sUser.username==board.writer}">
      <button type="button" class="btn btn-secondary btn-sm" id="btnUpdate">수정하기</button>
      <button type="button" class="btn btn-secondary btn-sm" id="btnDelete">삭제하기</button>
    </c:if>  
      <button type="button" class="btn btn-secondary btn-sm" id="btnList">목록보기</button>
    </div>
   
    
    <div class="container mt-5">
    	<div class="form-group">
    		<label for="comment">Comment:</label>
    		<textarea class="form-control" rows="5" id="replyContent" name="content"></textarea>
    	</div>
    	<button type="button" class="btn btn-success" id="replyBtn">Reply Write</button>
    </div>
    <div id="replyResult"></div>
    <fmt:formatDate value=""/>
<script type="text/javascript">

$(".filedown").click(function(){
	var fno=$(this).attr('fno');
	alert("fno:"+fno+" 원본:"+$(this).attr('ofile')+" 실제: "+$(this).attr('sfile'));
	location.href="/board/download/"+fno;
});

var init = function(){
	$.ajax({
		type:"get",
		url:"/replies/getList/${board.id}",
		dataType:"JSON",
		contentType:"application/json;charset=utf-8",
	})
	.done(function(resp){
		//alert("resp"+resp)
		var str = "<table class='table table-hover mt-3'>";
		$.each(resp,function(key,val){
			str += "<tr>"
			str += "<td>" + val.writer + "</td>"
			str += "<td>" + val.content + "</td>"
			str += "<td>" + val.regdate + "</td>"
			
			if("${sessionScope.sUser.username}"==val.writer){
				str+= "<td><a href='javascript:fdel("+val.id+")'>삭제</a></td>"
			}
			
			str += "</tr>"
		})
		str += "</table>"
		$("#replyResult").html(str);
	})
};

function fdel(rno){
	$.ajax({
		type:"delete",
		url:"/replies/"+rno,
	})
	.done(function(resp){
		alert(rno+"번 글 삭제 완료");
		init();
	})
	.fail(function(){
		alert("댓글 삭제 실패")
	})
}

$("#replyBtn").click(function(){
	
	if(${empty sessionScope.sUser}){
		alert("로그인하세요");
		location.href="/login";
		return;
	}
	var data={
			"content":$("#replyContent").val(),
			"writer":"${sUser.username}"
	}
	$.ajax({
		type:"post",
		url:"/replies/new/"+$("#id").val(),
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(data)
	})
	.done(function(resp){
		alert("댓글 추가 성공");
		init();
	})
	.fail(function(){
		alert("댓글 추가 실패")
	});
});

$("#btnUpdate").click(function(){
	if(confirm("정말 수정할까요?")){
		location.href="/board/update/${board.id}"
	}
});

$("#btnDelete").click(function(){
	if(confirm("정말 삭제할까요?")){
		location.href="/board/delete/${board.id}"
	}
});

$("#btnList").click(function(){
	if(confirm("정말 이동할까요?")){
		location.href="/board/list?pageNum=${pageNum}&field=${field}&word=${word}"
	}
});

init();

</script>   
 
    
<%@ include file="../includes/footer.jsp" %>
</div>