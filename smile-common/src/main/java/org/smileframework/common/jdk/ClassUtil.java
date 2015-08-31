package org.smileframework.common.jdk;

import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class ClassUtil {

	/** Suffix for array class names */
	public static final String ARRAY_SUFFIX = "[]";  //数组前缀

	/** The package separator character '.' */
	private static final char PACKAGE_SEPARATOR = '.';//包分隔符前缀

	/** The inner class separator character '$' */
	private static final char INNER_CLASS_SEPARATOR = '$';//内部类前缀

	/** The CGLIB class separator character "$$" */
	private static final String CGLIB_CLASS_SEPARATOR = "$$";//CGLIB类前缀

	/**
	 * Map with primitive wrapper type as key and corresponding primitive
	 * type as value, for example: Integer.class -> int.class.
	 */
	private static final Map primitiveWrapperTypeMap = new HashMap(8);//原始类型容器
	/**
	 * Map with primitive type name as key and corresponding primitive
	 * type as value, for example: "int" -> "int.class".
	 */
	private static final Map primitiveTypeNameMap = new HashMap(8);//原始类型容器

	static {
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

		for (Iterator it = primitiveWrapperTypeMap.values().iterator(); it.hasNext();) {
			Class primitiveClass = (Class) it.next();
			primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
		}
	}

	/**
	 * 返回默认的类加载器
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			ex.printStackTrace();
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtil.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * 判断该类是否可以加载
	 * @param className--类名
	 * @return
	 */
	public static boolean isPresent(String className) {
		try {
			forName(className);
			return true;
		}
		catch (Throwable ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据类名、加载指定类
	 * @param name --类名
	 * @return   --实例
	 * @throws ClassNotFoundException
	 * @throws LinkageError
	 */
	public static Class forName(String name) throws ClassNotFoundException, LinkageError {
		return forName(name, getDefaultClassLoader());
	}

	/**
	 * 根据类名加载指定类
	 * @param name --类名
	 * @param classLoader --类加载器
	 * @return
	 * @throws ClassNotFoundException
	 * @throws LinkageError
	 */
	public static Class forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		AssertUtil.notNull(name, "Name must not be null");
		//基本数据类型
		Class clazz = resolvePrimitiveClassName(name);
		if (clazz != null) {
			return clazz;
		}
		//数组数据类型
		if (name.endsWith(ARRAY_SUFFIX)) {
			// special handling for array class names
			String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
			Class elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}
		//传入加载类为空时,取默认加载器
		ClassLoader classLoaderToUse = classLoader;
		if (classLoaderToUse == null) {
			classLoaderToUse = getDefaultClassLoader();
		}
		return classLoaderToUse.loadClass(name);
	}
	/**
	 * 加载指定类实例: 支持基本类型int 或字符串类型 String[] 或其他类 
	 * @param className
	 * @param classLoader
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Class resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
		try {
			return forName(className, classLoader);
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalArgumentException("Cannot find class [" + className + "]. Root cause: " + ex);
		}
		catch (LinkageError ex) {
			throw new IllegalArgumentException("Error loading class [" + className +
					"]: problem with class file or dependent class. Root cause: " + ex);
		}
	}

	/**
	 * 返回基本的数据类型
	 * @param name
	 * @return
	 */
	public static Class resolvePrimitiveClassName(String name) {
		Class result = null;
		// Most class names will be quite long, considering that they
		// SHOULD sit in a package, so a length check is worthwhile.
		if (name != null && name.length() <= 8) {
			// Could be a primitive - likely.
			result = (Class) primitiveTypeNameMap.get(name);
		}
		return result;
	}
	
	/**
	 * 取得不包含类路径的名称 
	 * @param className --类名
	 * @return 
	 */
	public static String getShortName(String className) {
		AssertUtil.hasLength(className, "Class name must not be empty");
		int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
		int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
		if (nameEndIndex == -1) {
			nameEndIndex = className.length();
		}
		String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
		shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
		return shortName;
	}

	/**
	 * 取得不包含类路径的名称 
	 * @param clazz --类名
	 * @return
	 */
	public static String getShortName(Class clazz) {
		return getShortName(getQualifiedName(clazz));
	}

	/**
	 * Return the short string name of a Java class in decapitalized
	 * JavaBeans property format.
	 * @param clazz the class
	 * @return the short name rendered in a standard JavaBeans property format
	 * @see java.beans.Introspector#decapitalize(String)
	 */
	public static String getShortNameAsProperty(Class clazz) {
		return Introspector.decapitalize(getShortName(clazz));
	}

	/**
	 * Return the qualified name of the given class: usually simply
	 * the class name, but component type class name + "[]" for arrays.
	 * @param clazz the class
	 * @return the qualified name of the class
	 */
	public static String getQualifiedName(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		if (clazz.isArray()) {
			return getQualifiedNameForArray(clazz);
		}
		else {
			return clazz.getName();
		}
	}

	/**
	 * Build a nice qualified name for an array:
	 * component type class name + "[]".
	 * @param clazz the array class
	 * @return a qualified name for the array class
	 */
	private static String getQualifiedNameForArray(Class clazz) {
		StringBuffer buffer = new StringBuffer();
		while (clazz.isArray()) {
			clazz = clazz.getComponentType();
			buffer.append(ClassUtil.ARRAY_SUFFIX);
		}
		buffer.insert(0, clazz.getName());
		return buffer.toString();
	}

	/**
	 * Return the qualified name of the given method, consisting of
	 * fully qualified interface/class name + "." + method name.
	 * @param method the method
	 * @return the qualified name of the method
	 */
	public static String getQualifiedMethodName(Method method) {
		AssertUtil.notNull(method, "Method must not be null");
		return method.getDeclaringClass().getName() + "." + method.getName();
	}


	/**
	 * Determine whether the given class has a method with the given signature.
	 * <p>Essentially translates <code>NoSuchMethodException</code> to "false".
	 * @param clazz	the clazz to analyze
	 * @param methodName the name of the method
	 * @param paramTypes the parameter types of the method
	 * @see java.lang.Class#getMethod
	 */
	/**
	 * 判断类是否存在指定方法
	 * @param clazz  --类
	 * @param methodName --方法名
	 * @param paramTypes  --方法参数
	 * @return
	 */
	public static boolean hasMethod(Class clazz, String methodName, Class[] paramTypes) {
		return (getMethodIfAvailable(clazz, methodName, paramTypes) != null);
	}

	/**
	 * Determine whether the given class has a method with the given signature,
	 * and return it if available (else return <code>null</code>).
	 * <p>Essentially translates <code>NoSuchMethodException</code> to <code>null</code>.
	 * @param clazz	the clazz to analyze
	 * @param methodName the name of the method
	 * @param paramTypes the parameter types of the method
	 * @return the method, or <code>null</code> if not found
	 * @see java.lang.Class#getMethod
	 */
	public static Method getMethodIfAvailable(Class clazz, String methodName, Class[] paramTypes) {
		AssertUtil.notNull(clazz, "Class must not be null");
		AssertUtil.notNull(methodName, "Method name must not be null");
		try {
			return clazz.getMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return null;
		}
	}

	/**
	 * Return the number of methods with a given name (with any argument types),
	 * for the given class and/or its superclasses. Includes non-public methods.
	 * @param clazz	the clazz to check
	 * @param methodName the name of the method
	 * @return the number of methods with the given name
	 */
	/**
	 * 统计类存多少个同名的方法(名字相同，但传入参数可能不一致
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static int getMethodCountForName(Class clazz, String methodName) {
		AssertUtil.notNull(clazz, "Class must not be null");
		AssertUtil.notNull(methodName, "Method name must not be null");
		int count = 0;
		for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
			Method method = clazz.getDeclaredMethods()[i];
			if (methodName.equals(method.getName())) {
				count++;
			}
		}
		Class[] ifcs = clazz.getInterfaces();
		for (int i = 0; i < ifcs.length; i++) {
			count += getMethodCountForName(ifcs[i], methodName);
		}
		if (clazz.getSuperclass() != null) {
			count += getMethodCountForName(clazz.getSuperclass(), methodName);
		}
		return count;
	}

	/**
	 * Does the given class and/or its superclasses at least have one or more
	 * methods (with any argument types)? Includes non-public methods.
	 * @param clazz	the clazz to check
	 * @param methodName the name of the method
	 * @return whether there is at least one method with the given name
	 */
	/**
	 * 判断该类是否存在至少一个方法
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static boolean hasAtLeastOneMethodWithName(Class clazz, String methodName) {
		AssertUtil.notNull(clazz, "Class must not be null");
		AssertUtil.notNull(methodName, "Method name must not be null");
		for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
			Method method = clazz.getDeclaredMethods()[i];
			if (method.getName().equals(methodName)) {
				return true;
			}
		}
		Class[] ifcs = clazz.getInterfaces();
		for (int i = 0; i < ifcs.length; i++) {
			if (hasAtLeastOneMethodWithName(ifcs[i], methodName)) {
				return true;
			}
		}
		if (clazz.getSuperclass() != null) {
			return hasAtLeastOneMethodWithName(clazz.getSuperclass(), methodName);
		}
		return false;
	}

	/**
	 * Return a static method of a class.
	 * @param methodName the static method name
	 * @param clazz	the class which defines the method
	 * @param args the parameter types to the method
	 * @return the static method, or <code>null</code> if no static method was found
	 * @throws IllegalArgumentException if the method name is blank or the clazz is null
	 */
	/**
	 * 取得类的静态方法 
	 * @param clazz --类名
	 * @param methodName --方法
	 * @param args --参数
	 * @return
	 */
	public static Method getStaticMethod(Class clazz, String methodName, Class[] args) {
		AssertUtil.notNull(clazz, "Class must not be null");
		AssertUtil.notNull(methodName, "Method name must not be null");
		try {
			Method method = clazz.getDeclaredMethod(methodName, args);
			if ((method.getModifiers() & Modifier.STATIC) != 0) {
				return method;
			}
		}
		catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		}
		return null;
	}


	/**
	 * Check if the given class represents a primitive wrapper,
	 * i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 */
	/**
	 * 判断类是否属于基本数据类型人包装类
	 */
	public static boolean isPrimitiveWrapper(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		return primitiveWrapperTypeMap.containsKey(clazz);
	}

	/**
	 * Check if the given class represents a primitive (i.e. boolean, byte,
	 * char, short, int, long, float, or double) or a primitive wrapper
	 * (i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double).
	 */
	public static boolean isPrimitiveOrWrapper(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
	}

	/**
	 * Check if the given class represents an array of primitives,
	 * i.e. boolean, byte, char, short, int, long, float, or double.
	 */
	/**
	 * 判断类是否属于基本类型数组
	 */
	public static boolean isPrimitiveArray(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		return (clazz.isArray() && clazz.getComponentType().isPrimitive());
	}

	/**
	 * Check if the given class represents an array of primitive wrappers,
	 * i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 */
	/**
	 * 判断类是否属于基本类型数组或包装类的数组
	 */
	public static boolean isPrimitiveWrapperArray(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		return (clazz.isArray() && isPrimitiveWrapper(clazz.getComponentType()));
	}

	/**
	 * Determine if the given target type is assignable from the given value
	 * type, assuming setting by reflection. Considers primitive wrapper
	 * classes as assignable to the corresponding primitive types.
	 * @param targetType the target type
	 * @param valueType	the value type that should be assigned to the target type
	 * @return if the target type is assignable from the value type
	 */
	/**
	 * 判断类是否是目标类的子类
	 */
	public static boolean isAssignable(Class targetType, Class valueType) {
		AssertUtil.notNull(targetType, "Target type must not be null");
		AssertUtil.notNull(valueType, "Value type must not be null");
		return (targetType.isAssignableFrom(valueType) ||
				targetType.equals(primitiveWrapperTypeMap.get(valueType)));
	}

	/**
	 * Determine if the given type is assignable from the given value,
	 * assuming setting by reflection. Considers primitive wrapper classes
	 * as assignable to the corresponding primitive types.
	 * @param type	the target type
	 * @param value the value that should be assigned to the type
	 * @return if the type is assignable from the value
	 */
	public static boolean isAssignableValue(Class type, Object value) {
		AssertUtil.notNull(type, "Type must not be null");
		return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
	}

	/**
	 * 添加资源路径到类路径中（如需要加载com.SomeClasss同目录的confg.peroties资源)
	 * @param clazz
	 * @param resourceName
	 * @return
	 */
	public static String addResourcePathToPackagePath(Class clazz, String resourceName) {
		AssertUtil.notNull(resourceName, "Resource name must not be null");
		if (!resourceName.startsWith("/")) {
			return classPackageAsResourcePath(clazz) + "/" + resourceName;
		}
		return classPackageAsResourcePath(clazz) + resourceName;
	}

	/**
	 * Given an input class object, return a string which consists of the
	 * class's package name as a pathname, i.e., all dots ('.') are replaced by
	 * slashes ('/'). Neither a leading nor trailing slash is added. The result
	 * could be concatenated with a slash and the name of a resource, and fed
	 * directly to ClassLoader.getResource(). For it to be fed to Class.getResource,
	 * a leading slash would also have to be prepended to the return value.
	 * @param clazz the input class. A null value or the default (empty) package
	 * will result in an empty string ("") being returned.
	 * @return a path which represents the package name
	 * @see ClassLoader#getResource
	 * @see Class#getResource
	 */
	public static String classPackageAsResourcePath(Class clazz) {
		if (clazz == null || clazz.getPackage() == null) {
			return "";
		}
		return clazz.getPackage().getName().replace('.', '/');
	}

	/**
	 * Return all interfaces that the given object implements as array,
	 * including ones implemented by superclasses.
	 * @param object the object to analyse for interfaces
	 * @return all interfaces that the given object implements as array
	 */
	public static Class[] getAllInterfaces(Object object) {
		Set interfaces = getAllInterfacesAsSet(object);
		return (Class[]) interfaces.toArray(new Class[interfaces.size()]);
	}

	/**
	 * Return all interfaces that the given class implements as array,
	 * including ones implemented by superclasses.
	 * <p>If the class itself is an interface, it gets returned as sole interface.
	 * @param clazz the class to analyse for interfaces
	 * @return all interfaces that the given object implements as array
	 */
	/**
	 * 取得类的所有接口
	 */
	public static Class[] getAllInterfacesForClass(Class clazz) {
		Set interfaces = getAllInterfacesForClassAsSet(clazz);
		return (Class[]) interfaces.toArray(new Class[interfaces.size()]);
	}

	/**
	 * Return all interfaces that the given object implements as List,
	 * including ones implemented by superclasses.
	 * @param object the object to analyse for interfaces
	 * @return all interfaces that the given object implements as List
	 */
	public static Set getAllInterfacesAsSet(Object object) {
		AssertUtil.notNull(object, "Object must not be null");
		return getAllInterfacesForClassAsSet(object.getClass());
	}

	/**
	 * Return all interfaces that the given class implements as Set,
	 * including ones implemented by superclasses.
	 * <p>If the class itself is an interface, it gets returned as sole interface.
	 * @param clazz the class to analyse for interfaces
	 * @return all interfaces that the given object implements as Set
	 */
	public static Set getAllInterfacesForClassAsSet(Class clazz) {
		AssertUtil.notNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			return Collections.singleton(clazz);
		}
		Set interfaces = new HashSet();
		while (clazz != null) {
			for (int i = 0; i < clazz.getInterfaces().length; i++) {
				Class ifc = clazz.getInterfaces()[i];
				interfaces.add(ifc);
			}
			clazz = clazz.getSuperclass();
		}
		return interfaces;
	}

	/**
	 * Build a String that consists of the names of the classes/interfaces
	 * in the given collection.
	 * <p>Basically like <code>AbstractCollection.toString()</code>, but stripping
	 * the "class "/"interface " prefix before every class name.
	 * @param classes a Collection of Class objects (may be <code>null</code>)
	 * @return a String of form "[com.foo.Bar, com.foo.Baz]"
	 * @see java.util.AbstractCollection#toString()
	 */
	/**
	 * 类名转字符串
	 */
	public static String classNamesToString(Collection classes) {
		if (CollectionUtil.isEmpty(classes)) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer("[");
		for (Iterator it = classes.iterator(); it.hasNext(); ) {
			Class clazz = (Class) it.next();
			sb.append(clazz.getName());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
