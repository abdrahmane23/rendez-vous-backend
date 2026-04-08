package com.rendezvous.demo.domain.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.EmbeddedColumnNaming;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.transform.Result;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "duration", nullable = false)
    private Duration duration;
    @Column(name = "time_of_result", nullable = false)
    private Duration timeOfResult;
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne()
    @JoinColumn(name = "section_id",nullable = false)
    private Section section;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Test test = (Test) object;
        return Objects.equals(id, test.id) && Objects.equals(name, test.name) && Objects.equals(duration, test.duration) && Objects.equals(timeOfResult, test.timeOfResult) && Objects.equals(createdAt, test.createdAt) && Objects.equals(updatedAt, test.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, timeOfResult, createdAt, updatedAt);
    }
}
