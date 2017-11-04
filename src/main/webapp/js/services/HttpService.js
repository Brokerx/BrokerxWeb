(function () {

    HttpService.$inject = ['DSUtils', 'DSConstants', '$http'];

    function HttpService(DSUtils, DSConstants, $http) {

        var obj = {};
        obj = initializeHttpServiceFunction(DSUtils, DSConstants, $http);

        return obj;
    }
    ;

    var initializeHttpServiceFunction = function (DSUtils, DSConstants, $http) {
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
            getDataFromServer: function (url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier) {


                if (!navigator.onLine) {
                    DSUtils.showErrorToast("Error", "No Internet Connectivity.");
                    DSUtils.hideLoader();
                    return;
                }
                this.callService(url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier);
            },
            callService: function (url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier) {
                url = DSConstants.BASE_LIVE_URL + url;
                var startTime = new Date().getTime();
                $http({
                    method: "POST",
                    url: url,
                    timeout: timeOut,
                    data: requestParameters ? requestParameters : "",
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function (data, status) {
                    //  DSUtils.debugLog("success  web - data:" + JSON.stringify(data) + " status:" + status, true);
                    // Handle Session expired : GOVIND
                    if (data.messageId != undefined && data.messageId == 101) {

                        $.root_.addClass('animated fadeOutUp');
                        for (var key in localStorage) {
                            if (key != "BASE_LIVE_URL" && key != "BASE_URL_IMAGE")
                                localStorage.removeItem(key);
                        }
                        setTimeout(logout, 1000);
                        function logout() {
                            window.location.replace('index.html');
                        }
                    }  else {
                        successCallBack(data, requestIdentifier);
                    }
                }).error(function (data, status, headers, config, statusText) {
                    //If timeout occured show error message
                    var respTime = new Date().getTime() - startTime;
                    if (respTime >= config.timeout) {
                        DSUtils.hideLoader();
                        DSUtils.showWarningToast("Warning", "Slow Internet connection, request could not be completed.");
                    }

                    errorCallBack({"status": status, "statusText": "Some Error Occurred."}, requestIdentifier)

                });
            },
            //method for calling service for getting data from external API....By tj...11/5/2015
            getDataFromExternalAPI: function (url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier, xmashUpKey, acceptKey) {
                if (!navigator.onLine) {
                    DSUtils.showErrorToast("Error", "No Internet Connectivity.");
                    DSUtils.hideLoader();
                    return;
                } else
                {
                    this.callServiceForHeader(url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier, xmashUpKey, acceptKey);
                }


            },
            callServiceForHeader: function (url, methodType, requestParameters, httpBody, isAsync, timeOut, successCallBack, errorCallBack, requestIdentifier, xmashUpKey, acceptKey) {
                //url = DSConstants.BASE_LIVE_URL + url;

                $http({
                    method: "POST",
                    url: url,
                    data: requestParameters ? requestParameters : "",
                    headers: {'X-Mashape-Key': xmashUpKey,
                        'Accept': acceptKey}
                }).success(function (data, status) {
                    if (data) {
                        successCallBack(data[0], requestIdentifier);
                    }
                }).error(function (data, status) {
                    //  DSUtils.debugLog("error  web - data:" + data + " status:" + status, true);

                    DSUtils.showErrorToast("Error: " + status + " : Please contact Developer");
                    errorCallBack({"status": status, "statusText": "Some Error Occurred."}, requestIdentifier)
                });
            },
            uploadFileOnDataServer: function (url, formData, successCallBack, errorCallBack, requestIdentifier) {
                url = DSConstants.BASE_LIVE_URL + url;
//                 $http({
//                           url: url,
//                           method: "POST",
//                           data:formData,
//                           transformRequest: angular.identity,
//                           headers: { 'Content-Type': undefined }
                    
                $http.post(url, formData, {
                    enctype: "multipart/form-data",
                    headers: {'Content-Type': undefined},
                    transformRequest: angular.identity
               }).success(function (data, status) {
                    if (data.messageId != undefined && data.messageId == 101) {
                        $.root_.addClass('animated fadeOutUp');
                        for (var key in localStorage) {
                            if (key != "BASE_LIVE_URL" && key != "BASE_URL_IMAGE")
                                localStorage.removeItem(key);
                        }
                        setTimeout(logout, 1000);
                        function logout() {
                            window.location.replace('index.html');
                        }
                    }
                    else {
                        successCallBack(data, status);
                    }
                }).error(function (data, status) {
                    errorCallBack(data, status);
                });
            }

        };
    };

    angular.module('Reachx').service('HttpService', HttpService);

}());