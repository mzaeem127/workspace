var ractive = new Ractive({
	// The `el` option can be a node, an ID, or a CSS selector.
	el : '#container',
	debug : true,
	// We could pass in a string, but for the sake of convenience
	// we're passing the ID of the <script> tag above.
	template : '#template',

	// Here, we're passing in some initial data
	data : {
		"types" : [ {
			"id" : "mssql",
			"value" : "SQL Server"
		}, {
			"id" : "oracle",
			"value" : "Oracle"
		} ]
	}
});

ractive.on({
	execute : function(event) {
		var databases = [];
		$.each(ractive.get("databases"), function(index, database) {
			console.log(JSON.stringify(database));
			if (database.checked) {
				databases.push(database.id);
			}
		});
		var data = {
			"sql" : ractive.get("sql"),
			"databases" : databases
		};
		console.log(JSON.stringify(data));
		$.ajax({
			url : "/rest/execute-sql",
			type : 'POST',
			data : JSON.stringify(data),
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log(res);
				alert("it works!");
			},
			error : function(res) {
				console.log(res);
				alert("Bad thing happend! " + res.statusText);
			}
		});
		// $('#myModal').modal('show');
	}
});

ractive.observe('selectedType', function(selectedType) {
	reloadDatabases(selectedType);
});

function reloadDatabases(selectedType) {
	$.ajax({
		url : "/rest/databases/",
		type : 'GET',
		data : 'type=' + selectedType,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(res) {
			ractive.set("databases", res);
		},
		error : function(res) {
			alert("Bad thing happend! " + res.statusText);
		}
	});
}