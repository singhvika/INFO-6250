<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/resources/static/head.jsp" %>
<title>Login</title>
</head>
<body>
	<%@ include file="/resources/static/navbar.jsp" %>

	<form:form action="login.htm" method="POST" commandName="user"
		modelAttribute="user">
		username:<form:input path="email" name="email" />
		<br>
		<form:errors path="email" />
		<br>
		password<form:input path="pwd" name="pwd" type="password" />
		<input type="submit" name="submit" value="submit" />
		<form:errors path="pwd" />
		<br>
	</form:form>
	
	<br>
	LOGIN ERROR: ${map.loginError} 

</body>
</html>