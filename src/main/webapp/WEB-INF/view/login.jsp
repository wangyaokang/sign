<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登 录</title>
    <script src="js/jquery-v2.1.1.min.js"></script>
</head>
<body>
<div>
    <input type="file" name="FileUpload" id="FileUpload">
    <button id="btn_upload">上传图片</button>
</div>
</body>
<script type="text/javascript">
        $("#btn_upload").click(function () {
            var fileObj = document.getElementById("FileUpload").files[0]; // js 获取文件对象
            if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
                alert("请选择图片");
                return;
            }
            var param = {"file": fileObj};
            var data = {"params": param, "method": "uploadTaskFile", "token": "17717543071", "infoId": "1"};
            $.ajax({
                url: "http://localhost:8080/sign/api/task",
                data: JSON.stringify(data),
                type: "Post",
                dataType: "json",
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                success: function (result) {
                    alert("上传完成!");
                },
            })
        });

</script>
</html>
