package ru.alastorial.paidpolyclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.dto.AppointmentDto;
import ru.alastorial.paidpolyclinic.dto.PatientRegistryDTO;
import ru.alastorial.paidpolyclinic.entity.Appointment;
import ru.alastorial.paidpolyclinic.entity.Patient;
import ru.alastorial.paidpolyclinic.error.BadRequestException;
import ru.alastorial.paidpolyclinic.error.NotFoundException;
import ru.alastorial.paidpolyclinic.mapper.AppointmentMapper;
import ru.alastorial.paidpolyclinic.mapper.PatientMapper;
import ru.alastorial.paidpolyclinic.repository.AppointmentRepository;
import ru.alastorial.paidpolyclinic.repository.PatientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private static final String NO_PATIENT_MESSAGE = "There is no patient with id: ";

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;

    private final PasswordEncoder passwordEncoder;


    public List<PatientRegistryDTO> getAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }

    public PatientRegistryDTO getById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        return patientMapper.toDto(patient);
    }

    public Patient getByUsername(String username) {
        return patientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public List<AppointmentDto> getAppointmentsByPatientId(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        return patient.getAppointments().stream().map(appointmentMapper::toDto).toList();
    }

    public PatientRegistryDTO update(PatientRegistryDTO patientRegistryDTO) {
        Patient patient = patientMapper.toEntity(patientRegistryDTO);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public Patient save(PatientRegistryDTO patientRegistryDTO) {
        Patient patient = patientMapper.toEntity(patientRegistryDTO);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole("PATIENT");
        return patientRepository.save(patient);
    }

    public PatientRegistryDTO makeAppointment(UUID patientId, UUID appointmentId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new BadRequestException(NO_PATIENT_MESSAGE + patientId));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new BadRequestException("There is no appointment with id: " + appointmentId));
        patient.getAppointments().add(appointment);
        appointment.setPatient(patient);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientRegistryDTO removeAppointment(UUID patientId, UUID appointmentId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new BadRequestException(NO_PATIENT_MESSAGE + patientId));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new BadRequestException("There is no appointment with id: " + appointmentId));
        patient.getAppointments().remove(appointment);
        appointment.setPatient(null);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public void delete(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new BadRequestException(NO_PATIENT_MESSAGE + id));
        patientRepository.delete(patient);
    }


}
