package org.cloudfoundry.community.helloworldservice.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ErrorDescriptionMessage {

    String description;

    public ErrorDescriptionMessage() {}

    public ErrorDescriptionMessage(String description) {
        this.description = description;
    }

    @JsonSerialize
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

}
