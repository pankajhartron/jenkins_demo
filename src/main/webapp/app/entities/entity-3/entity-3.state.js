(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entity-3', {
            parent: 'entity',
            url: '/entity-3',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_3.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-3/entity-3-s.html',
                    controller: 'Entity_3Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_3');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entity-3-detail', {
            parent: 'entity-3',
            url: '/entity-3/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'myappApp.entity_3.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-3/entity-3-detail.html',
                    controller: 'Entity_3DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_3');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entity_3', function($stateParams, Entity_3) {
                    return Entity_3.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entity-3',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entity-3-detail.edit', {
            parent: 'entity-3-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-3/entity-3-dialog.html',
                    controller: 'Entity_3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_3', function(Entity_3) {
                            return Entity_3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-3.new', {
            parent: 'entity-3',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-3/entity-3-dialog.html',
                    controller: 'Entity_3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                password: null,
                                otp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entity-3', null, { reload: 'entity-3' });
                }, function() {
                    $state.go('entity-3');
                });
            }]
        })
        .state('entity-3.edit', {
            parent: 'entity-3',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-3/entity-3-dialog.html',
                    controller: 'Entity_3DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_3', function(Entity_3) {
                            return Entity_3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-3', null, { reload: 'entity-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-3.delete', {
            parent: 'entity-3',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-3/entity-3-delete-dialog.html',
                    controller: 'Entity_3DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entity_3', function(Entity_3) {
                            return Entity_3.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-3', null, { reload: 'entity-3' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
