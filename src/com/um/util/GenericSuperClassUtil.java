package com.um.util;

import java.lang.reflect.ParameterizedType;
/*
 * 泛型转换类
 * */
public class GenericSuperClassUtil {

	public static Class getActualTypeClass(Class entity) {
		ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];
		return entityClass;
	}

}
