/**
 * Created by Govind on 02-Oct-15.
 */
(function () {

    $(function () {

    });

    DashboardController.$inject = ['$scope', '$rootScope', 'HttpService', 'DSConstants', 'DSUtils', '$location', '$timeout']; //'LoginService',
    function DashboardController($scope, $rootScope, HttpService, DSConstants, DSUtils, $location, $timeout) {

        var userChart = {},adsChart={},rechargeChart={};
        $scope.dashboardData = {};

        $scope.init = function () {
            DSUtils.showLoader("Preparing Dashboard...");
            var localData = localStorage.getItem("DASHBOARD_DATA");
            if(localData) {
                $scope.dashboardData = JSON.parse(localData);
            }
            var userSummaryMonths = [], userSummeryData1 =[], userSummeryData2=[];
            var adsSummaryMonths=[], adsSummaryData1=[], adsSummaryData2=[];
            var walletSummaryMonths=[], walletSummaryData1=[],walletSummaryData2=[],walletSummaryData3=[];
            for(var i in $scope.dashboardData.userSummary) {
                var summary = $scope.dashboardData.userSummary[i];
                userSummaryMonths.push(summary.month);
                if(summary.data1) {
                    userSummeryData1.push(parseInt(summary.data1));
                } else {
                    userSummeryData1.push(0);
                }
                if(summary.data2) {
                    userSummeryData2.push(parseInt(summary.data2));
                } else {
                    userSummeryData2.push(0);
                }
            }

            for(var i in $scope.dashboardData.dealsSummary) {
                var summary = $scope.dashboardData.dealsSummary[i];
                adsSummaryMonths.push(summary.month);
                if(summary.data1) {
                    adsSummaryData1.push(parseInt(summary.data1));
                } else {
                    adsSummaryData1.push(0);
                }
                if(summary.data2) {
                    adsSummaryData2.push(parseInt(summary.data2));
                } else {
                    adsSummaryData2.push(0);
                }
            }

            for(var i in $scope.dashboardData.transactionSummary) {
                var summary = $scope.dashboardData.transactionSummary[i];
                walletSummaryMonths.push(summary.month);
                if(summary.data1) {
                    walletSummaryData1.push(parseInt(summary.data1));
                } else {
                    walletSummaryData1.push(0);
                }
                if(summary.data2) {
                    walletSummaryData2.push(parseInt(summary.data2));
                } else {
                    walletSummaryData2.push(0);
                }
                if(summary.data3) {
                    walletSummaryData3.push(parseInt(summary.data3));
                } else {
                    walletSummaryData3.push(0);
                }
            }

            userChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'userChartContainer'
                },
                colors: ['#7cb5ec', 'red'],
                title: {
                    text: 'User Summary'
                },
                xAxis: {
                    categories: userSummaryMonths
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'User Count'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Buyer/Seller',
                    data: userSummeryData1
                }, {
                    name: 'Brokers',
                    data: userSummeryData2
                }]
            });

            adsChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'adsChartContainer'
                },
                title: {
                    text: 'Deals Created'
                },
                xAxis: {
                    categories: adsSummaryMonths
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Deals Created'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Active Deals',
                    data: adsSummaryData1
                }, {
                    name: 'Deals Done',
                    data: adsSummaryData2
                }]
            });

            rechargeChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'rechargeChartContainer'
                },
                title: {
                    text: 'Transaction Summary'
                },
                xAxis: {
                    categories: walletSummaryMonths
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Spending/Eanrning'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Buyer Transaction',
                    data: walletSummaryData1
                }, {
                    name: 'Seller Transaction',
                    data: walletSummaryData2
                }, {
                    name: 'Broker Transaction',
                    data: walletSummaryData3
                }]
            });

            DSUtils.hideLoader();
            /*
            userChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'userChartContainer'
                },
                colors: ['#7cb5ec', 'red'],
                title: {
                    text: 'User Summary'
                },
                xAxis: {
                    categories: ['October', 'November', 'December', 'January', 'February']
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'New Users'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'New Users',
                    data: [150, 100, 140, 70, 200]
                }, {
                    name: 'Blocked Users',
                    data: [15, 50, 35, 12, 25]
                }]
            });

            adsChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'adsChartContainer'
                },
                title: {
                    text: 'Ads Posted'
                },
                xAxis: {
                    categories: ['October', 'November', 'December', 'January', 'February']
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Ads Posted'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Ads Posted',
                    data: [500, 600, 302, 700, 200]
                }, {
                    name: 'Ads Visitors',
                    data: [800, 400, 600, 500, 700]
                }]
            });

            rechargeChart = new Highcharts.Chart({
                chart: {
                    type: 'column',
                    renderTo: 'rechargeChartContainer'
                },
                title: {
                    text: 'Wallet Recharge'
                },
                xAxis: {
                    categories: ['October', 'November', 'December', 'January', 'February']
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'User Activity'
                    }
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: 'Posted Ads Amount',
                    data: [5000, 450, 3284, 400, 200]
                }, {
                    name: 'Recharge Amount',
                    data: [500, 500, 200, 500, 700]
                }]
            });
*/
            //userChart.redraw();
            // adsChart.redraw();
            // rechargeChart.redraw();
        }
        ;

    }

    angular.module('Reachx').controller('DashboardController', DashboardController);
}());