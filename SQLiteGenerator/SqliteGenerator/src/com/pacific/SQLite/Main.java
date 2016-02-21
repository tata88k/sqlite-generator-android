package com.pacific.SQLite;

public class Main {

    public static void main(String[] args) {

        //model package
        final String modelPackage = "com.example.ipad.unix.model";

        //api package
        final String apiPackage = "com.example.ipad.unix.db";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Generator generator = new Generator();
                generator.setOutDir("./gen-src");
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
