'use strict';

/*
 *  Using app module as "Reachx", this will be used through out the application.
 *  app.js to keep all the core functions of the application
 */


/**
 *  plugins injected :
 *  angular-animate - Enable animation support
 *  angular-cookies - A convenient wrapper for reading and writing browser cookies
 *  angular-resource - Interaction support with RESTful services via the $resource service
 *  angular-route - Routing and deeplinking services and directives for angular apps
 *  angular-sanitize - Functionality to sanitize HTML
 *  angular-touch - Touch events and other helpers for touch-enabled devices
 */
angular.module('Reachx', ['ngCookies','ngRoute','ngResource','ngAnimate', 'ngSanitize', 'ngTouch', 'angularFileUpload']);

angular.module('Reachx').config(['$routeProvider',
    // All Routing goes here
    
    function ($routeProvider) {
        //$routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginController'});
        $routeProvider.when('/allUsers', {templateUrl: 'pages/AllUsers.html?v=150914', controller: 'AllUsersController'});
        $routeProvider.when('/allBrokers', {templateUrl: 'pages/AllBrokers.html?v=150914', controller: 'AllBrokersController'});
        $routeProvider.when('/dashboard', {templateUrl: 'pages/dashboard.html?v=150914', controller: 'DashboardController'});
        $routeProvider.when('/addCategory', {templateUrl: 'pages/AddCategory.html?v=150914', controller: 'AddCategoryController'});
        $routeProvider.when('/addMoney', {templateUrl: 'pages/AddMoney.html?v=150914', controller: 'AddMoneyController'});
        $routeProvider.when('/categories', {templateUrl: 'pages/AllCategories.html?v=150914', controller: 'CategoryController'});
        $routeProvider.when('/adsReport', {templateUrl: 'pages/AdsReport.html?v=150914', controller: 'AdsReportController'});
        $routeProvider.when('/walletSummary', {templateUrl: 'pages/WalletSummary.html?v=150914', controller: 'WalletSummaryController'});

    }]);


/**
 * Do animations of view entering and leaving the ng-view.
 * So when location change starts the application adds and removes classes from the views, causing the
 * css3 animation to work on the views.
 */
angular.module('Reachx').run(function ($rootScope, $location) {
    $rootScope.$on("$locationChangeStart", function (event, next, current) {
        if (!$location.path().match(/^\/?$/) && !$rootScope.mainVisitedOnce) {
            $rootScope.mainVisitedOnce = true;
        }
    });
});

// For file upload
angular.module('Reachx').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

angular.module('Reachx').service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl) {
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function () {
            })
            .error(function () {
            });
    }
}]);


/**
 * THis will keep watch on the rootScope and will let us know change in the URL of  the app.
 * Using this URL we will hide or show the header and side panel
 */
angular.module('Reachx').run(function ($rootScope, $location, DSConstants, $timeout ) {
    $rootScope.$watch(function () {
            return $location.path();
        },
        function (a) {
//            //used when the application is refreshed to create menu after few minutes when UI is ready
//            if( $('.application-menu').length<=0){
//                $timeout(function(){
//                    if (a == DSConstants.PAGE_ADD_USER) {
//                        $('.application-menu .addUser').addClass('active');
//                    } else if (a == DSConstants.PAGE_ALL_USERS) {
//                        $('.application-menu .AllUser').addClass('active');
//                    }
//                },1000);
//            }

            /**
             * remoce focus from all menu and addd focus to teh item url is made for.
             */
            $(".main-menu li").removeClass('active');

            if (a == DSConstants.PAGE_ALL_BROKERS) {
                $('.main-menu .allUsers').addClass('active');
            }else if (a == DSConstants.PAGE_ALL_USERS) {
                $('.main-menu .allUsersNonBroker').addClass('active');
            } else if (a == DSConstants.PAGE_ADD_USER) {
                $('.main-menu .addUser').addClass('active');
            } else if (a == DSConstants.PAGE_DRUGS) {
                $('.main-menu .allDrugs').addClass('active');
            } else if (a == DSConstants.PAGE_ADD_DRUG) {
                $('.main-menu .addDrug').addClass('active');
            } else if (a == DSConstants.PAGE_CATEGORIES) {
                $('.main-menu .allCategories').addClass('active');
            } else if (a == DSConstants.PAGE_ADD_CATEGORY) {
                $('.main-menu .addCategory').addClass('active');
            } else if (a == DSConstants.PAGE_SAR) {
                $('.main-menu .allSAR').addClass('active');
            } else if (a == DSConstants.PAGE_ADD_SAR) {
                $('.main-menu .addSAR').addClass('active');
            } else if (a == DSConstants.PAGE_SYNTHESIS) {
                $('.main-menu .allSynthesis').addClass('active');
            } else if (a == DSConstants.PAGE_ADD_SYNTHESIS) {
                $('.main-menu .addSynthesis').addClass('active');
            } else if (a == DSConstants.PAGE_UPLOAD_EXEL) {
                $('.main-menu .importUser').addClass('active');
            }
//            if (a == DSConstants.PAGE_PATIENT) {
//                $('.application-menu .patient').addClass('active');
//            }
//            if (a == DSConstants.PAGE_CHARTING) {
//                $('.application-menu .charting').addClass('active');
//            }
//            if (a == DSConstants.PAGE_REPORTS) {
//                $('.application-menu .reports').addClass('active');
//            }
//            if(a == DSConstants.PAGE_COMMUNICATION){
//                $('.application-menu .communication').addClass('active');
//            }
        });
});

/*angular.module('Reachx').filter('objectAsArray', function () {
    return function (object) {
        var array = [];
        for (var item in object) {
            array.push(object[item]);
        }
        return array;
    }
});*/

angular.module('Reachx').directive('jq:animate', function (jQueryExpression, templateElement) {
    // I get run once on compile (when ng:repeat turns me into a template)
    // parse the jQeuryExperssio -> "dropdown;250" and extract animation information.
    return function (instanceElement) {
        // I get run on each instance, (when ng:repeat needs a new <li> to insert into the DOM)
        // instanceElement is already jQuery selector
        // use the animation instructions extracted above to animate
        instanceElement.show('slow');
        // not sure in instanceElement is part of the dom at this point, so you
        //may have to do setTimeout(0) and delay the execution of the animation
    }
});

angular.module('Reachx').run(function ($rootScope, $window) {
    // publish current transition direction on rootScope
    $rootScope.direction = 'ltr';
    // listen change start events
    $rootScope.$on('$routeChangeStart', function (event, next, current) {
        $rootScope.direction = 'rtl';
        if (current && next && (current.depth > next.depth)) {
            $rootScope.direction = 'ltr';
        }
        // back
        $rootScope.back = function () {
            $window.history.back();
        }
    });
});



/*
angular.module('Reachx').filter('slice', function() {
    return function(arr, start, end) {
        return arr.slice(start, end);
    };
});*/
