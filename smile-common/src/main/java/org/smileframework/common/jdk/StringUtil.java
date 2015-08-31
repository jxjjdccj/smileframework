package org.smileframework.common.jdk;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 *
 * @author cz
 */

public abstract class StringUtil {
    private static final String FOLDER_SEPARATOR = "/";//文件分隔符

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";//Window文件分隔符

    private static final String TOP_PATH = "..";//上层路径

    private static final String CURRENT_PATH = ".";//当前路径

    private static final char EXTENSION_SEPARATOR = '.';//扩展分隔符


    //---------------------------------------------------------------------
    // General convenience methods for working with Strings
    //---------------------------------------------------------------------

    /**
     * Check if a String has length.
     * <p><pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     * @param str the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null and has length
     */
    /**
     * 检查字符串长度
     * <p><pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null and has length
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }
    /**
     * Check if a String has text. More specifically, returns <code>true</code>
     * if the string not <code>null<code>, it's <code>length is > 0</code>, and
     * it has at least one non-whitespace character.
     * <p><pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     * @param str the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null, length > 0,
     * and not whitespace only
     * @see java.lang.Character#isWhitespace
     */
    /**
     * 检查字符串是否包含内容
     * if the string not <code>null<code>, it's <code>length is > 0</code>, and
     * it has at least one non-whitespace character.
     * <p><pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the String to check, may be <code>null</code>
     * @return <code>true</code> if the String is not null, length > 0,
     * and not whitespace only
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Trim leading and trailing whitespace from the given String.
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    /**
     * 字符串去首尾空格
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 字符串去左空格
     *
     * @param str
     * @return
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    /**
     * 字符串去右空格
     *
     * @param str
     * @return
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * Trim <i>all</i> whitespace from the given String:
     * leading, trailing, and inbetween characters.
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    /**
     * 字符串去空格
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        int index = 0;
        while (buf.length() > index) {
            if (Character.isWhitespace(buf.charAt(index))) {
                buf.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return buf.toString();
    }


    /**
     * Test if the given String starts with the specified prefix,
     * ignoring upper/lower case.
     * @param str the String to check
     * @param prefix the prefix to look for
     * @see java.lang.String#startsWith
     */
    /**
     * 判断字符串前缀是否一致
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * Test if the given String ends with the specified suffix,
     * ignoring upper/lower case.
     * @param str the String to check
     * @param suffix the suffix to look for
     * @see java.lang.String#endsWith
     */
    /**
     * 判断字符串结束是否一致
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str == null || suffix == null) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);
    }

    /**
     * Count the occurrences of the substring in string s.
     * @param str string to search in. Return 0 if this is null.
     * @param sub string to search for. Return 0 if this is null.
     */
    /**
     * 判断指定字符串出现某字符串的次数
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            return 0;
        }
        int count = 0, pos = 0, idx = 0;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * Replace all occurences of a substring within a string with
     * another string.
     * @param inString String to examine
     * @param oldPattern String to replace
     * @param newPattern String to insert
     * @return a String with the replacements
     */
    /**
     * 字符串替换
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }

    /**
     * Delete all occurrences of the given substring.
     * @param pattern the pattern to delete all occurrences of
     */
    /**
     * 删除特定字符串
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * Delete any character in a given string.
     *
     * @param charsToDelete a set of characters to delete.
     *                      E.g. "az\n" will delete 'a's, 'z's and new lines.
     */
    /*删除字符串的包含的子字符串
     * */
    public static String deleteAny(String inString, String charsToDelete) {
        if (inString == null || charsToDelete == null) {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                out.append(c);
            }
        }
        return out.toString();
    }
    //---------------------------------------------------------------------
    // Convenience methods for working with formatted Strings
    //---------------------------------------------------------------------

    /**
     * Quote the given String with single quotes.
     *
     * @param str the input String (e.g. "myString")
     * @return the quoted String (e.g. "'myString'"),
     * or <code>null<code> if the input was <code>null</code>
     */
    /*字符串引用处理
	 * */
    public static String quote(String str) {
        return (str != null ? "'" + str + "'" : null);
    }

    /**
     * Turn the given Object into a String with single quotes
     * if it is a String; keeping the Object as-is else.
     *
     * @param obj the input Object (e.g. "myString")
     * @return the quoted String (e.g. "'myString'"),
     * or the input object as-is if not a String
     */
    public static Object quoteIfString(Object obj) {
        return (obj instanceof String ? quote((String) obj) : obj);
    }

    /**
     * Unqualify a string qualified by a '.' dot character. For example,
     * "this.name.is.qualified", returns "qualified".
     *
     * @param qualifiedName the qualified name
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * Unqualify a string qualified by a separator character. For example,
     * "this:name:is:qualified" returns "qualified" if using a ':' separator.
     * @param qualifiedName the qualified name
     * @param separator the separator
     */
    /**
     * 字符串加后缀处理
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * Capitalize a <code>String</code>, changing the first letter to
     * upper case as per {@link Character#toUpperCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to capitalize, may be <code>null</code>
     * @return the capitalized String, <code>null</code> if null
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * Uncapitalize a <code>String</code>, changing the first letter to
     * lower case as per {@link Character#toLowerCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to uncapitalize, may be <code>null</code>
     * @return the uncapitalized String, <code>null</code> if null
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    /**
     * 字符串首字母大写或小写
     *
     * @param str
     * @param capitalize
     * @return
     */
    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    /**
     * Extract the filename from the given path,
     * e.g. "mypath/myfile.txt" -> "myfile.txt".
     * @param path the file path (may be <code>null</code>)
     * @return the extracted filename, or <code>null</code> if none
     */
    /**
     * 得到文件名
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * Extract the filename extension from the given path,
     * e.g. "mypath/myfile.txt" -> "txt".
     *
     * @param path the file path (may be <code>null</code>)
     * @return the extracted filename extension, or <code>null</code> if none
     */
	/*得到文件扩展名
	 * */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
    }

    /**
     * Strip the filename extension from the given path,
     * e.g. "mypath/myfile.txt" -> "mypath/myfile".
     *
     * @param path the file path (may be <code>null</code>)
     * @return the path with stripped filename extension,
     * or <code>null</code> if none
     */
	/*
	 * 去除文件扩展名
	 * */
    public static String stripFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
    }

    /**
     * Apply the given relative path to the given path,
     * assuming standard Java folder separation (i.e. "/" separators);
     *
     * @param path         the path to start from (usually a full file path)
     * @param relativePath the relative path to apply
     *                     (relative to the full file path above)
     * @return the full file path that results from applying the relative path
     */
	/*得到相对路径
	 * */
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
                newPath += FOLDER_SEPARATOR;
            }
            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    /**
     * Normalize the path by suppressing sequences like "path/.." and
     * inner simple dots.
     * <p>The result is convenient for path comparison. For other uses,
     * notice that Windows separators ("\") are replaced by simple slashes.
     *
     * @param path the original path
     * @return the normalized path
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String cleanPath(String path) {
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

        // Strip prefix from path to analyze, to not treat it as part of the
        // first path element. This is necessary to correctly parse paths like
        // "file:core/../core/io/Resource.class", where the ".." should just
        // strip the first "core" directory while keeping the "file:" prefix.
        int prefixIndex = pathToUse.indexOf(":");
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            pathToUse = pathToUse.substring(prefixIndex + 1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
        List pathElements = new LinkedList();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            if (CURRENT_PATH.equals(pathArray[i])) {
                // Points to current directory - drop it.
            } else if (TOP_PATH.equals(pathArray[i])) {
                // Registering top path found.
                tops++;
            } else {
                if (tops > 0) {
                    // Merging path element with corresponding to top path.
                    tops--;
                } else {
                    // Normal path element found.
                    pathElements.add(0, pathArray[i]);
                }
            }
        }
        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++) {
            pathElements.add(0, TOP_PATH);
        }
        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * Compare two paths after normalization of them.
     *
     * @param path1 First path for comparizon
     * @param path2 Second path for comparizon
     * @return whether the two paths are equivalent after normalization
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    /**
     * Parse the given locale string into a <code>java.util.Locale</code>.
     * This is the inverse operation of Locale's <code>toString</code>.
     *
     * @param localeString the locale string, following
     *                     <code>java.util.Locale</code>'s toString format ("en", "en_UK", etc).
     *                     Also accepts spaces as separators, as alternative to underscores.
     * @return a corresponding Locale instance
     */
    public static Locale parseLocaleString(String localeString) {
        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);//下划线方式拆分字符串
        String language = (parts.length > 0 ? parts[0] : "");
        String country = (parts.length > 1 ? parts[1] : "");
        String variant = (parts.length > 2 ? parts[2] : "");
        return (language.length() > 0 ? new Locale(language, country, variant) : null);
    }


    //---------------------------------------------------------------------
    // Convenience methods for working with String arrays
    //---------------------------------------------------------------------

    /**
     * Append the given String to the given String array, returning a new array
     * consisting of the input array contents plus the given String.
     *
     * @param array the array to append to (can be <code>null</code>)
     * @param str   the String to append
     * @return the new array (never <code>null</code>)
     */
	/*往数组中添加一个元素
	 * */
    public static String[] addStringToArray(String[] array, String str) {
        if (ObjectUtil.isEmpty(array)) {
            return new String[]{str};
        }
        String[] newArr = new String[array.length + 1];
        System.arraycopy(array, 0, newArr, 0, array.length);
        newArr[array.length] = str;
        return newArr;
    }

    /**
     * Concatenate the given String arrays into one,
     * with overlapping array elements included twice.
     * <p>The order of elements in the original arrays is preserved.
     *
     * @param array1 the first array (can be <code>null</code>)
     * @param array2 the second array (can be <code>null</code>)
     * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
     */
	/*连接两个字符串数组*/
    public static String[] concatenateStringArrays(String[] array1, String[] array2) {
        if (ObjectUtil.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtil.isEmpty(array2)) {
            return array1;
        }
        String[] newArr = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, newArr, 0, array1.length);
        System.arraycopy(array2, 0, newArr, array1.length, array2.length);
        return newArr;
    }

    /**
     * Merge the given String arrays into one, with overlapping
     * array elements only included once.
     * <p>The order of elements in the original arrays is preserved
     * (with the exception of overlapping elements, which are only
     * included on their first occurence).
     *
     * @param array1 the first array (can be <code>null</code>)
     * @param array2 the second array (can be <code>null</code>)
     * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
     */
	/*合并两个字符串数组.去除重复的元素
	 * */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String[] mergeStringArrays(String[] array1, String[] array2) {
        if (ObjectUtil.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtil.isEmpty(array2)) {
            return array1;
        }
        List result = new ArrayList();
        result.addAll(Arrays.asList(array1));
        for (int i = 0; i < array2.length; i++) {
            String str = array2[i];
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        return toStringArray(result);
    }

    /**
     * Turn given source String array into sorted array.
     * @param array the source array
     * @return the sorted array (never <code>null</code>)
     */
    /**
     * 字符串排序操作
     */
    public static String[] sortStringArray(String[] array) {
        if (ObjectUtil.isEmpty(array)) {
            return new String[0];
        }
        Arrays.sort(array);
        return array;
    }

    /**
     * Copy the given Collection into a String array.
     * The Collection must contain String elements only.
     *
     * @param collection the Collection to copy
     * @return the String array (<code>null</code> if the Collection
     * was <code>null</code> as well)
     */
	/*容器内容转字符串数组
	 * */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String[] toStringArray(Collection collection) {
        if (collection == null) {
            return null;
        }
        return (String[]) collection.toArray(new String[collection.size()]);
    }

    /**
     * Remove duplicate Strings from the given array.
     * Also sorts the array, as it uses a TreeSet.
     *
     * @param array the String array
     * @return an array without duplicates, in natural sort order
     */
	/*去重处理
	 * */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String[] removeDuplicateStrings(String[] array) {
        if (ObjectUtil.isEmpty(array)) {
            return array;
        }
        Set set = new TreeSet();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }
        return toStringArray(set);
    }

    /**
     * Split a String at the first occurrence of the delimiter.
     * Does not include the delimiter in the result.
     *
     * @param toSplit   the string to split
     * @param delimiter to split the string up with
     * @return a two element array with index 0 being before the delimiter, and
     * index 1 being after the delimiter (neither element includes the delimiter);
     * or <code>null</code> if the delimiter wasn't found in the given input String
     */
	/*以指定分隔符拆分数组
	 * */
    public static String[] split(String toSplit, String delimiter) {
        if (!hasLength(toSplit) || !hasLength(delimiter)) {
            return null;
        }
        int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[]{beforeDelimiter, afterDelimiter};
    }

    /**
     * Take an array Strings and split each element based on the given delimiter.
     * A <code>Properties</code> instance is then generated, with the left of the
     * delimiter providing the key, and the right of the delimiter providing the value.
     * <p>Will trim both the key and value before adding them to the
     * <code>Properties</code> instance.
     *
     * @param array     the array to process
     * @param delimiter to split each element using (typically the equals symbol)
     * @return a <code>Properties</code> instance representing the array contents,
     * or <code>null</code> if the array to process was null or empty
     */
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
        return splitArrayElementsIntoProperties(array, delimiter, null);
    }

    /**
     * Take an array Strings and split each element based on the given delimiter.
     * A <code>Properties</code> instance is then generated, with the left of the
     * delimiter providing the key, and the right of the delimiter providing the value.
     * <p>Will trim both the key and value before adding them to the
     * <code>Properties</code> instance.
     * @param array the array to process
     * @param delimiter to split each element using (typically the equals symbol)
     * @param charsToDelete one or more characters to remove from each element
     * prior to attempting the split operation (typically the quotation mark
     * symbol), or <code>null</code> if no removal should occur
     * @return a <code>Properties</code> instance representing the array contents,
     * or <code>null</code> if the array to process was <code>null</code> or empty
     */
    /**
     * 数组转化成Properties元素
     */
    public static Properties splitArrayElementsIntoProperties(
            String[] array, String delimiter, String charsToDelete) {

        if (ObjectUtil.isEmpty(array)) {
            return null;
        }
        Properties result = new Properties();
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            if (charsToDelete != null) {
                element = deleteAny(array[i], charsToDelete);
            }
            String[] splittedElement = split(element, delimiter);
            if (splittedElement == null) {
                continue;
            }
            result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
        }
        return result;
    }

    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * Trims tokens and omits empty tokens.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     *
     * @param str        the String to tokenize
     * @param delimiters the delimiter characters, assembled as String
     *                   (each of those characters is individually considered as delimiter).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     *
     * @param str               the String to tokenize
     * @param delimiters        the delimiter characters, assembled as String
     *                          (each of those characters is individually considered as delimiter)
     * @param trimTokens        trim the tokens via String's <code>trim</code>
     * @param ignoreEmptyTokens omit empty tokens from the result array
     *                          (only applies to tokens that are empty after trimming; StringTokenizer
     *                          will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim
     * @see #delimitedListToStringArray
     */
	/* 字符串根据标识拆分为数组
	 * */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    /**
     * Take a String which is a delimited list and convert it to a String array.
     * <p>A single delimiter can consists of more than one character: It will still
     * be considered as single delimiter string, rather than as bunch of potential
     * delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
     *
     * @param str       the input String
     * @param delimiter the delimiter between elements (this is a single delimiter,
     *                  rather than a bunch individual delimiter characters)
     * @return an array of the tokens in the list
     * @see #tokenizeToStringArray
     */
	/*以List方式拆分字符串
	 * */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String[] delimitedListToStringArray(String str, String delimiter) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[]{str};
        }
        List result = new ArrayList();
        if ("".equals(delimiter)) {
            for (int i = 0; i < str.length(); i++) {
                result.add(str.substring(i, i + 1));
            }
        } else {
            int pos = 0;
            int delPos = 0;
            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(str.substring(pos, delPos));
                pos = delPos + delimiter.length();
            }
            if (str.length() > 0 && pos <= str.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(str.substring(pos));
            }
        }
        return toStringArray(result);
    }

    /**
     * Convert a CSV list into an array of Strings.
     *
     * @param str CSV list
     * @return an array of Strings, or the empty array if s is null
     */
    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    /**
     * Convenience method to convert a CSV string list to a set.
     * Note that this will suppress duplicates.
     *
     * @param str CSV String
     * @return a Set of String entries in the list
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Set commaDelimitedListToSet(String str) {
        Set set = new TreeSet();
        String[] tokens = commaDelimitedListToStringArray(str);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.
     *
     * @param coll   Collection to display
     * @param delim  delimiter to use (probably a ",")
     * @param prefix string to start each element with
     * @param suffix string to end each element with
     */
    @SuppressWarnings("rawtypes")
    public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
        if (CollectionUtil.isEmpty(coll)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.
     *
     * @param coll  Collection to display
     * @param delim delimiter to use (probably a ",")
     */
    @SuppressWarnings("rawtypes")
    public static String collectionToDelimitedString(Collection coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convenience method to return a Collection as a CSV String.
     * E.g. useful for <code>toString()</code> implementations.
     *
     * @param coll Collection to display
     */
    @SuppressWarnings("rawtypes")
    public static String collectionToCommaDelimitedString(Collection coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * Convenience method to return a String array as a delimited (e.g. CSV)
     * String. E.g. useful for <code>toString()</code> implementations.
     *
     * @param arr   array to display. Elements may be of any type (toString
     *              will be called on each element).
     * @param delim delimiter to use (probably a ",")
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (ObjectUtil.isEmpty(arr)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * Convenience method to return a String array as a CSV String.
     * E.g. useful for <code>toString()</code> implementations.
     *
     * @param arr array to display. Elements may be of any type (toString
     *            will be called on each element).
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    /**
     * Unicode转码
     *
     * @param str 需要转化的字符串
     * @return
     */
    public static String ascii2native(String str) {
        if (str == null) {
            return null;
        }
        Pattern p = Pattern.compile("[%]u[0-9a-fA-F]{4,4}");
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        char[] cc = new char[1];
        while (m.find()) {
            cc[0] = (char) Integer.parseInt(str.substring(m.start() + 2, m.end()), 16);
            String s1 = new String(cc);
            m.appendReplacement(sb, s1);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 得到字符串的字节长度
     *
     * @param source --字符串
     * @return 字符串的字节长度
     */
    public static int getByteLength(String source) {
        int len = 0;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            int highByte = c >>> 8;
            len += highByte == 0 ? 1 : 2;
        }
        return len;
    }

    /**
     * 判断字符串是否为null
     *
     * @param source --字符串
     * @return 为null或为"null"都返回true
     */
    public static boolean isNull(String val) {
        return val == null || val.equals("null");
    }

    /**
     * 判断字符串是否为空
     *
     * @param val
     * @return true 为空 false不为空 example isEmpty("aa") = false
     */
    public static boolean isEmpty(String val) {
        if (null == val || "".equals(val.trim())) {
            return true;
        }
        return false;
    }


    /**
     * 比较两个字符串是否相同,不区别大小写。
     * 注意:传入两个对象为空时也认为是相同
     *
     * @param str1 --参数1
     * @param str2 --参数2
     * @return true为相同 false为不同
     */
    public static boolean compare(String str1, String str2) {
        if (isEmpty(str1) && isEmpty(str2)) {
            return true;
        }
        if (isEmpty(str1) || isEmpty(str2)) {
            return false;
        }
        return str1.trim().toUpperCase().equals(str2.trim().toUpperCase());
    }

    public static boolean inequality(String str1, String str2) {
        return !compare(str1, str2);
    }

    /**
     * 字符串格式化
     * 实现 format("a{0}{1}","b","c") = abc的功能
     *
     * @param str
     * @param args
     * @return
     */
    public static String format(String str, Object... args) {
        if (args.length == 0) return str;
        for (int index = 0; index < args.length; index++) {
            str = str.replace("{" + index + "}", args[index].toString());
        }
        return str;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param val
     * @return
     */
    public static boolean notEmpty(String val) {
        return val != null && val.length() > 0;
    }

    public static double percentStrToVlaue(String percentStr) {
        if (notEmpty(percentStr) && percentStr.indexOf("%") != -1) {
            percentStr = percentStr.substring(0, percentStr.indexOf("%"));
        }
        if (isNumeric(percentStr)) {
            return new BigDecimal(percentStr).divide(new BigDecimal(100)).doubleValue();
        }
        return 0.0;
    }


    public static String addPercentageSymbol(Double value) {
        if (!ObjectUtil.isEmpty(value)) {
            String strValue = value.toString();
            if (strValue.endsWith(".0")) {
                return strValue.replace(".0", "") + "%";
            } else {
                return strValue + "%";
            }
        }
        return "";
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                if (str.charAt(i) != '.') {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 是否包含中英文
     *
     * @param @param  s
     * @param @return
     * @return boolean
     * @throws
     * @Function: hasContainChineseOrEngLetter
     * @Description:是否包含中英文
     */
    public static boolean isNotContainChineseOrEngLetter(String s) {
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            char ch = s.charAt(i);
            if (!(((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')))) {
                boolean isChinese = isChinese(ch);
                if (!isChinese) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断是否全部为标点符号
     *
     * @param @param  str
     * @param @return
     * @return boolean
     * @throws
     * @Function: isAllPunctuation
     * @Description:
     */
    public static boolean isAllPunctuation(String str) {
        int matchCount = 0;
        Pattern p = Pattern.compile("[\\pP‘’“”]");
        Matcher m = p.matcher(str);
        while (m.find()) {
            matchCount++;
        }
        if (matchCount == str.length()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断是否全部为特殊符号
     *
     * @param @param  str
     * @param @return
     * @return boolean
     * @throws
     * @Function: isAllSpecialChar
     * @Description:
     */
    public static boolean isAllSpecialChar(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？× ：\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        int matchCount = 0;
        while (m.find()) {
            matchCount++;
        }
        if (matchCount == str.length()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是中文字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            //|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            //|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            //|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                ) {
            return true;
        }
        return false;
    }

    /**
     * 转换url成加密格式。
     * 如果内容中含有 网址类字符，将会加密成网站字符显示出来
     *
     * @param text
     * @return
     */
    public static String convertURL(String text) {
        String regex = "((http://)?([a-z]+[.])|(www.))\\w+[.]([a-z]{2,4})?[[.]([a-z]{2,4})]+((/[\\S&&[^,;\"”，。；、/()\\[\\]（）【】\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
        String A1 = " <a target=\"_bank\" href={0} title={1}>";
        String A2 = "</a>";
        StringBuffer sb = new StringBuffer(text);
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(text);
        int index = 0;
        int index1 = 0;
        List<String> urls = new ArrayList<String>();
        while (mat.find()) {
            String url = mat.group();
            urls.add(url);
//			 System.out.println(url);
            if (url.indexOf("http://") != 0)
                url = "http://" + url;

            Object obj[] = {"'" + url + "'", "'" + url + "'"};

            String a = MessageFormat.format(A1, obj);
            int l = a.length();
            index += index1;
            sb.insert(mat.start() + index, a);
            index += l;
            sb.insert((mat.end()) + index, A2);
            index1 = A2.length();
        }

        String result = sb.toString();
        for (String url : urls) {
            result = result.replace(">" + url + "</a>", ">http://www.51ret.com/</a>");
        }
        return result;
    }


    /**
     * 截取微博来源显示文字的链接地址
     *
     * @param conntent
     * @return
     */
    public static String subLinkText(String content) {
        if (content == null || content == "") {
            return content;
        }
        String href = "";
        String text = "";
        String subContent = "";
        StringBuffer newContent = new StringBuffer();
        boolean flag = false;
        Pattern p = Pattern.compile("<a .+>.+</a>");//判断是否是链接
        Matcher m = p.matcher(content);
        if (m.matches()) {
            String regex = "<[^<]*>";
            text = content.replaceAll(regex, "").replaceAll("<", "").replaceAll(">", "");//提前链接文本
            if (text.length() > 20) {
                subContent = text.substring(0, 15) + "...";
            } else {
                subContent = text;
            }
            Pattern p2 = Pattern.compile("href=['\"].[^>]+['\"]");//提前链接地址
            Matcher m2 = p2.matcher(content);
            if (m2.find()) {
                href = m2.group();
                if (!(href.toUpperCase().indexOf("HTTP") > -1)) {
                    href = "href='" + href.substring(href.indexOf("=") + 2, href.length() - 1) + "' ";
                }
                flag = true;
            }
        }
        if (flag) {
            //新的的链接文本
            newContent.append("<a target='blank' ").append(href).append(" title='").append(text).append("'>").append(subContent).append("</a>");
        } else {
            newContent.append(content);
        }
        return newContent.toString();

    }


    //获取nickname列表 并排重
    public static Set<String> formatAtNickname(String s) {
        if ((s == null) || (s.length() < 1)) {
            return null;
        }
        Set<String> list = new HashSet<String>();

        String regex = "@[^@\\s\\.:：*' '<#]+";//@[a-zA-Z0-9\u4e00-\u9fa5_]+
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(s);

        while (mat.find()) {
            String atStr = mat.group();
            String nickName = atStr.substring(1);
            list.add(nickName);
        }
        return list;
    }

    /**
     * 计算字符串长度，一个英文字长度为1；一个中文字长度为2
     *
     * @param s
     * @return
     */
    public static int calStringLength(String s) {
        if (s == null) {
            return 0;
        }

        int result = 0;
        for (char c : s.toCharArray()) {
            if (isChinese(c)) {
                result += 2;
            } else {
                result += 1;
            }
        }

        return result;
    }

    /**
     * 去掉html标签 , (如"<a href...>abc</a>" 结果为  "abc"）
     *
     * @param html
     * @return
     */
    public static String removeHtmlTag(String html) {

        if ((html == null) || (html.length() == 0)) {
            return "";
        }
        String nonHtml = html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", " ").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("\n", "");

        //增加截取4个//@作为
        nonHtml = truncate3AtContent(nonHtml);
        if (nonHtml.length() > 140) {
            nonHtml = nonHtml.substring(0, 135) + "...";
        }
        return nonHtml;
    }

    /**
     * 截取3个//@作为转发的附加语句。
     *
     * @param s
     * @return
     */
    public static String truncate3AtContent(String s) {
        String[] arr = s.split("//@");
        String temp = "";
        for (int i = 0; i < arr.length; i++) {
            temp += "//@" + arr[i];
            if (i == 3) {
                break;
            }
        }
        if (arr.length > 0) {
            return temp.substring(3);
        }
        return s;
    }

    public static String getMiniNickName(String nickName, int length) {
        if (isEmpty(nickName)) {
            return "";
        } else if (nickName.length() <= length) {
            return nickName;
        } else {
            StringBuilder ret = new StringBuilder();
            char[] ary = nickName.toCharArray();
            int counter = length * 2;
            int len = nickName.length();
            int i = 0, j = 0;
            for (; i < len && j < counter; i++) {
                if (isChinese(ary[i])) {
                    if (j + 2 <= counter) {
                        ret.append(ary[i]);
                        j = j + 2;
                    } else {
                        break;
                    }
                } else if (j + 1 <= counter) {
                    ret.append(ary[i]);
                    j = j + 1;
                } else {
                    break;
                }
            }
            if (i < nickName.length()) {
                ret.append("...");
            }
            return ret.toString();
        }
    }


    /*手机号加*号处理*/
    public static String hideMobile(String mobile) {
        if (isEmpty(mobile)) return mobile;
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }
    
    public static boolean isMobileNO(String mobiles) {
    	Pattern p = Pattern.compile("^1\\d{10}");
    	Matcher m = p.matcher(mobiles);
    	return m.matches();
    }
    
}