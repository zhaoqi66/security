package com.zhaoqi.security.core.properties;

public class ImageCodeProperties {

    private int width = 65;
    private int height = 23;
    private int length = 4;
    private int ezpireIn = 60;
    
    private String url;
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getEzpireIn() {
		return ezpireIn;
	}
	public void setEzpireIn(int ezpireIn) {
		this.ezpireIn = ezpireIn;
	}
    
    
    
}
