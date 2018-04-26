<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>${request_from}: ${pageContext.errorData.requestURI}</h4><br/>
<h4>${servlet_name}: ${pageContext.errorData.servletName}</h4><br/>
<h4>${status_code}: ${pageContext.errorData.statusCode}</h4><br/>
<h4>${exception}: ${pageContext.exception.toString()}</h4><br/>
</body>
</html>
