<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-definition>
    <!DOCTYPE html>
    <html lang="${pageContext.request.locale}">
        <head>
            <title><f:message key="${titlekey}"/></title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="description" content="Adaptivní aplikace na procvičování anatomie člověka">
            <meta name="keywords" content="anatomie člověka, biologie, orgány, soustavy, adaptivní procvičování">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/css/social.css" />
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/css/bootstrap.min.css" />
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/css/app.css" />
            <script type='text/javascript' src='${pageContext.request.contextPath}/lib/js/jquery-1.10.2.min.js'></script>
            <script type='text/javascript' src='${pageContext.request.contextPath}/lib/js/bootstrap.min.js'></script>
            <s:layout-component name="header"/>
        </head>

        <body>
            <div id="wrap">
                <div class="navbar navbar-inverse" role="navigation">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Anatomie člověka</a>
                        </div>
                        <div class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li><a href="${pageContext.request.contextPath}/practice">Člověk</a></li>
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle" data-toggle="dropdown">Soustavy <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <c:forEach items="${categories}" var="category">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/practice/start/${category.urlName}">
                                                    <c:out value="${category.name}"/>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </ul>

                            <ul class="nav navbar-nav navbar-right">
                                <c:choose>
                                    <c:when test="${empty user}">
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Přihlásit se <b class="caret"></b></a>
                                            <ul class="dropdown-menu login">
                                                <li><a class="btn btn-gp btn-lg btn-block" href="${pageContext.request.contextPath}/loginGP"><i class="social-google"></i> přes Google</a></li>
                                                <li><a class="btn btn-fb btn-lg btn-block" href="${pageContext.request.contextPath}/loginFB"><i class="social-facebook"></i> přes Facebook</a></li>
                                            </ul>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${user.name}"/> <b class="caret"></b></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="${pageContext.request.contextPath}/loginFB/logout">Odhlásit se</a></li>
                                            </ul>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                            </ul>
                        </div><!--/.navbar-collapse -->
                    </div>
                </div>
                <s:errors/>
                <s:messages/>
                <div id="content">
                    <s:layout-component name="body"/>
                </div><!--end of content-->

            </div>
        </body>
    </html>
</s:layout-definition>
