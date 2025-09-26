package local.code_compass_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginResponse {
    private String id;
    private String email;
    private String role;
    @JsonProperty("app_metadata")
    private Object appMetadata;

    @JsonProperty("user_metadata")
    private Object userMetadata;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Object getAppMetadata() {
        return appMetadata;
    }
    public void setAppMetadata(Object appMetadata) {
        this.appMetadata = appMetadata;
    }

    public Object getUserMetadata() {
        return userMetadata;
    }
    public void setUserMetadata(Object userMetadata) {
        this.userMetadata = userMetadata;
    }
}


