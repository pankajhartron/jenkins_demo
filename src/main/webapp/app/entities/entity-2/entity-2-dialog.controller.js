(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_2DialogController', Entity_2DialogController);

    Entity_2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entity_2'];

    function Entity_2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entity_2) {
        var vm = this;

        vm.entity_2 = entity;
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
            if (vm.entity_2.id !== null) {
                Entity_2.update(vm.entity_2, onSaveSuccess, onSaveError);
            } else {
                Entity_2.save(vm.entity_2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:entity_2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
