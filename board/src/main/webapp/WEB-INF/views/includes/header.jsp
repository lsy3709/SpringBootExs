<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<title>Insert title here</title>
</head>
<body>
	<div class="jumbotron" style="margin-bottom: 0">
		<h1>MyBoard</h1>
	</div>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-3">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="/index">HOME</a>

		<!-- Links -->
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="/board/list">Board</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="/files/uploadForm"> FILE</a></li>
			<li class="nav-item"><a class="nav-link" href="#"> FILEBOARD</a></li>
		</ul>
		<ul class="navbar-nav">
		<c:choose>
			<c:when test="${empty sessionScope.sUser }">
				<li class="nav-item"><a class="nav-link" href="/login">로그인</a></li>
				<li class="nav-item"><a class="nav-link" href="/join">회원가입</a></li>
			</c:when>
			<c:otherwise>
				<li class="nav-item"><a class="nav-link" href="/logout">로그아웃(${sUser.username})</a></li>
			</c:otherwise>
		</c:choose>
		</ul>
	</nav>