package com.um.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public final class StringUtil {

	private StringUtil() { // prevent instantiation
	}

	public static String arrayToString(String[] inputArray, String delim) {
		String outputString = "";
		if (inputArray == null || inputArray.length == 0) {
			return outputString;
		}
		outputString = inputArray[0].trim();
		for (int i = 1; i < inputArray.length; i++) {
			outputString = outputString + delim + inputArray[i].trim();
		}
		return outputString;
	}
	
	public static String collectionToString(Collection<String> collection, String delim) {
		StringBuffer outputString = new StringBuffer();
		if (collection == null || collection.size() == 0 || collection.isEmpty()) {
			return outputString.toString();
		}
		Iterator<String> i = collection.iterator();
		while(i.hasNext()) {
			outputString.append(i.next() + delim);
		}
		return outputString.substring(0, outputString.lastIndexOf(delim));
	}

	/**
	 * This method trim the input variable, so if it contains only spaces, then
	 * it will be empty string, then we have 0 token :-) The returned value is
	 * never null. If the input String is null, an empty String array will be
	 * returned All tokens are trimed before returning
	 */
	public static String[] getStringArray(String inputValue, String delim) {
		if (inputValue == null)
			inputValue = "";
		inputValue = inputValue.trim(); // very important
		java.util.StringTokenizer t = new java.util.StringTokenizer(inputValue,
				delim);
		String[] ret = new String[t.countTokens()];
		int index = 0;
		while (t.hasMoreTokens()) {
			String token = t.nextToken().trim();
			// check for valid value here if needed
			ret[index] = token;
			index++;
		}
		return ret;
	}

	public static String getEmptyStringIfNull(String str) {
		if (str == null)
			return "";
		return str;
	}

	public static String replace(String input, char from, String to) {
		if (input == null) {
			return null;
		}

		char[] s = input.toCharArray();
		int length = s.length;
		StringBuffer ret = new StringBuffer(length * 2);

		for (int i = 0; i < length; i++) {
			if (s[i] == from) {
				ret.append(to);
			} else {
				ret.append(s[i]);
			}
		} // for
		return ret.toString();
	}
	
	public static void  copyFile(File in, File out) throws Exception  {
	    FileInputStream fis  = new FileInputStream(in);
	    FileOutputStream fos = new FileOutputStream(out);
	    byte[] buf = new byte[1024];
	    int i = 0;
	    while((i=fis.read(buf))!=-1) {
	      fos.write(buf, 0, i);
	      }
	    fis.close();
	    fos.close();
	    //return 0;
	    }
	
	public static String replace(String input, String from, String to) {
		if (input == null) {
			return "";
		}
		Pattern p = Pattern.compile(from);
		Matcher m = p.matcher(input);
		String output = m.replaceAll(to);

		return output;

	}

	/**
	 * This method can be replaced by getStringArray
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection getSeparateString(String strContent, String pattern) {
		int beginIndex = 0;
		Collection coResult = new ArrayList();
		String result;
		int position = strContent.indexOf(pattern, beginIndex); // Get the first
		// position
		while (position != -1) {
			result = strContent.substring(beginIndex, position);
			if (!result.trim().equals("")) {
				coResult.add(result);
			}
			beginIndex = position + pattern.length(); // Cong 1 la chieu dai
			// cua ky tu ;
			position = strContent.indexOf(pattern, beginIndex);
		}

		return coResult;
	}

	/*
	 * for test only public static void main(String[] args) { //String[] s =
	 * getStringArray(" fasg;, zdgsag, ,,", ","); String[] s = getStringArray("
	 * fasg ", ","); System.out.println("length = " + s.length); for (int i = 0;
	 * i < s.length; i++) { System.out.println("" + i + " : " + s[i]); } }
	 */

	/**
	 * ��iso�����strת����gbk����
	 * 
	 * @param str
	 * @return str
	 */
	public static String isoToGbk(String str) {
		String result = null;
		try {
			result = new String(str.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��gbk�����strת����iso����
	 * 
	 * @param str
	 * @return str
	 */
	public static String gbkToIso(String str) {
		String result = null;
		try {
			result = new String(str.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��gbk�����strת����utf-8����
	 * 
	 * @param str
	 * @return str
	 */
	public static String gbkToUtf(String str) {
		String result = null;
		try {
			result = new String(str.getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��utf-8�����strת����gbk����
	 * 
	 * @param str
	 * @return str
	 */
	public static String utfToGbk(String str) {
		String result = null;
		try {
			result = new String(str.getBytes("UTF-8"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �ַ������
	 * 
	 * @param fillChar
	 * @param strLength
	 * @param sourceStr
	 * @return
	 */
	public static String leftFill(String fillChar, int strLength,
			String sourceStr) {
		int length = 0;
		try {
			if (null != sourceStr) {
				length = sourceStr.getBytes().length;
			}

			for (int i = 0; i < strLength - length; i++) {
				if (sourceStr == null)
					sourceStr = fillChar;
				else
					sourceStr = fillChar + sourceStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceStr;
	}

	/**
	 * �ַ������
	 * 
	 * @param fillChar
	 * @param strLength
	 * @param sourceStr
	 * @return
	 */
	public static String rightFill(String fillChar, int strLength,
			String sourceStr) {
		int length = 0;
		try {
			if (null != sourceStr) {
				length = sourceStr.getBytes().length;
			}

			for (int i = 0; i < strLength - length; i++) {
				if (sourceStr == null)
					sourceStr = fillChar;
				else
					sourceStr = sourceStr + fillChar;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceStr;
	}
	
	/**
	 * ����ֵ��string��ֵλ��ǰ��ת
	 * 
	 * @param num
	 * @return
	 */
	public static String reverseNum(String num) {
		String sTemp = "";
		long l = Long.valueOf(num).longValue();
		long lTemp = 0l;
		while(sTemp.length() != num.length()) {
			lTemp = l%10;
			sTemp = sTemp + String.valueOf(lTemp).toString();
			l = l/10;
		}
		return sTemp;
	}
	
	/**
	 * �ַ�ת��
	 * 
	 * @param str
	 * @return
	 */
	public static String repaceStr(String str) {
		String newStr = "";
		if ( str.length() <=17 )
		{
			newStr = str;
		}
		
		if ( str.length() > 17 && str.length() <= 34)
		{
			newStr = str.substring(0,17) + "\n" + str.substring(17,str.length());
		}
		
		if ( str.length() > 34 && str.length() <= 51)
		{
			newStr = str.substring(0,17) + "\n" + str.substring(17,34)+ "\n" + str.substring(34,str.length());
		}
		
		if ( str.length() > 51 && str.length() <= 68)
		{
			newStr = str.substring(0,17) + "\n" + str.substring(17,34)+ "\n" + str.substring(34,51)+ "\n" + str.substring(51,str.length());
		}
			
		return newStr;
	}
	
	/**
	 * �ַ�ת��
	 * 
	 * @param str
	 * @return
	 */
	public static String repaceDesc(String str) {
		String newStr = "";
		if ( str.length() <=7 )
		{
			newStr = str;
		}
		
		if ( str.length() > 7 && str.length() <= 14)
		{
			newStr = str.substring(0,7) + "\n" + str.substring(7,str.length());
		}
		
		return newStr;
	}
	
	/**
	 * ��str��sepΪ�ָ���ת�����ַ���
	 * @param str
	 * @param sep
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection strToCollection(String str, String sep){
		Collection vector = new Vector();
		if(str != null){
			StringTokenizer strToken = new StringTokenizer(str,sep);
			while(strToken.hasMoreTokens()){
				vector.add(strToken.nextToken().trim());
			}
		}
		return vector; 
	}
	
	/**
	 * ��str��sepΪ�ָ���ת�����ַ�Hashtable
	 * @param str
	 * @param sep
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Hashtable strToHashtable(String str, String sep){
		Hashtable table = new Hashtable();
		if(str != null){
			StringTokenizer strToken = new StringTokenizer(str,sep);
			long count = 0;
			while(strToken.hasMoreTokens()){
				table.put(strToken.nextToken().trim(), new Long(count));
				count ++;
			}
		}
		return table; 
	}
	
	/**
	 * �ж��Ƿ�Acsii��GB2312
	 * @param str
	 * @return
	 */
	public static boolean isAcsiiAndGB(String str){
		boolean result = true;
		if(str != null){
			for(int i = 0; i < str.length(); i++){
				String subStr = str.substring(i,i+1);
				if(!(isAcsii(subStr)
						|| isGB2312(subStr))){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * �ж��Ƿ�Acsii��GBK
	 * @param str
	 * @return
	 */
	public static boolean isAcsiiAndGBK(String str){
		boolean result = true;
		if(str != null){
			for(int i = 0; i < str.length(); i++){
				String subStr = str.substring(i,i+1);
				if(!(isAcsii(subStr)
						|| isGBK(subStr))){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * �ж��Ƿ�X�ַ��GBK
	 * @param str
	 * @return
	 */
	public static boolean isXCharSetAndGBK(String str){
		boolean result = true;
		if(str != null){
			for(int i = 0; i < str.length(); i++){
				String subStr = str.substring(i,i+1);
				if(!(isXCharSet(subStr)
						|| isGBK(subStr))){
					result = false;
					break;
				}
			}
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * �ж��Ƿ�acsii��
	 * @param str
	 * @return
	 */
	public static boolean isAcsii(String str){
		boolean isAcsii = true;
		if(str != null){
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				byte[] bytes = ("" + chars[i]).getBytes();
				if (bytes.length == 1) {
					if (!(bytes[0] >= 0x00 && bytes[0] <= 0x7E)) {
						isAcsii = false;
						break;
					}
				}else{
					isAcsii = false;
					break;
				}
			}
		}else{
			isAcsii = false;
		}
		return isAcsii;
	}
	
	/**
	 * �ж��Ƿ�X�ַ�
	 * @param str
	 * @return
	 */
	public static boolean isXCharSet(String str){
		boolean isXCharSet = true;
		if(str != null){
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				byte[] bytes = ("" + chars[i]).getBytes();
				if (bytes.length == 1) {
					if (!(bytes[0] >= 65 && bytes[0] <= 90
								|| bytes[0] >= 95 && bytes[0] <= 127
								|| bytes[0] >= 32 && bytes[0] <= 90)
							&& bytes[0] != 37
							&& bytes[0] != 34
							&& bytes[0] != 39) {
						isXCharSet = false;
						break;
					}
				}else{
					isXCharSet = false; 
					break;
				}
			}
		}else{
			isXCharSet = false;
		}
		return isXCharSet;
	}
	
	/**
	 * �ж��Ƿ�Gb2312,���򷵻�true
	 * @param str
	 * @return
	 */
	public static boolean isGB2312(String str) {
		boolean isGB2312 = true;
		if(str != null){
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				byte[] bytes = ("" + chars[i]).getBytes();
				if (bytes.length == 2) {
					int[] ints = new int[2];
					ints[0] = bytes[0] & 0xff;
					ints[1] = bytes[1] & 0xff;
					if (!(ints[0] >= 0xA1 && ints[0] <= 0xFE 
							&& ints[1] >= 0xA1 && ints[1] <= 0xFE)) {
						isGB2312 = false;
						break;
					}
				}else{
					isGB2312 = false;
					break;
				}
			}
		}else{
			isGB2312 = false;
		}
		
		return isGB2312;
	}
	
	/**
	 * �ж��Ƿ�GBK,���򷵻�true
	 * @param str
	 * @return
	 */
	public static boolean isGBK(String str) {
		boolean isGBK = true;
		if(str != null){
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				byte[] bytes = ("" + chars[i]).getBytes();
				if (bytes.length == 2) {
					int[] ints = new int[2];
					ints[0] = bytes[0] & 0xff;
					ints[1] = bytes[1] & 0xff;
					if (!(ints[0] >= 0x81 
								&& ints[0] <= 0xFE 
								&& (ints[1] >= 0x40 
										&& ints[1] <= 0x7E 
									|| ints[1] >= 0x80 
										&& ints[1] <= 0xFE))) {
						isGBK = false;
						break;
					}
				}else{
					isGBK = false;
					break;
				}
			}
		}else{
			isGBK = false;
		}
		return isGBK;
	}
	
	/**
	 * �ж��ַ��Ƿ�Ϊnull��մ�
	 * @param value
	 * @return value��ֵʱ����false�����򷵻�true
	 * @author hqy 2013-5-14
	 */
	public static boolean isEmpty(String value){
		return null == value || value.trim().length() < 1;
	}

}