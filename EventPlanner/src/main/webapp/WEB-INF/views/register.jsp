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
	<div class="container">
		<div class="col-md-6 col-md-offset-3 col-xs-12 col-lg-6 col-lg-offset-3 col-sm-12">
			<form:form action="register.htm" method="POST" commandName="user"
				modelAttribute="user">
				<div class="form-group">
					<label>Email:</label>
					<form:input path="email" name="email" class="form-control"></form:input>
					<form:errors path="email"></form:errors>
				</div>
				<div class="form-group">
					<label>Password</label>
					<form:input path="pwd" name="pwd" type="password" class="form-control" required="required"></form:input>
					<form:errors path="pwd"></form:errors>
				</div>
				
				<div class="form-group">
					<label>First Name:</label>
				<form:input path="firstName" name="firstName" required="required" class="form-control"></form:input>
				<form:errors path="firstName"></form:errors>
				</div>
				
				<div class="form-group">
					<label>Last Name: </label>
					<form:input path="lastName" name="lastName" class="form-control"></form:input>
					<form:errors path="lastName"></form:errors>
				</div>
								
				<input type="submit" name="submit" value="submit" class="btn-success btn"/><form:errors path="pwd" /><br>
			</form:form>
		</div>
	</div>

	
	
	Register Status: 	${registerError} 

</body>
</html>