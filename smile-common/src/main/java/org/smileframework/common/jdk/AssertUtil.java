package org.smileframework.common.jdk;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author: cz
 * @description:断言工具类,抽取自spring框架
 */
public abstract class AssertUtil {

	/**
	 * 判断表达式为是否为true
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断表达式为是否为true
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 判断对象是否为空
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断对象是否为空
	 * @param object
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	* 判断对象是否为空 
	* @param object
	* @param message
	*/
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	* 判断对象是否为空 
	* @param object
	* @param message
	*/
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it cannot be null");
	}

	/**
	 * 断言字符串是否为空
	 * @param text
	 * @param message
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtil.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断字符串是否为空
	 * @param text
	 */
	public static void hasLength(String text) {
		hasLength(text,
				"[Assertion failed] - this String argument must have length; it cannot be <code>null</code> or empty");
	}

	/**
	 * 判断字符串是否包含内容
	 * @param text
	 * @param message
	 */
	public static void hasText(String text, String message) {
		if (!StringUtil.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 判断字符串是否包含内容
	 * @param text
	 */
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it cannot be <code>null</code>, empty, or blank");
	}

	/**
	 * 断言字符串是否包含内容其指定字符串内容
	 * @param textToSearch
	 * @param substring
	 * @param message
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtil.hasLength(textToSearch) && StringUtil.hasLength(substring) &&
				textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言字符串是否包含内容其指定字符串内容
	 * @param textToSearch
	 * @param substring
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}
	
	/**
	 * 断言数组对象是否为空
	 * @param array
	 * @param message
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtil.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言数组对象是否为空
	 * @param array
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言容器对象是否为空
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言容器对象是否为空
	 * @param collection
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * 断言Map对象是否为空
	 * @param map
	 * @param message
	 */
	public static void notEmpty(Map<?,?> map, String message) {
		if (CollectionUtil.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言Map对象是否为空
	 * @param map
	 */
	public static void notEmpty(Map<?,?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}


	/**
	 * 断言某个对象是否该类型的实例
	 * @param clazz
	 * @param obj
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * 断言某个对象是否该类型的实例
	 * @param clazz
	 * @param obj
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj, String message) {
		AssertUtil.notNull(clazz, "The clazz to perform the instanceof assertion cannot be null");
		AssertUtil.isTrue(clazz.isInstance(obj), message +
				"Object of class '" + (obj != null ? obj.getClass().getName() : "[null]") +
				"' must be an instance of '" + clazz.getName() + "'");
	}

	/**
	 * 判断该对象是否其对象的子类
	 * @param superType
	 * @param subType
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	/**
	 * 判断该对象是否其对象的子类
	 * @param superType
	 * @param subType
	 * @param message
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		AssertUtil.notNull(superType, "superType cannot be null");
		AssertUtil.notNull(subType, "subType cannot be null");
		AssertUtil.isTrue(superType.isAssignableFrom(subType), message + "Type [" + subType.getName()
						+ "] is not assignable to type [" + superType.getName() + "]");
	}

	/**
	 * 断言对象内容是否为true
	 * @param expression
	 * @param message
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * 断言对象内容是否为true
	 * @param expression
	 */
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

}
