package com.um.util;

import java.util.Date;
import java.util.Hashtable;

/*
 * 事务传播
 * */
public class TransactionNestUtil {
	
	@SuppressWarnings("rawtypes")
	private final static ThreadLocal threadReference = new ThreadLocal();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized static String reference(){
		Hashtable refTb = (Hashtable) threadReference.get();
		if(refTb == null){
			refTb = new Hashtable();
		}
		
		String refString = new Long(new Date().getTime()).toString() + Math.random();
		refTb.put(refString, "");
		threadReference.set(refTb);
		
		return refString;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized static void releaseRef(String refString){
		Hashtable refTb = (Hashtable) threadReference.get();
		if(refTb != null){
			if(refTb.containsKey(refString)){
				refTb.remove(refString);
				threadReference.set(refTb);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public synchronized static boolean isReference(){
		Hashtable refTb = (Hashtable) threadReference.get();
		if(refTb == null || refTb.size() <= 0){
			return false;
		}else{
			return true;
		}
	}

}
