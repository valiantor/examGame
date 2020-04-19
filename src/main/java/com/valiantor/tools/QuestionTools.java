package com.valiantor.tools;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionTools {

    public static void main(String[] args) {
       String s = "(9) wqwrdqfqwf#";

        Pattern pattern = Pattern.compile("\\(?[0-9]*\\)?.");
        Pattern pattern2 = Pattern.compile("\\（?[0-9]*\\）?.");
        Pattern pattern3 = Pattern.compile("[0-9]*\\?");
        Matcher isNum = pattern.matcher(s);
        Matcher isNum2 = pattern2.matcher(s);
        Matcher isNum3 = pattern3.matcher(s);
    System.out.println(isNum.matches()+" "+isNum2.matches()+" "+isNum3.matches());

    }
    public static boolean checkQuestionDescription(String s) {

        Pattern pattern = Pattern.compile("\\(?[0-9]*\\)?.");
        Pattern pattern2 = Pattern.compile("\\（?[0-9]*\\）?.");
        Matcher isNum = pattern.matcher(s);
        Matcher isNum2 = pattern2.matcher(s);
    // System.out.println(isNum.matches()+" "+isNum2.matches());
        return isNum.matches() || isNum2.matches();
    }
    public static String getChoiceA(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"A.","a.","A、","a、","A ","a ","A:","a:"};
        for(String s:arr){
            if(str.startsWith(s)){
               String result = str.substring(s.length());
               if(result != null){
                   result = result.trim();
               }
               return result;
            }
        }
        return null;
    }
    public static String getChoiceB(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"B.","b.","B、","b、","B ","b ","B:","b:"};
        for(String s:arr){
            if(str.startsWith(s)){
               String result = str.substring(s.length());
               if(result != null){
                   result = result.trim();
               }
               return result;
            }
        }
        return null;
    }
    public static String getChoiceC(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"C.","c.","C、","c、","C ","c ","C:","c:"};
        for(String s:arr){
            if(str.startsWith(s)){
                String result = str.substring(s.length());
                if(result != null){
                    result = result.trim();
                }
                return result;
            }
        }
        return null;
    }
    public static String getChoiceD(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"D.","d.","D、","d、","D ","d ","D:","d:"};
        for(String s:arr){
            if(str.startsWith(s)){
                String result = str.substring(s.length());
                if(result != null){
                    result = result.trim();
                }
                return result;
            }
        }
        return null;
    }
    public static String getChoiceE(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"E.","e.","E、","e、","E ","e ","E:","e:"};
        for(String s:arr){
            if(str.startsWith(s)){
                String result = str.substring(s.length());
                if(result != null){
                    result = result.trim();
                }
                return result;
            }
        }
        return null;
    }

    public static String getCorrectAnswer(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] arr = {"答案.","答案、","答案 ","答案:","答案"};
        for(String s:arr){
            if(str.startsWith(s)){
                String result = str.substring(s.length());
                if(result != null){
                    result = result.trim();
                }
                return result;
            }
        }
        return null;
    }
}
