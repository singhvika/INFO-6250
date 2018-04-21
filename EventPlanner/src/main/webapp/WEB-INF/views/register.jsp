<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="/resources/static/head.jsp" %>
<title>Register User</title>
</head>
<body>

############## ${contextPath}
<%@ include file="/resources/static/navbar.jsp" %>
	<form:form action="register.htm" method="POST" commandName="user"
		modelAttribute="user">
		email:<form:input path="email" name="email" /><br>
		<form:errors path="email" /><br>
		password<form:input path="pwd" name="pwd" type="password" /><br>
		First Name: <form:input path="firstName" name="firstName" required="required"></form:input><form:errors path="firstName"></form:errors>
		Last Name: <form:input path="lastName" name="lastName"></form:input><form:errors path="lastName"></form:errors>
		<input type="submit" name="submit" value="submit"/><form:errors path="pwd" /><br>
	</form:form>
	
	Register Status: 	${registerError} 

</body>
</html>