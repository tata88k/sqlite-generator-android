# SQLiteCodeGenerator-android
A powerful sqlite code generator for android .

# Introduce
This repository is a code genarator for Android . By using it, you can generate java codes to read/write sqlite
 * This is a standard java application.
 * Dev Language: Java
 * Dev IDE: IntelliJ_v15.0.2

# Features
* Generate read/write java codes from an existing database file
* Support tables and data views
* Base on SQLiteOpenHelper and 100% support any SQL statement
* Generated codes are light,simple,expandable and pluggable
* Code quantity is less 40% than GreedDao

# Usage
* Clone the whole project from github
* Import the project into IntelliJ (Optionally, you can copy the sources to eclipse IDE)
* Set your own models package, api package ,table name format,and the target directory
* Run the project, and you will see the whole generated codes in the target directory

# Limit
* Currently , you must set a named "id" column as the table primary key.
  For more needs, you need to modify the project by yourself.

# Dependencies
* freemarker-2.3.23.jar
* sqlite-jdbc-3.8.11.2.jar
