package ru.alastorial.paidpolyclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alastorial.paidpolyclinic.entity.Patient;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByUsername(String username);
}
