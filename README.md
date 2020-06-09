# JSON Driven Form

## About
Simple [Spring Boot](http://projects.spring.io/spring-boot/) app which parse json template to html form and perform its basic CRUD operation to the db.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MongoDB](https://www.mongodb.com/)

## Technology Stack
-  Spring Boot, no-xml Spring MVC 4 web application for Servlet 3.0 environment
-  Spring Data MongoDB
-  Database (MongoDB, MongoLab)
-  Freemarker Template Engine and JSP pages
-  Heroku fully cloud deployable
-  Testing (JUnit/Mockito)
-  Java 8, Spring Security, Maven, SLF4J etc

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.jsondriventemplate.app.JSONDrivenTemplateApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
$ mvn test
$ mvn clean install  
$ mvn spring-boot:run
```
Navigate to http://localhost:8080.

## Default Login Credential
username: superadmin  
password: superadmin123

## JSON Template Menu Flow
- After Login to dashboard, Click on Editor Menu from sidebar
- Provide any readable name for template and its access url
- Submit the form
- It will list on the page
- Now ready to go for JSON Editor then make a JSON definitions for particular page 

## JSON Editor Menu Flow
- After Login to dashboard, Click on Editor Menu from sidebar
- Select a Login from dropdown menu
- Click on Load (this will load json definitions for login page)
    ![Editor Login](https://raw.githubusercontent.com/nrynjoshi/jsonDrivenForm/master/img/Editor-with-Login-Page.JPG?raw=true)
- Login JSON password and username before field chang
    ![Editor Login](https://raw.githubusercontent.com/nrynjoshi/jsonDrivenForm/master/img/Editor-Login-Page-Before-Password-Changed.JPG?raw=true)
- Login JSON password and username field changed
    ![Editor Login](https://raw.githubusercontent.com/nrynjoshi/jsonDrivenForm/master/img/Editor-Login-Page-After-Password-Changed.JPG?raw=true)    

## Deploying the application to Heroku

The following steps require that the Heroku Toolbelt has been installed locally and that a Heroku account has been created.

Navigate to the project directory on the command line.

Before creating your Heroku application, make sure that there is a Git repository associated with the project.
```shell
git status
```
If a Git repository is not associated with the project, then create one before continuing.

Create a new application on Heroku
```shell
$ heroku create
```
Rename your Heroku application if interested
```shell
$ heroku apps:rename new-name
```
Add a MongoDB database to your Heroku application with MongoLab. Note that your Heroku account must have a credit card attached in order to use free add-ons other than the PostgreSQL and MySQL add-ons.
```shell
heroku addons:create mongolab:sandbox
```

Retrieve your MongoDB database name by clicking on the MongoLab addon. Place the database name into the `src/main/resources/application.properties` configuration file in the database field.

Deploy project to Heroku.  
```
$ git push heroku master
```

Look at your application logs to see what is happening behind the scenes.  
```
$ heroku logs
```

If your application deploys without timing out then open it as follows. 
```
$ heroku open
```

JSON template Information
- If you dont wanna a use grid then also please mention  below in json for proper execution
    ```json
    {
        "grid":"",
        "gridindex":""
     }
    ```
- TO define grid below is the code snippet
    ```json
    {
        "grid":{
                 "columnsize": "11",
                 "minrowheight": "10vh"
               },
        "gridindex":{
                 "column": "1,6",
                 "row": "1,2"
               }
     }
    ```
    - columnsize = number of column [ default columnsize is 12 ]  
    -  minrowheight = minimum height of row 
    
    gridindex is used on each field to fixed grid position you can use
    - column = represent the json field to be populate on particular position/point
    - row = represent the json field to be populate on particular position/point
     

     
