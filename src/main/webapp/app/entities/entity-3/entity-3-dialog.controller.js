(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_3DialogController', Entity_3DialogController);

    Entity_3DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entity_3'];

    function Entity_3DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entity_3) {
        var vm = this;

        vm.entity_3 = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entity_3.id !== null) {
                Entity_3.update(vm.entity_3, onSaveSuccess, onSaveError);
            } else {
                Entity_3.save(vm.entity_3, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:entity_3Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
