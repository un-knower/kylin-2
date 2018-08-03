/**
 * kylin-common
 * com.ycgwl.kylin.util
 */
package com.ycgwl.kylin.util;

import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.exception.RRException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-28 10:12:29
 */
public abstract class Assert {
	


	public static void isBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw new RRException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new RRException(message);
		}
	}

	public static void state(String name, boolean expression, String message) {
		if (!expression) {
			throw new ParameterException(name, expression, message);
		}
	}
	public static void trueIsWrong(String name, boolean expression, String message) {
		if (expression) {
			throw new ParameterException(name, expression, message);
		}
	}
	public static void isTrue(String name, boolean expression, String message) {
		if (!expression) {
			throw new ParameterException(name, expression, message);
		}
	}
	public static void isNull(String name, Object object, String message) {
		if (object != null) {
			throw new ParameterException(name, object, message);
		}
	}

	public static void notNull(String name, Object object, String message) {
		if (object == null) {
			throw new ParameterException(name, object, message);
		}
	}
	public static void hasLength(String name, String text, String message) {
		if (text == null || text.length() <= 0) {
			throw new ParameterException(name, text, message);
		}
	}
	public static void hasText(String name, String text, String message) {
		if (text == null || text.length() <= 0) {
			throw new ParameterException(name, text, message);
		}
	}

	public static void notEmpty(String name, Object[] array, String message) {
		if (array == null || array.length <= 0) {
			throw new ParameterException(name, Arrays.toString(array), message);
		}
	}

	public static void noNullElements(String name, Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new ParameterException(name, Arrays.toString(array), message);
				}
			}
		}
	}

	public static void notEmpty(String name, Collection<?> collection, String message) {
		if (collection == null || collection.size() <= 0) {
			throw new ParameterException(name, collection, message);
		}
	}

	public static void notEmpty(String name, Map<?, ?> map, String message) {
		if (map == null || map.size() <= 0) {
			throw new ParameterException(name, map, message);
		}
	}
}
