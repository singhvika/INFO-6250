<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/resources/static/head.jsp" %>
<title>CREATE EVENT</title>

</head>
<body>
	
	
	<%@ include file="/resources/static/navbar.jsp" %>

	<form:form method="POST" modelAttribute="event">
		Event Name:<form:input path="eventName" name="eventName"
			required="required" />
		<br>
		<form:errors path="eventName" />
		<br>
		Evetn Start Date: <form:input path="fromDate" type="date" name="fromDate" required="required" />
		<br>
		Evetn End Date: <form:input path="toDate" type="date" name="toDate" required="required" />
		<br>
		<input type="submit" value="create" />
	</form:form>
	
	<br>
	${map.addError} 

</body>
</html>
