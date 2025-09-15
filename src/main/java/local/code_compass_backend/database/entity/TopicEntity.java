package local.code_compass_backend.database.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topic")
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // primary key

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;


   // todo get this working: @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true);

    // private List<SubTopicEntity> subTopicEntity = new ArrayList<>();

    // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;


 /*   public void addSubtopic(SubTopicEntity s) {
        subTopicEntity.add(s);
        s.setTopicEntity(this);
    }

    public void removeSubtopic(SubTopicEntity s) {
        subTopicEntity.remove(s);
        s.setTopicEntity(null);
    }*/

    }
}

