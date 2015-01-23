package com.hz.trexam.custom;

import java.util.List;
import java.util.Map;

import com.hz.trexam.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

/**
 * 
 * @author hz
 * 定制simpleadapter， 重写getView方法，改变不同item的背景色
 *
 */

public class CustomSimpleAdaper extends SimpleAdapter {

	public CustomSimpleAdaper(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) 
	{
		super(context, data, resource, from, to);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		
		if(position==0){
			LinearLayout itemLayout = (LinearLayout)v.findViewById(R.id.item_parent);
			
			itemLayout.setBackgroundResource(R.drawable.btn_green);
		}
		
		if(position==1){
			LinearLayout itemLayout = (LinearLayout)v.findViewById(R.id.item_parent);
			
			itemLayout.setBackgroundResource(R.drawable.btn_blue);
		}
		if(position==2){
			LinearLayout itemLayout = (LinearLayout)v.findViewById(R.id.item_parent);
			itemLayout.setBackgroundResource(R.drawable.btn_red);
		}
		
		if(position==3){
			LinearLayout itemLayout = (LinearLayout)v.findViewById(R.id.item_parent);
			itemLayout.setBackgroundResource(R.drawable.btn_purple);
		}
		
		return v;
	}
	
	

}
