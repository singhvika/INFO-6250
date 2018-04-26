<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/resources/static/head.jsp"%>
</head>
<body>

	<%@ include file="/resources/static/navbar.jsp"%>

	<div
		class="col-md-4 col-md-offset-4 col-xs-12 col-lg-6 col-lg-offset-3 col-sm-12">

		<div class="panel panel-primary">
			<div class="panel-heading">

				<form action="dashboard/createEvent.htm">
					<input type="submit" value="Create Event"
						class="btn btn-sm btn-success pull-right" />
				</form>
				<strong>EVENT LIST</strong>
			</div>
			<div class="panel-body">

				<div>

					<c:choose>
						<c:when test="${fn:length(map.eventList) gt 0}">
							<table
								class="table table-condensed table-hover table-responsive table-bordered">
								<thead>
									<tr>
										<th>Event Name</th>
										<th>Event By</th>
										<th>From Date</th>
										<th>To Date</th>
										<th>Active</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${map.eventList }" var="event">

										<tr onclick="gotoEvent(this)" class="clickable-event-row"
											data-href="${contextPath }/dashboard/event.htm?id=${event.id}">

											<td>${event.eventName }</td>
											<td>${event.createdByUser.firstName },
												${event.createdByUser.lastName }</td>
											<td>${event.fromDate }</td>
											<td>${event.toDate }</td>
											<td>${event.active }</td>


											</a>
									</c:forEach>

								</tbody>

							</table>
							<br>
							<ul class="pagination">
								<c:forEach begin="1" end="${map.totalPages }" var="page">
									<li><a href="${contextPath }/dashboard.htm?page=${page}">${page }</a></li>
								</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<div class="alert alert-infor">
								<strong>You don't Have Any Events</strong>
							</div>
						</c:otherwise>
					</c:choose>




				</div>
			</div>
		</div>



	</div>
	<script>
		function gotoEvent(dis) {
			var href_val = $(dis).attr("data-href");
			window.location = href_val

		}
	</script>

</body>
</html>