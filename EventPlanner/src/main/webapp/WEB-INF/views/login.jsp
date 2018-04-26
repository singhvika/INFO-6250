<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/resources/static/head.jsp"%>
<title>Login</title>
</head>
<body>
	<%@ include file="/resources/static/navbar.jsp"%>

	<div class="container">
		<div
			class="col-md-4 col-md-offset-4 col-xs-12 col-lg-4 col-lg-offset-4 col-sm-12 well well-sm">


			<form:form action="login.htm" method="POST" commandName="user"
				modelAttribute="user">
				<div class="form-group">
					<label for="email">Email address:</label>

					<form:input path="email" name="email" class="form-control" />
					<strong> <form:errors path="email" />
					</strong>
				</div>
				<div class="form-group">
					<label for="pwd">Password</label>
					<form:input path="pwd" name="pwd" type="password"
						class="form-control" />
					<strong> <form:errors path="pwd" />
					</strong>
				</div>
				<input type="submit" name="submit" value="Login"
					class="btn btn-success" />

				<br>
			</form:form>
		</div>
	</div>
	<br> LOGIN ERROR: ${map.loginError}

</body>
</html>