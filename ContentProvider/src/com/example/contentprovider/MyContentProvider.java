package com.example.contentprovider;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;

public class MyContentProvider extends ContentProvider
{

	//定义一个SQLiteDatabase变量
    private SQLiteDatabase sqlDB;
    //定义一个DatabaseHelper变量
    private DatabaseHelper dbHelper;
    //数据库名
    private static final String DATABASE_NAME = "Users.db";
    //数据库版本
    private static final int DATABASE_VERSION = 1;
    //表名
    private static final String TABLE_NAME = "User";
    //标签
    private static final String TAG = "MyContentProvider";

    /*
     * 定义一个内部类
     * 
     * 这个内部类继承SQLiteOpenHelper类，重写其方法
     */
    public static class DatabaseHelper extends SQLiteOpenHelper {

        //构造方法
        public DatabaseHelper(Context context) {
            //父类构造方法
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //当第一次创建数据库的时候调用该方法，可以为数据库增加一些表，和初始化一些数据
        @Override
        public void onCreate(SQLiteDatabase db) {
            //在数据库里生成一张表
            db.execSQL("Create table "
                    + TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT);");
        }

        //当更新数据库版本的时候，调用该方法。可以删除，修改表的一些信息
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        }

    }


	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		
		sqlDB = dbHelper.getWritableDatabase();
		int rowId = sqlDB.delete(TABLE_NAME, selection, selectionArgs);
		
		return rowId;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		// TODO Auto-generated method stub
		sqlDB = dbHelper.getWritableDatabase();
		long rowId = sqlDB.insert(TABLE_NAME, "", values);
		if ( rowId > 0 )
		{
			Uri rowUri = ContentUris.appendId( MyUsers.User.CONTENT_URI.buildUpon(), rowId).build();
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into" + uri);
	}

	@Override
	public boolean onCreate()
	{
		// TODO Auto-generated method stub
		dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder)
	{
		String path = null;
		List<String> list = uri.getPathSegments();
		if ( list != null || list.size() != 0 ) path = list.get(0);
		if ( path.equals(MyUsers.User.SINGLE))
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(getContext(), MainActivity.class);
			getContext().startActivity(intent);
			
		}
		// TODO Auto-generated method stub
		Log.w("MYUTIL", " uri.getAuthority() = " + uri.getAuthority());
		Log.w("MYUTIL", "uri.getLastPathSegment() = " + uri.getLastPathSegment());
		Log.w("MYUTIL", "uri.getPath() = " + uri.getPath());
		List<String> data = uri.getPathSegments();
		for ( int i = 0; i < data.size(); i++ )
		{
			System.out.println("data ="+data.get(i));
		}
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		qb.setTables(TABLE_NAME);
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		sqlDB = dbHelper.getWritableDatabase();
		int count = sqlDB.update(TABLE_NAME, values, selection, selectionArgs);
		return count;
	}

}
