package com.example.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	private Button button1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        insertRecord("MyUser");
//        insertRecord("YangYuLin");
//        displayRecords();
        init();
    }
    
    private void init ()
    {
    	tv = (TextView) findViewById(R.id.tv);
    	button1 = (Button) findViewById(R.id.button1);
    	button1.setOnClickListener( new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				updataRecord("ab1");
				deleteElment();
				displayRecords();
			}
		});
//    	Intent intent = getIntent();
//    	if ( intent.getAction() != null || intent.getAction().equals("MY.TEST.ACTION.CALL"))
//    	{
//    		Uri uri = intent.getData();
//    		StringBuffer buffer = new StringBuffer();
//    		buffer.append(uri+"\n");
//    		buffer.append(uri.getQueryParameter("phone")+"\n");
//    		buffer.append(uri.getQueryParameter("card")+"\n");
//    		buffer.append(uri.getQueryParameter("name")+"\n");
//    		buffer.append(uri.getQueryParameter("gender")+"\n");
//    		tv.setText(buffer.toString());
//    	}
//    	else
//    	{
//    		tv.setText("没数据~~~");
//    	}
    }
    
    private void insertRecord(String userName) {
        ContentValues values = new ContentValues();
        values.put(MyUsers.User.USER_NAME, userName);
        getContentResolver().insert(MyUsers.User.CONTENT_URI, values);
    }
    
    private void updataRecord ( String Name )
    {
    	ContentValues values = new ContentValues();
    	values.put(MyUsers.User.USER_NAME, Name);
//    	int i = getContentResolver().update(MyUsers.User.CONTENT_URI, values, MyUsers.User._ID + " = ?", new String[]{"3"});
    	int i = getContentResolver().update(MyUsers.User.CONTENT_URI, values, MyUsers.User.USER_NAME + " = ?", new String[]{"abcdefg"});
    	Log.w("MYUTIL", "==============="+i);
    }

    private void displayRecords() {
        String columns[] = new String[] { MyUsers.User._ID,
                MyUsers.User.USER_NAME };
        Uri myUri = MyUsers.User.CONTENT_URI;
        Cursor cur = managedQuery(myUri, columns, null, null, null);
//        Cursor cur = managedQuery(myUri, columns, MyUsers.User._ID + " = 1", null, null);
//        Cursor cur = managedQuery(myUri, columns, MyUsers.User.USER_NAME + " = YangYuLin", null, null);
//        Cursor cur = getContentResolver().query(myUri, columns, MyUsers.User._ID + " = ? AND " + MyUsers.User.USER_NAME + " = ?", new String[]{"4", "YangYuLin"}, null);
        if (cur.moveToFirst()) {
            String id = null;
            String userName = null;
            String result = "";
            do {
                id = cur.getString(cur.getColumnIndex(MyUsers.User._ID));
                userName = cur.getString(cur
                        .getColumnIndex(MyUsers.User.USER_NAME));
//                Toast.makeText(this, id + " : " + userName, Toast.LENGTH_LONG)
//                        .show();
                result += "ID =" + id + " : userName =" + userName + "\n";
                tv.setText(result);
                Log.w("Test", id + " : " + userName);
            } while (cur.moveToNext());
        }
    }


    private void deleteElment ()
    {
    	String columns[] = new String[] { MyUsers.User._ID,
                MyUsers.User.USER_NAME };
        Uri myUri = MyUsers.User.CONTENT_URI;
//        getContentResolver().delete(myUri, MyUsers.User._ID + " = ?", new String[]{"1"});
        getContentResolver().delete(myUri, MyUsers.User._ID + " = ? AND " + MyUsers.User.USER_NAME + " = ?", new String[]{"5", "MyUser"});
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
