package com.um.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Title: PMSW
 * </p>
 * <p>
 * Description: PMS Web Edition
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Talent information Solutions Ltd.
 * </p>
 * 
 * @author bobdog chan
 * @version 1.0
 */

public class FormatUtil {
	public FormatUtil() {
	}

	// Switch String to Date
	public static Date String2Date(String pattern, String date) {

		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);

			return df.parse(date, new ParsePosition(0));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	// Switch Date to String
	public static String Date2String(String pattern, Date date) {
		try {

			SimpleDateFormat df = new SimpleDateFormat(pattern);

			return df.format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String numberFormat(double num) {
		return numberFormat("###,###", '.', ',', num);
	}

	public static String amountFormat(double amount) {
		return numberFormat("###,##0.00", '.', ',', amount);
	}

	public static String amountFormatII(double amount) {
		return numberFormat("##0.00", '.', ',', amount);
	}
	public static String percentFormat(double amount) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		return nf.format(amount);
	}

	/**
	 * 
	 * @param format
	 * @param decimalSep
	 * @param groupSep
	 * @param num
	 * @return
	 */
	public static String numberFormat(String format, char decimalSep,
			char groupSep, double num) {
		try {
			DecimalFormat df = new DecimalFormat(format);
			DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
			dfs.setDecimalSeparator(decimalSep);
			dfs.setGroupingSeparator(groupSep);
			df.setDecimalFormatSymbols(dfs);
			return df.format(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String round(double v, int scale) {
		String temp = "#,##0.";
		for (int i = 0; i < scale; i++) {
			temp += "0";
		}
		return new java.text.DecimalFormat(temp).format(v);
	}

	public static String getNowTime(String pattern) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		return sdf.format(cal.getTime());
	} // End of getNowTime()

	/**
	 * 把元转为分，如9.5元->950分
	 * 
	 * @param money
	 * @return
	 */
	public static String yuanToFen(String money) {
		String result = null;
		try {
			if (money == null || money.equals(""))
				result = "";
			else {
				int index = money.indexOf(".");
				if (index > 0) {
					String mantissa = money.substring(index + 1);
					if (mantissa.length() > 2) {
						String tmp = mantissa.substring(0, 2);
						String byte3 = mantissa.substring(2, 3);
						if (Integer.parseInt(byte3) > 5) {
							tmp = new Integer(Integer.parseInt(tmp) + 1)
									.toString();
						}
						result = money.substring(0, index) + tmp;
					} else if(mantissa.length() ==2){
						result = money.substring(0,index) + money.substring(index + 1);
					} else{
						result = money.substring(0, index)
								+ money.substring(index + 1) + "0";
					}
				} else {
					result = money + "00";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String fenToYuan(String money){
		String result = "";
		try{
			if(money.indexOf(".") >= 0)
				result = money;
			else if(money != null && !money.equals("")){
				if(money.length() > 2)
					result = money.substring(0,money.length() - 2) + "." + money.substring(money.length() - 2);
				else if(money.length() == 2 )
					result = "0." + money;
				else if(money.length() == 1 )
					result = "0.0" + money;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>
	 * Title: money
	 * </p>
	 * <p>
	 * Description: *该类是把阿拉伯数字转换成中文大写的类。根据王大庆兄的C++程序稍做修改而成，后面附有王大庆兄的C++
	 * *程序，如果对所附的C++程序有什么问题请与王大庆兄联系email: wang_daqing@21cn.com
	 * *如果对java部分有什么看法和建议请与小弟联系，杨璇 email：netfalcon@263.net *类名：money *函数：String
	 * PositiveIntegerToHanStr(String NumStr) 负责把小数点前面的数转换为大写中文 *函数:String
	 * NumToRMBStr(double val) 负责把输入的double型的数转换为大写中文
	 * *注意java程序转换的范围是：小数点前面15位（已测试通过），C＋＋程序是24位（我没有测试）
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2003 你可以对本程序随意修改，复制，使用，但请保留这里注释声明！！！
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author 王大庆、杨璇
	 * @version 1.0
	 */

	private final static String HanDigiStr[] = new String[] { "零", "壹", "贰",
			"叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private final static String HanDiviStr[] = new String[] { "", "拾", "佰",
			"仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟",
			"亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

	public static String PositiveIntegerToHanStr(String NumStr) { // 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "数值过大!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "输入含非数字字符!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
					RMBStr += HanDiviStr[i]; // “亿”或“万”
			}
			if (i % 8 == 0)
				hasvalue = false; // 万进位前有值标记逢亿复位
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		
		return RMBStr;
	}

	public static String NumToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999)
			return "数值位数过大!";
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "角";
			if (integer == 0 && jiao == 0) // 零元后不写零几分
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "分";
		}

		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;

		// return "￥" + SignStr +
		// PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;

		String RMBStr = SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元"
				+ TailStr;
		
		if (RMBStr.startsWith("拾")) {
			RMBStr = "壹" + RMBStr;
		}
		
		return RMBStr;
	}

}