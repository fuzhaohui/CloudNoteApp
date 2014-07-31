/**
 * Copyright (c) 2008-2013 	Copyright © 2013 AutoNavi.All rights reserved.  
 * CloudNoteApp - 2013-6-3
 * 
 * 相关描述： 
 * 
 */
package com.ces.cloudnote.app.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * 相关描述：
 *
 * 文件名：FileUtils.java
 * 作者： AutoNavi 
 * 完成时间：2013-6-3 下午2:17:24 
 * 维护人员：AutoNavi  
 * 维护时间：2013-6-3 下午2:17:24 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public class FileUtils {
	
	
	/**
	 * 方法描述：保存内部存储时通过缓存文件夹存储
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2013-6-3 下午2:20:11
	 * 维护人员： AutoNavi
	 * 维护时间： 2013-6-3 下午2:20:11
	 * 维护原因: 
	 * 当前版本： v3.0 
	 * @param context
	 * @param url
	 * @return
	 */
	public File getTempFile(Context context, String url) {
		File file = null;
		try {
			String fileName = Uri.parse(url).getLastPathSegment();
			file = File.createTempFile(fileName, null, context.getCacheDir());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	/**
	 * 方法描述： 判断SD卡是不挂载
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2013-6-3 下午1:48:49
	 * 维护人员： AutoNavi
	 * 维护时间： 2013-6-3 下午1:48:49
	 * 维护原因: 
	 * 当前版本： v3.0 
	 * @return
	 */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 方法描述：保存公共文件夹 （公共文件夹当App卸载时，文件不会被删除）
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2013-6-3 下午1:57:59
	 * 维护人员： AutoNavi
	 * 维护时间： 2013-6-3 下午1:57:59
	 * 维护原因: 
	 * 当前版本： v3.0 
	 * @param albumName
	 * @return
	 */
	public File getAlbumStorageDir(String albumName) {
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
		if(!file.mkdirs()) {
			Log.e("tag", "Directory not created");
		}
		return file;
	}
	
	/**
	 * 方法描述：保存App私有文件夹（App卸载时，文件会被删除）
	 *
	 * 作者： AutoNavi
	 * 完成时间： 2013-6-3 下午2:02:50
	 * 维护人员： AutoNavi
	 * 维护时间： 2013-6-3 下午2:02:50
	 * 维护原因: 
	 * 当前版本： v3.0 
	 * @param context
	 * @param albumName
	 * @return
	 */
	public File getAlbumStorageDir(Context context, String albumName) {
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName) ;
		if(!file.mkdirs()) {
			Log.e("tag", "Directory not created");
		}
		return file;
	}
}
