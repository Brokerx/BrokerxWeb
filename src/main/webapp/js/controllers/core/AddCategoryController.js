/**
 * Created by Govind on 29-Sep-15.
 */
(function () {
    AddCategoryController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function AddCategoryController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        $scope.selectedFile;
        $scope.allCategories = [];
        $scope.selectedCategories = [];
        $scope.editCategory = {};
        
        $scope.isEdit = false;
        $scope.saveButtonLabel = "Save Category";
        $scope.allCategories = [{id: '1', name: 'Category 1'}, {id: '2', name: 'Category 2'},
            {id: '3', name: 'Category 3'},
            {id: '4', name: 'Category 4'},
            {id: '5', name: 'Category 6'},
            {id: '7', name: 'Category 7'}];
        $scope.newCategory = {
            id: '',
            name: '',
            buyerPrice: '',
            sellerPrice: '',
            parentCategoryID:'3',
            isGroup: true,
            isMainCategory:false
        };
        /**
         * Called on the init of the page or view
         * @returns {undefined}
         */
        $scope.init = function () {

            var localCategories = localStorage.getItem("ALL_CATEGORIES");
            if (localCategories) {
                $scope.allCategories = JSON.parse(localCategories);
            }
            var editCategory = localStorage.getItem("EDIT_CATEGORY");
            if (editCategory) {
                $scope.newCategory = JSON.parse(editCategory);
                $scope.isEdit = true;
                if($scope.newCategory.parentCategoryID) {
                    $scope.newCategory.isMainCategory=false;
                } else {
                    $scope.newCategory.isMainCategory=true;
                }
                localStorage.removeItem("EDIT_CATEGORY");
                $("#parentCategory").val($scope.newCategory.parentCategoryID);
                $scope.saveButtonLabel = "Update Category";
            }

        };
        $scope.isSelectedCategory = function (category) {
           // for (var j = 0; j < $scope.selectedCategories.length; j++) {
                if (category.categoryID == $scope.newCategory.parentCategoryID) {
                    return true;
                }
            //}
        }

        $scope.setSelectedParentCategory = function(e) {
            //alert("Selected Parent ID = "+categoryID);
            var categoryID= e.options[e.selectedIndex].value;
            $scope.newCategory.parentCategoryID = categoryID;
        };
        $scope.onFileSelect = function (element) {
            //clean the image name
            $scope.profileImageName = "";
            $scope.selectedFile = element.files[0];
        };
        $scope.onsaveCategorySuccess = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            if (data.messageID == 100) {
                if(data.data != undefined) {
                    $scope.allCategories.push(data.data);
                    localStorage.setItem("ALL_CATEGORIES", JSON.stringify($scope.allCategories));
                }
                DSUtils.showSuccessToast("Success", "Category Saved Sucessfully");
                $scope.newCategory = {};
                $scope.selectedCategories = [];
                $scope.clearSelectedCategory();
            } else {
                DSUtils.showErrorToast("Error", "Failed to save Drug");
            }
        };
        $scope.onSaveCategoryError = function (data, requestIdentifier) {
            DSUtils.hideLoader();
            DSUtils.showErrorToast("Error", "Failed to Category");
        };
        $scope.saveCategory = function () {
//            var categoryIDs = '';
            /*var parentCategory = $scope.getSelectCategories();
            if(parentCategory == '?') {
                alert("Please Select Parent Category");
                return;
            }*/
            DSUtils.showLoader("Please wait...");
            var fd = new FormData();
            fd.append("uploadedFile", $scope.selectedFile);
            fd.append("name", $scope.newCategory.name);
            
            if(!$scope.newCategory.isMainCategory){
                fd.append("isGroup", $scope.newCategory.isGroup);
                fd.append("parentCategoryID", $scope.newCategory.parentCategoryID);
                fd.append("buyerPrice", $scope.newCategory.buyerPrice);
                fd.append("sellerPrice", $scope.newCategory.sellerPrice);
            } else {
                fd.append("isGroup", true);
                fd.append("buyerPrice", "0");
                fd.append("sellerPrice", "0");
            }
            
            if( $scope.newCategory.paidInfo != undefined) {
                fd.append("paidInfo", $scope.newCategory.paidInfo);
            } else {
                fd.append("paidInfo", "");
            }
            if( $scope.newCategory.paidInfoPrice != undefined) {
                fd.append("paidInfoPrice", $scope.newCategory.paidInfoPrice);
            } else {
                fd.append("paidInfoPrice", "0");
            }
            
            
            if( $scope.newCategory.categoryID != undefined) {
                fd.append("categoryID", $scope.newCategory.categoryID);
            }
//            DSUtils.showInfoToast("Patient Photo Upload", "Patient photo is being uploaded. Will inform when done.");
            HttpService.uploadFileOnDataServer(DSConstants.SAVE_CATEGORY, fd, $scope.onsaveCategorySuccess, $scope.onSaveCategoryError);
        };
        $scope.getSelectCategories = function () {
            var values = $('#parentCategory').val();
           /* var e = document.getElementById("parentCategory");
            var strUser = e.options[e.selectedIndex].value;
            var result = "";
            for (var i = 0, iLen = values.length; i < iLen; i++) {
                opt = values[i];
                result += +opt + ",";
            }*/
            DSUtils.debugLog("Selected Options : " + values);
            return values;
        }

        $scope.clearSelectedCategory = function () {
            var elements = document.getElementById("categories").options;
            for (var i = 0; i < elements.length; i++) {
                elements[i].selected = false;
            }
            $('.chosen-choices .search-choice').remove();
            $(".chosen-results li").removeClass('result-selected');
            $('.chosen-results li').addClass('active-result');
        }


    }
    angular.module('Reachx').controller('AddCategoryController', AddCategoryController);
}());
