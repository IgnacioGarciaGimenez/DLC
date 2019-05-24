var myApp = angular.module('tpApp', []);

myApp.controller("tpController", function ($scope, $http) {

    $scope.pantallaPrincipal = true;
    $scope.busquedaActual = "";
    $scope.resultados = []

    $scope.indexando = false;

    $scope.indexar = function() {
        if ($scope.indexando) return;

        $scope.indexando = true;

        $http.get("http://localhost:8080/indexado").then(function(response) {
            $scope.indexando = false;
        });
    }

    $scope.buscarPagPpal = function() {
        $scope.pantallaPrincipal = false;
        $scope.buscar();
    };

    $scope.buscar = function(consulta) {

        $scope.get("http://localhost:8081/buscar", {params: {query: consulta}}).then(function(response) {
            $scope.resultados = response.data;
        });
        
    };
});