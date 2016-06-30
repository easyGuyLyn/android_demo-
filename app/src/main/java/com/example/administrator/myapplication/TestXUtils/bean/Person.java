package com.example.administrator.myapplication.TestXUtils.bean;

public class Person {
	private String imgUrl;
	private String name;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [imgUrl=" + imgUrl + ", name=" + name + "]";
	}
}
