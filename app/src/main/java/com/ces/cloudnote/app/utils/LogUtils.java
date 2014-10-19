package com.ces.cloudnote.app.utils;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 自己写的Log打印类，自动输出方法和行数
 * @author Yufei.Huang
 */
public class LogUtils {
	private static boolean printLogFlag = true;
	private static boolean recordLogFlag = false;

	public static void setPrintLogFlag(boolean isPrintLog) {
		printLogFlag = isPrintLog;
	}

	public static boolean getPrintLogFlag() {
		return printLogFlag;
	}

	public static void setRecordLogFlag(boolean isWriteLog) {
		recordLogFlag = isWriteLog;
	}

	public static boolean getRecordLogFlag() {
		return recordLogFlag;
	}

	//	private static String MYLOG_PATH_SDCARD_DIR = Environment
	//			.getExternalStorageDirectory().getAbsolutePath()
	//			+ "/LogUtil"
	//			+ "/Log";
	//	private static boolean isWriteToSDCard = false;
	//	private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
	//			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	//	private static SimpleDateFormat logfileFormat = new SimpleDateFormat(
	//			"yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
	//	private static String logfileName = "Log.txt";

	private static String classname;
	private static ArrayList<String> methods;
	static {
		classname = LogUtils.class.getName();
		methods = new ArrayList<String>();

		Method[] ms = LogUtils.class.getDeclaredMethods();
		for (Method m : ms) {
			methods.add(m.getName());
		}
	}

	public static void v(String tag, String msg) {
		if (printLogFlag) {
			Log.v(tag, getMsgWithLineNumber(msg));
		}
	}

	public static void d(String tag, String msg) {
		if (printLogFlag) {
			Log.d(tag, getMsgWithLineNumber(msg));
		}
	}

	public static void i(String tag, String msg) {
		if (printLogFlag) {
			Log.i(tag, getMsgWithLineNumber(msg));
		}
	}

	public static void w(String tag, String msg) {
		if (printLogFlag) {
			Log.w(tag, getMsgWithLineNumber(msg));
		}
	}

	public static void e(String tag, String msg) {
		if (printLogFlag) {
			Log.e(tag, getMsgWithLineNumber(msg));
		}
	}

	public static void v(String msg) {
		if (printLogFlag) {
			String[] content = getMsgAndTagWithLineNumber(msg);
			Log.v(content[0], content[1]);
		}
	}

	public static void d(String msg) {
		if (printLogFlag) {
			String[] content = getMsgAndTagWithLineNumber(msg);
			Log.d(content[0], content[1]);
		}
	}

	public static void i(String msg) {
		if (printLogFlag) {
			String[] content = getMsgAndTagWithLineNumber(msg);
            Log.i(content[0], content[1]);
		}
	}

	public static void w(String msg) {
		if (printLogFlag) {
			String[] content = getMsgAndTagWithLineNumber(msg);
            Log.w(content[0], content[1]);
		}
	}

	public static void e(String msg) {
		if (printLogFlag) {
			String[] content = getMsgAndTagWithLineNumber(msg);
            Log.e(content[0], content[1]);
		}
	}

	public static String getMsgWithLineNumber(String msg) {
		try {
			for (StackTraceElement st : (new Throwable()).getStackTrace()) {
				if (classname.equals(st.getClassName())
						|| methods.contains(st.getMethodName())) {
					continue;
				} else {
					int b = st.getClassName().lastIndexOf(".") + 1;
					String TAG = st.getClassName().substring(b);
					String message = TAG + "->" + st.getMethodName() + "():"
							+ st.getLineNumber() + "->" + msg;
					return message;
				}
			}
		} catch (Exception e) {

		}
		return msg;
	}

	public static String[] getMsgAndTagWithLineNumber(String msg) {
		try {
			for (StackTraceElement st : (new Throwable()).getStackTrace()) {
				if (classname.equals(st.getClassName())
						|| methods.contains(st.getMethodName())) {
					continue;
				} else {
					int b = st.getClassName().lastIndexOf(".") + 1;
					String TAG = st.getClassName().substring(b);
					String message = st.getMethodName() + "():"
							+ st.getLineNumber() + "->" + msg;
					String[] content = new String[] { TAG, message };
					return content;
				}

			}
		} catch (Exception e) {

		}
		return new String[] { "universal tag", msg };
	}
}
