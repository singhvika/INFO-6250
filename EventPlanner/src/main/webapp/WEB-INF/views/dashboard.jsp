<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/resources/static/head.jsp" %>
</head>
<body>
<%@ include file="/resources/static/navbar.jsp" %>
<a href="dashboard/createEvent.htm"">Create Event</a>

<br>

EVENT LIST:

<br>

<c:forEach items="${map.eventList}" var="event">
<br>
<a href="${pageContext.request.contextPath}/dashboard/event.htm?id=${event.id}">${event.eventName}</a>
</c:forEach>

${map.name}
</body>
</html>