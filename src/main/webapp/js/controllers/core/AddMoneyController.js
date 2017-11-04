/**
 * Created by Govind on 29-Sep-15.
 */
(function () {
    AddMoneyController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function AddMoneyController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.selectedFile;
        $scope.selectedCategories = [];
        $scope.user = {};
        $scope.isChq = false;
        $scope.isCsh = false;
        $scope.allPaymentModes = [{value: '', name: 'Select Payment Mode'}, {value: 'CSH', name: 'Cash   '}, {value: 'CHQ', name: 'Cheque    '}];

        $scope.order = {};
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {

            var localUser = localStorage.getItem("SELECTED_USER");
            if (localUser) {
                $scope.user = JSON.parse(localUser);
            }
            $("#ChqDetails").hide();
            $("#CshDetails").hide();
            
            var now = new Date();

            var day = ("0" + now.getDate()).slice(-2);
            var month = ("0" + (now.getMonth() + 1)).slice(-2);

            var today = now.getFullYear() + "-" + (month) + "-" + (day);
            $scope.order.merchantParam2 = today;
//            $('#datePicker').val(today);
        };

        $scope.setSelectedPaymentMode = function (e) {
            //alert("Selected Parent ID = "+categoryID);
            var paymentMode = e.options[e.selectedIndex].value;
            $scope.order.paymentMode = paymentMode;
            $("#ChqDetails").hide();
            $("#CshDetails").hide();
            if (paymentMode == 'CHQ')
                $("#ChqDetails").show();
            if (paymentMode == 'CSH')
                $("#CshDetails").show();
        };


        $scope.onsaveOrderSuccess = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                DSUtils.showSuccessToast("Success", "Amount Added Successfully");
                $scope.getUsers();
            } else {
                DSUtils.showErrorToast("Error", "Failed to Add Money");
            }
        };
        $scope.onSaveOrderError = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            DSUtils.showErrorToast("Error", "Failed to Add Money");
        };
        $scope.AddOrder = function () {
            if ($scope.order.amount == '' || parseInt($scope.order.amount) <= 0) {
                DSUtils.showWarningToast("Required", "Please Enter Amount");
                return;
            }
            if ($scope.order.paymentMode == undefined || $scope.order.paymentMode == '') {
                DSUtils.showWarningToast("Required", "Please Select Payment Mode");
                return;
            }
            DSUtils.showLoader("Please wait...");
            $scope.order.orderType = "AR";
            $scope.order.userID = $scope.user.userID;
            $scope.order.paymentStatus = "C";

//            DSUtils.showInfoToast("Patient Photo Upload", "Patient photo is being uploaded. Will inform when done.");
            HttpService.getDataFromServer(DSConstants.ADD_ORDER_URL, "POST", $scope.order, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onsaveOrderSuccess, $scope.onSaveOrderError, "ADD_ORDER_URL");
        };

        $scope.onGetUserSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.users = data.data;
                localStorage.setItem("ALL_USERS", JSON.stringify($scope.users));
                $location.path(DSConstants.PAGE_ALL_USERS);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetUserError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get User List");
        };
        $scope.getUsers = function () {
            DSUtils.showLoader("Getting Users...");
            var requestParameter = "";
            HttpService.getDataFromServer(DSConstants.GET_USERS, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetUserSuccess, $scope.onGetUserError, "GET_USERS");
        }

    }
    angular.module('Reachx').controller('AddMoneyController', AddMoneyController);
}());
