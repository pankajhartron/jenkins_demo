(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_3DetailController', Entity_3DetailController);

    Entity_3DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entity_3'];

    function Entity_3DetailController($scope, $rootScope, $stateParams, previousState, entity, Entity_3) {
        var vm = this;

        vm.entity_3 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:entity_3Update', function(event, result) {
            vm.entity_3 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
