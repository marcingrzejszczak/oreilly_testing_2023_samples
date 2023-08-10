package com.example.week1.part2;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public static <T> void injectDependency(Class<T> clazz, String fieldName, T objectToInjectTo, Object dependencyToInject) {
		Field f1 = null;
		try {
			f1 = clazz.getDeclaredField(fieldName);
			f1.setAccessible(true);
			f1.set(objectToInjectTo, dependencyToInject);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
