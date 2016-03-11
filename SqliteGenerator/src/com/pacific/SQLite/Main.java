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
        //output dir
        final String outDir = "./gen-src";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Generator generator = new Generator();
                generator.setOutDir(outDir);
                generator.setTargetDB(targetDB);
                generator.setModelPackage(modelPackage);
                generator.setApiPackage(apiPackage);

                //tables and views name format
                generator.setTableNameCodeStyle(CodeStyle.Camel);
                //column name format
                generator.setColumnNameCodeStyle(CodeStyle.Camel);

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
