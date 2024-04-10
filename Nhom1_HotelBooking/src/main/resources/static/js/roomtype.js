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
	
	$scope.pager={
		page:0,
		size:5,
		get roomtypes(){
			var start = this.page * this.size;
			return $scope.roomtypes.slice(start,start + this.size);
		},
		get count(){
			return Math.ceil(1.0*$scope.roomtypes.length/this.size);
		},
		first(){
			this.page=0;
		},
		previous(){
			this.page--;
			if(this.page<0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page>=this.count){
				this.first();
			}
		},
		last(){
			this.page  = this.count-1;
		}
		
	}
})