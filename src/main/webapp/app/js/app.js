proto = function(appName, appHomePath, resourceURL) {
    var project = angular.module(appName, []);

    //config
    project.config(['$routeProvider', function($routeProvider) {
        $routeProvider.
                when('/', {
            controller: 'homeCtrl',
            templateUrl: appHomePath + '/views/home.html'
        }).
                otherwise(
                {redirectTo: '/'}
        );
    }]);

    //Home Controller
    project.controller('homeCtrl', function($scope, $http) {

        //get context as JSON
        $http({method: 'GET', url: url(resourceURL, "getJSON")}).
            success(function (data) {
                //i18n messages
                $scope.msgs = data.messages;
                $scope.uid = data.uid;
                $scope.portletMode = data.portletMode;
                $scope.windowState = data.windowState;
            });

        $scope.getContent = function() {
            $scope.content = $scope.url ;
        }
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
