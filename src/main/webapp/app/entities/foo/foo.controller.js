(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('FooController', FooController);

    FooController.$inject = ['Foo'];

    function FooController(Foo) {

        var vm = this;

        vm.foos = [];

        loadAll();

        function loadAll() {
            Foo.query(function(result) {
                vm.foos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
