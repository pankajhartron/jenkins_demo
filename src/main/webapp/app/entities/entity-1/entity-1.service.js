(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('Entity_1', Entity_1);

    Entity_1.$inject = ['$resource'];

    function Entity_1 ($resource) {
        var resourceUrl =  'api/entity-1-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
