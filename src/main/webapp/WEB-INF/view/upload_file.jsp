<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String dataObj = request.getParameter("dataObj");
    JSONObject dataJson = JSONObject.parseObject(dataObj);
    String url = dataJson.getString("url");
    String method = dataJson.getString("method");
    String infoId = dataJson.getString("infoId");
    String token = dataJson.getString("token");
    String fileno = dataJson.getString("fileno");
    request.setAttribute("url", url);
    request.setAttribute("method", method);
    request.setAttribute("infoId", infoId);
    request.setAttribute("token", token);
    request.setAttribute("fileno", fileno);
%>
<!DOCTYPE html>
<html>
<head>
    <title>文件上传</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, tbUser-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/fileinput.min.css"/>
    <script src="js/jquery-v2.1.1.min.js"></script>
    <script type="text/javascript" src="js/fileinput.min.js"></script>
    <script type="text/javascript" src="js/zh.js"></script>
    <script type="text/javascript" src="js/jweixin-1.3.2.js"></script>
    <script src="js/bootstrap.js"></script>
</head>
<body>
<div class="container-fluid">
    <form id="form" action="" method="post" enctype="multipart/form-data">
        <div class="row form-group">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <div class="col-sm-12">
                        <input id="input-id" name="file" multiple type="file" data-show-caption="true">
                        <input type="hidden" id="token" name="token" value="${token}"/>
                        <input type="hidden" id="infoId" name="infoId" value="${infoId}"/>
                        <input type="hidden" id="fileno" name="fileno" value="${fileno}"/>
                        <input type="hidden" id="fileInfo" name="fileInfo"/>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="col-sm-12" style="text-align: center;">
                        <input class="btn btn-primary" type="button" onclick="cancelUpLoad()"
                               value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
                        <input class="btn btn-primary" type="button" onclick="confirmUpload()"
                               value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        initFileInput("input-id");
    });

    function initFileInput(ctrlName) {
        var control = $('#' + ctrlName);
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: "${url}/${method}", //上传的地址
            allowedFileExtensions: ['jpg', 'png', 'doc', 'docx', 'pdf', 'ppt', 'pptx', 'xls', 'xlsx'],//接收的文件后缀
            maxFilesNum: 5,//上传最大的文件数量
            uploadAsync: true, //默认异步上传
            showUpload: true, //是否显示上传按钮
            showRemove: true, //显示移除按钮
            showPreview: true, //是否显示预览
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            dropZoneEnabled: false,
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            uploadExtraData: function () {//向后台传递参数
                var file = $("input[name='file']").val();
                var filename = file.substring(file.lastIndexOf("\\") + 1, file.lastIndexOf("."));
                var data = {
                    fileno: $("#fileno").val(),
                    token: $("#token").val(),
                    infoId: $("#infoId").val(),
                    filename: filename
                };
                return data;
            }
        }).on('filepreupload', function (event, data, previewId, index) {
            var form = data.form,
                files = data.files,
                extra = data.extra,
                response = data.response,
                reader = data.reader;
        }).on("fileuploaded", function (event, data, previewId, index) {
            var res = JSON.stringify(data.response.data);
            $("#fileInfo").val(res);
        }).on('fileerror', function (event, data, msg) {
            console.log('文件上传失败！');
        })
    };

    function deleteUploadFile(fileUrl) {
        var deleteUrl = "${url}/deleteUploadFile?filename=" + fileUrl;
        $.ajax({
            type: "GET",
            url: deleteUrl,
            dataType: "json",
            success: function (res) {
                console.log(res.data.msg);
            },
            error: function (res) {
                console.log(res.data.msg);
            }
        });
    }

    function cancelUpLoad() {
        // 如果有文件已经上传，则删除
        var fileInfo = $("#fileInfo").val();
        if (fileInfo) {
            deleteUploadFile(fileInfo.url);
        }
        wx.miniProgram.navigateBack();
    }

    function confirmUpload() {
        var fileInfo = $("#fileInfo").val();
        if (fileInfo) {
            wx.miniProgram.postMessage({
                data: JSON.parse(fileInfo)
            });
            wx.miniProgram.navigateBack();
        } else {
            alert("没有上传任何文件！");
        }
    };
</script>
</body>
</html>
