app.controller("part1", function($scope, $location) {
	$scope.message = "";
	$scope.left = function() {
		return 100 - $scope.message.length;
	};
	$scope.clear = function() {
		$scope.message = "";
	};
	$scope.save = function() {
		alert("Note Saved");
		$location.path("part2");
	};
});

app.controller("part2", function($scope, $location, $modal) {
	$scope.message = "";
	$scope.left = function() {
		return 100 - $scope.message.length;
	};
	$scope.clear = function() {
		$scope.message = "";
		$modal.open({
			templateUrl : 'part1.html',
			controller : "part1"
		});
	};
	$scope.save = function() {
		alert("Note Saved Successfully! - part 2");
	};
});