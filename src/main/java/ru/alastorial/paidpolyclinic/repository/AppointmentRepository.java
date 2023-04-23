package ru.alastorial.paidpolyclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alastorial.paidpolyclinic.entity.Appointment;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
