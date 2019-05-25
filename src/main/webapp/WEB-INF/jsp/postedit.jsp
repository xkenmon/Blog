<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<jsp:useBean id="article" scope="request" class="cn.xkenmon.blog.vo.Article"/>

<html>
<head>
    <title>发表文章</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="keywords" content="">
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!-- bootstrap-css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/edit/css/bootstrap.min.css">
    <!-- //bootstrap-css -->
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/res/edit/css/style.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/res/edit/css/style-responsive.css" rel="stylesheet">
    <!-- font CSS -->
    <link href="http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic"
          rel="stylesheet" type="text/css">
    <!-- font-awesome icons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/edit/css/font.css" type="text/css">
    <link href="${pageContext.request.contextPath}/res/edit/css/font-awesome.css" rel="stylesheet">
    <!-- //font-awesome icons -->
    <script src="${pageContext.request.contextPath}/res/edit/js/jquery2.0.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/ckeditor/ckeditor.js"></script>
</head>

<body>

<script>
    function autoRefresh(seconds) {
        if (typeof period === "undefined") {   //如果是第一次执行
            period = seconds * 1000;    //定义全局变量period
            var bodyDOM = document.getElementsByTagName("body")[0];
            if (bodyDOM) {
                bodyDOM.innerHTML += '<img id="auto_refresh_img" src="" style="display:none" />';   //添加隐藏的图片
                imgDOM = document.getElementById("auto_refresh_img");   //定义全局Image对象
            }
        }
        if (typeof imgDOM !== "undefined") {
            imgDOM.src = "${pageContext.request.contextPath}/ping?sid=" + new Date().getTime();    //防止缓存
            setTimeout("autoRefresh(" + seconds + ")", period);
        }
    }

    autoRefresh(1300);   //调用方法启动定时刷新
</script>

<script>
    function sub() {
        for (var instance in CKEDITOR.instances) {
            CKEDITOR.instances[instance].updateElement();
        }

        var content = CKEDITOR.instances.content.getData();
        if (content === "") {
            alert("请输入文章内容！");
            return false;
        } else {
            submit.submit();
            return true;
        }
    }
</script>

<section id="container">
    <!--header start-->

    <!--header end-->
    <!--sidebar start-->

    <!--sidebar end-->
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <!-- page start-->
            <div class="mail-w3agile">
                <div class="row">

                    <div class="col-sm-9 mail-w3agile">
                        <section class="panel">
                            <header class="panel-heading wht-bg">
                                <h4 class="gen-case"> Post Blog
                                </h4>
                            </header>
                            <div class="panel-body">
                                <div class="compose-mail">
                                    <form role="form-horizontal" name="article" id="article" method="post"
                                            <c:if test="${article.id eq 0}">
                                                action="${pageContext.request.contextPath}/manage/addArticle"
                                            </c:if>
                                            <c:if test="${article.id ne 0}">
                                                action="${pageContext.request.contextPath}/manage/addArticle?id=${article.id}"
                                            </c:if>
                                          onsubmit="return sub()" enctype="multipart/form-data">
                                        <div class="form-group">
                                            <label class="">标题：</label>
                                            <input required="required" type="text" tabindex="1" name="title"
                                                   class="form-control" value="${article.title}"/>
                                        </div>

                                        <div class="form-group">
                                            <label class="">类型：</label>
                                            <select name="type">
                                                <jsp:useBean id="typeList" scope="request" type="java.util.List"/>
                                                <c:forEach items="${typeList}" var="type">
                                                    <option name="type" tabindex="1" value="${type}">${type}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="">概要：</label>
                                            <input required="required" type="text" tabindex="1" name="summary"
                                                   class="form-control" value="${article.summary}">
                                        </div>
                                        <div class="form-group">
                                            <label class="">封面图片：</label>
                                            <input type="file" tabindex="1" name="coverImg" value="${article.cover}">
                                            推荐尺寸800*800
                                        </div>

                                        <!--<div class="compose-editor">-->
                                        <!--<textarea class="wysihtml5 form-control" rows="9" style="margin: 0px -10px 0px 0px; width: 912px; height: 199px;"></textarea>-->
                                        <!--<input type="file" class="default">-->
                                        <!--</div>-->
                                        <textarea required="required" class="ckeditor" name="content" id="content"
                                                  cols="80" rows="80">${article.content}
                                        </textarea>


                                        <div class="compose-btn">
                                            <button class="btn btn-primary btn-sm"><i
                                                    class="fa fa-check"></i> Send
                                            </button>
                                            <button class="btn btn-sm"><i class="fa fa-times"></i> Discard</button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>

                <!-- page end-->
            </div>
        </section>
        <!-- footer -->

        <!-- / footer -->
    </section>

    <!--main content end-->
</section>
<script src="${pageContext.request.contextPath}/res/edit/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/res/edit/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="${pageContext.request.contextPath}/res/edit/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/res/edit/js/jquery.slimscroll.js"></script>
<script src="${pageContext.request.contextPath}/res/edit/js/jquery.nicescroll.js"></script>
<!--[if lte IE 8]>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/res/edit/js/flot-chart/excanvas.min.js"></script><![endif]-->
<script src="${pageContext.request.contextPath}/res/edit/js/jquery.scrollTo.js"></script>
<!--love.js-->
<script src="${pageContext.request.contextPath}/res/js/love.js"></script>

</body>
</html>
