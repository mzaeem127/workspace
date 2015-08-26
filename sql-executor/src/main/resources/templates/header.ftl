<!doctype html>
<html lang='en-US'>
<head>
<meta charset='utf-8'>
<title>Database Upgrade</title>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script src=//code.jquery.com/jquery-2.1.4.js></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src='//cdn.ractivejs.org/latest/ractive.min.js'></script>
<script src='//cdn.jsdelivr.net/ractive.load/latest/ractive-load.min.js'></script>
<link rel="stylesheet" href="app.css">
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Web SQL Executor <small>Executes
						SQLs against multiple databases</small></a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li <#if tabname == "home">class="active"</#if>><a href="home.vml">Home</a></li>
					<li <#if tabname == "viewlogs">class="active"</#if>><a href="/viewlogs.vml">View Logs</a></li>
				</ul>
			</div>
		</div>
	</div>