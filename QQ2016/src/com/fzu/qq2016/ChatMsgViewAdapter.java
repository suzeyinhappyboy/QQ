package com.fzu.qq2016;

import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMsgViewAdapter extends BaseAdapter {
	public static interface IMsgViewType{
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	
	private List<ChatMsgEntity> coll;
	private Context ctx;
	private LayoutInflater mInflater;
	
	public ChatMsgViewAdapter(Context context,List<ChatMsgEntity> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return coll.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return coll.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}
	
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);
		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
			
		}else {
			return IMsgViewType.IMVT_TO_MSG;
		}		
	}
	
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public View getView(int position, View converView, ViewGroup parent) {

		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();
		ViewHolder viewHolder = null;
		if (converView == null) {
			if (isComMsg) {
				converView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);	
			}else {
				converView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.tvUserName=(TextView) converView.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) converView.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg=isComMsg;
			converView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) converView.getTag();
		}
		viewHolder.tvUserName.setText(entity.getName());
		viewHolder.tvContent.setText(entity.getText());
		return converView;
	}
		
	static class ViewHolder{
		public TextView tvUserName;
		public TextView tvContent;
		public boolean isComMsg =true;
	}
	
	
}
