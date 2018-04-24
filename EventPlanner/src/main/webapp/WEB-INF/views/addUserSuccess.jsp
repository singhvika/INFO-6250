<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add User Success</title>
<%@ include file="/resources/static/head.jsp" %>
</head>
<body>
<%@ include file="/resources/static/navbar.jsp" %>

<br>
${map.success }
<br>
Click <a href="${contextPath}${map.redirect}">here</a> to go back to event.

</body>
</html>