(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_3Controller', Entity_3Controller);

    Entity_3Controller.$inject = ['Entity_3'];

    function Entity_3Controller(Entity_3) {

        var vm = this;

        vm.entity_3S = [];

        loadAll();

        function loadAll() {
            Entity_3.query(function(result) {
                vm.entity_3S = result;
                vm.searchQuery = null;
            });
        }
    }
})();
