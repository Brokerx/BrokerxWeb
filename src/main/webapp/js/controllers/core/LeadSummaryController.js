/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    LeadSummaryController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function LeadSummaryController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.userLeads = [];
        $scope.title = '';
        $scope.type = '';
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            var localUsers = localStorage.getItem("LEAD_SUMMARY");
            $scope.type = localStorage.getItem("GET_DASHBOARD_LEADS_TYPE");
            if ($scope.type == 'Active') {
                $scope.title='All Active Deals';
            } else if ($scope.type == 'Done') {
                $scope.title='All Completed Deals';
            } else {
                $scope.title='All Rejected Deals';
            }
            if(localUsers) {
                $scope.userLeads = JSON.parse(localUsers);
            } else {
                //Show Error
            }
        }
        
    $scope.showLeadHistory = function (leadID) {
            var requestParameter = "leadID="+leadID;
            HttpService.getDataFromServer(DSConstants.GET_LEAD_HISTORY, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetLeadHistorySuccess, $scope.onGetLeadHistoryError, "GET_USER_LEADS_HISTORY");
//            $location.path(DSConstants.PAGE_ADD_USER);
        }; 
        $scope.onGetLeadHistorySuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                localStorage.setItem("LEAD_HISTORY", JSON.stringify(data.data));
                $location.path(DSConstants.PAGE_LEAD_HISTORY);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetLeadHistoryError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Lead History");
        };
        
        $scope.leadStatusCalled = false;
        $scope.leadDocumentsCalled = false;
    $scope.showLeadStatus = function (leadID, itemName) {
            var requestParameter = "leadID="+leadID;
            localStorage.setItem("LEAD_STATUS_ITEM", itemName);
            HttpService.getDataFromServer(DSConstants.GET_LEAD_STATUS, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetLeadStatusSuccess, $scope.onGetLeadStatusError, "GET_LEAD_STATUS");
            HttpService.getDataFromServer(DSConstants.GET_LEAD_DOCUMENTS, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetLeadDocumentsSuccess, $scope.onGetLeadDocumentsError, "GET_LEAD_DOCUMENTS");
        }; 
        $scope.onGetLeadStatusSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                localStorage.setItem("LEAD_STATUS", JSON.stringify(data.data));
            } 
            $scope.leadStatusCalled = true;
            $scope.gotoStatushistory();
        };
        $scope.onGetLeadStatusError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Lead History");
        };
        
        $scope.onGetLeadDocumentsSuccess = function (data) {
            
            if (data.messageID == 100) {
                localStorage.setItem("LEAD_DOCUMENTS", JSON.stringify(data.data));
            } 
            $scope.leadDocumentsCalled = true;
            $scope.gotoStatushistory();
        };
        $scope.onGetLeadDocumentsError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Lead History");
        };
        
        $scope.gotoStatushistory = function () {
            if ($scope.leadStatusCalled && $scope.leadDocumentsCalled) {
                $scope.leadStatusCalled = false;
                $scope.leadDocumentsCalled = false;
                DSUtils.hideLoader();
                $location.path(DSConstants.PAGE_LEAD_STATUS);
            }
        };
    }

    angular.module('Reachx').controller('LeadSummaryController', LeadSummaryController);
}());