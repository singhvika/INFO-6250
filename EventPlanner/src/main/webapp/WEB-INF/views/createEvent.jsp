<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/resources/static/head.jsp"%>
<title>CREATE EVENT</title>

</head>
<body>


	<%@ include file="/resources/static/navbar.jsp"%>

	<div
			class="col-md-4 col-md-offset-4 col-xs-12 col-lg-4 col-lg-offset-4 col-sm-12 well well-sm" >

		<form:form method="POST" modelAttribute="event">
			<div class="form-group">
				<label for="eventName">Event Name</label>
				<form:input path="eventName" name="eventName" required="required"
					class="form-control" />
				<form:errors path="eventName" />
			</div>


			<div class="form-group">
				<label for="fromDate">Event Start Date</label>
				<form:input path="fromDate" type="date" name="fromDate"
					required="required" class="form-control" />
				<form:errors path="fromDate" />
			</div>

			<div class="form-group">
				<label for="toDate">Event End Date</label>
				<form:input path="toDate" type="date" name="toDate"
					required="required" class="form-control" />
				<form:errors path="toDate" />
			</div>
			<div class="form-group">
				<input type="submit" value="create" class="btn" />
			</div>
		</form:form>

		<br>

		<c:if test="${not empty map.addError }">
			<div class="alert alert-danger">
				<strong>${map.addError}</strong>
			</div>
		</c:if>
		
		<c:if test="${not empty map.addSuccess }">
		<div class="alert alert-success">
				<strong>${map.addSuccess}</strong>
			</div>
		</c:if>
	</div>
	</div>

</body>
</html>
