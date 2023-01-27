<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<div class="container">
	<h2>로그인</h2>
	<form action="/myapp06/member/login" method="post">

		<div class="form-group">
			<label for="username">아이디:</label> 
			<input type="text" class="form-control"
				id="username" placeholder="Enter username" name="username">
		</div>
		<div class="form-group">
			<label for="pass">비밀번호:</label> <input type="password"
				class="form-control" id="password" placeholder="Enter password"
				name="password">
		</div>
		<button type="button" class="btn btn-primary btn-sm" id="btnLogin">로그인</button>
	</form>
</div>
<script type="text/javascript" src="../js/member.js"></script>
<%@ include file="../includes/footer.jsp" %>