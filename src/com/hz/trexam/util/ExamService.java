package com.hz.trexam.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.hz.trexam.bean.Exam;

public class ExamService {
	
	public static List<Exam> getExamList(InputStream is) throws Exception{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		List<Exam> examList = null;
		Exam exam = null;
		int type = parser.getEventType();
		while(type != XmlPullParser.END_DOCUMENT){
			switch(type){
				case XmlPullParser.START_TAG:
					if("root".equals(parser.getName())){
						examList = new ArrayList<Exam>();
						
					}
					else if("exam".equals(parser.getName())){
						exam = new Exam();
						
						exam.setAnswer(parser.getAttributeValue(0));
						exam.setAnswerDesc(parser.getAttributeValue(1));
						exam.setId(parser.getAttributeValue(2));
						exam.setImgName(parser.getAttributeValue(3));
						exam.setOption(parser.getAttributeValue(4));
						exam.setQuestion(parser.getAttributeValue(5));
						exam.setType(parser.getAttributeValue(6));
					}
					
					break;
					
				case XmlPullParser.END_TAG:
					if("exam".equals(parser.getName())){
						examList.add(exam);
						exam = null;
					}
						
						break;
					}
				type = parser.next();
			}
			
			return examList;
		}
	
		
	}

