<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:useActionBean beanclass="cz.muni.fi.anatomytutor.web.ProfileActionBean" var="actionBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Jsem na postgresu?</h1>
        <p><c:out value=" ${actionBean.user.name}"/></p>
        <p><c:out value=" ${actionBean.user.email}"/></p>
    </body>
</html>
