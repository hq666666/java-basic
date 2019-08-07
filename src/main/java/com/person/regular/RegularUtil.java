package com.person.regular;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *正则工具类：
 *      01：封装 Pattern类的正则匹配方法；
 *
 *      02：封装并使用 Pattern类split方法；
 *
 *      03：通过系统流自由的书写正则表达式和与之匹配的数值；
 */
public class RegularUtil {

    //private static  boolean flag = true;
    private static void matches() {
        boolean b = Pattern.matches("^.*?\\d+.*?$", "iia88p");
        System.out.println(b);
    }

    public static void split() {
        String input = "aab:op:p";
        Pattern p = Pattern.compile(":");
        String[] splits = p.split(input);
        for (String str : splits)
            System.out.println(str);
    }

    public static void main(String[] args) throws IOException {
       //正则匹配
        matches();
        //按字符串进行分割
        split();
        //通过控制台输入进行正则匹配
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));

        while (true) {
            System.out.print("Enter your regex: ");
            Pattern pattern = Pattern.compile(br.readLine());
            System.out.print("Enter input string to search: ");
            Matcher matcher = pattern.matcher(br.readLine());
            boolean found = false;
            if (matcher.find()) {

                for (int i = 0; i <= matcher.groupCount(); i++) {
                        System.out.println("group(" + i + ")=" + matcher.group(i));
                    }
            }else{
                System.out.println("No match found.");
            }
            //退出
            System.exit(0);
        }

    }
}
