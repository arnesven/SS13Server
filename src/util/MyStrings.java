package util;

import java.util.List;

public class MyStrings {

    public static String join(List<?> items) {
        return join(items, "<list-part>");
    }

	public static String join(List<?> items, String delimiter) {
		StringBuffer buf = new StringBuffer("[");

        int count = 1;
		for (Object o : items) {
			if (o instanceof String) {
				buf.append((String)o);
			} else {
				buf.append(o.toString());
			}
            if (count < items.size()) {
                buf.append(delimiter);
            }
            count++;
		}
		//buf.delete(buf.length()-1, buf.length());

		buf.append("]");
		//Logger.log(buf.toString());
		return buf.toString();
	}

}
