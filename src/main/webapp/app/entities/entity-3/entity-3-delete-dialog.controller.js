(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_3DeleteController',Entity_3DeleteController);

    Entity_3DeleteController.$inject = ['$uibModalInstance', 'entity', 'Entity_3'];

    function Entity_3DeleteController($uibModalInstance, entity, Entity_3) {
        var vm = this;

        vm.entity_3 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entity_3.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
