# sudoku repository

Design and implement a sudoku algorithm with Java Swing and AWT or JavaFX.

## ~~Sudoku deprecated~~

A basic sudoku algorithm. Learn new features of JDK 13.

## Sudoku2

A Non-Modular-Maven project for sudoku core.

## Sudoku3

A Modular-Maven project for sudoku core.

## ~~SudokuFX deprecated~~

Design and implement a sudoku algorithm with [JavaFX 14](https://openjfx.io/openjfx-docs/).

[JavaFX](https://openjfx.io/openjfx-docs/) builds on top of JDK and is a standalone component. There are 2 different options for developing JavaFX applications:

* Use the JavaFX SDK
* Use a build system (e.g. maven/gradle) to download the required modules from Maven Central.

For both options, it is required to have a recent version of JDK 14, or at least JDK 11.

## SudokuFX2

A Non-Modular-Maven project for sudoku GUI with JavaFX.

### Netbeans

Before running the project from the Run button, we need to instruct NetBeans to use the javafx plugin instead of the default one. For that, go to Properties -> Actions -> Run project and change the Execute Goals with this: 

> clean javafx:run

## SudokuFX3

A Modular-Maven project for sudoku GUI with JavaFX.

### Netbeans

Before running the project from the Run button, we need to instruct NetBeans to use the javafx plugin instead of the default one. For that, go to Properties -> Actions -> Run project and change the Execute Goals with this: 

> clean javafx:run

### Eclipse

Edit the module-info class. To prevent Eclipse from showing a warning when creating the Application class, add also the transitive modules to the file: 

```java
requires transitive javafx.graphics;
```

## SudokuFXSimple

A Modular-Maven project for sudoku GUI with JavaFX, but without FXML files

## SudokuJSF

A Non-Modular-Maven project for sudoku GUI with JSF. A Java Web Server is needed for deploying. Create custom UI component.

Since H2 database ships with WildFly / JBoss EAP, set up H2 for testing. Use [h2console.war](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/7.1.1.Final/h2-console) to view the database content. Just deploy it in

```
<wildfly_home>/(domain|standalone)/deployments
```

and access the console at the following URL:  <http://localhost:8080/h2console>.
