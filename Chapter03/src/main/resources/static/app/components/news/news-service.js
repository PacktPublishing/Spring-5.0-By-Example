(function (angular) {
  'use strict';

  /* Services */
  angular.module('cms.modules.news.services', []).
  service('NewsService', ['$http',
    function ($http) {

      var serviceAddress = 'http://localhost:8080';
      var urlCollections = serviceAddress + '/api/news';
      var urlBase = serviceAddress + '/api/news/';

      this.find = function () {
        return $http.get(urlCollections);
      };

      this.findOne = function (id) {
        return $http.get(urlBase + id);
      };

      this.create = function (data) {
        return $http.post(urlBase, data);
      };

      this.update = function (data) {
        return $http.put(urlBase + data._id, data);
      };

      this.remove = function (data) {
        return $http.delete(urlBase + '/id/' + data._id, data);
      };
    }
  ]);
})(angular);