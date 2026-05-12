# HMCTS Dev Test Backend
This is the backend for the brand new HMCTS case management system. 

Run `./gradlew clean build` to start with to ensure it builds successfully. 

Run `./gradlew clean bootRun` to start the app

Note:

I Tried to document API endpoints but SpringDoc is simply not being initialised as a Spring MVC module at runtime and I have no idea why, eventhough the dependency resolves and SpringDoc is on runtime Classpath. even making my won OpenApiConfig didnt help.


