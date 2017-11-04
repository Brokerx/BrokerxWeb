/**
 * Created by Govind on 28/09/15.
 */



$(document).ready(function () {

 /*   if (location.href.indexOf('main.html') > -1){
        $(window).bind("beforeunload", function() {
            return "For better safety, Stay on this Page and Log out";
        });
    }*/

    function supports_local_storage() {
        try {
            return 'localStorage' in window && window['localStorage'] !== null;
        } catch(e){
            return false;
        }
    }

    /**
     * check browser version and through error.
     * */

    if (!supports_local_storage()) {
        alert("Please update your browser settings to enable local storage. Search 'local data' in your browser settings and enable ");
    }
   /* if(navigator.userAgent.toLowerCase().indexOf('chrome') > -1){
        if(parseInt(window.navigator.appVersion.match(/Chrome\/(\d+)\./)[1], 10) <  30){
            alert("Your Chrome browser is outdated. Please update to latest version.");
        }
    }else{
        alert("Reachx works best on latest version of chrome browser. Please get chrome from https://www.google.co.in/chrome/browser/desktop/index.html.");
    }*/

    if( navigator.cookieEnabled == false){
        alert("Please enable cookies to use Reachx. Search 'cookies' in your browser settings and enable");
    }



    if (location.href.indexOf('Reachx.in') || location.href.indexOf('finalproject.in') > -1) { //Production Mode
        localStorage.setItem("BASE_LIVE_URL", "/Brokerx/");
        localStorage.setItem("BASE_URL_IMAGE", "/img/Charting");
    }else if (location.href.indexOf('8080') > -1 
            || location.href.indexOf('8181') > -1) { //Developement mode
        localStorage.setItem("BASE_LIVE_URL", "/Brokerx/");
        localStorage.setItem("BASE_URL_IMAGE", "/Reachx/img/Charting");
    }
    
});

