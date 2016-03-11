package com.pacific.SQLite;

/**
 * Created by UsherBaby on 2016/1/6.
 */

public class Main {

    public static void main(String[] args) {

        //target sqlite database file
        final String targetDB = "Unix.db";
        //model package
        final String modelPackage = "com.example.ipad.unix.model";
        //api package
        final String apiPackage = "com.example.ipad.unix.db";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Generator generator = new Generator();
                generator.setOutDir("./gen-src");
                generator.setTargetDB(targetDB);
                generator.setModelPackage(modelPackage);
                generator.setApiPackage(apiPackage);

                //tables and views name format
                generator.setTableNameCodeStyle(CodeStyle.Camel);

                //generated java code style
                generator.setMemberNameCodeStyle(CodeStyle.Camel);

                generator.generateSuperModel();
                generator.generateViewModel();
                generator.generateModel();
                generator.generateDBOpenHelper();
                generator.generateDBContext();
                generator.generateDataAccessProxy();
            }
        }).start();
    }
}
