/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    WalletSummaryController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function WalletSummaryController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.orders = [];
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            
            
            var localSummary = localStorage.getItem("WALLET_SUMMARY");
            if(localSummary) {
                $scope.orders = JSON.parse(localSummary);
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

    angular.module('Reachx').controller('WalletSummaryController', WalletSummaryController);
}());