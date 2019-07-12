package com.azad.trips.model;

public class Response implements Comparable<Response>{

	private String text;
	private String value;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean hasMatchingValue(String value) {
		return this.value.equals(value);
	}
	
	public Boolean hasMatchingText(String text) {
		return this.text.equals(text);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [text=");
		builder.append(text);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Response o) {
		return this.value.compareTo(o.getValue());
	}
}
