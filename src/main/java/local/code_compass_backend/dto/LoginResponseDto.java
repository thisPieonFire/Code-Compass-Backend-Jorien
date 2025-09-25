package local.code_compass_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
//todo aparte class voor User

public class LoginResponseDto {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private User user;

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
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
}
