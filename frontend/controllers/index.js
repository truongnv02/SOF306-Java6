const app = angular.module("myapp", ["ngRoute"]);

app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix("");
    $routeProvider
        .when("/home", {
            templateUrl: "/views/client/home.html",
        })
        .when("/shop", {
            templateUrl: "/views/client/shop.html",
            controller: "product-controller",
        })
        .when("/shop-detail/:id", {
            templateUrl: "/views/client/shop-detail.html",
            controller: "product-detail-controller",
        })
        .when("/contact", {
            templateUrl: "/views/client/contact.html",
        })
        .when("/about", {
            templateUrl: "/views/client/about.html",
        })
        .when("/cart", {
            templateUrl: "/views/client/cart.html",
            controller: "cart-controller",
        })
        .otherwise({
                redirectTo: "/home"
        });
});