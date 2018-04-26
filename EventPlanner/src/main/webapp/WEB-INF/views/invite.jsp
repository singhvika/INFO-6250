<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Invite Details</title>
<%@ include file="/resources/static/head.jsp"%>
</head>
<body>
	<%@ include file="/resources/static/navbar.jsp"%>
	<div
		class="col-lg-10 col-lg-offset-1 col-xs-10 col-xs-offset-1 col-sm-10 col-sm-offset-1 col-md-10 col-md-offset-1 well well-sm">

		You have been invited to Join, <strong>${map.invite.inviteForEvent.eventName}<strong>
				<br>By:<strong> ${map.invite.inviteFromUser.lastName },
					${map.invite.inviteFromUser.firstName } <strong> |
						${map.invite.inviteFromUser.email } <br> Click the below link
						to join <br>
						<form
							action="http://localhost:8080/eventplanner/dashboard/user/acceptInvite.htm?uid=${map.invite.uniqueId }&answer=yes">
							<input type="submit" value="Accept" class="btn btn-success" />
						</form>
	</div>
</body>
</html>