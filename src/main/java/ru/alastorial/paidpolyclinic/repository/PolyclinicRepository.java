package ru.alastorial.paidpolyclinic.repository;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alastorial.paidpolyclinic.entity.Polyclinic;

import java.util.UUID;

@Repository
public interface PolyclinicRepository extends JpaRepository<Polyclinic, UUID> {

    Polyclinic getByName(@NotEmpty(message = "Name should not be empty") String name);


}
