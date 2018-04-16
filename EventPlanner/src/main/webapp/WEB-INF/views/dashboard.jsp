<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<title>DASHBOARD</title>

</head>
<body>
<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
  <a class="navbar-brand" href="#">Event Planner</a>
  <ul class="navbar-nav">
    <li class="nav-item active">
      <a class="nav-link" href="#">Login</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="register.htm">Signup</a>
    </li>
    
    <li class="nav-item">
      <a class="nav-link disabled" href="#">Disabled</a>
    </li>
  </ul>
</nav>
<br>

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
	
	<br>
	
	USER LOGGEDIIN: ${sessionScope.user} 

</body>
</html>
