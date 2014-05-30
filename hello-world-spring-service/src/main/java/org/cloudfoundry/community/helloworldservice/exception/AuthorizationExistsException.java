package org.cloudfoundry.community.helloworldservice.exception;

public class AuthorizationExistsException extends Exception {

    private static final long serialVersionUID = -914571352227417755L;

    private String id;
    private String accuntId;
    private String hwInstanceId;

    public AuthorizationExistsException(String id) {
        this.id = id;
    }
    public AuthorizationExistsException(String accountId,  String hwInstanceId)
    {
        this.accuntId = accountId;
        this.hwInstanceId = hwInstanceId;
    }

    public String getMessage() {
        if(id != null) {
            return String.format("Authorization with the given id (%s)already exists: ",
                    id);
        }
        return String.format("Authorization with the given account id (%s) and HW instance id (%s) already exists: ",
                accuntId, hwInstanceId);
    }
}
