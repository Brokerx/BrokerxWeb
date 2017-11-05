/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    AllBrokersController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function AllBrokersController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.users = [];
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            
            
            var localUsers = localStorage.getItem("ALL_BROKERS");
            if(localUsers) {
                $scope.users = JSON.parse(localUsers);
            } else {
                //Show Error
            }
        }

        $scope.editUser = function (user) {
            localStorage.setItem("BROKER_TO_BE_EDITED",JSON.stringify(user));
            $location.path(DSConstants.PAGE_ADD_USER);
        }; 
        $scope.showLeads = function (userID, type) {
            localStorage.setItem("SHOW_LEADS_USERID",userID);
            localStorage.setItem("SHOW_LEADS_TYPE",type);
            var status = 'A,R,W';
            if(type=='Done') {
                status='D';
            }
            var requestParameter = "userID="+userID+"&status="+status+"&isBroker=true";
            HttpService.getDataFromServer(DSConstants.GET_DASHBOARD_LEAD, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetUserLeadsSuccess, $scope.onGetUserLeadsError, "GET_USER_LEADS");
//            $location.path(DSConstants.PAGE_ADD_USER);
        }; 
        $scope.onGetUserLeadsSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                localStorage.setItem("USER_LEADS", JSON.stringify(data.data));
                $location.path(DSConstants.PAGE_USER_LEADS);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetUserLeadsError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Lead");
        };
        
        $scope.addMoney = function (user) {
            localStorage.setItem("SELECTED_broker",JSON.stringify(user));
            $location.path(DSConstants.PAGE_ADD_MONEY);
        }; 
        $scope.blockUnblockUser = function (userID, isBlock) {

        };

        $scope.resendCode = function (userID) {
            alert("Clicked resendCode For UserID = " + userID);
        };

    }

    angular.module('Reachx').controller('AllBrokersController', AllBrokersController);
}());