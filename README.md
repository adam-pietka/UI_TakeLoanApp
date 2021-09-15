## General information
- UI is almoset not usable, there is still a lot of work with it
- to start application demandet is start at localhost both layers, FN, and HOST, in file application.properties there are settup difference port, so it's possible to run without any changes
- demandet is also to set MySql db at local and change settup in  application.properties file on HOST side


## Link to HOST application

- Main link to main repsitory [adam-pietka](https://github.com/adam-pietka)
- Repository to HOST [SimpleLoanApplication](https://github.com/adam-pietka/TakeLoanApp)

# Simple Take a Loan apps

This project can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to set up a development environment for
Vaadin projects](https://vaadin.com/docs/latest/guide/install) (Windows, Linux, macOS).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/simpletakealoanapps-1.0-SNAPSHOT.jar`

## Project structure

- `MainView.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Link to HOST application

- Main link to main repsitory [adam-pietka](https://github.com/adam-pietka)
- Repository to [SimpleLoanApplication](https://github.com/adam-pietka/TakeLoanApp)


##
