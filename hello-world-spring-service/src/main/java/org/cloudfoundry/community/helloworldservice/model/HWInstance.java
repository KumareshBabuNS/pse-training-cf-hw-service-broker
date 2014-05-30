package org.cloudfoundry.community.helloworldservice.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(getterVisibility = Visibility.NONE)
public class HWInstance {

    @JsonSerialize
    @JsonProperty("id")
    private String id;

    public HWInstance(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HWInstance{" +
                "id='" + id + '\'' +
                '}';
    }
}
