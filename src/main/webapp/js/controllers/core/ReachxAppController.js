/**
 * Created by Govind on 28/09/15.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {

    ReachxAppController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function ReachxAppController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $.root_ = $('body');
        var socket;
        var rememberMeEnabled = false;
        var expiryDate = "", todayDate = new Date();
        var sendParam = "", globalAdv = "";
        var flag = 0, personID = "";
        $scope.showLoader = false;

        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {

            $scope.userName = $.cookie('LoginUserName');
            if ($scope.userName == undefined) {
                window.location.replace('index.html');
                return;
            }
            $scope.userName = $scope.userName.trim();
            $scope.password = $.cookie('LoginPassword');
            $scope.fromLogin = JSON.parse(localStorage.getItem("FROM_LOGIN"));
            $scope.Days = 0;
            doLogin();

        };

        /**
         * on click of remember me, toogle the value, which will checked on saving.
         */
        $scope.rememberCredentials = function () {
            rememberMeEnabled = !rememberMeEnabled;
        };

        /**
         *  save the username and password in localstorage
         */
        function implementRememberMe() {
            // localStorage.setItem("LoginUserName", $scope.userName);
            //localStorage.setItem("LoginPassword", $scope.password);
            $.cookie('LoginUserName', $scope.userName, {expires: 2});
            $.cookie('LoginPassword', $scope.password, {expires: 2});
        }

        /**
         *  clear local storage.
         */
        function clearSavedCredentials() {
            // localStorage.removeItem('LoginUserName');
            //localStorage.removeItem('LoginPassword');
            $.cookie('LoginUserName', null);
            $.cookie('LoginPassword', null);
        }

        /**
         * Start Login
         */
        doLogin = function () {

            var userName = $scope.userName;
            var password = $scope.password;

            DSUtils.debugLog("UserName : " + userName + " password : " + password, true);


            if (userName == undefined || password == undefined) {
                showInformationDialogue();
            } else {
                var requestParameter = '';
                userName = userName.trim();
                requestParameter = "userName=" + userName + "&password=" + password + "&source=B";
                callLoginService(requestParameter);
            }
        };


        function loginFailure(msg) {
            window.location.replace('index.html?failed=ERROR');
            localStorage.setItem("ERR_MSG", msg);
            clearSavedCredentials();
        }

        function checkEmail(userName) {
            var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return reg.test(userName);
        }

        function checkNumber(userName) {
            return /^\d+$/.test(userName);
        }

        function checkPasswordForBadInputs(password) {
            var blackList = ["--", ";--", ";", "/*", "*/", "=", "<", ">", "union ", "all ", "char ", "nchar ", "varchar ", "nvarchar ",
                "alter ", "begin ", "cast ", "create ", "cursor ", "declare ", "delete ", "drop ", "end ", "exec ", "execute ",
                "fetch ", "insert ", "kill ", "open ", "select ", "sys ", "sysobjects ", "syscolumns ", "table ", "update ",
                "information_schema ", "where "];

            for (var i = 0; i < blackList.length; i++) {
                var temp = password.toLowerCase().indexOf(blackList[i].toLowerCase());
                if (temp >= 0) {
                    return false;
                }
            }
            return true;
        }

        $scope.removeError = function () {
            $scope.errorShow = false;
        };

        //Function For getting logged in browser name and other details....
        var getLoggedInBrowser = function () {
            var nVer = navigator.appVersion;
            var nAgt = navigator.userAgent;
            var browserName = navigator.appName;
            var fullVersion = '' + parseFloat(navigator.appVersion);
            var majorVersion = parseInt(navigator.appVersion, 10);
            var nameOffset, verOffset, ix;

            // In Opera 15+, the true version is after "OPR/"
            if ((verOffset = nAgt.indexOf("OPR/")) != -1) {
                browserName = "Opera";
                fullVersion = nAgt.substring(verOffset + 4);
            }
            // In older Opera, the true version is after "Opera" or after "Version"
            else if ((verOffset = nAgt.indexOf("Opera")) != -1) {
                browserName = "Opera";
                fullVersion = nAgt.substring(verOffset + 6);
                if ((verOffset = nAgt.indexOf("Version")) != -1)
                    fullVersion = nAgt.substring(verOffset + 8);
            }
            // In MSIE, the true version is after "MSIE" in userAgent
            else if ((verOffset = nAgt.indexOf("MSIE")) != -1) {
                browserName = "Microsoft Internet Explorer";
                fullVersion = nAgt.substring(verOffset + 5);
            }
            // In Chrome, the true version is after "Chrome"
            else if ((verOffset = nAgt.indexOf("Chrome")) != -1) {
                browserName = "Chrome";
                fullVersion = nAgt.substring(verOffset + 7);
            }
            // In Safari, the true version is after "Safari" or after "Version"
            else if ((verOffset = nAgt.indexOf("Safari")) != -1) {
                browserName = "Safari";
                fullVersion = nAgt.substring(verOffset + 7);
                if ((verOffset = nAgt.indexOf("Version")) != -1)
                    fullVersion = nAgt.substring(verOffset + 8);
            }
            // In Firefox, the true version is after "Firefox"
            else if ((verOffset = nAgt.indexOf("Firefox")) != -1) {
                browserName = "Firefox";
                fullVersion = nAgt.substring(verOffset + 8);
            }
            // In most other browsers, "name/version" is at the end of userAgent
            else if ((nameOffset = nAgt.lastIndexOf(' ') + 1) <
                (verOffset = nAgt.lastIndexOf('/'))) {
                browserName = nAgt.substring(nameOffset, verOffset);
                fullVersion = nAgt.substring(verOffset + 1);
                if (browserName.toLowerCase() == browserName.toUpperCase()) {
                    browserName = navigator.appName;
                }
            }
            // trim the fullVersion string at semicolon/space if present
            if ((ix = fullVersion.indexOf(";")) != -1)
                fullVersion = fullVersion.substring(0, ix);
            if ((ix = fullVersion.indexOf(" ")) != -1)
                fullVersion = fullVersion.substring(0, ix);

            majorVersion = parseInt('' + fullVersion, 10);
            if (isNaN(majorVersion)) {
                fullVersion = '' + parseFloat(navigator.appVersion);
                majorVersion = parseInt(navigator.appVersion, 10);
            }

            //document.write(''
            //    +'Browser name  = '+browserName+'<br>'
            //    +'Full version  = '+fullVersion+'<br>'
            //    +'Major version = '+majorVersion+'<br>'
            //    +'navigator.appName = '+navigator.appName+'<br>'
            //    +'navigator.userAgent = '+navigator.userAgent+'<br>'
            //)
            return browserName;
        };

        var callLoginService = function (requestParameter) {
            DSUtils.showLoader("Verifying credentials...");
            DSUtils.debugLog("Starting login..");
            HttpService.getDataFromServer(DSConstants.LOGIN_URL, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onSuccess, $scope.onError, "LOGIN_URL");
        };
        /**
         * To be called on success of login service
         */
        $scope.onSuccess = function (data, requestIdentifier) {

            loginBoost(data);


        };


        var loginBoost = function (data) {
            DSUtils.debugLog("Got login success...");
            if (rememberMeEnabled) {
                implementRememberMe();
            }

            DSUtils.debugLog("Success Data : " + JSON.stringify(data), true);

            /*check if login is failed, need to check the errorID == 99*/

            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                DSUtils.hideLoader();
//                $scope.getUsers();
                $scope.getDashBoardData();

            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                DSUtils.hideLoader();
                loginFailure(data.messageText);
            }
        };

        /**
         * to be called on error of login service
         */
        $scope.onError = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            $scope.errorShow = true;
            $scope.errorMessage = data.statusText;
            loginFailure(data.messageText);
        };

        $scope.getDashBoardData = function() {
            DSUtils.showLoader("Getting Dashboard...");
            var requestParameter = "";
            HttpService.getDataFromServer(DSConstants.GET_DASHBOARD, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetDashboardSuccess, $scope.onGetDashboardError, "GET_DASHBOARD");
        };

        $scope.onGetDashboardSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.dashboard = data.data;
                localStorage.setItem("DASHBOARD_DATA", JSON.stringify($scope.dashboard));
                $location.path(DSConstants.PAGE_DASHBOARD);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetDashboardError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Dashboard");
        };
        $scope.getUsers = function (type) {
            DSUtils.showLoader("Getting Users...");
            var requestParameter = "userType="+type;
            HttpService.getDataFromServer(DSConstants.GET_USERS, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetUserSuccess, $scope.onGetUserError, "GET_USERS");
        }

        /**
         * open dilaogue with error message
         */
        var showInformationDialogue = function () {
            localStorage.setItem("ERR_MSG", "Username or password is missing");
            window.location.replace('index.html?failed=CREDENTIALS_MISSING');
        };

        imageExists = function (image_url) {

            var http = new XMLHttpRequest();

            http.open('HEAD', image_url, false);
            http.send();

            $scope.isImage = http.status != 404;

        };

        $scope.openForgotPasswordPage = function () {
            $location.path("/forgotpassword");
        };

        $scope.openAddUserPage = function () {
            localStorage.removeItem("USER_TO_BE_EDITED");
            $location.path(DSConstants.PAGE_ADD_USER);
        };
        $scope.openAddCategoryPage = function () {
            if(localStorage.getItem("ALL_CATEGORIES") != null && localStorage.getItem("ALL_CATEGORIES") != undefined
                && localStorage.getItem("ALL_CATEGORIES").length > 0)
            {
                $location.path(DSConstants.PAGE_ADD_CATEGORY);
            }else
            {
                $scope.getCategories("ADD_CATEGORY")
            }

        };

        $scope.openAllUsersPage = function () {
            $location.path(DSConstants.PAGE_ALL_USERS);
        };
        $scope.getDashBoard = function () {
            $location.path(DSConstants.PAGE_DASHBOARD);
        };

        $scope.userLogout = function () {
            $.SmartMessageBox({
                title: "<i class='fa fa-sign-out txt-color-orangeDark'></i> Logout <span class='txt-color-orangeDark'><strong>" + $(".loggedInUserName").html() + "</strong></span> ?",
                content: "After logging out, you may close this browser window for enhanced security",
                buttons: '[No][Yes]'

            }, function (ButtonPressed) {
                if (ButtonPressed == "Yes") {
                    $.root_.addClass('animated fadeOutUp');
                    for (var key in localStorage) {
                        localStorage.removeItem(key);
                    }
                    deleteAllCookies();
                    //If you are adding something here, must add the same code to HttpService for Checking Invalidat Session : GOVIND
                    $timeout(function () {
                        window.location.replace('index.html');
                    }, 1000)
                }
            });

        };

        function deleteAllCookies() {
            var cookies = document.cookie.split(";");

            for (var i = 0; i < cookies.length; i++) {
                var cookie = cookies[i];
                var eqPos = cookie.indexOf("=");
                var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
                document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
            }
        }

        $scope.onGetUserSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.users = data.data;
                if($scope.users[0].userInfo.isBroker) {
                    localStorage.setItem("ALL_BROKERS", JSON.stringify($scope.users));
                    $location.path(DSConstants.PAGE_ALL_BROKERS);
                } else {
                    localStorage.setItem("ALL_USERS", JSON.stringify($scope.users));
                    $location.path(DSConstants.PAGE_ALL_USERS);
                }
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetUserError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get User List");
        };
        
        $scope.getDashboardLeads = function (type) {
            DSUtils.showLoader("Getting Deals...");
            localStorage.setItem("GET_DASHBOARD_LEADS_TYPE", type);
            var requestParameter = "type=" + type;
            HttpService.getDataFromServer(DSConstants.GET_DASHBOARD_LEADS_BY_TYPE, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetDashboardLeadsSuccess, $scope.onGetDashboardLeadsError, "GET_DASHBOARD_LEADS");
        }
        
        $scope.onGetDashboardLeadsSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                var type = localStorage.getItem("GET_DASHBOARD_LEADS_TYPE");
                localStorage.setItem("LEAD_SUMMARY", JSON.stringify(data.data));
