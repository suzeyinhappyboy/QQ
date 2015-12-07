package com.fzu.qq2016;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLoginButton;
	private Button mRegisterButton;
	private EditText mAccount;
	private EditText mPassword;;
	
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private String jsonData;
	private String message;
	private int success;
	public static String user_name;
	public static String BaseURL = "http://192.168.253.1/myqq/";
	private static String url_register = BaseURL +"register.php";
	private static String url_login = BaseURL +"login.php";
//	private NetService mNetService = NetService.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initViews();
		initEvents();
	}

	protected void initViews() {
		// TODO Auto-generated method stub
		rl_user = (RelativeLayout) findViewById(R.id.rl_user);
		mLoginButton = (Button) findViewById(R.id.login);
		mRegisterButton = (Button) findViewById(R.id.register);
		mAccount = (EditText) findViewById(R.id.account);
		mPassword = (EditText) findViewById(R.id.password);
		mAccount.requestFocus();

	}

	protected void initEvents() {
		// TODO Auto-generated method stub
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		
		mRegisterButton.setOnClickListener(registerOnClickListener);
		mLoginButton.setOnClickListener(loginOnClickListener);

	}
	
	private OnClickListener registerOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mAccount.getText().toString().equals("") || mPassword.getText().toString().equals("")) {
				Toast toast = Toast.makeText(getApplicationContext(), " 请输入账号密码?", Toast.LENGTH_SHORT);
				toast.show();
			}else {
				new Register().execute();
			}
		}
	};
	
	private OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mAccount.getText().toString().equals("") || mPassword.getText().toString().equals("")) {
				Toast toast = Toast.makeText(getApplicationContext(), " 请输入账号密码", Toast.LENGTH_SHORT);
				toast.show();
			}else {
				new Login().execute();
			}
		}
	};
	
	class Register extends AsyncTask<String, String, String>{
		protected void  onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("正在注册。。。");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", mAccount.getText().toString()));
			params.add(new BasicNameValuePair("user_passwd", mPassword.getText().toString()));
			try {
				jsonData = jsonParser.makeHttpRequest(url_register, "POST", params);
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
			pDialog.dismiss();
			String str = "" +success;
			Toast toast = Toast.makeText(getApplicationContext(), "返回码=" + str+ ":" + message	, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	class Login extends AsyncTask<String, String, String>{

		protected void  onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("正在登录。。。");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", mAccount.getText().toString()));
			params.add(new BasicNameValuePair("user_passwd", mPassword.getText().toString()));
			try {
				jsonData = jsonParser.makeHttpRequest(url_login, "POST", params);
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
			pDialog.dismiss();
			String str = "" +success;
			Toast toast = Toast.makeText(getApplicationContext(), "返回码=" + str+ ":" + message	, Toast.LENGTH_LONG);
			toast.show();
			if (success == 1) {
				user_name = mAccount.getText().toString();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	}


}
