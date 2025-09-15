package local.code_compass_backend.database.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
    @Table(name = "profile")
    public class ProfileEntity {

     @Id
     @Column(nullable = false, length = 36)
     private String id;  // Supabase UID als primary key

     @Column(length = 500)
     private String displayName;

    @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private Role role;

     @Column (nullable = false)
     private Instant createdAt;

     @Column (nullable = false)
     private Instant updatedAt;

     @PrePersist
     protected void onCreate() {
         Instant now = Instant.now();
         if (this.createdAt == null) {
             this.createdAt = now;
         }
         this.updatedAt = now;
     }

     @PreUpdate
     protected void onUpdate() {
         this.updatedAt = Instant.now();
     }

     public String getId() {
         return id;
     }
     public void setId(String id) {
         this.id = id;
     }

     public String getDisplayName() {
         return displayName;
     }

     public void setDisplayName(String displayName) {
         this.displayName = displayName;
     }

     public Role getRole() {
         return role;
     }

     public void setRole(Role role) {
         this.role = role;
     }

     public Instant getCreatedAt() {
         return createdAt;
     }

     public void setCreatedAt(Instant createdAt) {
         this.createdAt = createdAt;
     }

     public Instant getUpdatedAt() {
         return updatedAt;
     }

     public void setUpdatedAt(Instant updatedAt) {
         this.updatedAt = updatedAt;
     }
}
