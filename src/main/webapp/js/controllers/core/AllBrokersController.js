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
            alert("userID: "+userID+" Type: "+type)
//            $location.path(DSConstants.PAGE_ADD_USER);
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