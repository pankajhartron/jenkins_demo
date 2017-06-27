(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entity-1', {
            parent: 'entity',
            url: '/entity-1',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_1.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-1/entity-1-s.html',
                    controller: 'Entity_1Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_1');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entity-1-detail', {
            parent: 'entity-1',
            url: '/entity-1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_1.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-1/entity-1-detail.html',
                    controller: 'Entity_1DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_1');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entity_1', function($stateParams, Entity_1) {
                    return Entity_1.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entity-1',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entity-1-detail.edit', {
            parent: 'entity-1-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-1/entity-1-dialog.html',
                    controller: 'Entity_1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_1', function(Entity_1) {
                            return Entity_1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-1.new', {
            parent: 'entity-1',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-1/entity-1-dialog.html',
                    controller: 'Entity_1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entity-1', null, { reload: 'entity-1' });
                }, function() {
                    $state.go('entity-1');
                });
            }]
        })
        .state('entity-1.edit', {
            parent: 'entity-1',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-1/entity-1-dialog.html',
                    controller: 'Entity_1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_1', function(Entity_1) {
                            return Entity_1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-1', null, { reload: 'entity-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-1.delete', {
            parent: 'entity-1',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-1/entity-1-delete-dialog.html',
                    controller: 'Entity_1DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entity_1', function(Entity_1) {
                            return Entity_1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-1', null, { reload: 'entity-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
