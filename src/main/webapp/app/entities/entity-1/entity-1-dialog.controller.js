(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_1DialogController', Entity_1DialogController);

    Entity_1DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entity_1'];

    function Entity_1DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entity_1) {
        var vm = this;

        vm.entity_1 = entity;
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
            if (vm.entity_1.id !== null) {
                Entity_1.update(vm.entity_1, onSaveSuccess, onSaveError);
            } else {
                Entity_1.save(vm.entity_1, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:entity_1Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
