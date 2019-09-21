# Punchclock
## Introduction

This application allows you to make your own account, sign in with it and track your time. You have the possibility to see your profile info (username), adjust your account or delete it.
Whenever you perform a log in or log out action on your account, the application will automatically track the exact time of these actions and write them down for you to see in the "/entries" tab.
There you also gain access to either clear all entries or adjust them.
The application comes with 3 accounts ready for usage, thanks to a script being called inside the UserController constructor generating mock data.
While a JPQL query was defined in /PunchclockAPI/Test/java/ch.zli.m223.punchclock/PunchlockApplicationTests it somewhy doesn't register the User as an entity.
It is also worth noting that every single process is secured through JWT.

## Set up
### Client side
For the frontend, you need to install Angular as shown [here](https://angular.io/guide/setup-local).

Create a new project at your desired location by typing in `ng new PunchclockClient` into the CLI of your choice.

Navigate to the created project and replace its src folder with the one in /PunchclockClient.
Run the following command to install the modules:
`npm install`
Now you might be ready to run the frontend project by typing in `ng serve -o` or `ng serve` and opening `http://localhost:4200` in a new browser window.

`Note: If the above command ended up throwing errors you might need to add the packages manually by typing in:`
`npm install --save @angular/material @angular/cdk @angular/animations`
`npm install ngx-cookie-service --save`

### Server side
For the backend, it is recommended to use [Intellij Ultimate](https://www.jetbrains.com/idea/download/#section=windows). Otherwise you can run it as shown in the README.MD file in /PunchclockAPI.
Once Intellij Ultimate is installed, you might need to change the sourceCompability to a matching sdk version installed on your hard drive. You can check out your current sdk versions in File -> Project Structure -> Project -> Project SDK.
You likely will be asked to import the changes when performed the step above. Click on the button to import the changes.
After the changes have been imported you should be able to run the backend project as well.
`Note: If you don't seem to be able to run the project just yet you might need to restart Intellij Ultimate and import the changes if asked to do so.`

### Enjoy!