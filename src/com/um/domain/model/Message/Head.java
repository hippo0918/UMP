package com.um.domain.model.Message;

public class Head {

	private String MessageId;
	private String FromNodeType;
	private String FromNode;
	private String ToNodeType;
	private String ToNode;
	private String TargetId;
	private String SendDate;
	private String SendTime;

	public Head() {
		super();
	}

	public Head(String messageId, String fromNodeType, String fromNode,
			String toNodeType, String toNode, String targetId, String sendDate,
			String sendTime) {
		super();
		MessageId = messageId;
		FromNodeType = fromNodeType;
		FromNode = fromNode;
		ToNodeType = toNodeType;
		ToNode = toNode;
		TargetId = targetId;
		SendDate = sendDate;
		SendTime = sendTime;
	}

	public String getMessageId() {
		return MessageId;
	}

	public void setMessageId(String messageId) {
		MessageId = messageId;
	}

	public String getFromNodeType() {
		return FromNodeType;
	}

	public void setFromNodeType(String fromNodeType) {
		FromNodeType = fromNodeType;
	}

	public String getFromNode() {
		return FromNode;
	}

	public void setFromNode(String fromNode) {
		FromNode = fromNode;
	}

	public String getToNodeType() {
		return ToNodeType;
	}

	public void setToNodeType(String toNodeType) {
		ToNodeType = toNodeType;
	}

	public String getToNode() {
		return ToNode;
	}

	public void setToNode(String toNode) {
		ToNode = toNode;
	}

	public String getTargetId() {
		return TargetId;
	}

	public void setTargetId(String targetId) {
		TargetId = targetId;
	}

	public String getSendDate() {
		return SendDate;
	}

	public void setSendDate(String sendDate) {
		SendDate = sendDate;
	}

	public String getSendTime() {
		return SendTime;
	}

	public void setSendTime(String sendTime) {
		SendTime = sendTime;
	}

}
