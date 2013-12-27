package com.um.domain.model.Message;

public class YJTMessage {

	private Head Head;
	private Body Body;

	public YJTMessage() {
		super();
	}

	public YJTMessage(Head head, Body body) {
		super();
		this.Head = head;
		this.Body = body;
	}

	public Head getHead() {
		return Head;
	}

	public void setHead(Head head) {
		this.Head = head;
	}

	public Body getBody() {
		return Body;
	}

	public void setBody(Body body) {
		this.Body = body;
	}

}
