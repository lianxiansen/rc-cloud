package com.rc.cloud.app.product.infrastructure.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/11
 * @Description:
 */
public class StringUtil {

    public static boolean IsDigitOrLetter(String s) {
        int len=0;
        for (int i = 0; i < s.length(); i++){
            if( 'a'< s.charAt(i) && s.charAt(i) < 'z' ||
                    'A'< s.charAt(i) && s.charAt(i) < 'Z'||
                    Character.isDigit(s.charAt(i))
            ){
                len++;
            }
        }
        if (s.length()==len){
            return  true;
        }else{
            return false;
        }
    }

    public static boolean IsDigitOrJian(String s)
    {
        int len=0;
        for (int i = 0; i < s.length(); i++){
            if( '-'==s.charAt(i) ||
                    Character.isDigit(s.charAt(i))
            ) {
                len++;
            }
        }
        if (s.length() == len) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换正整数
     *
     * @param obj
     * @return
     */
    public static int toPositive(Object obj) {
        try {
            String strc = obj.toString();
            for (int i = strc.length(); --i >= 0; ) {
                if (!Character.isDigit(strc.charAt(i))) {
                    throw new RuntimeException("请输入正确的int值");
                }
            }
            return Convert.toInt(strc);
        } catch (Exception e) {
            throw new RuntimeException("请输入正确的int值");
        }
    }
    public static String trim(String str, char beTrim) {
        int st = 0;
        int len = str.length();
        char[] val = str.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[st] <= sbeTrim)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= sbeTrim)) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }

    public  static String filterEmoji(String nick_name) {
        //nick_name 所获取的用户昵称
        if (nick_name == null) {
            return nick_name;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(nick_name);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            nick_name = emojiMatcher.replaceAll("*");
            return nick_name;
        }
        return nick_name;
    }


    public static String addressFilter(String address) {
        address = address.replace("'", "").replace("\"", "");
        address = filterEmoji(address);
        return address;
    }

    public static String filterHtml(String str) {
        str = StrUtil.replace(str, "\\r\\n", "");
        str = StrUtil.replace(str, "\\n", "");
        str = StrUtil.replace(str, "\\t", "");
        str = StrUtil.replace(str, " ", "");
        str = StrUtil.replace(str, "\\r", "");
        return str;
    }
}
