package com.sizatn.springdemo.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.google.common.collect.Lists;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
	public static byte[] getBytes(String str) {
		if (str == null) {
			return null;
		}
		try {
			return str.getBytes(CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
	public static String toString(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}
    
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str == null){
    		return false;
    	}
    	for (String s : strs){
    		if (str.equals(trim(s))){
    			return true;
    		}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(EncodesUtils.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		// 是不是HTML代码
		boolean isCode = false;
		// 是不是HTML特殊字符,如&nbsp;
		boolean isHTML = false;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result.replaceAll(
				"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
				"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}
	
	/**
	 * 获得i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = (LocaleResolver) ContextUtils.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return ContextUtils.getApplicationContext().getMessage(code, args, localLocale);
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}
    
    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)) {
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}
	
	public static String[] splitByTokenizer(String s, String spliter) {
		StringTokenizer strToken = new StringTokenizer(s, spliter, false);
		StringTokenizer childToken = null;
		int tokenNum = strToken.countTokens();
		String sa[] = new String[tokenNum];
		int i = 0;
		while (strToken.hasMoreTokens()) {
			childToken = new StringTokenizer(strToken.nextToken());
			try {
				sa[i] = childToken.nextToken();
			} catch (NoSuchElementException ex) {
				sa[i] = "";
			}
			i++;
		}
		return sa;
	}
	
	/**
	 * 分隔字符串，包含空内容
	 */
	public static String[] split(String s, String spliter) {
		List v = new ArrayList();
		String temp = s;
		if (s == null || s.trim().equals("")) {
			return new String[] {};
		}
		int index, len = spliter.length();
		while ((index = temp.indexOf(spliter)) != -1) {
			String val = temp.substring(0, index);
			v.add(val);
			temp = temp.substring(index + len);
		}
		v.add(temp);
		String[] rs = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			rs[i] = (String) v.get(i);
		}
		return rs;
	}
	
	/**
	 * 分隔字符串，不包括空（'',null,'null')字符
	 * @param s
	 * @param spliter
	 * @return
	 */
	public static String[] splitExcludeEmpty(String s, String spliter) {
		ArrayList v = new ArrayList();
		String temp = s;
		if (s == null || s.trim().equals("")) {
			return new String[] {};
		}
		int index, len = spliter.length();
		while ((index = temp.indexOf(spliter)) != -1) {
			String val = temp.substring(0, index);
			if (StringUtils.isNotEmpty(val)) {
				v.add(val);
			}
			temp = temp.substring(index + len);
		}
		if (StringUtils.isNotEmpty(temp)) {
			v.add(temp);
		}
		String[] rs = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			rs[i] = (String) v.get(i);
		}
		return rs;
	}

	public static String replace(String s, String s1, String s2) {
		if (s2 == null || s2.equals("")) {
			return "";
		}
		StringBuffer stringbuffer = new StringBuffer();
		int i = s2.indexOf(s);
		if (i == -1) {
			return s2;
		}
		stringbuffer.append(s2.substring(0, i) + s1);
		if (i + s.length() < s2.length()) {
			stringbuffer.append(replace(s, s1, s2.substring(i + s.length(), s2.length())));
		}
		return stringbuffer.toString();
	}
	
	public static String replace2(String s, String tobeReplaced, String replacer) {
		String right = "";
		StringBuffer sr = new StringBuffer("");
		int index = -1;
		String sb = tobeReplaced, st = replacer;

		if (s == "" || sb == "") {
			return s;
		}
		right = s;

		while ((index = right.indexOf(sb)) != -1) {
			sr.append(right.substring(0, index)).append(st);
			right = right.substring(index + sb.length());
		}
		return sr.append(right).toString();
	}
	
	public static String getParameterString(Map paramMap) {
		StringBuffer paramStr = new StringBuffer();
		Iterator keyIterator = paramMap.keySet().iterator();
		boolean first = true;
		while (keyIterator.hasNext()) {
			String key = keyIterator.next().toString();
			String[] values = (String[]) paramMap.get(key);
			int len = values.length;
			for (int i = 0; i < len; i++) {
				if (StringUtils.isEmpty(values[i])) {
					continue;
				}
				if (first) {
					paramStr.append("?").append(key).append("=").append(values[i]);
					first = false;
				} else {
					paramStr.append("&").append(key).append("=").append(values[i]);
				}
			}
		}
		String params = paramStr.toString();
		return params;
	}
	
	public static String getPropertyString(String tmpStr) {
		if (StringUtils.isEmpty(tmpStr)) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		String[] arr = tmpStr.toLowerCase().split("[_]");
		int len = arr.length;
		result.append(arr[0].toLowerCase());
		for (int i = 1; i < len; i++) {
			result.append(String.valueOf(arr[i].charAt(0)).toUpperCase()).append(arr[i].substring(1));
		}
		return result.toString();
	}
	
	public static String getPropertyDescString(String tmpStr) {
		if (StringUtils.isEmpty(tmpStr)) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		String[] arr = tmpStr.toLowerCase().split("[_]");
		int len = arr.length;
		// result.append(arr[0].toLowerCase());
		for (int i = 0; i < len; i++) {
			if ("ID".equalsIgnoreCase(arr[i])) {
				result.append("ID");
			} else {
				result.append(String.valueOf(arr[i].charAt(0)).toUpperCase()).append(arr[i].substring(1));
			}
			if (i != len - 1) {
				result.append(" ");
			}
		}
		return result.toString();
	}
	

	public static String fillTail(String s, String f, int i) {
		int len = s.length();
		if (len >= i) {
			return s;
		} else {
			int sub = i - len;
			for (int j = 0; j < sub; j++) {
				s = s + f;
			}
			return s;
		}
	}

	public static String cutTail(String s, int i) {
		if (s == null) {
			return "";
		}
		if (s.length() > i) {
			return s.substring(0, s.length() - i);
		} else {
			return "";
		}
	}
    
}
