package ru.alastorial.paidpolyclinic.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.dto.AuthenticationRequestDto;
import ru.alastorial.paidpolyclinic.dto.PatientRegistryDto;
import ru.alastorial.paidpolyclinic.dto.Token;
import ru.alastorial.paidpolyclinic.service.PatientService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PatientService patientService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Token register(PatientRegistryDto patientRegistryDTO) {

        var patient = patientService.save(patientRegistryDTO);
        var jwtToken = jwtService.generateToken(patient);
        return new Token(jwtToken);
    }

    // аутентификация по логину, паролю
    public Token authenticate(AuthenticationRequestDto request) {
        auth(request);
        var user = patientService.getByUsername(request.getUsername());
        return new Token(jwtService.generateToken(user));
    }

    private void auth(AuthenticationRequestDto request) {
        // если не получится аутентифицироваться - выкинет исключение
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword(),
                        new ArrayList<>()
                )
        );
    }
}