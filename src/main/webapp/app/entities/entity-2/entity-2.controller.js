(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_2Controller', Entity_2Controller);

    Entity_2Controller.$inject = ['Entity_2'];

    function Entity_2Controller(Entity_2) {

        var vm = this;

        vm.entity_2S = [];

        loadAll();

        function loadAll() {
            Entity_2.query(function(result) {
                vm.entity_2S = result;
                vm.searchQuery = null;
            });
        }
    }
})();
