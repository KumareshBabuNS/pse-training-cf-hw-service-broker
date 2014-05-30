package org.cloudfoundry.community.helloworldservice.exception;

public class HWInstanceExistsException extends Exception {

	private static final long serialVersionUID = -914575358277517785L;
	
	private String hwInstanceId;
	
	public HWInstanceExistsException(String hwInstanceId) {
		this.hwInstanceId = hwInstanceId;
	}
	
	public String getMessage() {
		return String.format("HW Instance with id %s already exists", hwInstanceId);
	}
}
