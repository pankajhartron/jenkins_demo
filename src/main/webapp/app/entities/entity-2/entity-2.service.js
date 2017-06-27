(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('Entity_2', Entity_2);

    Entity_2.$inject = ['$resource'];

    function Entity_2 ($resource) {
        var resourceUrl =  'api/entity-2-s/:id';

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
