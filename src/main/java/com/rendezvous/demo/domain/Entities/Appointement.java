package com.rendezvous.demo.domain.Entities;

import com.rendezvous.demo.domain.Enums.AppointementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointement {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointementStatus status;
    @Column(name="start_time",nullable = false)
    private LocalDateTime start;
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "examiner_id")
    private User examiner;

    @OneToMany(mappedBy = "appointement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestResult> testResults;

    @OneToMany(mappedBy = "appointement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Appointement that = (Appointement) object;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(start, that.start) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, start, createdAt, updatedAt);
    }
}
