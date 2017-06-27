(function() {
    'use strict';
    angular
        .module('myappApp')
        .factory('Entity_3', Entity_3);

    Entity_3.$inject = ['$resource'];

    function Entity_3 ($resource) {
        var resourceUrl =  'api/entity-3-s/:id';

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
