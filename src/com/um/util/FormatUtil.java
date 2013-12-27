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
	 * ��ԪתΪ�֣���9.5Ԫ->950��
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
	 * Description: *�����ǰѰ���������ת�������Ĵ�д���ࡣ�����������ֵ�C++���������޸Ķ��ɣ����渽���������ֵ�C++
	 * *���������������C++������ʲô������������������ϵemail: wang_daqing@21cn.com
	 * *�����java������ʲô�����ͽ�������С����ϵ����� email��netfalcon@263.net *������money *������String
	 * PositiveIntegerToHanStr(String NumStr) �����С����ǰ�����ת��Ϊ��д���� *����:�GString
	 * NumToRMBStr(double val) ����������double�͵���ת��Ϊ��д����
	 * *ע��java����ת���ķ�Χ�ǣ�С����ǰ��15λ���Ѳ���ͨ������C����������24λ����û�в��ԣ�
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2003 ����ԶԱ����������޸ģ����ƣ�ʹ�ã����뱣������ע������������
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author �����졢���
	 * @version 1.0
	 */

	private final static String HanDigiStr[] = new String[] { "��", "Ҽ", "��",
			"��", "��", "��", "½", "��", "��", "��" };

	private final static String HanDiviStr[] = new String[] { "", "ʰ", "��",
			"Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ",
			"��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ" };

	public static String PositiveIntegerToHanStr(String NumStr) { // �����ַ���������������ֻ����ǰ���ո�(�����Ҷ���)��������ǰ����
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // �ڡ����λǰ����ֵ���
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "��ֵ����!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "���뺬�������ַ�!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // ���������������ֵ��ֻ��ʾһ����
				// ��������ǰ���㲻��������
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// ��ʮ��λǰ����Ҳ����Ҽ���ô���
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // ʮ��λ���ڵ�һλ����Ҽ��
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // ����ֵ��ӽ�λ����λΪ��
				hasvalue = true; // �����λǰ��ֵ���

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // ����֮������з���ֵ����ʾ��
					RMBStr += HanDiviStr[i]; // ���ڡ�����
			}
			if (i % 8 == 0)
				hasvalue = false; // ���λǰ��ֵ��Ƿ��ڸ�λ
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // ������ַ���"0"������"��"
		
		return RMBStr;
	}

	public static String NumToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "��";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999)
			return "��ֵλ������!";
		// �������뵽��
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "��";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "��";
			if (integer == 0 && jiao == 0) // ��Ԫ��д�㼸��
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "��";
		}

		// ��һ�п����ڷ�������ڳ��ϣ�0.03ֻ��ʾ�����֡������ǡ���Ԫ���֡�
		// if( !integer ) return SignStr+TailStr;

		// return "��" + SignStr +
		// PositiveIntegerToHanStr(String.valueOf(integer)) + "Ԫ" + TailStr;

		String RMBStr = SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "Ԫ"
				+ TailStr;
		
		if (RMBStr.startsWith("ʰ")) {
			RMBStr = "Ҽ" + RMBStr;
		}
		
		return RMBStr;
	}

}