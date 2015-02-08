package com.hz.trexam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hz.trexam.custom.CustomSimpleAdaper;

public class MainActivity extends Activity {
	
	private ListView mainList;//���˵������б�
	private List<Map<String,Object>> mainDataList;//������˵�listview������Դ
	private CustomSimpleAdaper mainListSimpleAdapter;
	private ImageButton shareBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		
		//���÷���ť
		shareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_SEND); 
				intent.setType("image/*");    
				intent.putExtra(Intent.EXTRA_SUBJECT, "Share");    
	            intent.putExtra(Intent.EXTRA_TEXT, "�ҵ�app");
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
                startActivity(Intent.createChooser(intent, getTitle()));   
			}
		});
		
		//���˵������б�Adapter����
		mainListSimpleAdapter = new CustomSimpleAdaper(this, getData(), R.layout.main_list_item
																 ,new String[]{"img", "title", "content"}
																 ,new int[]{R.id.item_icon, R.id.item_title, R.id.item_content});
		mainList.setAdapter(mainListSimpleAdapter);
		
		//�������˵�item����¼�
		mainList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			if(position==0){
				Intent intent = new Intent(MainActivity.this, SelfLearningActivity.class);
				startActivity(intent);
			}
			
			if(position==1){
				Intent intent = new Intent(MainActivity.this, TestAcivity.class);
				startActivity(intent);
			}
			
			if(position==2){
				Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
				startActivity(intent);
			}
			
			if(position==3){
				Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
				startActivity(intent);
			}
				
			}
			
		});
	}
	
	private void init(){
		mainList = (ListView)findViewById(R.id.main_list);
		shareBtn = (ImageButton)findViewById(R.id.top_share_btn);
	}
	
	//�������˵�listview����Դ
	private List<Map<String,Object>> getData(){
		 mainDataList = new ArrayList<Map<String, Object>>();
		 Map<String, Object> mapData = new HashMap<String, Object>();
		 mapData.put("img", R.drawable.selflearning);
		 mapData.put("title",this.getString(R.string.selflearning_title));
		 mapData.put("content", this.getString(R.string.selflearning_content));
		 mainDataList.add(mapData);
		 
		 mapData = new HashMap<String, Object>();
		 mapData.put("img", R.drawable.test);
		 mapData.put("title",this.getString(R.string.test_title));
		 mapData.put("content", this.getString(R.string.test_content));
		 mainDataList.add(mapData);
		 
		 mapData = new HashMap<String, Object>();
		 mapData.put("img", R.drawable.error);
		 mapData.put("title",this.getString(R.string.error_title));
		 mapData.put("content", this.getString(R.string.error_content));
		 mainDataList.add(mapData);
		 
		 mapData = new HashMap<String, Object>();
		 mapData.put("img", R.drawable.favorite);
		 mapData.put("title",this.getString(R.string.favorite_title));
		 mapData.put("content", this.getString(R.string.favorite_content));
		 mainDataList.add(mapData);
		 
		 return mainDataList;
	}
}
