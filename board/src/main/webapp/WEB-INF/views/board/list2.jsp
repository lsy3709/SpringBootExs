<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../includes/header.jsp" %>
<div class="container">
	<h2>Board Total(${boardCount})</h2>
	<div class="form-group text-right">
		<button type="button" class="btn btn-secondary btn-sm" id="btnWrite">글쓰기</button>
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
		<c:forEach items="${list}" var="board">
			<tr>
				<td>${board.id}</td>
				<td><a href="/board/detail/${board.id}">${board.title}</a></td>
				<td>${board.writer}</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}"/></td>
				<td>${board.hitcount}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<div class="d-flex justify-content-between mt-3">
		<ul class="pagination">
		<!-- 이전 -->
		<li class="page-item"><a class="page-link" href="#">Previous</a></li>
		<!--페이지 리스트-->
		<li class="page-item"><a class="page-link" href="#">1</a></li>
		<li class="page-item"><a class="page-link" href="#">2</a></li>
		<li class="page-item"><a class="page-link" href="#">3</a></li>
		<li class="page-item"><a class="page-link" href="#">4</a></li>
		<li class="page-item"><a class="page-link" href="#">5</a></li>
		<li class="page-item"><a class="page-link" href="#">6</a></li>
		<li class="page-item"><a class="page-link" href="#">7</a></li>
		<li class="page-item"><a class="page-link" href="#">8</a></li>
		<li class="page-item"><a class="page-link" href="#">9</a></li>
		<li class="page-item"><a class="page-link" href="#">10</a></li>
		<!-- 다음 -->
		<li class="page-item"><a class="page-link" href="#">Next</a></li>				
		</ul>

		<form class="form-inline" action="#" id="searchFrm">
			<select name="field" class="form-control mb-2 mr-sm-2">
				<option value="writer">작성자</option>
				<option value="title">제목</option>
			</select> <input type="text" class="form-control mb-2 mr-sm-2"
				placeholder="Enter Search" name="word">
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








