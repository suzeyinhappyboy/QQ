package com.fzu.qq2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements OnClickListener{

	private Button mBtnSend;
	private Button mBtnBack;
	private TextView mTextView;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	JSONParser jsonParser = new JSONParser();
	private String jsonData;
	private String message;
	private String tempMessage;
	private int success;
	private String far_user_name;
	private String send_content;
	private static String url_sendmsg = LoginActivity.BaseURL +"sendmsg.php";
	private static String url_getmsg = LoginActivity.BaseURL +"getmsg.php";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initView();
		initData();
		Thread thread = new Thread(myRun);
		thread.start();
	}
	public void initView() {
		mTextView = (TextView) findViewById(R.id.chat_name);
		mTextView.setText(LoginActivity.user_name);
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.chat_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.chat_back);
		mBtnBack.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.chat_editmessage);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.chat_send:
			sendMsg();
			break;
		case R.id.chat_back:
			finish();
			break;	
		default:
			break;
		}
	}
	
	private void sendMsg() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			send_content = contString;
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName(LoginActivity.user_name);
			entity.setMsgType(false);
			entity.setText(contString);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() -1);
			new SendMsg().execute();
		}
		
	}
	
	private void getMsg() {
		new GetMsg().execute();
		
	}
	
	private String[] msgArray = new String[]{"你好帅啊！我好喜欢你啊"};
	
	public void initData() {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setName("美女");
		entity.setMsgType(true);
		entity.setText(msgArray[0]);
		mDataArrays.add(entity);
		mAdapter = new ChatMsgViewAdapter(this,mDataArrays); 
		mListView.setAdapter(mAdapter);
	}
	
	class SendMsg extends AsyncTask<String, String, String>{
		protected void  onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("send_content", send_content));
			try {
				jsonData = jsonParser.makeHttpRequest(url_sendmsg, "POST", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				message = jsonObject.getString("message");
				success = jsonObject.getInt("success");
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data" + e.toString());
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			String str = "" +success;
			Toast toast = Toast.makeText(getApplicationContext(), "返回码=" + str+ ":" + message	, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	class GetMsg extends AsyncTask<String, String, String>{

		protected void  onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", LoginActivity.user_name));			
			try {
				jsonData = jsonParser.makeHttpRequest(url_getmsg, "POST", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				JSONObject jsonObject = new JSONObject(jsonData);
				message = jsonObject.getString("message");
				success = jsonObject.getInt("success");
				far_user_name = jsonObject.getString("user_name");
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data" + e.toString());
			}
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			String str = "" +success;
			Toast toast = Toast.makeText(getApplicationContext(), "返回码=" + str+ ":" + message	, Toast.LENGTH_LONG);
			toast.show();
			Log.d("tempmessage", tempMessage);
			Log.d("message=", message);
			if (success == 1 && tempMessage.equals(message) == false) {
				tempMessage = message;
				String contString = message;
				if (contString.length() > 0) {
					ChatMsgEntity entity = new ChatMsgEntity();
					entity.setName(far_user_name);
					entity.setMsgType(true);
					entity.setText(contString);
					
					mDataArrays.add(entity);
					mAdapter.notifyDataSetChanged();
					mEditTextContent.setText("");
					mListView.setSelection(mListView.getCount() - 1);
				}
			}
		}
		
	}
	
	Runnable myRun= new Runnable() {
		
		@Override
		public void run() {
			
			while (true) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			//	new GetMsg().execute();
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

}
