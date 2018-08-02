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
    <input type="file" name="FileUpload" id="FileUpload"/><br />
    <input type="text" id="url" value=""/><br />
    <button id="btn_upload">上传图片</button><br/>
    <a href="api/task/downloadTaskFile?filename=attachment/file/taskInfo1/201106214_tREb2q.exe">
        我的文件
    </a>
    <a href="api/task/deleteTaskFile?filename=attachment/file/taskInfo1/201106214_tREb2q.exe">
        删除
    </a>
</div>
</body>
<script type="text/javascript">
        $("#btn_upload").click(function () {
            var fileObj = document.getElementById("FileUpload").files[0]; // js 获取文件对象
            if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
                alert("请选择图片");
                return;
            }
            var formData = new FormData();
            formData.append("file", fileObj);
            formData.append("wxId", "17717543071");
            formData.append("infoId", "1");
            var data = formData;
            $.ajax({
                url: "http://localhost:8080/sign/api/task/uploadTaskFile",
                data: data,
                type: "Post",
                dataType: "json",
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                success: function (result) {
                    $("#url").val(result.data);
                    alert("上传完成!");
                },
            })
        });

</script>
</html>
