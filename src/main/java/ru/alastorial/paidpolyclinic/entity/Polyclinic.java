package ru.alastorial.paidpolyclinic.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "polyclinic")
public class Polyclinic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NotEmpty(message = "Polyclinic name should be empty")
    private String name;

    @OneToMany(mappedBy = "polyclinic", cascade = {CascadeType.PERSIST})
    private List<Doctor> doctors;

    public List<Doctor> getDoctors() {
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        return doctors;
    }
}
