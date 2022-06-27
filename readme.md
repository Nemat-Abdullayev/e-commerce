# E-Commerce application (Task)
***
### About application installation tools
* Java jdk version 18
* data base (postgresql in the docker container should be up)
* cache redis (in the docker container should be up)
* test created with the Junit5
***
* first run tests and enjoy relaxed
* second build application with gradle for create build file
* then go to the docker-compose.yml file direction
* run this command [ docker-compose -f docker-compose.yml up --build ]
***
## hint for application
* [in the application swagger-ui used for documentation](http://localhost:8080/swagger-ui.html)
* in the application user have 2 role [ 'SELLER', 'BUYER' ]
* if user have active session,other user with same cridentionals must be wait until the exparation of token or go
to '/all/logout' endpoint and terminate all the active sessions
* role inserted by default (while application up)
* SELLER insert a product and other operation
* BUYER add deposit for buy product and etc.