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

	<div class="alert alert-success" >
	<strong>Event Added Successfully: ${map.eventName }</strong>
	</div>
	Click<a href="${contextPath}/dashboard.htm"> here </a>to go back to Dashboard

</body>
</html>
