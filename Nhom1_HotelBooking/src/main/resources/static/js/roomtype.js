const app = angular.module("roomtype-app",[]);
app.controller("roomtype-ctrl", function ($scope, $http) {
    $scope.roomtypes=[];
    $scope.form={};
    
    $scope.initialize= function(){
		$http.get("/rest/roomtypes").then(resp=>{
			$scope.roomtypes=resp.data;
			console.log($scope.roomtypes);
		}).catch(error=>{
			console.log("Error",error);
		})
	}
	$scope.initialize();
})