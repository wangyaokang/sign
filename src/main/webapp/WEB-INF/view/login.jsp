<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登 录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/api/task" method="post" enctype="multipart/form-data">
    文件名<input type="file" name="photo"/><br/>
    <input type="text" name="desc"/> <br/>
    <input type="button" value="提交" onclick="upload()"/><br/>
</form>
<h3>童话镇.mp3 陈一发儿</h3>
<a href="${pageContext.request.contextPath}/fileDownLoad">前去下载</a>
</body>
<script type="application/javascript">

</script>
</html>
