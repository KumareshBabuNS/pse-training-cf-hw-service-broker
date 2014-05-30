package org.cloudfoundry.community.helloworldservice.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public class Authorization {

    @JsonSerialize
    @JsonProperty("id")
    private String id;


    @JsonSerialize
    @JsonProperty("accountId")
    private String accountId;

    @JsonSerialize
    @JsonProperty("hwInstanceId")
    private String hwInstanceId;

    public Authorization(String id, String accountId, String hwInstanceId) {
        this.id = id;
        this.accountId = accountId;
        this.hwInstanceId = hwInstanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHwInstanceId() {
        return hwInstanceId;
    }

    public void setHwInstanceId(String hwInstanceId) {
        this.hwInstanceId = hwInstanceId;
    }

    public boolean equals(Object o) {
        if( o instanceof Authorization) {
            Authorization a = (Authorization)o;
            return this.getHwInstanceId().equals(a.getHwInstanceId()) &&
                    this.getAccountId().equals(a.getAccountId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", hwInstanceId='" + hwInstanceId + '\'' +
                '}';
    }
}
