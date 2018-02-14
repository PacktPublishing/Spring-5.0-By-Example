(function (angular) {
  'use strict';

  // Controllers
  angular.module('cms.modules.news.controllers', []).

  controller('NewsCreateController',
      ['$scope', 'NewsService',
        function ($scope, NewsService) {

          $scope.resetForm = function () {
            $scope.news = null;
          };

          $scope.create = function (customer) {
            NewsService.create(customer).then(
                function (data) {
                  console.log("Success on create Category!!!")
                }, function (err) {
                  console.log("Error on create Category!!!")
                });
          };
        }]).

  controller('NewsEditController',
      ['$scope', '$stateParams','NewsService',
        function ($scope,$stateParams, NewsService) {

          var id = $stateParams.productId;
          NewsService.findOne(id).then(function (result) {
            $scope.news = result.data;
            console.log($scope.news);
          }, function (err) {
            console.log(err);
          });

          $scope.resetForm = function () {
            $scope.product.name = null;
            $scope.product.price = null;
          };

          $scope.edit = function (productForm) {
            NewsService.update($scope.product).then(
                function (data) {
                  console.log("Success on update Product!!!")
                }, function (err) {
                  console.log("Error on update Product!!!")
                });
          };
        }]).

  controller('NewsListController',
      ['$scope', 'NewsService',
        function ($scope, NewsService) {
          NewsService.find().then(function (data) {
            $scope.newsList = data.data;
          }, function (err) {
            console.log(err);
          });
        }]).

  controller('NewsDetailController',
      ['$scope', 'NewsService',
        function ($scope, NewsService) {
          $scope.findOne = function (id) {
            NewsService.findOne(id).then(function (data) {
              $scope.product = data;
            }, function (err) {
              console.log(err);
            });
          };

        }]);
})(angular);