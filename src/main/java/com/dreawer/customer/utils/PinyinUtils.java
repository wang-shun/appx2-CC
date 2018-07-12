package com.dreawer.customer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import org.apache.commons.lang.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

	/** 
     * 将指定‘字符串’里的所有‘汉字’转换为‘汉语拼音’。 <br/>
     * <strong>注意：</strong>字符串中的空格将被替换为‘-’，而特殊符号（如标点符号及'-'）默认会被忽略。
     * <pre>
     * StringUtils.spell("Java Language");
     * // 返回 Java-Language
     * 
     * StringUtils.spell("我们就是D族人，我们要实现自己的梦想。");
     * // 返回 wo-men-jiu-shi-D-zu-ren-wo-men-yao-shi-xian-zi-ji-de-meng-xiang
     * 
     * StringUtils.spell("召唤TC130-X控制器");
     * // 返回 zhao-huan-TC-130-X-kong-zhi-qi
     * 
     * </pre>
     * @param str 待转换的字符串。
     * @return 转换后的字符串。
     * @author David Dai, Sdanly
     * @since 1.0
     */
	public static String spell(String str) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isBlank(str)) {
            return sb.toString();
        }
        
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写拼音字母  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不加语调标识  
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // u:的声母替换为v  

        String hanZiStr = StringUtils.trim(str);
        int strLength = hanZiStr.length();
        try {
            char beforCh = ' ';
            for (int i = 0; i < strLength; i++) {
                char ch = hanZiStr.charAt(i);
                if (isChinese(ch)) { //当前字符为汉字时处理方式
                    if (i > 0) {
                        sb.append("-");
                    }
                    String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    if (arr != null && arr.length > 0) sb.append(arr[0]);
                } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') { // 当前字符为字母或空格时处理方式
                    if (ch == ' ') {
                        sb.append("-");
                    } else {
                        if (!(beforCh >= 'a' && beforCh <= 'z') && !(beforCh >= 'A' && beforCh <= 'Z') && beforCh != ' ') { // 汉字,数字与字母分开处理
                            sb.append("-");
                        }
                        sb.append(ch);
                    }
                } else if (Character.isDigit(ch)) { // 当前字符为数字时处理方式
                    if (!Character.isDigit(beforCh) && beforCh != ' ') { // 数字与字母分开处理，上一个字符是空格已改为'-',不再处理。
                        sb.append("-");
                    }
                    sb.append(ch);
                }
                beforCh = ch;
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
	
	/**
     * 验证指定的“字符”是否为“中文”。
     * <pre>
     * MatchUtils.isChinese('你'); // 返回 true
     * MatchUtils.isChinese('我'); // 返回 true
     * MatchUtils.isChinese('他'); // 返回 true
     * 
     * MatchUtils.isChinese('x'); // 返回 false
     * MatchUtils.isChinese('y'); // 返回 false
     * MatchUtils.isChinese('z'); // 返回 false
     * </pre>
     * @param ch 待验证的字符。
     * @return 是则返回 true，否则返回 false。
     * @author David Dai
     * @since 1.0
     */
    public static boolean isChinese(Character ch) {
        if (ch != null) {
            String regex = "[\\u4e00-\\u9fa5]";
            return validate(ch.toString(), regex);
        }
        return false;
    }
    
    /**
     * 根据用户指定的正则表达式对字符串进行有效验证。
     * <pre>
     * MatchUtils.validate("0123456789", "^[a-zA-Z0-9_]*$"); // 返回 true
     * MatchUtils.validate("abcdefg", "^[a-zA-Z_][a-zA-Z0-9_]*$"); // 返回 true
     * MatchUtils.validate("test123@sina.cn", "^[a-zA-Z_][a-zA-Z0-9_]*@[a-zA-Z0-9_-]{1,}\\.[a-zA-Z]{1,}[a-zA-Z]$"); // 返回 true
     * </pre>
     * @param str 待验证的字符串信息。
     * @param regex 正则表达式。
     * @return 如果合法则返回 true；否则返回 false。
     * @author David Dai
     * @since 1.0
     */
    public static boolean validate(String str, String regex) {
        if (isNotBlank(str) && isNotBlank(regex)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 根据用户指定的正则表达式以及匹配模式对字符串进行有效验证。
     * <pre>
     * MatchUtils.validate("abcdefg", "^[A-Z]*$", {@link Pattern.CASE_INSENSITIVE}); // 返回 true
     * MatchUtils.validate("ABCDEFG", "^[a-z]*$", {@link Pattern.CASE_INSENSITIVE}); // 返回 true
     * 
     * MatchUtils.validate("abcdefg", "^[A-Z]*$", {@link Pattern.UNICODE_CASE}); // 返回 false
     * MatchUtils.validate("ABCDEFG", "^[a-z]*$", {@link Pattern.UNICODE_CASE}); // 返回 false
     * </pre>
     * @param str 目标字符串。
     * @param regex 正则表达式。
     * @param model 匹配模式。
     * @return 如果合法则返回 true；否则返回 false。
     * @author David Dai
     * @since 1.0
     */
    public static boolean validate(String str, String regex, int model) {
        if (isNotBlank(str) && isNotBlank(regex)) {
            Pattern pattern = Pattern.compile(regex, model);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
