package com.hz.trexam.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * 
 * @author hz
 * �Զ���TextView , �����н���
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
	 * α��һ���н����textView,����ʲô���������true
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		
		return true;
	}
	
	
}
