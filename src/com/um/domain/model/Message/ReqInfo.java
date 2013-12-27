package com.um.domain.model.Message;

public class ReqInfo {

	private String EntrustDate;
	private String TxnType;
	private String OrgId;
	private String protocolActionType;
	private String UserNo;
	private String userName;
	private String contactName;
	private String contactAddress;
	private String contactAddressPost;
	private String contactPhone;
	private String OrigBank;
	private String OrigAcc;
	private String OutBank;
	private String OutAccBank;
	private String PayerAcc;
	private String PayerName;
	private String payerBank;
	private String payerCreditType;
	private String payerCreditNo;
	private String payerPhone;
	private String payerEmail;
	private String PayeeAcc;
	private String PayeeName;
	private String TranType;
	private String ContractId;
	private String PrivateFlag;
	private String Currency;
	private String Amout;
	private String Charge;
	private String Remark;
	private String ActivePayFlag;
	private String OPRSign;
	private String BulletinId;
	private String Title;
	private String Issuer;
	private String IssueDate;
	private String Content;
	private String HasAttachment;
	private String AttachmentName;
	private String Attachment;
	private String status;

	// ========================余额查询报文 start==============================
	private String AccNo;
	private String AccName;
	// private String Currency;
	private String Bank;
	private String Province;
	private String City;
	// ========================余额查询报文 end==============================
	// ========================明细查询报文 start==============================
	private String QryType;
	private String StartDate;
	private String EndDate;
	private String bifCode;
	private String bocbankcode;
	// ========================明细查询报文 end==============================

	public ReqInfo() {
		super();
	}

	public ReqInfo(String entrustDate, String txnType, String orgId,
			String protocolActionType, String userNo, String userName,
			String contactName, String contactAddress,
			String contactAddressPost, String contactPhone, String origBank,
			String origAcc, String outBank, String outAccBank, String payerAcc,
			String payerName, String payerBank, String payerCreditType,
			String payerCreditNo, String payerPhone, String payerEmail,
			String payeeAcc, String payeeName, String tranType,
			String contractId, String privateFlag, String currency,
			String amout, String charge, String remark, String activePayFlag,
			String oPRSign, String bulletinId, String title, String issuer,
			String issueDate, String content, String hasAttachment,
			String attachmentName, String attachment, String status) {
		super();
		EntrustDate = entrustDate;
		TxnType = txnType;
		OrgId = orgId;
		this.protocolActionType = protocolActionType;
		UserNo = userNo;
		this.userName = userName;
		this.contactName = contactName;
		this.contactAddress = contactAddress;
		this.contactAddressPost = contactAddressPost;
		this.contactPhone = contactPhone;
		OrigBank = origBank;
		OrigAcc = origAcc;
		OutBank = outBank;
		OutAccBank = outAccBank;
		PayerAcc = payerAcc;
		PayerName = payerName;
		this.payerBank = payerBank;
		this.payerCreditType = payerCreditType;
		this.payerCreditNo = payerCreditNo;
		this.payerPhone = payerPhone;
		this.payerEmail = payerEmail;
		PayeeAcc = payeeAcc;
		PayeeName = payeeName;
		TranType = tranType;
		ContractId = contractId;
		PrivateFlag = privateFlag;
		Currency = currency;
		Amout = amout;
		Charge = charge;
		Remark = remark;
		ActivePayFlag = activePayFlag;
		OPRSign = oPRSign;
		BulletinId = bulletinId;
		Title = title;
		Issuer = issuer;
		IssueDate = issueDate;
		Content = content;
		HasAttachment = hasAttachment;
		AttachmentName = attachmentName;
		Attachment = attachment;
		this.status = status;
	}

	public String getEntrustDate() {
		return EntrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		EntrustDate = entrustDate;
	}

	public String getTxnType() {
		return TxnType;
	}

	public void setTxnType(String txnType) {
		TxnType = txnType;
	}

	public String getOrgId() {
		return OrgId;
	}

	public void setOrgId(String orgId) {
		OrgId = orgId;
	}

	public String getProtocolActionType() {
		return protocolActionType;
	}

