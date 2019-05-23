var myApp = angular.module('tpApp', []);

myApp.controller("tpController", function ($scope, $http) {
    $scope.pantallaPrincipal = true;
    $scope.busquedaActual = "";
    $scope.resultados = []

    $scope.indexando = false;

    $scope.indexar = function() {
        if ($scope.indexando) return;

        $scope.indexando = true;
    }

    $scope.buscarPagPpal = function() {
        $scope.pantallaPrincipal = false;
        $scope.buscar();
    };

    $scope.buscar = function() {

        console.log("buscando");
        $scope.resultados = [
            {
                titulo: 'Ejemplo1',
                url: 'https://www.frc.utn.edu.ar/libro1'
            },
            {
                titulo: 'Ejemplo2',
                url: 'https://www.frc.utn.edu.ar/libro2'
            },
            {
                titulo: 'Ejemplo3',
                url: 'https://www.frc.utn.edu.ar/libro3'
            },
            {
                titulo: 'Ejemplo4',
                url: 'https://www.frc.utn.edu.ar/libro4'
            },
            {
                titulo: 'Ejemplo5',
                url: 'https://www.frc.utn.edu.ar/libro5'
            },
            {
                titulo: 'Ejemplo6',
                url: 'https://www.frc.utn.edu.ar/libro6'
            },
        ];
    };
});