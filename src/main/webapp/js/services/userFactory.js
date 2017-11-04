(function () {

    UserFactory.$inject = ['$scope'];

    function UserFactory($scope) {

        var obj = {};
        obj = initializeHttpServiceFunction($scope);

        return obj;
    }
    ;

    var initializeHttpServiceFunction = function ($scope) {
        return {
            /**
             * To be used to call service, following will be input params
             * @param {type} url - service url
             * @param {type} methodType - get, jsonp, post
             * @param {type} requestParameters
             * @param {type} httpBody
             * @param {type} isAsync
             * @param {type} timeOut- service timeout
             * @param {type} successCallBack - success callback method for the request
             * @param {type} errorCallBack - error callback method for the request
             * @param {type} requestIdentifier
             * @returns {undefined}
             */
            
        };
    };

    angular.module('Reachx').factory('UserFactory', UserFactory);

}());
