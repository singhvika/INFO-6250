<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Event Details</title>
<%@ include file="/resources/static/head.jsp"%>
</head>
<body>
	<br> EVENT NAME : ${map.event.eventName}
	<%@ include file="/resources/static/navbar.jsp"%>
	<br> USER LIST:
	<br>
	<c:forEach items="${map.event.participatingUsers}"
		var="participatingUser">
${participatingUser.firstName} | ${participatingUser.email} <br>
	</c:forEach>
	<br> ITEM LIST:


	<c:if test="${not empty map.isUserAdmin}">
		<form:form action="${contextPath}/dashboard/event/addUser.htm"
			modelAttribute="newUserForEvent">
			User Email<form:input path="email" name="email"/>
			<input type="submit" value="Send Invite" />
			<input type="hidden" name="eventId" value="${map.event.id} " />
		</form:form>
	</c:if>



	<br>
	<br>
	<br> Add new ITEMS:
	<br> Item Name:
	<%-- <form:form
		action="${pageContext.request.contextPath}/dashboard/event/itemadd?eventId=${event.id}">
		<form:input path="name" />
		<form:errors path="name"></form:errors>
	Unit Price:
	<form:input path="unitPrice" />
		<form:errors path="unitPrice"></form:errors>
	Requested Quantity:
	<form:input path="requestedQuanntity" />
		<form:errors path="requestedQuanntity"></form:errors>
	</form:form> --%>
</body>
</html>