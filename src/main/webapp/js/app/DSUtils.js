/* 
 * DentoSysUtils 
 * Utils class to contain all the utils functions, 
 * To be used through out the application.
 */

(function () {

    var isPatientLogin = false;
    function DSUtils() {
    }
    ;
    DSUtils.$inject = ['DSConstants', '$log', 'AppConstant'];

    function DSUtils(DSConstants, $log, AppConstant) {
        var obj = {};
        obj = initializeDSUtilsFunction(DSConstants, $log, AppConstant);
        return obj;
    }
    ;


    var initializeDSUtilsFunction = function (DSConstants, $log, AppConstant) {
        return {
            /**
             *
             * @returns true if internet available other wise returns false
             * check the values of navigator and return according to it.
             */
            isInternetAvailable: function () {
                if (navigator.network) {
                    var networkType = navigator.network.connection.type;
                    this.debugLog('Network type - ' + networkType, true);
                    return !!(networkType && networkType != "none");
                } else {
                    return navigator.onLine;
                }
            },
            /**
             * show and hide loader as per demand
             */
            showLoader: function (text) {


                if (text == undefined) {
                    text = "Just a minute...";
                }
                $(".loadertext").html(text);
                $(".loading-container").show();
            },
            hideLoader: function () {
                $(".loading-container").hide();
            },
            /**
             * console log used for debugging can be on of during call or globally
             * @param logData - log text to print
             * @param isPrinting - flag, if log is going to print or not true to print false to not print
             * @constructor
             */
            debugLog: function (logData, isPrinting) {

                if (DSConstants.IS_PRINTING_LOG) {
                    if (typeof logData === 'object') {
                        $log.debug('>>>>>>>>>>>>> DENTOSYS Logs ->>> ' + JSON.stringify(logData));
                    } else {
                        $log.debug('>>>>>>>>>>>>> DENTOSYS Logs ->>> ' + logData);
                    }

                }
            },
            /**
             * window alert used for debugging can be on of during call or globally
             * @param alertData - log text to show alert
             * @param isShowing - flag, if alert is going to print or not true to print false to not print
             * @constructor
             */
            debugAlert: function (alertData, isShowing) {
                if (WFConstants.IS_SHOWING_ALERT && isShowing) {
                    alert('DENTOSYS Alert - ' + alertData);
                }
            },
            /**
             *
             */
            isLoggedIn: function () {
                return true;
            },
            /**
             * return date with time factor cleared
             * @param date
             * @returns {Date}
             */
            getFiltedDateWithNoTime: function (date) {
                var newDate = new Date(date);
                newDate.setHours(0, 0, 0, 0);
                return newDate;
            },
            /* convert to title case & remove unused spaces */
            getTitleCaseString: function (string) {
                if (string != undefined) {
                    /*                    return string.replace(/\w\S*//*g, function (txt) {
                     var titleCase = txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
                     return titleCase.replace(/\s+/g,' ').trim();
                     });*/
                    string = string.replace(/\s{2,}/g, ' ').trim();
                    string = string.replace(/\w\S*/g, function (txt) {
                        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
                    });
                    return string;

                } else {
                    return "";
                }

            },
            getKeyFromValue: function (array, text) {
                $.each(array, function (key, value) {
                    if (text == value) {
                        return key;
                    }
                });
            },
            convertToDigit: function (str, max) {
                str = str.toString();
                return str.length < max ? this.convertToDigit("0" + str, max) : str;
            },
            showSuccessToast: function (title, message) {
                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "positionClass": "toast-bottom-right",
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "4000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };
                toastr.success(message, title);
            },
            showInfoToast: function (title, message) {
                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "positionClass": "toast-bottom-right",
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "4000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };
                toastr.info(message, title);
            },
            showWarningToast: function (title, message) {
                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "positionClass": "toast-bottom-right",
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "4000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };
                toastr.warning(message, title);
            },
            showErrorToast: function (title, message) {
                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "positionClass": "toast-bottom-right",
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "4000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };
                toastr.error(message, title);
            },
            /**
             * validates data by making them title case and removing extra spaces.
             * @param data
             */
            validateNrectifyData: function (data) {
                for (var key in data) {
                    var value = data[key];
                    if (typeof value != "boolean") {
                        var trimmedString = value.replace(/\s+/g, ' ').trim();
                        var titleCasedString = this.getTitleCaseString(trimmedString);
                        data[key] = titleCasedString;
                    }
                }
                return data;
            },
            /**
             * checks if the application is running in the tablet
             */
            checkIfRunningDeviceIsTablet: function () {
                return device == 'mobile';
                // return true;
            },
            stopBodyOverFlow: function () {
                if ($rootScope.isTablet)
                    $('body').css('overflow', 'none');
            },
            startBodyOverFlow: function () {
                if ($rootScope.isTablet)
                    $('body').css('overflow', 'auto');
            },
            /******************************************
             *  Check Return type from IDs
             *****************************************/
            getPersonFromID: function (id) {
                var personID = id % 100000;
                //DoctorStaff
                if (personID >= 101 && personID < 999) {
                    return AppConstant.DOCTOR;
                }
                //Patient
                if (personID >= 1001 && personID <= 79999) {
                    return AppConstant.PATIENT;
                }
                //Vendor
                if (personID >= 80001 && personID <= 89999) {
                    return AppConstant.VENDOR;
                }
                // Accounts
                if (personID >= 90001 && personID <= 99999) {
                    return AppConstant.ACCOUNT;
                }
                // Center
                if (personID >= 1 && personID <= 99) {
                    return AppConstant.CENTER;
                }

                return "";
            }




        };
    };

    angular.module('Reachx').service('DSUtils', DSUtils);

}());