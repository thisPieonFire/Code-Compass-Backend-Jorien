package local.code_compass_backend.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subtopics")

public class SubTopicEntity {


   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;
*/

    // getters/setters*/




}
