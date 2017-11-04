/**
 * Created by Govind on 29-Sep-15.
 */
(function () {

    CategoryController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function CategoryController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {


        $scope.categories = [];
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {
            var localCategories = localStorage.getItem("ALL_CATEGORIES");
            if (localCategories) {
                $scope.categories = JSON.parse(localCategories);
            } else {
                //Show Error
            }
        }

        $scope.editCategory = function (category) {
            //alert("Clicked Edit User For ID = " + userID);
            localStorage.setItem("EDIT_CATEGORY",JSON.stringify(category));
            $location.path(DSConstants.PAGE_ADD_CATEGORY);
        };
        
        $scope.setDeleteCategory = function (category) {
            localStorage.setItem("CATEGORY_TO_BE_DELETED",category.categoryID);
            $('#myModal').modal('show');
        };

        $scope.deleteCategory = function () {
            var categoryToBeDeted = localStorage.getItem("CATEGORY_TO_BE_DELETED");
//            alert("Deleting Category For ID = " + categoryToBeDeted );
            DSUtils.showLoader("Deleting Category");
            var params="categoryID="+categoryToBeDeted;

//            DSUtils.showInfoToast("Patient Photo Upload", "Patient photo is being uploaded. Will inform when done.");
            HttpService.getDataFromServer(DSConstants.DELETE_CATEGORY, "POST", params, null, true, DSConstants.REQUEST_TIME_OUT, $scope.onDeleteCategorySuccess, $scope.onDeleteCategoryError, "DELETE_CATEGORY");
        
        };
        
        $scope.onDeleteCategorySuccess = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                DSUtils.showSuccessToast("Success", "Category Deleted Successfully");
                $scope.getUsers();
            } else {
                DSUtils.showErrorToast("Error", "Failed to Delete Category");
            }
        };
        $scope.onDeleteCategoryError = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            DSUtils.showErrorToast("Error", "Failed to Deleted Category");
        };

        $scope.downloadInfo = function (fileName) {
            if (fileName != undefined && fileName.length > 0) {
                var scope_baseURlDoc = DSConstants.BASE_LIVE_URL + "files/CategoryInfo/"
                var urlDoc = scope_baseURlDoc + fileName;
                // window.open(urlDoc);
                urlDoc.replace(" ", "%20");
                var dummy = window.open(urlDoc);
                setTimeout(function () {
                    dummy.close();
                }, 2000);
            } else {
                DSUtils.showWarningToast("No file","Info File Yet to be uploaded");
            }
        };


    }

    angular.module('Reachx').controller('CategoryController', CategoryController);
}());