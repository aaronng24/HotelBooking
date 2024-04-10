const app = angular.module("room-app", [])
app.controller("room-ctrl", function($scope, $http) {
	$scope.rooms = [];
	$scope.form = {};

	$scope.initialize = function() {
		$http.get("/rest/room").then(resp => {
			$scope.rooms = resp.data;
		});
	}

	//Khoi dau
	$scope.initialize();

	$scope.findRoom = function(find) {
		$http.get(`/rest/room/${find}`).then(resp => {
			$scope.rooms = resp.data;
		})
	}


	$scope.edit = function(room) {
		$scope.form = angular.copy(room);
		$(".nav-tabs a:eq(0)").tab('show')

	}

	$scope.reset = function() {
		$scope.form = [];
	}

	$scope.create = function() {
		var room = angular.copy($scope.form);
		$http.post(`/rest/room`, room).then(resp => {
			$scope.rooms.push(resp.data);
			$scope.reset
			alert("Thêm thành công!")
		}).catch(error => {
			alert("Lỗi thêm mới")
			console.log("Error", error);
		})
	}
	$scope.delete = function(room) {
		$http.delete(`/rest/room/${room.roomid}`).then(resp => {
			var index = $scope.rooms.findIndex(p => p.roomid == room.roomid);
			$scope.rooms.splice(index, 1);
			$scope.reset();
			alert("Xóa thành công!")
		}).catch(error => {
			alert("Lỗi xóa")
			console.log("Error", error);
		})
	}


	$scope.pager = {
		page: 0,
		size: 5,
		get rooms() {
			var start = this.page * this.size;
			return $scope.rooms.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.rooms.length / this.size);
		},
		first() {
			this.page = 0;
		},
		previous() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}

	}

});