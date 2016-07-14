package com.kevin.shadowview;

import java.io.Serializable;

public class Shadow implements Serializable {

	private static final long serialVersionUID = -6002037371501250647L;
	
	private float radius;
	private float dx;
	private float dy; 
	private int color;
	
	public Shadow(float radius, float dx, float dy, int color) {
		super();
		this.radius = radius;
		this.dx = dx;
		this.dy = dy;
		this.color = color;
	}
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Shadow{" +
				"radius=" + radius +
				", dx=" + dx +
				", dy=" + dy +
				", color=" + color +
				'}';
	}
}
