package ru.alastorial.paidpolyclinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.alastorial.paidpolyclinic.entity.enums.Specialty;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "doctor")
@EntityListeners(AuditingEntityListener.class)
public class Doctor {

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

    @Column(name = "specialty")
    @NotNull(message = "Specialty should not be empty")
    @Enumerated(value = EnumType.STRING)
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "polyclinic_id", referencedColumnName = "id")
    @JsonIgnore
    private Polyclinic polyclinic;

    @Column(name = "work_start")
    @NotNull(message = "Work start should not be empty")
    private LocalTime workStart;

    @Column(name = "work_end")
    @NotNull(message = "Work end should not be empty")
    private LocalTime workEnd;

    @Column(name = "duration")
    @Min(1)
    private int duration;

    @Column(name = "price")
    @Min(1)
    private int price;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Appointment> appointments;

    public List<Appointment> getAppointments() {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        return appointments;
    }

}
