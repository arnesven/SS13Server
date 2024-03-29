package util;

import java.util.ArrayList;
import java.util.Collection;
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

    public static String makeIntoSpacedCapitals(String realName) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < realName.length(); ++i) {
            buf.append(Character.toUpperCase(realName.charAt(i)));
            if (i != realName.length() - 1 ) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }

    public static String capitalize(String realName) {
        return realName.substring(0,1).toUpperCase() + realName.substring(1).toLowerCase();
    }

    public static String stripForbiddenCharacters(String data) {
        String forbidden = ":/\\(){}&%$¤#@_|<>^'*~;.,£+[]\"!?";
        for (int i = 0; i < forbidden.length(); ++i) {
            data = data.replace(forbidden.charAt(i)+"", "");
        }
        return data;
    }


}
