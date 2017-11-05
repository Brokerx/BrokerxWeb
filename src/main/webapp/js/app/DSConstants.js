/*
 * Reachx Constants
 * To be used to have all application level constants at one place.
 * These will be used through out the application
 *
 * things to be included :
 * webservices,
 * application configuration constants,
 *
 */


angular.module('Reachx').constant('DSConstants', {
    'IS_PRINTING_LOG': true,
    'IS_SHOWING_ALERT': true,
    
    'BASE_LIVE_URL': localStorage.getItem("BASE_LIVE_URL"),
    'BASE_URL_IMAGE': localStorage.getItem("BASE_URL_IMAGE"),


    'REQUEST_TIME_OUT': 1500000,
    "DEFAULT_ERROR_MSG":"Error Occured, Please try again.",

    'FAILURE_ID': "99",
    'SUCCESS_ID': "100",
    'VALID_URL_FAILURE_ID': "101",
    'VALID_URL_SUCCESS_ID': "102",
    'INSERT_FAILURE_ID': "103",
    'INSERT_SUCCESS_ID': "104",
    'UPDATE_SUCCESS_ID': "105",
    'UPDATE_FAILURE_ID': "106",
    'INSUFFICEINT_DATA_ID': "107",
    'SMS_SENT_ID': "108",
    'SMS_FAILURE_ID': "109",
    'PATIENT_ADD_DUPLICATE': "110",
    'DUPLICATE_PATIENT_ID' : "116",

    'PAGE_LOGIN': "/login",
    'PAGE_FORGOTPASSWORD': "/forgotpassword",
    'PAGE_SIGNUP': "/signup",
    'PAGE_DASHBOARD':"/dashboard",
    'PAGE_ALL_BROKERS':"/allBrokers",
    'PAGE_ALL_USERS':"/allUsers",
    'PAGE_ADS_REPORT':"/adsReport",
    'PAGE_WALLET_SUMMARY':"/walletSummary",
    'PAGE_ADD_USER':"/addUser",
    'PAGE_ADD_MONEY':"/addMoney",
    'PAGE_DRUGS': "/drugs",
    'PAGE_ADD_DRUG': "/addDrug",
    'PAGE_CATEGORIES': "/categories",
    'PAGE_ADD_CATEGORY': "/addCategory",
    'PAGE_ADD_SAR': "/addSAR",
    'PAGE_ADD_SYNTHESIS': "/addSynthesis",
    'PAGE_SAR': "/SAR",
    'PAGE_SYNTHESIS': "/Synthesis",
    'PAGE_UPLOAD_EXEL': "/uploadExel",
    'PAGE_USER_LEADS': "/userLeads",

    'LOGIN_URL': 'webresources/login/admin',
    'GET_USERS': 'webresources/user/getUsers',
    'GET_ALL_ADS': 'webresources/advertisement/getAdds',
    'GET_WALLET_SUMMARY': 'webresources/order/getWalletSummary',
    'GET_DRUGS': 'webresources/drugs/getDrugs',
    'SAVE_USER': 'webresources/user/saveUser',
    'SAVE_DRUG': 'webresources/drugs/saveDrug',
    'GET_CATEGORIES': 'webresources/category/getAllCategories',
    'DELETE_CATEGORY': 'webresources/category/deleteCategory',
    'GET_DASHBOARD': 'webresources/dashboard/getDashboard',
    'GET_DASHBOARD_LEAD': 'webresources/lead/getDashboardLeads',
    'SAVE_CATEGORY': 'webresources/category/saveCategory',
    'GET_FILES': 'webresources/files/getFiles',
    'SAVE_FILE': 'webresources/files/saveFile',
    'ADD_ORDER_URL':"webresources/order/addOrder",
    'UPLOAD_USER_EXEL': 'webresources/user/uploadUserExel'
    

});
