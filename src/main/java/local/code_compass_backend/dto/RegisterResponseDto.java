// ... existing code ...
package local.code_compass_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterResponseDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("aud")
    private String aud;

    @JsonProperty("role")
    private String role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("confirmation_sent_at")
    private String confirmationSentAt;

    @JsonProperty("app_metadata")
    private Object appMetadata;

    @JsonProperty("user_metadata")
    private Object userMetadata;

    @JsonProperty("identities")
    private Object identities;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;

    @JsonProperty("sub")
    private String sub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmationSentAt() {
        return confirmationSentAt;
    }

    public void setConfirmationSentAt(String confirmationSentAt) {
        this.confirmationSentAt = confirmationSentAt;
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

    public Object getIdentities() {
        return identities;
    }

    public void setIdentities(Object identities) {
        this.identities = identities;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}