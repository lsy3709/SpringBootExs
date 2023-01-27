<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<form action="uploadFormAction" method="post" enctype="multipart/form-data">
	<input type="file" name="uploads" multiple>
	<input type="submit" value="전송">
</form>

<%@ include file="../includes/footer.jsp" %>    