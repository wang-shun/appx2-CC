package com.dreawer.customer.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtils {
      
    /** 
     * 获取来访者的浏览器版本 
     * @param request 
     * @return 
     */  
    public static String getBrowser(HttpServletRequest request){  
        String browserVersion = "unknow";  
        String header = request.getHeader("user-agent");  
        if(header == null || header.equals("")){  
        	return browserVersion;  
        }  
        if(header.indexOf("MSIE")>0){  
            browserVersion = "IE";  
        }else if(header.indexOf("Firefox")>0){  
            browserVersion = "Firefox";  
        }else if(header.indexOf("Chrome")>0){  
            browserVersion = "Chrome";  
        }else if(header.indexOf("Safari")>0){  
            browserVersion = "Safari";  
        }else if(header.indexOf("Camino")>0){  
            browserVersion = "Camino";  
        }else if(header.indexOf("Konqueror")>0){  
            browserVersion = "Konqueror";  
        }  
        return browserVersion;  
    } 
    
    /** 
     * 命令获取mac地址 
     * @param cmd 
     * @return 
     */  
    private static String callCmd(String[] cmd) {  
    	String result = "";  
    	String line = "";  
        try {  
            Process proc = Runtime.getRuntime().exec(cmd);  
            InputStreamReader is = new InputStreamReader(proc.getInputStream());  
            BufferedReader br = new BufferedReader (is);  
            while ((line = br.readLine ()) != null) {  
            	result += line;  
            }  
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    private static String callCmd(String[] cmd,String[] another) {  
    	String result = "";  
    	String line = "";  
    	try {  
    		Runtime rt = Runtime.getRuntime();  
    		Process proc = rt.exec(cmd);  
    		proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令  
    		proc = rt.exec(another);  
    		InputStreamReader is = new InputStreamReader(proc.getInputStream());  
    		BufferedReader br = new BufferedReader (is);  
    		while ((line = br.readLine ()) != null) {  
    			result += line;  
    		}  
    	}catch(Exception e) {  
            e.printStackTrace();  
    	}  
        return result;  
    }  
  
    private static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) {  
    	String result = "";  
    	String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";  
    	Pattern pattern = Pattern.compile(regExp);  
    	Matcher matcher = pattern.matcher(sourceString);  
    	while(matcher.find()){  
    		result = matcher.group(1);  
    		if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {  
    			break; // 如果有多个IP,只匹配本IP对应的Mac.  
            }  
        }  
        return result;  
    }  
  
    private static String getMacInWindows(final String ip){  
    	String result = "";  
    	String[] cmd = {"cmd","/c","ping " + ip};  
    	String[] another = {"cmd","/c","arp -a"};  
    	String cmdResult = callCmd(cmd, another);  
    	result = filterMacAddress(ip,cmdResult,"-");  
    	return result;  
    }
    
    private static String getMacInLinux(final String ip){   
    	String result = "";   
    	String[] cmd = {"/bin/sh","-c","ping " +  ip + " -c 2 && arp -a" };   
    	String cmdResult = callCmd(cmd);   
    	result = filterMacAddress(ip, cmdResult, ":");   
        return result;   
    }   
  
    public static String getMacAddress(String ip){  
    	String macAddress = "";  
    	macAddress = getMacInWindows(ip).trim();  
    	if(macAddress==null||"".equals(macAddress)){  
    		macAddress = getMacInLinux(ip).trim();  
    	}
    	if(StringUtils.isBlank(macAddress)){
    		macAddress="unknow";
    	}
    	return macAddress;  
    } 
}
