(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_2DeleteController',Entity_2DeleteController);

    Entity_2DeleteController.$inject = ['$uibModalInstance', 'entity', 'Entity_2'];

    function Entity_2DeleteController($uibModalInstance, entity, Entity_2) {
        var vm = this;

        vm.entity_2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entity_2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
