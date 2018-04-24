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

	ITEM ID : ${eventItem.id }
	Claim this Item:
	<form:form action="${contextPath}/dashboard/event/item/claimItem.htm"
		method="POST" modelAttribute="eventItem">
		<br> Event : ${event.eventName }
					<br> Item Name : <form:input path="name" name="itemName"
			disabled="true" value="${eventItem.name }" />
		<form:errors path="name" />
		<br> Requested Quantity : <form:input path="requestedQuantity"
			name="requestedQuantity" disabled="true"
			value="${eventItem.requestedQuantity }" />
		<form:errors path="requestedQuantity" />
		<br> FullFill Quantity	<form:input path="fullFulledQuantity"
			name="fullFulledQuantity" required="required" />
		<br>Total Price 		<form:input path="totalPrice" name="totalPrice" required="required"/>
		<br>
		<input type="hidden" value="${map.event.id}" name="eventId" />
		<input type="hidden" value="${eventItem.id}" name="itemId" />

		<input type="submit" value="submit">
	</form:form>





</body>
</html>