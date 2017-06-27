(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('Entity_1Controller', Entity_1Controller);

    Entity_1Controller.$inject = ['Entity_1'];

    function Entity_1Controller(Entity_1) {

        var vm = this;

        vm.entity_1S = [];

        loadAll();

        function loadAll() {
            Entity_1.query(function(result) {
                vm.entity_1S = result;
                vm.searchQuery = null;
            });
        }
    }
})();
