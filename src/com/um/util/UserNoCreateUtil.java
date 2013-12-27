package com.um.util;
/*
 * 生成员工工号
 * 
 * */
public class UserNoCreateUtil {

	private static UserNoCreateUtil instance = null;
	
	private UserNoCreateUtil(){}
	
	//公司名称
	public static final String COMPANY_NAME = "YJT";
	
	//公司部门编号 
	public static final String DEPARTMENT_NO = "00";
	
	public synchronized String createUserno(String userno) {
		String _userno = userno.substring(5);
		return COMPANY_NAME + DEPARTMENT_NO + (Integer.parseInt(_userno) + 1);
	}
	
	public static UserNoCreateUtil getInstance() {
		if (instance == null) {
            synchronized (UserNoCreateUtil.class) {
                instance = new UserNoCreateUtil();
            }
        }
        return instance;
	}
}
