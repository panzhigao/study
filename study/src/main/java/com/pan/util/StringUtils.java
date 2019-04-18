package com.pan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author panzhigao
 */
public class StringUtils {

	public static final char UNDERLINE = '_';

	/**
	 * 驼峰转为下划线连接
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static final Pattern LINE_PATTERN= Pattern.compile("_");

	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = LINE_PATTERN.matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 清除路径中的转义字符
	 */
	public static String clearXss(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		return value;
	}

	private static final Pattern scriptPattern1=Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);

	private static final Pattern scriptPattern2=Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

	private static final Pattern scriptPattern3=Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	private static final Pattern scriptPattern4=Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

	private static final Pattern scriptPattern5=Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);

	private static final Pattern scriptPattern6=Pattern.compile("</iframe>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE|Pattern.DOTALL);

	private static final Pattern scriptPattern7=Pattern.compile("onload(.*?)=",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);

	private static final Pattern scriptPattern8=Pattern.compile("onerror(.*?)=",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);

	private static final Pattern scriptPattern9=Pattern.compile("confirm(.*?)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);

	private static final Pattern scriptPattern10= Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	private static final Pattern scriptPattern11= Pattern.compile("alert(.*?)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);

	public static String stripXSS(String value) {
		if (value != null) {
			value = scriptPattern1.matcher(value).replaceAll("");
			value = scriptPattern2.matcher(value).replaceAll("");
			value = scriptPattern3.matcher(value).replaceAll("");
			value = scriptPattern4.matcher(value).replaceAll("");
			value = scriptPattern5.matcher(value).replaceAll("");
			value = scriptPattern6.matcher(value).replaceAll("");
			value = scriptPattern7.matcher(value).replaceAll("");
			value = scriptPattern8.matcher(value).replaceAll("");
			value = scriptPattern9.matcher(value).replaceAll("");
			value = scriptPattern10.matcher(value).replaceAll("");
			value = scriptPattern11.matcher(value).replaceAll("");
		}
		return clearXss(value);
	}

	public static void main(String[] args) {
		System.out.println(camelToUnderline("yT"));
		System.out.println(stripXSS("<javascript src='http'></script>"));
	}
}
