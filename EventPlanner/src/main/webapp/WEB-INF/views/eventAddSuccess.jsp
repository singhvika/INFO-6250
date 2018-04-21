<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/resources/static/head.jsp" %>

<title>Event Success</title>

</head>
<body>
	<%@ include file="/resources/static/navbar.jsp" %>
	<br>

	YOUR EVENT WAS ADDED:
	<br>
	Event ID: ${map.eventId} 
	<br>
	Event Name: ${map.eventName}
	<br>
	<a href="${contextPath}/dashboard.htm">Click here to go back to Dashboard</a>

</body>
</html>
