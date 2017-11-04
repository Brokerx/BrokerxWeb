/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    AdsReportController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function AdsReportController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.ads = [];
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            
            
            var localAds = localStorage.getItem("ALL_ADS");
            if(localAds) {
                $scope.ads = JSON.parse(localAds);
            } else {
                //Show Error
            }
        }

        $scope.getDuration = function(startDate, endDate) {
            var date1 = new Date(startDate);
            var date2 = new Date(endDate);
            //var diffDays = date2.getDate() - date1.getDate();
            //return Math.abs(diffDays);
            var timeDiff = Math.abs(date2.getTime() - date1.getTime());
            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            return diffDays;
        };


    }

    angular.module('Reachx').controller('AdsReportController', AdsReportController);
}());