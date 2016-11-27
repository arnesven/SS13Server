package util;

import java.io.File;

public class MyPaths {

	public static String makePath(String[] strings) {
		StringBuffer buf = new StringBuffer();
		
		for (String part : strings) {
			buf.append(part + File.separator);
		}
		
		return buf.toString();
	}

}
