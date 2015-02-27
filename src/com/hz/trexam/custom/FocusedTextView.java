package com.hz.trexam.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * 
 * @author hz
 * 自定义TextView , 天生有焦点
 *
 */

public class FocusedTextView extends TextView {

	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FocusedTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 伪造一个有焦点的textView,无论什么情况都返回true
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		
		return true;
	}
	
	
}
