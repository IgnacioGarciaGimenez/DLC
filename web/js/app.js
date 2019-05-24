var myApp = angular.module('tpApp', []);

myApp.controller("tpController", function ($scope, $http) {

    $scope.pantallaPrincipal = true;
    $scope.busquedaActual = "";
    $scope.resultados = []

    $scope.indexando = false;

    $scope.indexar = function() {
        if ($scope.indexando) return;

        $scope.indexando = true;

        $http.get("http://localhost:8082/indexar").then(function(response) {
            $scope.indexando = false;
        });
    }

    $scope.buscarPagPpal = function() {
        $scope.pantallaPrincipal = false;
        $scope.buscar();
    };

    $scope.buscar = function() {

        $http.get("http://localhost:8081/buscar/query", {params: {query: $scope.busquedaActual}}).then(function(response) {
            $scope.resultados = response.data;
        });

    };
});