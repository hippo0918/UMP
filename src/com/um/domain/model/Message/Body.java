package com.um.domain.model.Message;

public class Body {

	private BaseInfo BaseInfo;
	private ReqInfo ReqInfo;
	private ExtendInfo ExtendInfo;
	
	private String Plaintext;// 明文（原始报文）
	private String SignInfo;// 签名信息（对明文的签名信息）
	private String EncryptInfo;// 密文（对明文和签名信息加密后的报文）

	public Body() {
		super();
	}

	public Body(BaseInfo baseInfo,ReqInfo reqInfo,ExtendInfo extendInfo) {
		super();
		BaseInfo = baseInfo;
		ReqInfo = reqInfo;
		ExtendInfo = extendInfo;
	}

	public BaseInfo getBaseInfo() {
		return BaseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		BaseInfo = baseInfo;
	}

	public ReqInfo getReqInfo() {
		return ReqInfo;
	}

	public void setReqInfo(ReqInfo reqInfo) {
		ReqInfo = reqInfo;
	}

	public ExtendInfo getExtendInfo() {
		return ExtendInfo;
	}

	public void setExtendInfo(ExtendInfo extendInfo) {
		ExtendInfo = extendInfo;
	}

	public String getPlaintext() {
		return Plaintext;
	}

	public void setPlaintext(String plaintext) {
		Plaintext = plaintext;
	}

	public String getSignInfo() {
		return SignInfo;
	}

	public void setSignInfo(String signInfo) {
		SignInfo = signInfo;
	}

	public String getEncryptInfo() {
		return EncryptInfo;
	}

	public void setEncryptInfo(String encryptInfo) {
		EncryptInfo = encryptInfo;
	}

}
