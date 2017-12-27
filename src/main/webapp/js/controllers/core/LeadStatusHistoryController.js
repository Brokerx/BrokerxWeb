/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    LeadStatusHistoryController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function LeadStatusHistoryController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.leadStatus = [];
        $scope.leadDocuments = [];
        $scope.itemName='';
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            var localUsers = localStorage.getItem("LEAD_STATUS");
            var localDocuments = localStorage.getItem("LEAD_DOCUMENTS");
            $scope.itemName = localStorage.getItem("LEAD_STATUS_ITEM");
            if(localUsers) {
                $scope.leadStatus = JSON.parse(localUsers);
            } 
            if(localDocuments) {
                $scope.leadDocuments = JSON.parse(localDocuments);
            } 
        }

        $scope.getDocumentPath = function (fileName) {
            var path = localStorage.getItem("BASE_LIVE_URL");
            path = path+'files/LeadDocuments/'+fileName;
            return path;
        }; 

    }

    angular.module('Reachx').controller('LeadStatusHistoryController', LeadStatusHistoryController);
}());