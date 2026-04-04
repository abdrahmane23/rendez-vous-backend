package com.rendezvous.demo.domain.Entities;

import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShiftStatus status;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime start;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime end;
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "examiner_id", nullable = false)
    private User examiner;


    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Shift shift = (Shift) object;
        return Objects.equals(id, shift.id) && status == shift.status && Objects.equals(start, shift.start) && Objects.equals(end, shift.end) && Objects.equals(createdAt, shift.createdAt) && Objects.equals(updatedAt, shift.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, start, end, createdAt, updatedAt);
    }
}
