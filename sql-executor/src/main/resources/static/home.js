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
			"id" : "mysql",
			"value" : "MySQL Server"
		}, {
			"id" : "oracle",
			"value" : "Oracle Server"
		}, {
			"id" : "mssql",
			"value" : "SQL Server"
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
		if (databases.length == 0) {
			// alert("You must select at least one database");
			showModal('Error', 'You must select at least one database');
			return;
		}
		if (ractive.get("sql").length == 0) {
			// alert("Please enter at least one SQL statement to be exected");
			showModal('Error',
					'Please enter at least one SQL statement to be exected');
			return;
		}

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
				window.location.href = "viewlog.vml?id=" + res.executionId;
				// alert("it works!");
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

function showModal(title, message) {
	$('.modal-title').html(title);
	$('.modal-body').html('<p>' + message + '</p>');
	$('#myModal').modal('show');
}