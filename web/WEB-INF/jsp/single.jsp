<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.xkenmon.blog.vo.Article" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="cn.xkenmon.blog.util.TimeUtil" %>
<!DOCTYPE HTML>
<!--
Justice by gettemplates.co
Twitter: http://twitter.com/gettemplateco
URL: #
-->
<html>
<script>
    if (window.name === "ref"){
        window.location.reload();
        window.name = "doNotRef";
    }
</script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Blog - BigMeng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="xkenmon.cn"/>
    <meta name="keywords" content="xkenmon.cn mix"/>
    <meta name="author" content="Mix"/>


    <link href="https://fonts.googleapis.com/css?family=Crimson+Text:400,400i|Roboto+Mono" rel="stylesheet">

    <!-- Animate.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/icomoon.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/bootstrap.css">

    <!-- Magnific Popup -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/magnific-popup.css">

    <!-- Theme style  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/style.css">

    <!-- Modernizr JS -->
    <script src="${pageContext.request.contextPath}/res/js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="/res/js/respond.min.js"></script>
    <![endif]-->
</head>
<jsp:useBean id="popularList" scope="request" type="java.util.List"/>
<jsp:useBean id="typeList" scope="request" type="java.util.List"/>
<jsp:useBean id="relatedList" scope="request" type="java.util.List"/>
<jsp:useBean id="article" scope="request" type="cn.xkenmon.blog.vo.Article"/>

<script>
    function del_ask() {
        return confirm("确定要删除吗?(lll￢ω￢)");
    }
</script>

<body>

<div class="gtco-loader"></div>

