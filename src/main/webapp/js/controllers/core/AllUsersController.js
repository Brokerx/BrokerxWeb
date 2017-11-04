/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    AllUsersController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function AllUsersController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.users = [];
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            
            
            var localUsers = localStorage.getItem("ALL_USERS");
            if(localUsers) {
                $scope.users = JSON.parse(localUsers);
            } else {
                //Show Error
            }
        }

        $scope.editUser = function (user) {
            localStorage.setItem("USER_TO_BE_EDITED",JSON.stringify(user));
            $location.path(DSConstants.PAGE_ADD_USER);
        }; 
        $scope.addMoney = function (user) {
            localStorage.setItem("SELECTED_USER",JSON.stringify(user));
            $location.path(DSConstants.PAGE_ADD_MONEY);
        }; 
        $scope.blockUnblockUser = function (userID, isBlock) {

        };

        $scope.resendCode = function (userID) {
            alert("Clicked resendCode For UserID = " + userID);
        };

    }

    angular.module('Reachx').controller('AllUsersController', AllUsersController);
}());