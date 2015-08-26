var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var ractive = new Ractive({
	// The `el` option can be a node, an ID, or a CSS selector.
	el : '#container',
	debug : true,
	// We could pass in a string, but for the sake of convenience
	// we're passing the ID of the <script> tag above.
	template : '#template',

	// Here, we're passing in some initial data
	data : {}
});

function loadData(id) {
	//alert(id);
	$.ajax({
		url : "/rest/execution/",
		type : 'GET',
		data : 'id=' + id,
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function(res) {
			console.log(res);
			ractive.set("items", res);
		},
		error : function(res) {
			alert("Bad thing happend! " + res.statusText);
		}
	});
}
loadData(getUrlParameter("id"));