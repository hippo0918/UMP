package com.um.domain.model.Message;

public class BaseInfo {

	private String TxnType;
	private String SubTxnType;
	private String TxnId;
	private String VersionId = "00";
	private String dzFile;

	public BaseInfo() {
		super();
	}

	public BaseInfo(String txnType, String subTxnType, String txnId,
			String versionId, String dzFile) {
		super();
		TxnType = txnType;
		SubTxnType = subTxnType;
		TxnId = txnId;
		VersionId = versionId;
		dzFile = dzFile;
	}

	public String getTxnType() {
		return TxnType;
	}

	public void setTxnType(String txnType) {
		TxnType = txnType;
	}

	public String getSubTxnType() {
		return SubTxnType;
	}

	public void setSubTxnType(String subTxnType) {
		SubTxnType = subTxnType;
	}

	public String getTxnId() {
		return TxnId;
	}

	public void setTxnId(String txnId) {
		TxnId = txnId;
	}

	public String getVersionId() {
		return VersionId;
	}

	public void setVersionId(String versionId) {
		VersionId = versionId;
	}

	public String getDzFile() {
		return dzFile;
	}

	public void setDzFile(String dzFile) {
		this.dzFile = dzFile;
	}

}
