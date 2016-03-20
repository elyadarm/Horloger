angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});

			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});
			
			$routeProvider.otherwise({
				templateUrl: 'partials/index.html',
				controller: IndexController
			});
			
			$routeProvider.when('/commande', {
				templateUrl: 'partials/commande.html',
				controller: CommandeController
			});
			$routeProvider.when('/commande/edit/:id', {
				templateUrl: 'partials/editCommande.html',
				controller: EditCommandeController
			});
			$routeProvider.when('/commande/create', {
				templateUrl: 'partials/createCommande.html',
				controller: CreateCommandeController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore, UserService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	});


function IndexController($scope, NewsService) {
	
	$scope.newsEntries = NewsService.query();
	
	$scope.deleteEntry = function(newsEntry) {
		newsEntry.$remove(function() {
			$scope.newsEntries = NewsService.query();
		});
	};
};


function EditController($scope, $routeParams, $location, NewsService) {

	$scope.newsEntry = NewsService.get({id: $routeParams.id});
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};


function CreateController($scope, $location, NewsService) {
	
	$scope.newsEntry = new NewsService();
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};


function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path("/");
			});
		});
	};
};

function CommandeController($scope, CommandeService) {
	
	$scope.clients = [];
	
	$scope.commandes = CommandeService.query(function (){
		
		for(var i= 0; i <  $scope.commandes.length; i++)
		{
			$scope.commandes[i].clients = [];
		    $scope.commandes[i].clients.push($scope.commandes[i].client);
		}
	});
	

	$scope.deleteCommande = function(commande) {
		commande.$remove(function() {
			$scope.commandes = CommandeService.query();
		});
	};
};
function EditCommandeController($scope, $routeParams, $location, CommandeService) {

	$scope.commande = CommandeService.get({id: $routeParams.id});
	
	$scope.save = function() {
		$scope.commande.$save(function() {
			$location.path('/commande');
		});
	};
};
function CreateCommandeController($scope, $location, CommandeService) {
	
	$scope.commande = new CommandeService();
	$scope.etape = 1;
	$scope.client = {};
	$scope.montres = [];
	
	$scope.save = function() {
		$scope.commande.client = $scope.client;
		$scope.commande.montres = $scope.montres;
		$scope.commande.$save(function() {
			$location.path('/commande');
		});
	};
	
	$scope.ajouterMontre = function(){
		$scope.montres.push({});
	};
	$scope.subMontre = function(){
		$scope.montres.splice($scope.montres.length-1);
	};
	$scope.back = function(){
		
		$scope.etape--;
		$scope.width = 25 * ($scope.etape-1) + "%";
	};
	$scope.continue = function(){
		
		$scope.etape++;
		$scope.width = 25 * ($scope.etape-1) + "%";
	};
};
var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

services.factory('NewsService', function($resource) {
	
	return $resource('rest/news/:id', {id: '@id'});
});



services.factory('CommandeService', function($resource) {
	
	return $resource('rest/commande/:id', {id: '@id'});
});