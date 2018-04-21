<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%@ include file="/resources/static/head.jsp" %>
<title>Home</title>
</head>
<body>
<%@ include file="/resources/static/navbar.jsp" %>
<br>

<div class="jumbotron">
  <div class="container">
		<br>
		<h1>Planning Events is Simple</h1>
		<br>
		<h2>Step 1: Sign-up or Login</h2>
		<br>
		<h2>Step 2: Create Events and Invite your friends</h2>
		<br>
		<h2>Step 3: Add Items and decide who brings what</h2>
		<br>
		<h2>Step 4: Let the Party Begin</h2>
		<br>
		<h2>Simply signup or login to begin</h2>
	</div> 
</div>

	
	
	<br>
	
	USER LOGGEDIIN: ${sessionScope.user} 

</body>
</html>
