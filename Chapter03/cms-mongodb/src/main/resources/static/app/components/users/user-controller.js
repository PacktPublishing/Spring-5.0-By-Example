(function (angular) {
  'use strict';

  // Controllers
  angular.module('cms.modules.user.controllers', []).

  controller('UserCreateController',
      ['$scope', 'UserService',
        function ($scope, UserService) {

          $scope.resetForm = function () {
            $scope.user = null;
          };

          $scope.create = function (user) {
            UserService.create(user).then(
                function (data) {
                  console.log("Success on create User!!!")
                }, function (err) {
                  console.log("Error on create User!!!")
                });
          };
        }]).

  controller('UserListController',
      ['$scope', 'UserService',
        function ($scope, UserService) {
          UserService.find().then(function (data) {
            $scope.users = data;
          }, function (err) {
            console.log(err);
          });
        }]).

  controller('UserDetailController',
      ['$scope', 'UserService',
        function ($scope, UserService) {
          $scope.findOne = function (id) {
            UserService.findOne(id).then(function (data) {
              $scope.user = data;
            }, function (err) {
              console.log(err);
            });
          };

        }]);
})(angular);