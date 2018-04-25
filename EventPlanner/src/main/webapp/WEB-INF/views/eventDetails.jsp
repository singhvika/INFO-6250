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

	<form action="${contextPath }/dashboard/event/downloadPDF">
		<input type="hidden" name="eventId" value="${map.event.id }" /> <input
			type="submit" value="PDF Report" />
	</form>

	<c:if test="${not empty map.isUserAdmin}">
		<c:set var="isAdmin" value="true" />
	</c:if>
	ADMINs: ${isAdmin }
	<form action="${contextPath}/dashboard/event/summary.htm">
		<input type="hidden" name="eventId" value="${map.event.id }" /> <input
			type="submit" value="Show Summary" />
	</form>


	<br> EVENT Details
	<br> EVENT NAME : ${map.event.eventName} : ${map.event.id}
	<br> EVENT STATUS : ${map.event.active}
	<br> EVENT PACKUP :
	<c:choose>
		<c:when test="${map.event.active}">
			<c:if test="${ isAdmin}">
				<form
					action="${contextPath}/dashboard/event/packup.htm?action=deactivate"
					method="POST">
					<input type="submit" value="Packup" /><input type="hidden"
						name="eventId" value="${map.event.id}" />
				</form>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${isAdmin }">
				<form
					action="${contextPath}/dashboard/event/packup.htm?action=activate"
					method="POST">
					<input type="submit" value="Activate" /><input type="hidden"
						name="eventId" value="${map.event.id}" />
				</form>
			</c:if>
		</c:otherwise>
	</c:choose>



	<br> USER LIST:
	<br>
	<c:forEach items="${map.event.participatingUsers}"
		var="participatingUser">
${participatingUser.firstName} | ${participatingUser.email} <br>
	</c:forEach>
	<br>

	<c:if test="${isAdmin}">
	Button for new members
		<form action="${contextPath}/dashboard/event/addUser.htm" method="GET">
			<input type="hidden" name="eventId" value="${map.event.id }" /> <input
				type="submit" value="Add Members" />
		</form>
	</c:if>

	<br> ITEM LIST:
	<br>

	<table border="2">
		<tr>
			<th>Item Name</th>
			<th>Requested Quantity</th>
			<th>FullFilled By</th>
			<th>Fullfilled Quantitty</th>
			<th>Total Price</th>
			<th>Delete Action</th>
		</tr>
		<c:forEach items="${map.event.itemList}" var="item">
			<tr>
				<td>${item.name }</td>
				<td>${item.requestedQuantity }</td>

				<c:choose>
					<c:when test="${empty item.fullfilledByUser }">
						<td colspan="3"><form
								action="${contextPath }/dashboard/event/item/claimItem.htm"
								method="GET">
								<input type="hidden" name="itemId" value="${item.id }" /> <input
									type="hidden" value="${map.event.id}" name="eventId" /> <input
									type="submit" value="Claim" />
							</form></td>
							<td><form
								action="${contextPath }/dashboard/event/item/deleteitem.htm"
								method="POST">
								<input type="hidden" name="itemId" value="${item.id }" /> <input
									type="hidden" name="eventId" value="${map.event.id }" />
								<input type="submit" value="DELETE" name="submit"/>
							</form></td>
							

					</c:when>

					<c:otherwise>
						<td>${item.fullfilledByUser }</td>
						<td>${item.fullFulledQuantity }</td>
						<td>${item.totalPrice}</td>
						<td><form
								action="${contextPath }/dashboard/event/item/deleteitem.htm"
								method="POST">
								<input type="hidden" name="itemId" value="${item.id }" /> <input
									type="hidden" name="eventId" value="${map.event.id }" />
								<input type="submit" value="DELETE" name="submit"/>
							</form></td>
					</c:otherwise>
				</c:choose>

				<c:if test="${empty item.fullfilledByUser}">

				</c:if>

			</tr>
		</c:forEach>

	</table>

	<c:if test="${isAdmin }">
		<c:if test="${map.event.active }">
			<form:form action="${contextPath}/dashboard/event/addItem.htm"
				method="POST" modelAttribute="eventItem">
				<input type="hidden" name="eventId" value="${map.event.id }" />
	Item Name:<form:input path="name" name="itemName" required="required" />
				<form:errors path="name" />
				<form:errors path="name" />
				<br>
	Requested Quantity:<form:input path="requestedQuantity"
					name="requestedQuantity" required="required" />
				<form:errors path="requestedQuantity" />
				<br>
				<input type="submit" value="Add Items" />
			</form:form>
		</c:if>
	</c:if>

	<c:if test="${not map.event.active}">
	CANNOT CLAIM ITEMS..EVENT IS INACTIVE
	</c:if>

	<c:choose>
		<c:when test="${map.event.active}">


		</c:when>

		<c:otherwise>
			<br>
		CANNOT ADD NEW MEMBERS OR ITEMS... EVENT IS INACTIVE
		</c:otherwise>
	</c:choose>






</body>
</html>