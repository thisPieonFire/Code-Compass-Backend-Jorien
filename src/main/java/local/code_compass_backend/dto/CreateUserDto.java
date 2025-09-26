package local.code_compass_backend.dto;

public class CreateUserDto {
    private String email;
    private String displayName;
    private String role;
    private String password = "Wachtwoord123";

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() { return password;}
}