	public void setProtocolActionType(String protocolActionType) {
		this.protocolActionType = protocolActionType;
	}

	public String getUserNo() {
		return UserNo;
	}

	public void setUserNo(String userNo) {
		UserNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactAddressPost() {
		return contactAddressPost;
	}

	public void setContactAddressPost(String contactAddressPost) {
		this.contactAddressPost = contactAddressPost;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getOrigBank() {
		return OrigBank;
	}

	public void setOrigBank(String origBank) {
		OrigBank = origBank;
	}

	public String getOrigAcc() {
		return OrigAcc;
	}

	public void setOrigAcc(String origAcc) {
		OrigAcc = origAcc;
	}

	public String getOutBank() {
		return OutBank;
	}

	public void setOutBank(String outBank) {
		OutBank = outBank;
	}

	public String getOutAccBank() {
		return OutAccBank;
	}

	public void setOutAccBank(String outAccBank) {
		OutAccBank = outAccBank;
	}

	public String getPayerAcc() {
		return PayerAcc;
	}

	public void setPayerAcc(String payerAcc) {
		PayerAcc = payerAcc;
	}

	public String getPayerName() {
		return PayerName;
	}

	public void setPayerName(String payerName) {
		PayerName = payerName;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayerCreditType() {
		return payerCreditType;
	}

	public void setPayerCreditType(String payerCreditType) {
		this.payerCreditType = payerCreditType;
	}

	public String getPayerCreditNo() {
		return payerCreditNo;
	}

	public void setPayerCreditNo(String payerCreditNo) {
		this.payerCreditNo = payerCreditNo;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getPayeeAcc() {
		return PayeeAcc;
	}

	public void setPayeeAcc(String payeeAcc) {
		PayeeAcc = payeeAcc;
	}

	public String getPayeeName() {
		return PayeeName;
	}

	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}

	public String getTranType() {
		return TranType;
	}

	public void setTranType(String tranType) {
		TranType = tranType;
	}

	public String getContractId() {
		return ContractId;
	}

	public void setContractId(String contractId) {
		ContractId = contractId;
	}

	public String getPrivateFlag() {
		return PrivateFlag;
	}

	public void setPrivateFlag(String privateFlag) {
		PrivateFlag = privateFlag;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getAmout() {
		return Amout;
	}

	public void setAmout(String amout) {
		Amout = amout;
	}

	public String getCharge() {
		return Charge;
	}

	public void setCharge(String charge) {
		Charge = charge;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getActivePayFlag() {
		return ActivePayFlag;
	}

	public void setActivePayFlag(String activePayFlag) {
		ActivePayFlag = activePayFlag;
	}

	public String getOPRSign() {
		return OPRSign;
	}

	public void setOPRSign(String oPRSign) {
		OPRSign = oPRSign;
	}

	public String getBulletinId() {
		return BulletinId;
	}

	public void setBulletinId(String bulletinId) {
		BulletinId = bulletinId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getIssuer() {
		return Issuer;
	}

	public void setIssuer(String issuer) {
		Issuer = issuer;
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getHasAttachment() {
		return HasAttachment;
	}

	public void setHasAttachment(String hasAttachment) {
		HasAttachment = hasAttachment;
	}

	public String getAttachmentName() {
		return AttachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		AttachmentName = attachmentName;
	}

	public String getAttachment() {
		return Attachment;
	}

	public void setAttachment(String attachment) {
		Attachment = attachment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccNo() {
		return AccNo;
	}

	public void setAccNo(String accNo) {
		AccNo = accNo;
	}

	public String getAccName() {
		return AccName;
	}

	public void setAccName(String accName) {
		AccName = accName;
	}

	public String getBank() {
		return Bank;
	}

	public void setBank(String bank) {
		Bank = bank;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getQryType() {
		return QryType;
	}

	public void setQryType(String qryType) {
		QryType = qryType;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getBifCode() {
		return bifCode;
	}

	public void setBifCode(String bifCode) {
		this.bifCode = bifCode;
	}

	public String getBocbankcode() {
		return bocbankcode;
	}

	public void setBocbankcode(String bocbankcode) {
		this.bocbankcode = bocbankcode;
	}
	
	
	

}
