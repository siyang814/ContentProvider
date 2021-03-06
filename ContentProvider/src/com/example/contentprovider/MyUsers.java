package com.example.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyUsers
{
	public static final String AUTHORITY = "com.yyl.android.MyContentProvider";
	public static final String PATH = "PATH";
	//BaseColumn类中已经包含了_id字段
    public static final class User implements BaseColumns
    {
    	public static final String SINGLE = "SINGLE";
        //定义Uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
        //定义数据表列
        public static final String USER_NAME = "USER_NAME";
    }
}
