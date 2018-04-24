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



	<br>EVENT SUMMARY:

	<br>EVENT NAME: ${map.event.eventName }
	<br>EVENT START DATE: ${map.event.fromDate }
	<br>EVENT END DATE: ${map.event.toDate }
	<br>EVENT BY: ${map.event.createdByUser.lastName },
	<br>EVENT LOCATION: XYZ todo ${map.event.createdByUser.firstName }
	<br>EVENT EMAIL: ${map.event.createdByUser.email }

	<br> PARTICIPATING USERS:
	<table border=2>
		<tr>

			<th>Last Name</th>
			<th>First Name</th>
			<th>Email</th>
		</tr>
		<c:forEach items="${event.participatingUsers }" var="user">
			<tr>
				<td>${ user.lastName}</td>
				<td>${user.firstName }</td>
				<td>${user.email }</td>
			</tr>
		</c:forEach>
	</table>


	CLAIMED ITEMS:

	<table border="2">
		<tr>
			<th>Item Name</th>
			<th>Requested Quantity</th>
			<th>FullFilled By</th>
			<th>Fullfilled Quantitty</th>
			<th>Total Price</th>
		</tr>
		<c:forEach items="${map.event.itemList}" var="item">
			<c:if test="${not empty item.fullfilledByUser}">
				<tr>
					<td>${item.name }</td>
					<td>${item.requestedQuantity }</td>
					<td>${item.fullfilledByUser }</td>
					<td>${item.fullFulledQuantity }</td>
					<td>${item.totalPrice}</td>


				</tr>
			</c:if>
		</c:forEach>

	</table>



	<br> UNCLAIMED ITEMS:
	<br>
	<table border="2">
		<tr>
			<th>Item Name</th>
			<th>Requested Quantity</th>
			<th>FullFilled By</th>
			<th>Fullfilled Quantitty</th>
			<th>Total Price</th>
		</tr>
		<c:forEach items="${map.event.itemList}" var="item">
			<c:if test="${ empty item.fullfilledByUser}">
				<tr>

					<td>${item.name }</td>
					<td>${item.requestedQuantity }</td>

					<td>${item.fullfilledByUser }</td>
					<td>${item.fullFulledQuantity }</td>
					<td>${item.totalPrice}</td>

				</tr>
			</c:if>
		</c:forEach>

		<br> EVENT TOTAL EXPENSE : ${map.eventTotalExpense }
		<br> YOUR TOTAL EXPENSE		: ${map.userTotalExpense }
		<br> YOUR CREDIT / DEVBIT : ${map.creditOrDebit }


	</table>
	<br>

</body>
</html>