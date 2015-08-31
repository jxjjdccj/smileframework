package org.smileframework.common.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
/**
 * 容器工具类
 * @author cz
 */
public abstract class CollectionUtil {

	/**
	 * Return <code>true</code> if the supplied Collection is <code>null</code>
	 * or empty. Otherwise, return <code>false</code>.
	 * @param collection the Collection to check
	 */
	/**
	 *判断 collection是否为空
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Return <code>true</code> if the supplied Map is <code>null</code>
	 * or empty. Otherwise, return <code>false</code>.
	 * @param map the Map to check
	 */
	/**
	 * 判断Map 是否为空
	 */
	public static boolean isEmpty(Map<?,?> map) 
	{
		return (map == null || map.isEmpty());
	}

	/**
	 * Check whether the given Collection contains the given element instance.
	 * <p>Enforces the given instance to be present, rather than returning
	 * <code>true</code> for an equal element as well.
	 * @param collection the Collection to check
	 * @param element the element to look for
	 * @return <code>true</code> if found, <code>false</code> else
	 */
	/*判断容器中是否包含该对象
	 * */
	@SuppressWarnings("rawtypes")
	public static boolean containsInstance(Collection<?> collection, Object element) {
		if (collection != null) {
			for (Iterator it = collection.iterator(); it.hasNext();) {
				Object candidate = it.next();
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Iterator contains the given element.
	 * @param iterator the Iterator to check
	 * @param element the element to look for
	 * @return <code>true</code> if found, <code>false</code> else
	 */
	/**
	 * 判断叠代器中是否包含该对象
	 */
	@SuppressWarnings("rawtypes")
	public static boolean contains(Iterator iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (ObjectUtil.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Enumeration contains the given element.
	 * @param enumeration the Enumeration to check
	 * @param element the element to look for
	 * @return <code>true</code> if found, <code>false</code> else
	 */
	/**
	 * 判断枚举是否包含该对象
	 */
	public static boolean contains(Enumeration<?> enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (ObjectUtil.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determine whether the given Collection only contains a single unique object.
	 * @param collection the Collection to check
	 * @return <code>true</code> if the collection contains a single reference or
	 * multiple references to the same instance, <code>false</code> else
	 */
	/**
	 * 判断容器中的元素是否唯一
	 */
	@SuppressWarnings("rawtypes")
	public static boolean hasUniqueObject(Collection<?> collection) {
		if (isEmpty(collection)) {
			return false;
		}
		boolean hasCandidate = false;
		Object candidate = null;
		for (Iterator it = collection.iterator(); it.hasNext();) {
			Object elem = it.next();
			if (!hasCandidate) {
				hasCandidate = true;
				candidate = elem;
			}
			else if (candidate != elem) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Find a value of the given type in the given Collection.
	 * @param collection the Collection to search
	 * @param type the type to look for
	 * @return a value of the given type found, or <code>null</code> if none
	 * @throws IllegalArgumentException if more than one value of the given type found
	 */
	/***
	 * 查找容器内是否存在该种类型的对象存在
	 */
	@SuppressWarnings("rawtypes")
	public static Object findValueOfType(Collection<?> collection, Class<?> type) throws IllegalArgumentException {
		if (isEmpty(collection)) {
			return null;
		}
		Class typeToUse = (type != null ? type : Object.class);
		Object value = null;
		for (Iterator it = collection.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (typeToUse.isInstance(obj)) {
				if (value != null) {
					throw new IllegalArgumentException("More than one value of type [" + typeToUse.getName() + "] found");
				}
				value = obj;
			}
		}
		return value;
	}

	/**
	 * Find a value of one of the given types in the given Collection:
	 * searching the Collection for a value of the first type, then
	 * searching for a value of the second type, etc.
	 * @param collection the collection to search
	 * @param types the types to look for, in prioritized order
	 * @return a of one of the given types found, or <code>null</code> if none
	 * @throws IllegalArgumentException if more than one value of the given type found
	 */
	public static Object findValueOfType(Collection<?> collection, Class<?>[] types) throws IllegalArgumentException {
		if (isEmpty(collection) || ObjectUtil.isEmpty(types)) {
			return null;
		}
		for (int i = 0; i < types.length; i++) {
			Object value = findValueOfType(collection, types[i]);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Convert the supplied array into a List. Primitive arrays are
	 * correctly converted into Lists of the appropriate wrapper type.
	 * @param source the original array
	 * @return the converted List result
	 */
	/*数组转化成List
	 * */
	public static List<?> arrayToList(Object source) {
		if (source == null || !source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		boolean primitive = source.getClass().getComponentType().isPrimitive();
		Object[] array = (primitive ? ObjectUtil.toObjectArray(source) : (Object[]) source);
		return Arrays.asList(array);
	}

	/**
	 * Merge the given Properties instance into the given Map,
	 * copying all properties (key-value pairs) over.
	 * <p>Uses <code>Properties.propertyNames()</code> to even catch
	 * default properties linked into the original Properties instance.
	 * @param props the Properties instance to merge (may be <code>null</code>)
	 * @param map the target Map to merge the properties into
	 */
	/*转化Properties对象值为Map
	 * */
	public static void mergePropertiesIntoMap(Properties props, Map<String,String> map) {
		if (map == null) {
			throw new IllegalArgumentException("Map must not be null");
		}
		if (props != null) {
			for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				map.put(key, props.getProperty(key));
			}
		}
	}

	/**
	 * Return <code>true</code> if any element in '<code>candidates</code>' is
	 * contained in '<code>source</code>'; otherwise returns <code>false</code>.
	 */
	/*判断source容器内是否存在candidates内容
	 * */
	@SuppressWarnings("rawtypes")
	public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return false;
		}
		for (Iterator it = candidates.iterator(); it.hasNext();) {
			if (source.contains(it.next())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the first element in '<code>candidates</code>' that is contained in
	 * '<code>source</code>'. If no element in '<code>candidates</code>' is present in
	 * '<code>source</code>' returns <code>null</code>. Iteration order is
	 * {@link Collection} implementation specific.
	 */
	/**
	 * 比较容器source是否包含容器candidates的元素.如何存在.则返回匹配成功的第一个对象
	 */
	@SuppressWarnings("rawtypes")
	public static Object findFirstMatch(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return null;
		}
		for (Iterator it = candidates.iterator(); it.hasNext();) {
			Object candidate = it.next();
			if (source.contains(candidate)) {
				return candidate;
			}
		}
		return null;
	}
	
	public static boolean isEmptyOrNull(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}
	
	public static boolean isNotEmptyOrNull(Collection<?> collection) {
		return null != collection && !collection.isEmpty();
	}
	
	/**排除List 重复对象*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    } 

}
