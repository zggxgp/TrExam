package com.hz.trexam.bean;

import java.io.Serializable;

public class Exam implements Serializable{
	String answer;
	String answerDesc;
	String id;
	String imgName;
	String option;
	String question;
	String type;
	
	
	
	@Override
	public String toString() {
		return "Exam [answer=" + answer + ", answerDesc=" + answerDesc
				+ ", id=" + id + ", imgName=" + imgName + ", option=" + option
				+ ", question=" + question + ", type=" + type + "]";
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswerDesc() {
		return answerDesc;
	}
	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
