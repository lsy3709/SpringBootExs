<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ include file="../includes/header.jsp" %>
    
    <div class="container">
  <h2>글쓰기</h2>
  <form action="insert" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="title">제목:</label>
      <input type="text" class="form-control" id="title" placeholder="Enter title" name="title">
    </div>
    <div class="form-group">
      <label for="writer">작성자:</label>
      <input type="text" class="form-control" id="writer" name="writer" value="${sUser.username}" readonly>
    </div>
    <div class="form-group">
      <label for="fileUpload">파일업로드:</label>
      <input type="file" class="form-control" id="uploads" name="uploads" multiple>
    </div>
    <div class="form-group">
      <label for="content">내용:</label>
      <textarea class="form-control" rows="5" id="content" name="content"></textarea>
    </div>
    <button type="submit" class="btn btn-primary btn-sm">Submit</button>
  </form>
</div>
    
    <%@ include file="../includes/footer.jsp" %>