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
	<%@ include file="/resources/static/navbar.jsp"%>
	<c:if test="${not empty map.isUserAdmin}">
		<c:set var="isAdmin" value="true" />
	</c:if>
	<br> EVENT Details
	<br> EVENT NAME		: ${map.event.eventName} : ${map.event.id}
	<br> EVENT STATUS	: ${map.event.active}

	<br> USER LIST:
	<br>
	<c:forEach items="${map.event.participatingUsers}"
		var="participatingUser">
${participatingUser.firstName} | ${participatingUser.email} <br>
	</c:forEach>



	<c:choose>
		<c:when test="${map.event.active}">
			<c:if test="${isAdmin}">

				<br> Add New Members
		
	<form:form action="${contextPath}/dashboard/event/addUser.htm"
					modelAttribute="newUserForEvent" method="POST">
			User Email<form:input path="email" name="email"  required="true" type="email"/> <form:errors path="email"/>
					<input type="submit" value="Send Invite" />
					<input type="hidden" name="eventId" value="${map.event.id} " />
				</form:form>




			</c:if>

		</c:when>

		<c:otherwise>
			<br>
		CANNOT ADD NEW MEMBERS... EVENT IS INACTIVE
		</c:otherwise>
	</c:choose>






</body>
</html>