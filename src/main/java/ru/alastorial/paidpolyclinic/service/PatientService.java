package ru.alastorial.paidpolyclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.dto.AppointmentDto;
import ru.alastorial.paidpolyclinic.dto.PatientDto;
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

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;


    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }

    public PatientDto getById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        return patientMapper.toDto(patient);
    }

    public List<AppointmentDto> getAppointmentsByPatientId(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient with id " + id + " not found"));
        return patient.getAppointments().stream().map(appointmentMapper::toDto).toList();
    }

    public PatientDto save(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        return patientMapper.toDto(patientRepository.save(patient));
    }

//    @Transactional
//    public PatientDto makeAppointment(PatientDto patientDto) {
//        Patient patient = patientMapper.toEntity(patientDto);
//        System.out.println(patientDto.getAppointmentsId());
//        if (patientDto.getAppointmentsId() == null || patientDto.getAppointmentsId().size() == 0) {
//            System.out.println(patient.getAppointments());
//            for (Appointment appointment : patient.getAppointments()) {
//                appointment.setPatient(null);
//            }
//            patient.getAppointments().clear();
//        } else {
//            List<Appointment> appointments = patientDto.getAppointmentsId().stream().map(id -> appointmentRepository.findById(id).orElseThrow(() -> new BadRequestException("There is no appointment with id: " + id))).toList();
//            patient.setAppointments(appointments);
//            for (Appointment appointment : appointments) {
//                appointment.setPatient(patient);
//            }
//        }
//        return patientMapper.toDto(patientRepository.save(patient));
//    }

    public void delete(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new BadRequestException("There is no patient with id: " + id));
        patientRepository.delete(patient);
    }


}
