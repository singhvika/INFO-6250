<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
	<ul class="navbar-nav mr-auto">
		
		<li class="nav-item"><a class="navbar-brand" href="#">Event
				Planner</a></li>
	</ul>
	<ul class="navbar-nav ml-auto">

		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<li class="nav-item"><a class="nav-link"
					href="${contextPath}/register.htm">Signup</a></li>
				</li>
				<li class="nav-item"><a class="nav-link"
					href="${contextPath}/login.htm">Login</a></li>
				</li>
			</c:when>
			<c:otherwise>
				<li class="nav-item">Hi, ${sessionScope.user}</li>
				<li class="nav-item"><a class="nav-link"
					href="${contextPath }/dashboard.htm">Dashboard</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${contextPath}/logout.htm"></li>Logout</a>
			</c:otherwise>
		</c:choose>
</nav>