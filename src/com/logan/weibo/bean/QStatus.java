package com.logan.weibo.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.logan.util.TimeUtil;
import com.weibo.net.WeiboException;


public class QStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private final static String TAG = "QStatus";

	public String id = "";
	public String head = "";
	private String nick = "";
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String origText = "";
	// String text = "";// 适用于转发微博
	private String image = "";// 微博图片
	private String from = "";
	private int isVip = 0;
	private String mcount = "";
	private String count = "";
	private String created_at = "";
	// --转发微博---
	private JSONObject source = null;
	// String source_nick = "";
	private String source_text = "";// 微博文字信息，包含URL、昵称、是否认证的信息
	private String source_image = "";
	private Boolean isVisible = true;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOrigText() {
		return origText;
	}

	public void setOrigText(String origText) {
		this.origText = origText;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public String getMcount() {
		return mcount;
	}

	public void setMcount(String mcount) {
		this.mcount = mcount;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public JSONObject getSource() {
		return source;
	}

	public void setSource(JSONObject source) {
		this.source = source;
	}

	public String getSource_text() {
		return source_text;
	}

	public void setSource_text(String source_text) {
		this.source_text = source_text;
	}

	public String getSource_image() {
		return source_image;
	}

	public void setSource_image(String source_image) {
		this.source_image = source_image;
	}

	private QStatus()  {
		//TODO sth
	}

	public static QStatus getQStatus(JSONObject json) throws JSONException {
		QStatus mQStatus = new QStatus();
		mQStatus.head = json.getString("head") + "/100";
		mQStatus.head = json.getString("id");
		mQStatus.nick = json.getString("nick");
		mQStatus.origText = json.getString("origtext");
		
		JSONArray imageArray = json.optJSONArray("image");// 如果此微博有图片内容，就显示出来
		if (imageArray != null && imageArray.length() > 0) mQStatus.image = imageArray.optString(0) + "/160";
		// /120 /160 /460 /2000返回相应大小的图片
			
		mQStatus.from = json.getString("from");// 不是超链接的数据，纯文本
		mQStatus.isVip = json.getInt("isvip");
		mQStatus.mcount = json.getString("mcount");
		mQStatus.count = json.getString("count");
		mQStatus.created_at = TimeUtil.converTime(Long.parseLong(json.getString("timestamp")));

		// --------------获取转发微博的信息----包括图片、文字信息，不包含视频、音频信息------------------------
		if (!json.isNull("source")) {
			mQStatus.source = json.getJSONObject("source");
			Log.v(TAG, "sourcel:  " + mQStatus.source);

			if (!mQStatus.source.isNull("image")) {
				JSONArray images = mQStatus.source.getJSONArray("image");
				if (images != null && images.length() > 0) {
					mQStatus.source_image = images.optString(0) + "/120";
					Log.v(TAG, "source_image_url:  " + mQStatus.source_image);
				}
			}
			if (!mQStatus.source.isNull("nick")) {
				String source_nick = mQStatus.source.getString("nick");
				Log.v(TAG, "source_nick:  " + source_nick);

				if (!mQStatus.source.isNull("origtext")) {
					mQStatus.source_text = source_nick + ":  " + mQStatus.source.getString("origtext");
					Log.v(TAG, "source_origtext:  " + mQStatus.source_text);
				}
			}
		}
		else mQStatus.isVisible = false;
		

		Log.v(TAG, "id:  " + mQStatus.id);
		Log.v(TAG, "head:  " + mQStatus.head);
		Log.v(TAG, "nick: " + mQStatus.nick);
		Log.v(TAG, "origText:  " + mQStatus.origText);
		Log.v(TAG, "image:  " +mQStatus.image);
		Log.v(TAG, "from:  " +mQStatus.from);
		Log.v(TAG, "isVip:  " + mQStatus.isVip);
		Log.v(TAG, "mcount:  " + mQStatus.mcount);
		Log.v(TAG, "count:  " +mQStatus.count);
		
		return mQStatus;
	}
	@Override
	public String toString(){
		
		return "id:"+id+", nick:"+nick+", text:"+origText+", from:"+from+", created_at:"+created_at;
		
	}
	
}
