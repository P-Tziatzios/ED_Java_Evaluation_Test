# Accounting System

A Sandbox project that has the goal to assess the development skills of a Web Developer candidate by completing
exercises. The Web Application is a small scale Accounting System to manage Accounts of Users and their transactions. 
On the home screen the user is presented with his transactions, and his accounts. The Application allows searching for 
transactions as well as adding new ones. The application allows adding new Accounts and Users as well. 

To complete the exercises the candidate requires some knowledge in Java, HTML, Typescript/Javascript and SQL.

These instructions include everything the candidate needs to know on how to download the sources of this simplified web
application on a personal computer as well as how to deploy and run it. The instructions begin with going through the
installation process. After the application is successfully installed and running, the candidate is ready to start with
the exercises that follow. Each exercise awards a different amount of points totaling 100 points.

The use of any IDE and/or editor to edit the source code is allowed. It is totally acceptable, for the candidate to get
help and information he/she needs from the internet. However, it is not acceptable to ask for another person's help.

Nevertheless, the candidate is encouraged to contact the assessor for any questions he/she might have, be it he/she
needs any additional information, or he/she requires some help for completing the installation and/or an exercise. If
the candidate has difficulties for example to complete the installation, he/she could ask for help from the assessor in
order to be able to go on with the exercises and gain points.

The assessor should provide contact information to the candidate.

The candidate has to locate the files needed to complete the exercises. The exercises cover both front-end and back-end
implementation tasks.

## Installation

To install and run the application an active internet connection is required. Before this project can be build, the
following dependencies must be installed and configured on the candidates machine:

### 1: JDK 17

Download and install any version of the [JDK 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows).
Install JDK in a folder whose path does not contain spaces. Also Make sure to set the environmental variable JAVA_HOME
in your system. The variable must point to your JDK folder (
absolute path). The path of the "\jre\bin" folder under the JDK folder should also be **prepended** to the PATH system
variable.

### 2: Maven Version 3.x

Download and install any version of [Maven 3](https://maven.apache.org/download.cgi). Install Maven in a folder whose
path does not contain spaces. Make sure to set the environmental variables M2_HOME and M2 in your system. The M2_HOME
variable must point to the maven folder (absolute path). The M2 variable must point to the "bin" folder under the maven
folder (absolute path). The absolute path of the "bin" folder under the maven folder (M2) should also be **prepended**
to the PATH system variable.

### 3: Git (Optional)

The candidate can download and install the [Git command line client](https://git-scm.com/download/win) to download the
sources of the application.

### 4: Source code

Download the sources of the application. If Git client was installed in the previous step, then navigate with the
command line to a folder in which you want to download the source code. Run the following command:

```
git clone https://github.com/DimiTerz/EDAS.git
```

Alternatively, if Git client was not installed in the previous step, you can download the zip from

```
https://github.com/DimiTerz/EDAS.git
```

For your convinience, make sure that the folder with the application's sources is saved under a path which does not
contain spaces.

### 5: Node.js (Optional)

Node.js is used to run a development web server and build the project. Depending on your system, you can install Node.js
either from source or as a pre-packaged bundle.

### 6: Compile and start App

Navigate to the project folder and execute the command `mvn clean install`

This should compile your application. When this process finishes navigate to `/backEnd` folder and
run `mvn spring-boot:run`

Navigate to http://localhost:8080 in your browser to open the application. There are 4 available Users you can use to
login to the application:
(Username and password are same)

`JDoe`
`LDoe`
`SScotts`
`admin`

To terminate the application, just terminate the execution of the previous command `Ctrl + C`. After making changes to
the code stop the application and repeat step `6`

More information regarding the components of the Application can be found in further README files in each of the project
modules.

## Excercices

The following tasks award points up to a maximum of 100.

### Installation and Exercise 1 (20 points)

At the moment the Application can not be compiled. The code has some deliberate placed errors. For this exercise and the
Test to begin the candidate should identify and fix the compilation errors in the code.

### Exercise 2 (10 points)

At the moment code runs into some exceptions when the User tries to open the About page. The candidate should Find the
exceptions and eliminate the broken behaviour.

### Exercise 3 (20 points)

To display the current Balance of an account the Function `Calcbalance2` is used. To complete this Exercise the
candidate should identify and fix issues with this function regarding code style, functionality, performance, etc. For
each successful improvement the candidate gets awarded with points up to the maximum for this Exercise.

### Exercise 4 (10 points)
At the moment there are some Tests implemented for the Java code. In class `UserControllerTests` only a sceleton remains
for the tests remain. To complete this exercise the candidate needs to complete the missing JUnit test.

### Exercise 5 (30 points)
At the moment Users are unable to register their Age. For this exercise add an age Attribute to the users. To complete
this task the candidate should implement all the necessary changes in the frontend, the backend and the database.

### Extra Credit Exercise

This Exercise is optional and is designed to give credits for design and logical thinking. To score points the candidate
should implement an export functionality for the Transactions listed in an Account. The candidate is free to use any
libraries and can implement it in any way. The only conditions in place are the following:

#### The data should be exported to a file in `csv` format.

#### The export should de triggered from the Account view with a button.

## Delivery

When finished the candidate should upload the finished project to his personal GitHub, creating a new repository and 
provide the evaluators with the link. A screenshot of the candidate's browser with the Application running and a user logged in
should be sent in order to verify that the installation was successfully completed.

The deadline to send the compressed file is determined by the assessor. Normally it should be exactly three days after
the test has started.

Thank you for participating and good luck!!!
