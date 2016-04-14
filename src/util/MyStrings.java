package util;

import java.util.List;

public class MyStrings {

	public static String join(List<?> items, String delimiter) {
		StringBuffer buf = new StringBuffer("[");
		for (Object o : items) {
			if (o instanceof String) {
				buf.append((String)o);
			} else {
				buf.append(o.toString());
			}
			buf.append(delimiter);
		}
		buf.delete(buf.length()-1, buf.length());
		buf.append("]");
		System.out.println(buf.toString());
		return buf.toString();
	}

}
