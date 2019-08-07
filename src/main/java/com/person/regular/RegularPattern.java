package com.person.regular;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hq
 *正则表达式：
 *      01：正则表达式的创建;
 *      02：正则表达式的匹配；
 */
public class RegularPattern {
    //正则表达式：匹配xing前面的字符串
    private static final String S_1 = "fan.*?(?=xing)";
    //正则表达式：匹配后面不是xing的字符串
    private static final String S_2 = "fan.*?hong(?!xing)";
    //正则表达式：匹配字符串前面是fan后面的字符串
    private static final String S_3 = "(?<=fan).*?$";
    //正则表达式：匹配前面不是fan的字符串
    private static final String S_4 = "(?<!fan)hong.*?$";

    //获取对应的正则表达式编译过后的模式
    private static final Pattern P_1 = Pattern.compile(S_1, Pattern.DOTALL);
    private static final Pattern P_2 = Pattern.compile(S_2, Pattern.DOTALL);
    private static final Pattern P_3 = Pattern.compile(S_3, Pattern.DOTALL);
    private static final Pattern P_4 = Pattern.compile(S_4, Pattern.DOTALL);

    public static void main(String[] args) {

        String str = "ii_fanhongxing";
        //获取对应的匹配器
        Matcher matcher = P_1.matcher(str);
        /**
         *@function： matcher.find():
         *          表示是否能寻找到该匹配器的模式
         * @function：  matcher.group()：
         *                     表示返回与该匹配模式相匹配输入序列中的子序列；
         *
         */
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
        matcher = P_2.matcher(str);
        if (!matcher.find())
            System.out.println("No Matched!");
        matcher = P_3.matcher(str);
        if (matcher.find())
            System.out.println(matcher.group());
        matcher = P_4.matcher(str);
        if (!matcher.find())
            System.out.println("No Matched!");
    }
}
