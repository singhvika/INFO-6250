<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Invite Details</title>
<%@ include file="/resources/static/head.jsp" %>
</head>
<body>
<%@ include file="/resources/static/navbar.jsp" %>

You have been invited to Join, ${map.invite.inviteForEvent.eventName}
<br>By: ${map.invite.inviteFromUser.lastName }, ${map.invite.inviteFromUser.firstName } | ${map.invite.inviteFromUser.email } 
<br>
Click the below link to join
<br>
<a href="http://localhost:8080/eventplanner/dashboard/user/acceptInvite.htm?uid=${map.invite.uniqueId }&answer=yes">Accept Invitation</a>

</body>
</html>