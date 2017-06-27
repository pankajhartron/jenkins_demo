(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_1DetailController', Entity_1DetailController);

    Entity_1DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entity_1'];

    function Entity_1DetailController($scope, $rootScope, $stateParams, previousState, entity, Entity_1) {
        var vm = this;

        vm.entity_1 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:entity_1Update', function(event, result) {
            vm.entity_1 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
