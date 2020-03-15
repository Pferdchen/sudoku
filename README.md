# sudoku

Basic sudoku algorithm (todo maybe a module). Learn new features of JDK 13.

## Sudoku

Design and implement a sudoku algorithm with Java Swing and AWT.

## SudokuFX

Design and implement a sudoku algorithm with [JavaFX 13](https://openjfx.io/openjfx-docs/).

[JavaFX 13](https://openjfx.io/openjfx-docs/) builds on top of JDK 13 and is a standalone component. There are 2 different options for developing JavaFX applications:

* Use the JavaFX SDK
* Use a build system (e.g. maven/gradle) to download the required modules from Maven Central.

For both options, it is required to have a recent version of JDK 13, or at least JDK 11.

## SudokuFX2

A Non-Modular-Maven project for Sudoku.

### For Netbeans

Before running the project from the Run button, we need to instruct NetBeans to use the javafx plugin instead of the default one. For that, go to Properties -> Actions -> Run project and change the Execute Goals with this: 

> clean javafx:run



## SudokuFX3

A Modular-Maven project for Sudoku.

### Netbeans

Before running the project from the Run button, we need to instruct NetBeans to use the javafx plugin instead of the default one. For that, go to Properties -> Actions -> Run project and change the Execute Goals with this: 

> clean javafx:run

### Eclipse

Edit the module-info class. To prevent Eclipse from showing a warning when creating the Application class, add also the transitive modules to the file: 

```java
requires transitive javafx.graphics;
```

### Maven sample

[hellofx](https://github.com/openjfx/samples/tree/master/HelloFX/Maven) is good start.
