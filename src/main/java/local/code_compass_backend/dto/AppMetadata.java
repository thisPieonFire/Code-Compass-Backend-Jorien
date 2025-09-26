package local.code_compass_backend.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class AppMetadata {
    @JsonProperty("role")
    private String role;
   /* private Map<String, Object> additionalProperties = new HashMap<>();*/

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

/*    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }*/
}
