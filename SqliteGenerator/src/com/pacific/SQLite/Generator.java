package com.pacific.SQLite;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by UsherBaby on 2016/1/6.
 */
public class Generator {

//    private Pattern patternKeepIncludes;
//    private Pattern patternKeepFields;
//    private Pattern patternKeepMethods;

    private String modelPackage;
    private String apiPackage;
    private String outDir;
    private String targetDB;
    private TypeMap typeMap;
    private Configuration config;
    private CodeStyle tableNameCodeStyle;
    private CodeStyle memberNameCodeStyle;

    public Generator() {
//        patternKeepIncludes = compilePattern("INCLUDES");
//        patternKeepFields = compilePattern("FIELDS");
//        patternKeepMethods = compilePattern("METHODS");
        typeMap = new TypeMap();
        try {
            config = new Configuration(Configuration.VERSION_2_3_23);
            config.setDirectoryForTemplateLoading(new File("src-template"));
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public void generateViewModel() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Unix.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String getTableNameSql = "select name from sqlite_master where type='view'";
            ResultSet rs = statement.executeQuery(getTableNameSql);
            while (rs.next()) {
                statement = connection.createStatement();
                String tableName = rs.getString(1);
                ResultSetMetaData meta = statement.executeQuery("select * from " + tableName).getMetaData();
                ViewModel model = new ViewModel();
                model.setJavaPackage(modelPackage);
                model.setTableNameCodeStyle(tableNameCodeStyle);
                model.setMemberNameCodeStyle(memberNameCodeStyle);
                model.setSuperModelClassName("SuperModel");
                model.setTableName(tableName);
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String name = meta.getColumnName(i);
                    String type;
                    if (meta.isNullable(i) == 0) {
                        type = typeMap.getDb2javaNotNull().get(meta.getColumnTypeName(i));
                    } else {
                        type = typeMap.getDb2javaNullable().get(meta.getColumnTypeName(i));
                    }
                    if (type == null) {
                        type = "String";
                    }
                    model.addType(type);
                    model.addMember(name, type);
                    if (tableNameCodeStyle == CodeStyle.Camel) {
                        model.setClassName(StringHelper.camel2Pascal(tableName));
                    } else if (tableNameCodeStyle == CodeStyle.Pascal) {
                        model.setClassName(tableName);
                    } else {
                        model.setClassName(StringHelper.score2Pascal(tableName));
                    }
                }

                Map<String, Object> root = new HashMap();
                root.put("model", model);
                root.put("stringHelper", new StringHelper());
                try {
                    File file = package2File(getOutDir(), model.getJavaPackage(), model.getClassName());
                    file.getParentFile().mkdirs();
                    Writer writer = new FileWriter(file);
                    try {
                        Template t = config.getTemplate("ViewModel.ftl");
                        t.process(root, writer);
                        writer.flush();
                        System.out.println(model.getClassName() + ".java");
                    } finally {
                        writer.close();
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void generateModel() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Unix.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String getTableNameSql = "select name from sqlite_master where type='table' and name!='sqlite_sequence'";
            ResultSet rs = statement.executeQuery(getTableNameSql);
            while (rs.next()) {
                statement = connection.createStatement();
                String tableName = rs.getString(1);
                ResultSetMetaData meta = statement.executeQuery("select * from " + tableName).getMetaData();
                Model model = new Model();
                model.setJavaPackage(modelPackage);
                model.setTableNameCodeStyle(tableNameCodeStyle);
                model.setMemberNameCodeStyle(memberNameCodeStyle);
                model.setSuperModelClassName("SuperModel");
                model.setTableName(tableName);
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String name = meta.getColumnName(i);
                    String type;
                    if (meta.isNullable(i) == 0) {
                        type = typeMap.getDb2javaNotNull().get(meta.getColumnTypeName(i));
                    } else {
                        type = typeMap.getDb2javaNullable().get(meta.getColumnTypeName(i));
                    }
                    if (type == null) {
                        type = "String";
                    }
                    model.addType(type);
                    model.addMember(name, type);
                    if (meta.isAutoIncrement(i)) {
                        model.addAutoIncrementMember(name);
                    }
                    if (tableNameCodeStyle == CodeStyle.Camel) {
                        model.setClassName(StringHelper.camel2Pascal(tableName));
                    } else if (tableNameCodeStyle == CodeStyle.Pascal) {
                        model.setClassName(tableName);
                    } else {
                        model.setClassName(StringHelper.score2Pascal(tableName));
                    }
                }

                if (!model.hasIdPrimaryKey()) {
                    String msg = "table : " + model.getTableName() + " doesn't has a \"id\" column";
                    throw new RuntimeException(msg);
                }

                Map<String, Object> root = new HashMap();
                root.put("model", model);
                root.put("stringHelper", new StringHelper());
                try {
                    File file = package2File(getOutDir(), model.getJavaPackage(), model.getClassName());
                    file.getParentFile().mkdirs();
                    Writer writer = new FileWriter(file);
                    try {
                        Template t = config.getTemplate("Model.ftl");
                        t.process(root, writer);
                        writer.flush();
                        System.out.println(model.getClassName() + ".java");
                    } finally {
                        writer.close();
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void generateDBOpenHelper() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + targetDB);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String getTableNameSql = "select name,sql from sqlite_master where type='table' and name!='sqlite_sequence'";
            ResultSet rs = statement.executeQuery(getTableNameSql);
            Api api = new Api();
            api.setJavaPackage(apiPackage);
            api.setDbOpenHelperClassName("DBOpenHelper");
            while (rs.next()) {
                api.addTable(rs.getString(1), rs.getString(2));
            }
            rs.close();

            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            getTableNameSql = "select name,sql from sqlite_master where type='view'";
            rs = statement.executeQuery(getTableNameSql);
            while (rs.next()) {
                api.addView(rs.getString(1), rs.getString(2));
            }
            rs.close();

            Map<String, Object> root = new HashMap();
            root.put("api", api);
            root.put("stringHelper", new StringHelper());
            try {
                File file = package2File(getOutDir(), api.getJavaPackage(), api.getDbOpenHelperClassName());
                file.getParentFile().mkdirs();
                Writer writer = new FileWriter(file);
                try {
                    Template t = config.getTemplate("DBOpenHelper.ftl");
                    t.process(root, writer);
                    writer.flush();
                    System.out.println("Written " + file.getCanonicalPath());
                } finally {
                    writer.close();
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void generateDataAccessProxy() {
        Map<String, Object> root = new HashMap();
        Api api = new Api();
        api.setJavaPackage(apiPackage);
        api.setModelJavaPackage(modelPackage);
        api.setDataAccessClassName("DataAccessProxy");
        api.setDbOpenHelperClassName("DBOpenHelper");
        api.setSuperModelClassName("SuperModel");
        root.put("api", api);

        try {
            File file = package2File(getOutDir(), api.getJavaPackage(), api.getDataAccessClassName());
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            try {
                Template t = config.getTemplate("DataAccessProxy.ftl");
                t.process(root, writer);
                writer.flush();
                System.out.println("Written " + file.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void generateDBContext() {
        Map<String, Object> root = new HashMap();
        Api api = new Api();
        api.setJavaPackage(apiPackage);
        api.setDbContextClassName("DbContext");
        root.put("api", api);
        try {
            File file = package2File(getOutDir(), api.getJavaPackage(), api.getDbContextClassName());
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            try {
                Template t = config.getTemplate("DbContext.ftl");
                t.process(root, writer);
                writer.flush();
                System.out.println("Written " + file.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void generateSuperModel() {
        Map<String, Object> root = new HashMap();
        Model model = new Model();
        model.setJavaPackage(modelPackage);
        model.setClassName("SuperModel");
        root.put("model", model);
        try {
            File file = package2File(getOutDir(), model.getJavaPackage(), model.getClassName());
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            try {
                Template t = config.getTemplate("SuperModel.ftl");
                t.process(root, writer);
                writer.flush();
                System.out.println("Written " + file.getCanonicalPath());
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    protected File package2File(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        return file;
    }

    protected File getOutDir() {
        File file = new File(outDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    private Pattern compilePattern(String sectionName) {
        int flags = Pattern.DOTALL | Pattern.MULTILINE;
        return Pattern.compile(".*^\\s*?//\\s*?KEEP " + sectionName + ".*?\n(.*?)^\\s*// KEEP " + sectionName
                + " END.*?\n", flags);
    }

    public void setTableNameCodeStyle(CodeStyle tableNameCodeStyle) {
        this.tableNameCodeStyle = tableNameCodeStyle;
    }

    public void setMemberNameCodeStyle(CodeStyle memberNameCodeStyle) {
        this.memberNameCodeStyle = memberNameCodeStyle;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    public void setApiPackage(String apiPackage) {
        this.apiPackage = apiPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public void setTargetDB(String targetDB) {
        this.targetDB = targetDB;
    }
}
