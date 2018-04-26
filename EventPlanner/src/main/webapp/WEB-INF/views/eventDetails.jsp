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
		class="col-md-4 col-md-offset-4 col-xs-12 col-lg-8 col-lg-offset-2 col-sm-12 well well-sm">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<c:choose>
					<c:when test="${map.event.active}">
						<c:if test="${ isAdmin}">
							<form class="pull-right"
								action="${contextPath}/dashboard/event/packup.htm?action=deactivate"
								method="POST">
								<input type="submit" value="Packup"
									class="btn btn-sm btn-danger" /><input type="hidden"
									name="eventId" value="${map.event.id}" />
							</form>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${isAdmin }">
							<form class="pull-right"
								action="${contextPath}/dashboard/event/packup.htm?action=activate"
								method="POST">
								<input type="submit" value="Activate"
									class="btn btn-sm btn-primary" /><input type="hidden"
									name="eventId" value="${map.event.id}" />
							</form>
						</c:if>
					</c:otherwise>
				</c:choose>
				<strong>Event Details</strong>
			</div>




			<div class="panel-body">
				<div>
					<br> EVENT NAME : ${map.event.eventName} <br> EVENT
					STATUS : ${map.event.active} <br> EVENT CREATED BY :
					${map.event.createdByUser.lastName },
					${map.event.createdByUser.firstName }
				</div>

				<br>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<c:if test="${isAdmin}">
							<c:choose>
								<c:when test="${map.event.active }">
									<form class="pull-right"
										action="${contextPath}/dashboard/event/addUser.htm"
										method="GET">
										<input type="hidden" name="eventId" value="${map.event.id }" />
										<input type="submit" value="Add Members"
											class="btn btn-sm btn-success" />
									</form>
								</c:when>
								<c:otherwise>
									<form class="pull-right"
										action="${contextPath}/dashboard/event/addUser.htm"
										method="GET">
										<input type="hidden" name="eventId" value="${map.event.id }" />
										<input type="submit" value="Add Members"
											class="btn btn-sm btn-success" disabled="disabled" />
									</form>
								</c:otherwise>
							</c:choose>

						</c:if>
						<strong>User List</strong>
					</div>
					<div class="panel-body">
						<table
							class="table table-responsive table-hover table-compact table-bordered">
							<thead>
								<tr>
									<th>Name</th>
									<th>Email</th>
									<c:if test="${isAdmin }">
										<th>Delete User</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${map.event.participatingUsers}" var="user">
									<tr>
										<td>${user.lastName },${user.firstName }</td>
										<td>${user.email }</td>
										<c:if test="${isAdmin }">
											<c:choose>
												<c:when test="${map.event.active }">
													<td><form
															action="${contextPath }/dashboard/event/user/delete.htm?id=${user.id}"
															method="POST">
															<input type="hidden" name="eventId"
																value="${map.event.id }" /> <input type="submit"
																value="Delete" class="btn btn-sm btn-danger" />
														</form></td>
												</c:when>
												<c:otherwise>
													<td><form
															action="${contextPath }/dashboard/event/user/delete.htm?id=${user.id}"
															method="POST">
															<input type="hidden" name="eventId"
																value="${map.event.id }" /> <input type="submit"
																value="Delete" class="btn btn-sm btn-danger"
																disabled="disabled" />
														</form></td>
												</c:otherwise>
											</c:choose>
										</c:if>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>


				</div>



				<div class="panel panel-primary">
					<div class="panel-heading">
						<strong>Item List</strong>
					</div>

					<div class="panel-body">
						<table
							class="table table-responsive table-compact table-hover table-bordered">
							<thead>
								<tr>
									<th>Item Name</th>
									<th>Requested Quantity</th>
									<th>FullFilled By</th>
									<th>Fullfilled Quantitty</th>
									<th>Total Price</th>
									<th>Delete Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${map.event.itemList}" var="item">
									<tr>
										<td>${item.name }</td>
										<td>${item.requestedQuantity }</td>

										<c:choose>
											<c:when test="${empty item.fullfilledByUser }">
												<td colspan="3"><form
														action="${contextPath }/dashboard/event/item/claimItem.htm"
														method="GET">
														<input type="hidden" name="itemId" value="${item.id }" />
														<input type="hidden" value="${map.event.id}"
															name="eventId" /> <input type="submit" value="Claim"
															class="btn btn-sm btn-success" />
													</form></td>
												<td><form
														action="${contextPath }/dashboard/event/item/deleteitem.htm"
														method="POST">
														<input type="hidden" name="itemId" value="${item.id }" />
														<input type="hidden" name="eventId"
															value="${map.event.id }" />

														<c:choose>
															<c:when test="${map.event.active }">
																<input type="submit" value="DELETE" name="submit"
																	class="btn btn-sm btn-danger" />
															</c:when>
															<c:otherwise>
																<input type="submit" value="DELETE" name="submit"
																	class="btn btn-sm btn-danger" disabled="disabled" />
															</c:otherwise>
														</c:choose>
													</form></td>


											</c:when>

											<c:otherwise>
												<td>${item.fullfilledByUser.lastName }
													${item.fullfilledByUser.firstName }</td>
												<td>${item.fullFulledQuantity }</td>
												<td>${item.totalPrice}</td>
												<td><form
														action="${contextPath }/dashboard/event/item/deleteitem.htm"
														method="POST">
														<input type="hidden" name="itemId" value="${item.id }" />
														<c:choose>
															<c:when test="${map.event.active }">
																<input type="submit" value="DELETE" name="submit"
																	class="btn btn-sm btn-danger" />
															</c:when>
															<c:otherwise>
																<input type="submit" value="DELETE" name="submit"
																	class="btn btn-sm btn-danger" disabled="disabled" />
															</c:otherwise>
														</c:choose>
													</form></td>
												</form>
												</td>
											</c:otherwise>
										</c:choose>

										<c:if test="${empty item.fullfilledByUser}">

										</c:if>

									</tr>
								</c:forEach>
							</tbody>
						</table>

						<c:if test="${isAdmin }">
							<c:if test="${map.event.active }">
								<div>
									<form:form action="${contextPath}/dashboard/event/addItem.htm"
										method="POST" modelAttribute="eventItem" class="form-inline">

										<div class="form-group">
											<input type="hidden" name="eventId" value="${map.event.id }" />

											<label for="itemName">Item Name</label>
											<form:input path="name" name="itemName" required="required"
												class="form-control" />
											<form:errors path="name" />
										</div>

										<div class="form-group">

											<label for="requestedQuantity">Requested Quantity</label>
											<form:input path="requestedQuantity" name="requestedQuantity"
												required="required" class="form-control" />
											<form:errors path="requestedQuantity" />
										</div>

										<input type="submit" value="Add Items"
											class="btn btn-sm btn-success" />
									</form:form>
								</div>
							</c:if>
						</c:if>
					</div>
				</div>
</body>
</html>