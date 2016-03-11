package com.pacific.SQLite;


import java.util.HashMap;

/**
 * Created by UsherBaby on 2016/1/6.
 */
public class TypeMap {
//    private HashMap<String, String> java2db;
    private HashMap<String, String> db2javaNullable;
    private HashMap<String, String> db2javaNotNull;

    public TypeMap() {
//        java2db = new HashMap<>();
        db2javaNullable = new HashMap<>();
        db2javaNotNull = new HashMap<>();

//        java2db.put("int","INTEGER");
//        java2db.put("Integer","INTEGER");
//        java2db.put("float","REAL");
//        java2db.put("Float","REAL");
//        java2db.put("double","DOUBLE");
//        java2db.put("Double","DOUBLE");
//        java2db.put("boolean","BOOLEAN");
//        java2db.put("Boolean","BOOLEAN");
//        java2db.put("byte[]","BLOB");
//        java2db.put("java.util.Date","TEXT");
//        java2db.put("String", "TEXT");

        db2javaNullable.put("BIGINT", "Long");
        db2javaNullable.put("INTEGER", "Integer");
        db2javaNullable.put("INT", "Integer");
        db2javaNullable.put("NUMERIC", "Integer");
        db2javaNullable.put("DECIMAL", "Integer");
        db2javaNullable.put("DOUBLE", "Double");
        db2javaNullable.put("REAL", "Float");
        db2javaNullable.put("BOOLEAN", "Boolean");
        db2javaNullable.put("BLOB", "byte[]");
        db2javaNullable.put("DATETIME", "java.util.Date");
        db2javaNullable.put("DATE", "java.util.Date");
        db2javaNullable.put("NONE", "String");
        db2javaNullable.put("STRING", "String");
        db2javaNullable.put("CHAR", "String");
        db2javaNullable.put("VARCHAR", "String");
        db2javaNullable.put("TEXT", "String");
        db2javaNullable.put("TIME", "String");

        db2javaNotNull.put("BIGINT", "long");
        db2javaNotNull.put("INTEGER", "int");
        db2javaNotNull.put("INT", "int");
        db2javaNotNull.put("NUMERIC", "int");
        db2javaNotNull.put("DECIMAL", "int");
        db2javaNotNull.put("DOUBLE", "double");
        db2javaNotNull.put("REAL", "float");
        db2javaNotNull.put("BOOLEAN", "boolean");
        db2javaNotNull.put("BLOB", "byte[]");
        db2javaNotNull.put("DATETIME", "java.util.Date");
        db2javaNotNull.put("DATE", "java.util.Date");
        db2javaNotNull.put("NONE", "String");
        db2javaNotNull.put("STRING", "String");
        db2javaNotNull.put("CHAR", "String");
        db2javaNotNull.put("VARCHAR", "String");
        db2javaNotNull.put("TEXT", "String");
        db2javaNotNull.put("TIME", "String");
    }

//    public HashMap<String, String> getJava2db() {
//        return java2db;
//    }

    public HashMap<String, String> getDb2javaNullable() {
        return db2javaNullable;
    }

    public HashMap<String, String> getDb2javaNotNull() {
        return db2javaNotNull;
    }

//    enum DBType{
//        BIGINT,
//        INTEGER,
//        INT,
//        NUMERIC,
//        DECIMAL,
//        DOUBLE,
//        REAL,
//        BOOLEAN,
//        BLOB,
//        DATETIME,
//        DATE,
//        NONE,
//        STRING,
//        CHAR,
//        VARCHAR,
//        TEXT,
//        TIME
//    }
}
