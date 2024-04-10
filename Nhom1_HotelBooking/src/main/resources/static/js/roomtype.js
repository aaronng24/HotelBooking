const app = angular.module("roomtype-app", [])
app.controller("roomtype-ctrl", function($scope, $http) {
	$scope.roomtypes = [];
	$scope.form={};

	$scope.initialize = function() {
		$http.get("/rest/roomtypes").then(resp => {
			$scope.roomtypes = resp.data;
			console.log($scope.roomtypes);
		});
	}

	//Khoi dau
	$scope.initialize();
	
	$scope.edit = function(roomtype) {
		$scope.form = angular.copy(roomtype);
		$(".nav-tabs a:eq(0)").tab('show')

	}
	
	$scope.reset=function(){
		$scope.form=[];
	}
	
	$scope.create=function(){
		var roomtype= angular.copy($scope.form);
		$http.post(`/rest/roomtypes`, roomtype).then(resp => {
			$scope.roomtypes.push(resp.data);
			$scope.reset
			alert("Thêm thành công!")
		}).catch(error => {
			alert("Lỗi thêm mới")
			console.log("Error", error);
		})
	}
	$scope.delete = function(roomtype) {
		$http.delete(`/rest/roomtypes/${roomtype.typeid}`).then(resp => {
			var index = $scope.roomtypes.findIndex(p => p.typeid == roomtype.typeid);
			$scope.roomtypes.splice(index, 1);
			$scope.reset();
			alert("Xóa thành công!")
		}).catch(error => {
			alert("Lỗi xóa")
			console.log("Error", error);
		})
	}
	
	
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
	
});