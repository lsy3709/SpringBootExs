<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../includes/header.jsp" %>
<div class="container">
	<h2>list_search 페이지입니다.</h2>
	<h2>Board rowNum(${count})</h2>
	<h2>Board pageSize(${pageSize})</h2>
	<h2>Board startPage(${startPage})</h2>
	<h2>Board endPage-1(${endPage})</h2>
	<h2>Board totPage(${totPage})</h2>
	<h2>Board currPage(${cp})</h2>
	<div class="form-group text-right">
	<c:if test="${not empty sessionScope.sUser }">
		<button type="button" class="btn btn-secondary btn-sm" id="btnWrite">글쓰기</button>
	</c:if>
	</div>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일시</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${lists.content}" var="board">
			<tr>
				<td>${board.id}</td>
				<%-- <td><a href="/board/detail/${board.bno}">${board.title}</a></td> --%>
				<td><a href="/board/detail?bno=${board.id}&page=${cp}&field=${field}&word=${word}">${board.title}</a></td>
				<td>${board.writer}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd-HH:mm:ss" value="${board.regdate}"/></td>
				<td>${board.hitcount}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
	<%-- 
	<div class="d-flex justify-content-between mt-5">
		<ul class="pagination">
		
		  <c:if test="${lists.first== false}">
			 <li class="page-item"><a class="page-link" href="?page=${lists.number-1}">이전</a></li>
		  </c:if>
		  
		  <c:forEach begin="${lists.number}" end="${totPage-1}" var="i">
		  	<li class="page-item"><a class="page-link" href="?page=${i}&field=${field}&word=${word}">${i+1}</a></li>
		  </c:forEach>
		  
		<c:if test="${lists.last== false}">
		    <li class="page-item"><a class="page-link" href="?page=${lists.number+1}">다음</a></li>
		</c:if>
			
		  </ul>
	</div>
	--%>
	
	<div class="d-flex justify-content-between mt-3">
		<ul class="pagination">
		<!-- 이전 -->
		<c:if test="${prev==true}">
			<li class="page-item"><a class="page-link" href="list?page=${startPage-pageSize}&field=${field}&word=${word}">Previous</a></li>
		</c:if>
		<!--페이지 리스트-->
		<c:forEach begin="${startPage}" end="${endPage}" var="i">
			<li class="page-item"><a class="page-link" href="?page=${i}&field=${field}&word=${word}">${i+1}</a></li>
		</c:forEach>
		<!-- 다음 -->
		<c:if test="${next==true}">
			<li class="page-item"><a class="page-link" href="?page=${endPage+1}&field=${field}&word=${word}">Next</a></li>
		</c:if>			
		</ul>
		
		<form class="form-inline" action="/board/search" id="searchFrm">
			<select name="field" class="form-control mb-2 mr-sm-2">
			
				<option value="" disabled selected>--</option>
                  <option value="title" ${ field=='title' ? 'selected' : '' }>제목</option>
                  <option value="writer" ${ field=='writer' ? 'selected' : '' }>작성자</option>
                  <option value="content" ${ (field=='content') ? 'selected' : '' }>내용</option>
                  <option value="cwt" ${ (field=='cwt') ? 'selected' : '' }>제목or내용or작성자</option>
				<%-- <option value="" disabled selected>--</option>
				<option value="writer" ${(field=='writer') ? 'selected' : ''}>작성자</option>
				<option value="title" ${(field=='title') ? 'selected' : ''}>제목</option>
				<option value="content" ${(field=='content') ? 'selected' : ''}>내용</option>
				<option value="cwt" ${(field=='cwt') ? 'selected' : ''}>제목or작성자or내용</option> --%>
			</select> 
			<input type="text" class="form-control mb-2 mr-sm-2"
				placeholder="Enter Search" name="word" value="${word}">
			<button type="submit" class="btn btn-secondary mb-2 btn-sm">Search</button>
		</form>
	</div>
</div>

<script type="text/javascript">
 $("#btnWrite").click(function(){
	 location.href="/board/register"
 });
</script>

<%@ include file="../includes/footer.jsp" %>








