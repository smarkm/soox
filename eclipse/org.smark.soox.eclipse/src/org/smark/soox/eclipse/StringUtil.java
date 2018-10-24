package org.smark.soox.eclipse;

public class StringUtil {
	public static String join(String[] strArray,String j) {
		StringBuilder sb = new StringBuilder("");
		if (strArray!=null&&strArray.length>0) {
			for (String string : strArray) {
				sb.append(string+j);
			}
			sb = new StringBuilder(sb.subSequence(0, sb.length()-j.length()));
		}
		return sb.toString();
	}
	
	public static String joinFirstN(String[] strArray,int n ,String j) {
		if (strArray.length<=n) {
			return join(strArray, j);
		}
		String[] tmp = new String[n];
		for (int i = 0; i < n; i++) {
			tmp[i] = strArray[i];
		}
		return join(tmp, j);
	}
}
