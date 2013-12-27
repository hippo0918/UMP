package com.um.util.json;

import java.util.Collection;

import com.common.easyui.DataGridJsonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * Gson工具类
 * 
 * */
public class GsonUtil {

	private static Gson gson = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization()
    .disableHtmlEscaping() 
    .create();
	
	
	/**  
	* @Name: parseJsonArray
	* @Description: 集合转化为JsonArray
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: JsonArray
	*/
	public static JsonArray parseJsonArray(Collection<?> list) {
		String r = gson.toJson(list);
        JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(r);
		JsonArray jsonArray = null;
		if(el.isJsonArray()){
			jsonArray = el.getAsJsonArray();
		}	
		return jsonArray;
	}
	
	/**  
	* @Name: parseJsonObject
	* @Description: 实体对象转化为JsonArray
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: JsonObject
	*/
	public static JsonObject parseJsonObject(Object o) {
		String r = gson.toJson(o);
        JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(r);
		JsonObject j = null;
		if(el.isJsonObject()){
			j = el.getAsJsonObject();
		}	
		return j;
	}
	
	/**  
	* @Name: easyuiDataGridJsonToString
	* @Description: 转化成页面需要接收的格式
	* @Author: 李泽彬（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2013-07-05 （创建日期）
	* @Parameters: 无
	* @Return: String
	*/
	public static String dataGridJsonToString(DataGridJsonDTO<?> t) {
		JsonObject json = new JsonObject();
		json.addProperty("total", t.getTotal());
		json.add("rows", parseJsonArray(t.getRows()));
		return json.toString();
	}
}
