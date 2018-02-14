
(function (angular) {
  'use strict';

  angular.module('cms', [
    'ui.router',
    'cms.controllers',
    'cms.services'
  ]).config(function($stateProvider,$httpProvider){

    $stateProvider.state('categories',{
      url:'/categories',
      templateUrl:'app/components/categories/view/categories.html',
      controller:'CategoryListController'
    }).state('create-category',{
      url:'/create-category',
      templateUrl:'app/components/categories/view/create-category.html',
      controller:'CategoryCreateController'
    }).state('category-detail',{
      url:'/category/:id',
      templateUrl:'app/components/categories/view/category.html',
      controller:'CategoryDetailController'
    });

  }).run(function($state){
    $state.go('categories');
  });
})(angular);
