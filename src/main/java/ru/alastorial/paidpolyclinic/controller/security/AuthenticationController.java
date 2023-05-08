package ru.alastorial.paidpolyclinic.controller.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alastorial.paidpolyclinic.dto.AuthenticationRequestDTO;
import ru.alastorial.paidpolyclinic.dto.PatientRegistryDTO;
import ru.alastorial.paidpolyclinic.dto.Token;
import ru.alastorial.paidpolyclinic.service.security.AuthenticationService;


//todo кидать нормальные ошибки с описанием причины и корректным http кодом
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationService service;


    @PostMapping("/register")
    public Token register(@RequestBody @Valid PatientRegistryDTO patient) {
        return service.register(patient);
    }

    @PostMapping("/authenticate")
    public Token authenticate(@RequestBody @Valid AuthenticationRequestDTO request) {
        return service.authenticate(request);
    }

}
