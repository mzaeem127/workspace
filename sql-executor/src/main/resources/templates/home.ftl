<#assign tabname = "home">
<#include "header.ftl">
	<div class="container">
		<div id='container'></div>
	</div>
	<script id='template' type='text/ractive'>
	<div>
		<div class="pull-left col-md-10">
			<div>
				Database Type: <select value="{{selectedType}}">
					{{#types}}
					<option value="{{id}}">{{value}}</option>
					{{/types}}
				</select>
			</div>
			<div class="form-group">
				<label for="script">Enter the script</label>
				<textarea class="form-control" rows="25" id="script" value="{{sql}}"></textarea>
			</div>
			<div>
				<input type="button" value="Execute" class="btn-primary" on-click="execute">
			</div>
		</div>
		<div class="pull-right col-md-2">
			<div>
				Select Databases
			</div>
			{{#databases}}
			<div class="checkbox">
				<label><input type="checkbox" value="{{id}}" checked="{{.checked}}"> {{description}}</label>
			</div>
			{{/databases}}
		</div>
	</div>
	</script>


	<div class="modal fade" id="myModal" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Modal title</h4>
				</div>
				<div class="modal-body">
					<p>One fine body&hellip;</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<script src="home.js"></script>
<#include "footer.ftl">
