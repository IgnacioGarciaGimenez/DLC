var myApp = angular.module('tpApp', []);

myApp.controller("tpController", function ($scope, $http) {

    $scope.pantallaPrincipal = true;
    $scope.busquedaActual = "";
    $scope.resultados = []

    $scope.indexando = false;

    $scope.indexar = function() {
        if ($scope.indexando) return;

        $scope.indexando = true;

        $http.post("http://localhost:8082/indexar").then(function(response) {
            $scope.indexando = false;
        });
    }

    $scope.buscarPagPpal = function(avanzado) {
        $scope.pantallaPrincipal = false;
        $scope.buscar(avanzado);
    };

    $scope.buscar = function(avanzado) {

        $scope.resultados = [];

        $http.get("http://localhost:8083/buscar/" + $scope.busquedaActual + "/" + (avanzado ? "1" : "0") + "/20").then(function(response) {
            $scope.resultados = response.data;
        });

    };
});