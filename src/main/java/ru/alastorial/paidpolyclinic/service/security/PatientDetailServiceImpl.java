package ru.alastorial.paidpolyclinic.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.security.PatientDetails;
import ru.alastorial.paidpolyclinic.service.PatientService;


// класс судя по всему нужен только для нахождения пользователя по логину в фильтрации токена
@Service
@RequiredArgsConstructor
public class PatientDetailServiceImpl implements UserDetailsService {

    private final PatientService patientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new PatientDetails(patientService.getByUsername(username));
    }
}
