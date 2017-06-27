(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_1DeleteController',Entity_1DeleteController);

    Entity_1DeleteController.$inject = ['$uibModalInstance', 'entity', 'Entity_1'];

    function Entity_1DeleteController($uibModalInstance, entity, Entity_1) {
        var vm = this;

        vm.entity_1 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entity_1.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
