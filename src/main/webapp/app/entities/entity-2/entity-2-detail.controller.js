(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_2DetailController', Entity_2DetailController);

    Entity_2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entity_2'];

    function Entity_2DetailController($scope, $rootScope, $stateParams, previousState, entity, Entity_2) {
        var vm = this;

        vm.entity_2 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:entity_2Update', function(event, result) {
            vm.entity_2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
