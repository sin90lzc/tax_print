package com.sin90lzc.tax.util;

import com.sin90lzc.tax.constant.Alignment;

public class StringUtil {
	public static final String EMPTY = "";

	public static final String alignTo(Alignment align, int len,
			String srcString, char append) {
		if (srcString == null) {
			srcString = EMPTY;
		}
		if (srcString.length() >= len) {
			return srcString;
		}
		int appendNum = len - srcString.length();
		StringBuffer ret = new StringBuffer();
		switch (align) {

		case RIGHT_ALIGN:
			ret.append(srcString);
			for (int i = 0; i < appendNum; i++) {
				ret.append(append);
			}
			break;
		case CENTRAL_ALIGN:
			for (int i = 0; i < appendNum / 2; i++) {
				ret.append(append);
			}
			ret.append(srcString);
			for (int i = 0; i < appendNum / 2; i++) {
				ret.append(append);
			}
			if (ret.length() < len) {
				ret.append(append);
			}
			break;
		default:
		case LEFT_ALIGN:
			for (int i = 0; i < appendNum; i++) {
				ret.append(append);
			}
			ret.append(srcString);
			break;
		}
		return ret.toString();
	}
}