<div id="page">
    <nav class="gtco-nav" role="navigation">
        <div class="container">
            <div class="row">
                <div class="col-xs-2 text-left">
                    <div id="gtco-logo"><a href="${pageContext.request.contextPath}/index.html">大<span>.</span>孟</a>
                    </div>
                </div>
                <div class="col-xs-10 text-right menu-1">
                    <ul>
                        <li class="has-dropdown">
                            <a>文章类型</a>
                            <ul class="dropdown">
                                <%--<%--%>
                                <%--for (String type : typeList) {--%>
                                <%--out.write("<li><a href=\"/category/" + URLEncoder.encode(type, "utf-8") + "\">" + type + "</a></li>");--%>
                                <%--}--%>
                                <%--out.flush();--%>
                                <%--%>--%>
                                <c:forEach var="type" items="${typeList}">
                                    <li><a href="${pageContext.request.contextPath}/category/${type}">${type}</a></li>
                                </c:forEach>
                            </ul>
                        </li>

                        <li class="has-dropdown">
                            <%--&nbsp;--%>
                            <c:if test="${empty currentUser}">
                            <a href="${pageContext.request.contextPath}/static/login.html">
                                <img height="30" width="30" src="${pageContext.request.contextPath}/res/userpic1.png"/>
                                [登陆/注册]
                                </c:if>
                                <c:if test="${not empty currentUser}">
                                <a>
                                    <img height="30" width="30"
                                         src="${pageContext.request.contextPath}/res/userpic1.png"/>
                                        ${currentUser.userName}
                                    </c:if>
                                </a>
                                <c:if test="${not empty currentUser}">
                                <ul class="dropdown">
                                    <li><a href="/profile/${currentUser.userName}">个人主页</a></li>
                                    <li><a href="${pageContext.request.contextPath}/manage/edit">写文章</a></li>
                                    <li><a href="${pageContext.request.contextPath}/manage/logout.do">注销登录</a></li>
                                </ul>
                                </c:if>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
    </nav>

    <header id="gtco-header" class="gtco-cover" role="banner" style="background-image:url(/res/images/img_4.jpg);"
            data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-7 text-left">
                    <div class="display-t">
                        <div class="display-tc animate-box" data-animate-effect="fadeInUp">
                            <span class="date-post"><%=TimeUtil.getShortTime(article.getDate())%></span>
                            <h1 class="mb30"><a ><%=article.getTitle()%>
                            </a></h1>
                            <p>by <a href="/profile/${article.author}" class="text-link"><%=article.getAuthor()%>
                            </a></p>
                            <p>点击数：<%=article.getReadCount()%>
                            </p>
                            <c:if test="${(not empty currentUser) and (article.author eq currentUser.userName)}">
                                <a class="text-link" href="${pageContext.request.contextPath}/manage/edit/${article.id}">[编辑文章]</a>
                                <a class="text-link" onclick="return del_ask()" href="${pageContext.request.contextPath}/manage/delete/${article.id}">[删除文章]</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <div id="gtco-maine">
        <div class="container">
            <div class="row row-pb-md">
                <div class="col-md-12">
                    <article class="mt-negative">
                        <div class="text-left content-article">
                            <!--文本-->
                            <div class="row">
                                <div class="col-lg-8 cp-r animate-box">
                                    <%=article.getContent()%>
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h2 class="related-title animate-box"><c:if test="${relatedList.size() ne 1}">Related Posts</c:if></h2>
                </div>
            </div>
            <div class="row row-pb-md">
                <div class="col-md-12">
                    <ul id="gtco-post-list">
                        <!--相关文章-->

                            <c:forEach items="${relatedList}" var="related">
                                <c:if test="${related.id ne article.id}">
                                    <li class="one-third animate-box entry" data-animate-effect="fadeIn">
                                        <a href="/Article/${related.id}">
                                            <c:if test="${empty related.cover}">
                                                <div class="entry-img"
                                                     style="background-image: url(/res/images/img_5.jpg)"></div>
                                            </c:if>
                                            <c:if test="${not empty related.cover}">
                                                <div class="entry-img"
                                                     style="background-image: url(${pageContext.request.contextPath}/userImg/ArticlePic?fileName=${related.cover})"></div>
                                            </c:if>
                                            <div class="entry-desc">
                                                <h3>${related.title}</h3>
                                                <p>${related.summary}</p>
                                            </div>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/profile/${related.author}" class="post-meta">By ${related.author} <span
                                                class="date-posted">${TimeUtil.getShortTime(related.date)}</span></a>
                                    </li>
                                </c:if>
                            </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <footer id="gtco-footer" role="contentinfo">
        <div class="container">
            <div class="row row-pb-md">
                <div class="col-md-12">
                    <h3 class="footer-heading">Most Popular</h3>
                </div>
                <!--最流行-->

                <c:forEach var="popular" items="${popularList}">
                    <div class="col-md-4">
                        <div class="post-entry">
                            <div class="post-img">
                                <a href="/Article/${popular.id}"><img
                                        src="${pageContext.request.contextPath}/userImg/ArticlePic?fileName=${popular.cover}"
                                        class="img-responsive" alt="Most Popular"></a>
                            </div>
                            <div class="post-copy">
                                <h4><a href="/Article/${popular.id}">${popular.title}</a></h4>
                                <a href="/Article/${popular.id}" class="post-meta"><span
                                        class="date-posted">点击数：${popular.readCount}</span> </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="row copyright">
                <div class="col-md-12 text-center">
                    <p>
                        <small class="block">&copy; 2017 BigMeng.top & xkenmon.cn All Rights Reserved. <a
                                href="http://www.xkenmon.cn/" target="_blank" title="xkenmon.cn">xkenmon.cn</a> -
                            Powered By <a href="http://www.xkenmon.cn/" title="xkenmon.cn"
                                          target="_blank">xkenmon.cn</a></small>
                    </p>
                    <p>
                    <ul class="gtco-social-icons">
                        <li><a href="https://twitter.com/xkenmon"><i class="icon-twitter"></i></a></li>
                        <li><a href="https://www.facebook.com/profile.php?id=100012841268713"><i
                                class="icon-facebook"></i></a></li>
                        <li><a href="https://www.linkedin.com/in/%E7%A5%A5%E5%9D%A4-%E5%AD%9F-7049b2128/"><i
                                class="icon-linkedin"></i></a></li>
                        <li><a href="#"><i class="icon-qq"></i></a></li>
                    </ul>
                    </p>
                </div>
            </div>

        </div>
    </footer>
</div>

<div class="gototop js-top">
    <a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
</div>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/res/js/jquery.min.js"></script>
<!-- jQuery Easing -->
<script src="${pageContext.request.contextPath}/res/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="${pageContext.request.contextPath}/res/js/jquery.waypoints.min.js"></script>
<!-- Stellar -->
<script src="${pageContext.request.contextPath}/res/js/jquery.stellar.min.js"></script>
<!-- Main -->
<script src="${pageContext.request.contextPath}/res/js/main.js"></script>
<!--love.js-->
<script src="${pageContext.request.contextPath}/res/js/love.js"></script>

</body>
</html>

