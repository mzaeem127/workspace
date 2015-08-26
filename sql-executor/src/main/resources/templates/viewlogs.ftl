<#assign tabname = "viewlogs">
<#include "header.ftl">
	<div class="container">
		<h1 class="page-header">Database Upgrade</h1>

		<div id='container'></div>
	</div>
	<script id='template' type='text/ractive'>
	<div>
		{{#items}}
			<div class="alert {{#if success}}alert-success{{else}}alert-danger{{/if}}">
				{{#if success}}
				<div class="fa fa-check fa-6">Success</div>
				{{else}}
				<div class="fa fa-exclamation-triangle fa-6">{{errorMessage}}</div>
				<br />
				{{/if}}
				<div>{{sqlCommand}}</div>
			</div>
		{{/items}}
	</div>
	</script>
	<script src="viewlog.js"></script>
<#include "footer.ftl">