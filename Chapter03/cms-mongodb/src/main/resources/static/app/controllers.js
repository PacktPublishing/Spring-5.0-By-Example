(function (angular) {
  'use strict';

  // Controllers
  angular.module('cms.controllers',
      [
        'cms.modules.category.controllers',
        'cms.modules.user.controllers',
        'cms.modules.news.controllers'
      ]);
})(angular);