//                $location.path(DSConstants.PAGE_LEAD_SUMMARY);
                if(type=='Active') {
                    $location.path(DSConstants.PAGE_ALL_ACTIVE_DEALS);
                } else if(type=='Done') {
                    $location.path(DSConstants.PAGE_ALL_COMPLTED_DEALS);
                } else {
                    $location.path(DSConstants.PAGE_ALL_REJECTED_DEALS);
                }
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetDashboardLeadsError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Deals");
        };
        
        $scope.onGetCategoriesSuccess = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.users = data.data;
                localStorage.setItem("ALL_CATEGORIES", JSON.stringify($scope.users));
                if (requestIdentifier == "GET_CATEGORIES") {
                    $location.path(DSConstants.PAGE_CATEGORIES);
                } else if (requestIdentifier == "ADD_CATEGORY") {
                    $location.path(DSConstants.PAGE_ADD_CATEGORY);
                }
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetCategoriesError = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            alert("Unable to get Categories List");
        };
        $scope.getCategories = function (requestIdentifier) {
//            $location.path(DSConstants.PAGE_DRUGS);
            DSUtils.showLoader("Getting Categories...");
            var requestParameter = "parentCategoryID=-1";
            HttpService.getDataFromServer(DSConstants.GET_CATEGORIES, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetCategoriesSuccess, $scope.onGetCategoriesError, requestIdentifier);
        };

        $scope.onGetAdsSuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.users = data.data;
                localStorage.setItem("ALL_ADS", JSON.stringify($scope.users));
                $location.path(DSConstants.PAGE_ADS_REPORT);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetAdsError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get Advertisement Report");
        };
        $scope.getAllAds = function () {
            DSUtils.showLoader("Preparing Advertisement Report...");
            var requestParameter = "isAll=true";
            HttpService.getDataFromServer(DSConstants.GET_ALL_ADS, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetAdsSuccess, $scope.onGetAdsError, "GET_ALL_ADS");
        }

        $scope.onGetWalletSummarySuccess = function (data) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                //TODO Set loginUser details tocontrollerdatainstanceService or loginService
                $scope.orders = data.data;
                localStorage.setItem("WALLET_SUMMARY", JSON.stringify($scope.orders));
                $location.path(DSConstants.PAGE_WALLET_SUMMARY);
            } else {  // login is successful ir erroID == 100, so save the subscription and call the calendar request
                //DSUtils.hideLoader();
                //loginFailure(data.messageText);
            }
        };
        $scope.onGetWalletSummaryError = function (data) {
            DSUtils.hideLoader();
            alert("Unable to get onGetWallet Summary");
        };
        $scope.GetWalletSummary = function () {
            DSUtils.showLoader("Preparing Advertisement Report...");
            var requestParameter = "";
            HttpService.getDataFromServer(DSConstants.GET_WALLET_SUMMARY, "POST", requestParameter, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onGetWalletSummarySuccess, $scope.onGetWalletSummaryError, "GET_ALL_ADS");
        }
    }

    angular.module('Reachx').controller('ReachxAppController', ReachxAppController);
}());