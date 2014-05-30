package org.cloudfoundry.community.helloworldservice.exception;

public class HWInstanceDoesNotExistException extends Exception {

    private static final long serialVersionUID = -914111358227517785L;

    private String hwInstanceId;

    public HWInstanceDoesNotExistException(String hwInstanceId) {
        this.hwInstanceId = hwInstanceId;
    }

    public String getMessage() {
        return String.format("HW Instance with id %s does not exist", hwInstanceId);
    }
}
