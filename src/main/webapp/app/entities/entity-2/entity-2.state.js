(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entity-2', {
            parent: 'entity',
            url: '/entity-2',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_2.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-2/entity-2-s.html',
                    controller: 'Entity_2Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_2');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entity-2-detail', {
            parent: 'entity-2',
            url: '/entity-2/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_2.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-2/entity-2-detail.html',
                    controller: 'Entity_2DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_2');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entity_2', function($stateParams, Entity_2) {
                    return Entity_2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entity-2',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entity-2-detail.edit', {
            parent: 'entity-2-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-2/entity-2-dialog.html',
                    controller: 'Entity_2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_2', function(Entity_2) {
                            return Entity_2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-2.new', {
            parent: 'entity-2',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-2/entity-2-dialog.html',
                    controller: 'Entity_2DialogController',
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
                    $state.go('entity-2', null, { reload: 'entity-2' });
                }, function() {
                    $state.go('entity-2');
                });
            }]
        })
        .state('entity-2.edit', {
            parent: 'entity-2',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-2/entity-2-dialog.html',
                    controller: 'Entity_2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_2', function(Entity_2) {
                            return Entity_2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-2', null, { reload: 'entity-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-2.delete', {
            parent: 'entity-2',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-2/entity-2-delete-dialog.html',
                    controller: 'Entity_2DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entity_2', function(Entity_2) {
                            return Entity_2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-2', null, { reload: 'entity-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
