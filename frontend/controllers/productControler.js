var api_url = "http://localhost:8088/api/v1";

app.controller('product-controller', function product($scope, $http, $routeParams, $location) {
    $scope.listProduct = [];
    $scope.listCategory = [];
    $scope.currentPage = 1;
    $scope.page = {
        limit: 12
    };
    $scope.product = {
        id: "",
        name: "",
        image: "",
        price: 0,
        createDate: "",
        available: true,
        category: ""
    };
    $scope.category = {
        id: "",
        name: ""
    };

    $scope.getStartIndex = function() {
        return ($scope.currentPage - 1) * $scope.page.limit;
    };

    $scope.totalItems = 0;
    $scope.totalPages = Math.ceil($scope.totalItems / $scope.page.limit);

    $scope.getPages = function() {
        var pages = [];
        for (var i = 1; i <= $scope.totalPages; i++) {
            pages.push(i);
        }
        return pages;
    };
    $scope.loadProducts = function() {
        $http.get(api_url + "/products").then(function(response) {
            $scope.listProduct = response.data;
        });
    };
    $http.get(api_url + "/categories").then(function (response) {
        $scope.listCategory = response.data;
    });
    // Lấy tổng số sản phẩm
    $http.get(api_url + "/products/count").then(function(response) {
        $scope.totalItems = response.data;
        // Khởi tạo danh sách sản phẩm
        $scope.loadProducts();
    });
});

app.controller('product-detail-controller', function detail($scope, $http, $routeParams, $rootScope, $location) {
    var id = $routeParams.id;
    $scope.product = {};
    $http.get(api_url + "/products/" + id).then(function (response) {
        $scope.product = response.data;
        $scope.product.available = $scope.product.available ? "Còn hàng" : "Hết hàng";
    });

});


