<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:setBundle basename="cz.muni.fi.anatomytutor.api.dto.QuestionType" var="questionType"/>

<s:layout-render name="/layout.jsp" titlekey="home.titlekey">
    <s:layout-component name="header">
        <script type="text/javascript" src="${pageContext.request.contextPath}/lib/js/SVGPan.js"></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/lib/js/practice.js'></script>
        <script type="text/javascript">
            $(function() {
                practice = new Practice('<c:url value="/practice"/>');
                practice.loadNewQuestion();

            });
        </script>
        <s:useActionBean var="ab" beanclass="cz.muni.fi.anatomytutor.web.PracticeActionBean"/>
        <c:set var="categories" scope="request" value="${ab.categories}"/>
    </s:layout-component>
    <s:layout-component name="body">
        <div id="practiceBox">
            <div class="progress">
                <div id="progressBar" class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                    <span class="sr-only">60% Complete (warning)</span>
                </div>
            </div>
            <div id="spinner">Načítám se...</div>

            <h2 id="pictureTitle">
                <s:useActionBean var="ab" beanclass="cz.muni.fi.anatomytutor.web.PracticeActionBean"/>
                <c:choose>
                    <c:when test="${empty ab.category}">
                        <f:message key="human"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${ab.category.name}"/>
                    </c:otherwise>
                </c:choose><span id="pictureName"></span></h2>
            <div id="pictureSvg"></div>
            <div id="questionBox">
                <span id="questionText"></span>
                <div id="nextQuestionButton" class="btn btn-lg btn-default btn-primary" onclick="practice.loadNewQuestion()">Pokračovat</div>
                <div id="newSetQButton" class="btn btn-lg btn-default btn-primary" onclick="practice.newSetQ()">Pokračovat</div>
            </div>
        </div>


    </s:layout-component>
</s:layout-render>
