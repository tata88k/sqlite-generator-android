# SQLiteCodeGenerator-android
A powerful sqlite code generator for android .
<p>
![](https://github.com/ThePacific/SQLiteCodeGenerator-android/blob/master/gif/guide.gif)
<p>
![](https://github.com/ThePacific/SQLiteCodeGenerator-android/blob/master/gif/android.gif)
# Introduce
This repository is a code genarator for Android . By using it, you can generate java codes to read/write sqlite
 * This is a standard java application.
 * Dev Language: Java
 * Dev IDE: IntelliJ_v15.0.2

# Features
* Generate map objects(tables and data views) from an existing sqlite file
* Generate API to read/write sqlite (insert,update,delete,query and so on)
* API based on SQLiteOpenHelper and 100% support any SQL statement
* API is super light , expandable and pluggable
* Code quantity is less 40% than GreedDao

# Usage
* Clone the whole project from github
* Import the project into IntelliJ (Optionally, you can copy the sources to eclipse IDE)
* Set your own models package, api package ,table name format,and the target directory
* Run the project, and you will see the whole generated codes in the target directory

ps: I design my sqlite database file on SQLiteStudio and generate code with SQLiteCodeGenerator-android

# Limit
* Currently , you must set a named "id" column as the table primary key.
  For more needs, you need to modify the project by yourself.
* You must name your tables or views in a strict format . Currently, Pascal ,Hungarian and Camel format are supported.

# Dependencies
* freemarker-2.3.23.jar
* sqlite-jdbc-3.8.11.2.jar
