package ru.alastorial.paidpolyclinic.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "patient")
@EntityListeners(AuditingEntityListener.class)
public class Patient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @Column(name = "phone_number")
    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments;

    public List<Appointment> getAppointments() {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        return appointments;
    }
}
