<!--<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
	<ul class="navbar-nav mr-auto">
		
		<li class="nav-item"><a class="navbar-brand" href="#"></a></li>
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
</nav>-->


 <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Event
				Planner</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
           <c:choose>
			<c:when test="${sessionScope.user == null}">
				<li><a class="nav-link"
					href="${contextPath}/register.htm">Signup</a></li>
				</li>
				<li><a class="nav-link"
					href="${contextPath}/login.htm">Login</a></li>
				</li>
			</c:when>
			<c:otherwise>
				<li>Hi, ${sessionScope.user}</li>
				<li><a class="nav-link"
					href="${contextPath }/dashboard.htm">Dashboard</a></li>
				<li><a class="nav-link"
					href="${contextPath}/logout.htm"></li>Logout</a>
			</c:otherwise>
		</c:choose>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    
    <br><br><br><br>


