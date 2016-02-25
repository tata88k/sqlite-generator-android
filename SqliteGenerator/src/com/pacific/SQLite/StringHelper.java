package com.pacific.SQLite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhanyi on 2016/1/5.
 */
public class StringHelper {

    public static String camel2Pascal(String source) {
        return source.replaceFirst(source.substring(0, 1), source.substring(0, 1).toUpperCase());
    }

    public static String score2Pascal(String source) {
        Pattern pattern = Pattern.compile("_[a-z]");
        Matcher matcher = pattern.matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String firstChar = matcher.group().substring(1, 2);
            matcher.appendReplacement(stringBuffer, firstChar.toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.replace(0, 1, stringBuffer.substring(0, 1).toUpperCase()).toString();
    }


    public static String pascal2Camle(String source) {
        return source.replaceFirst(source.substring(0, 1), source.substring(0, 1).toLowerCase());
    }

    public static String score2Camle(String source) {
        Pattern pattern = Pattern.compile("_[a-z]");
        Matcher matcher = pattern.matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String firstChar = matcher.group().substring(1, 2);
            matcher.appendReplacement(stringBuffer, firstChar.toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String camel2score(String source) {
        return pascal2score(source);
    }

    public static String pascal2score(String source) {
        String regexStr = "[A-Z]";
        Matcher matcher = Pattern.compile(regexStr).matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(stringBuffer, "_" + g.toLowerCase());
        }
        matcher.appendTail(stringBuffer);
        if (stringBuffer.charAt(0) == '_') {
            stringBuffer.delete(0, 1);
        }
        return stringBuffer.toString();
    }

    public static String toIFExists(String source) {
        if(source.contains("CREATE VIEW")){
            return source.replaceFirst("CREATE VIEW", "CREATE VIEW IF NOT EXISTS");
        }
        return source.replaceFirst("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
    }
}
