package ru.alastorial.paidpolyclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alastorial.paidpolyclinic.dto.AppointmentDto;
import ru.alastorial.paidpolyclinic.entity.Appointment;
import ru.alastorial.paidpolyclinic.entity.Doctor;
import ru.alastorial.paidpolyclinic.error.BadRequestException;
import ru.alastorial.paidpolyclinic.error.NotFoundException;
import ru.alastorial.paidpolyclinic.mapper.AppointmentMapper;
import ru.alastorial.paidpolyclinic.repository.AppointmentRepository;
import ru.alastorial.paidpolyclinic.repository.DoctorRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;

    private final int scheduleDuration = 3;

    public List<AppointmentDto> getAll() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toDto).toList();
    }

    public AppointmentDto getById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment with id " + id + " not found"));
        return appointmentMapper.toDto(appointment);
    }

    public AppointmentDto save(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);

        appointment.setDoctor(doctorRepository.findById(appointmentDto.getDoctorId()).orElseThrow(() -> new BadRequestException("There is no doctor with id: " + appointmentDto.getDoctorId())));
        appointment.setPatient(null);

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    public void delete(UUID id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new BadRequestException("There is no appointment with id: " + id));
        appointmentRepository.delete(appointment);
    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 10)
    public void processAppointment() {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(this::createDoctorAppointments);
    }

    private void createDoctorAppointments(Doctor doctor) {

        LocalDateTime now = LocalDateTime.now();
        long nowDay = now.getDayOfMonth();
        long nowMonth = now.getMonth().getValue();

        List<Appointment> appointments = doctor.getAppointments();

        for (long i = 0; i < scheduleDuration; i++) { // счетчик дня

            LocalDateTime currentDate = now.plusDays(i); // текущая дата + кол-во дней, которое уже обработали

            int currentDay = currentDate.getDayOfMonth(); // обрабатываемый день в цикле (23-е, 24-е итд)
            int currentMonth = currentDate.getMonth().getValue(); // обрабатываемый месяц в цикле

            for (int j = getMinutes(doctor.getWorkStart()); j < getMinutes(doctor.getWorkEnd()); j += doctor.getDuration()) { // для всех записей в определенный день

                // если текущее время меньше предполгаемой записи и день итерирования это сегодняшний день (месяц)
                // или день итерирование это завтра/послезавтра/итд
                // и в эту дату и время нет других записей, то создаем запись
                if (((getMinutes(now) < j && currentDay == nowDay && currentMonth == nowMonth) || i > 0) && !isAppointmentsAtThatTime(appointments, now.plusDays(i), j)) { // если сегодня время приема еще не наступило или это уже завтра итд

                    // собираем LocalDateTime
                    String hours = String.format("%02d", getHours(j));
                    String minutes = String.format("%02d", getMinWOutHours(j));
                    String month = String.format("%02d", currentDate.getMonth().getValue());
                    String day = String.format("%02d", currentDate.getDayOfMonth());
                    String date = String.format("%d-%s-%s %s:%s", currentDate.getYear(), month, day, hours, minutes);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    Appointment newAppointment = Appointment.builder()
                            .doctor(doctor)
                            .time(LocalDateTime.parse(date, formatter))
                            .patient(null)
                            .build();

                    appointmentRepository.save(newAppointment);

                    System.out.println(date + " - создана запись врача с id: " + doctor.getId());
                }
            }
        }
    }

    public int getMinutes(LocalDateTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public int getMinutes(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public int getHours(int minutes) {
        return minutes / 60;
    }

    public int getMinWOutHours(int minutes) {
        return minutes - (getHours(minutes) * 60);
    }

    public boolean isAppointmentsAtThatTime(List<Appointment> appointments, LocalDateTime time, int minutes) {
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentTime = appointment.getTime(); // время, использованное для проверки наличия/отсутствия записей (может быть любая дата в будущем)
            if (time.getYear() == appointmentTime.getYear()
                    && time.getMonth() == appointmentTime.getMonth()
                    && time.getDayOfMonth() == appointmentTime.getDayOfMonth()
                    && getHours(minutes) == appointmentTime.getHour()
                    && getMinWOutHours(minutes) == appointmentTime.getMinute()) {
                System.out.println("Запись с id " + appointment.getId() + " уже существует на дату " + appointmentTime);
                return true;
            }
        }
        return false;
    }


}

