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



	<div
		class="col-md-4 col-md-offset-4 col-xs-12 col-lg-6 col-lg-offset-3 col-sm-12 well well-sm">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<strong>Send Invite for this event</strong>
			</div>
			<div class="panel-body">
				<div>
					<br> EVENT NAME : ${map.event.eventName} <br> EVENT
					STATUS : ${map.event.active} <br> EVENT CREATED BY :
					${map.event.createdByUser.lastName },
					${map.event.createdByUser.firstName }
				</div>
				<br>
				<c:choose>
					<c:when test="${isAdmin }">
						<c:choose>
							<c:when test="${map.event.active }">
								<form:form action="${contextPath}/dashboard/event/addUser.htm"
									modelAttribute="newUserForEvent" method="POST">
									<div class="form-group">
										<label for="email">Email</label>
										<form:input path="email" name="email" required="true"
											type="email" />
										<form:errors path="email" class="form-control" />
									</div>
									<input type="submit" value="Send Invite" />
									<input type="hidden" name="eventId" value="${map.event.id} " class="btn btn-success"/>
								</form:form>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger">
									<strong>event is inactive<strong>
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<strong>You are not authorised to add Members</strong>
					</c:otherwise>
				</c:choose>

			</div>

		</div>
</body>
</html>