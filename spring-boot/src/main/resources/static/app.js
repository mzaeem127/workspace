var app = angular.module("myapp", [ 'ngRoute', 'ui.bootstrap' ]);

app.config(function($routeProvider) {
	$routeProvider.when("/part1", {
		templateUrl : "/part1.html",
		controller : "part1"
	});
	$routeProvider.when("/part2", {
		templateUrl : "/part2.html",
		controller : "part2"
	});
	$routeProvider.otherwise({
		"redirectTo" : "/part1"
	});
});