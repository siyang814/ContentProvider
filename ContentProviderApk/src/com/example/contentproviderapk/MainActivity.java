package com.example.contentproviderapk;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		displayRecords();
	}

	 private void displayRecords() {
	        String columns[] = new String[] { MyUsers.User._ID,
	                MyUsers.User.USER_NAME };
	        Uri myUri = MyUsers.User.CONTENT_URI;
	        Cursor cur = managedQuery(myUri, columns, null, null, null);
//	        Cursor cur = managedQuery(myUri, columns, MyUsers.User._ID + " = 1", null, null);
//	        Cursor cur = managedQuery(myUri, columns, MyUsers.User.USER_NAME + " = YangYuLin", null, null);
//	        Cursor cur = getContentResolver().query(myUri, columns, MyUsers.User._ID + " = ? AND " + MyUsers.User.USER_NAME + " = ?", new String[]{"4", "YangYuLin"}, null);
	        if (cur.moveToFirst()) {
	            String id = null;
	            String userName = null;
	            String result = "";
	            do {
	                id = cur.getString(cur.getColumnIndex(MyUsers.User._ID));
	                userName = cur.getString(cur
	                        .getColumnIndex(MyUsers.User.USER_NAME));
//	                Toast.makeText(this, id + " : " + userName, Toast.LENGTH_LONG)
//	                        .show();
	                result += "ID =" + id + " : userName =" + userName + "\n";
	                tv.setText(result);
	                Log.w("Test", id + " : " + userName);
	            } while (cur.moveToNext());
	        }
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
