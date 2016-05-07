package com.cg.domain;

import java.util.List;

public class RemoteGeneratorInfoEntity {
	private List<RemoteSku> remoteSkus;
	private List<String> errorMessages;
	public List<RemoteSku> getRemoteSkus() {
		return remoteSkus;
	}
	public void setRemoteSkus(List<RemoteSku> remoteSkus) {
		this.remoteSkus = remoteSkus;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
}
