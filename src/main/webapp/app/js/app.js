proto = function(appName, appHomePath, resourceURL) {
    var project = angular.module(appName, []);

    //config
    project.config(function($routeProvider, $httpProvider) {
        $routeProvider.
                when('/', {
            controller: 'homeCtrl',
            templateUrl: appHomePath + '/views/home.html'
        }).
                otherwise(
                {redirectTo: '/'}
        );
        $httpProvider.defaults.withCredentials = true;
    });

    //Home Controller
    project.controller('homeCtrl', function($scope, $http) {

        //get context as JSON
        $http({method: 'GET', url: url(resourceURL, "getJSON")}).
            success(function (data) {
                //i18n messages
                $scope.msgs = data.messages;
                $scope.pref1 = data.pref1;
                $scope.uid = data.uid;
                $scope.portletMode = data.portletMode;
                $scope.windowState = data.windowState;
            });

        $scope.getContent = function(p1) {
            $http({method: 'GET', url: url(resourceURL, "getDate", p1)}).
                success(function (data) {
                    $scope.content = data.date;
                });
        };

        $scope.isMinimized = function() {
            return ($scope.windowState == "minimized");
        };

    });
};

// ************* utils *************

//forge a portlet resource url
function url(pattern, id, p1, p2, p3, p4) {
    return pattern.
            replace("@@id@@", id).
            replace("__p1__", p1).
            replace("__p2__", p2).
            replace("__p3__", p3).
            replace("__p4__", p4);
}
