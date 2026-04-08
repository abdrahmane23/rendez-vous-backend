package com.rendezvous.demo.Service.Impl;

import com.rendezvous.demo.Exception.UserNotCreatedException;
import com.rendezvous.demo.Service.KeycloakAdminService;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.awt.*;


@Service
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final Keycloak keycloak;

    @Override
    public void createUser(CreateExaminerRequestDto createExaminerRequestDto) {
        String password = createExaminerRequestDto.getName()+"123" ;
        System.out.println(password);
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createExaminerRequestDto.getName());
        user.setLastName(createExaminerRequestDto.getName());
        user.setFirstName(createExaminerRequestDto.getFirstName());
        user.setEmail(createExaminerRequestDto.getEmail());
        user.singleAttribute("PhoneNumber",createExaminerRequestDto.getNumber());
        user.singleAttribute("Adresse",createExaminerRequestDto.getAddress());
        user.singleAttribute("field",createExaminerRequestDto.getField());
        user.setEnabled(true);
        try (Response response = keycloak.realm("Rendez-vous").users().create(user)) {
            if (response.getStatus() == 201) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                System.out.println("User created with ID: " + userId);
                CredentialRepresentation credential = new CredentialRepresentation();
                credential.setType(CredentialRepresentation.PASSWORD);
                credential.setValue(password);
                credential.setTemporary(false);
                keycloak.realm("Rendez-vous").users().get(userId).resetPassword(credential);

            } else {
                throw new UserNotCreatedException("Failed to create user: " + response.getStatusInfo());
            }
        }

    }
}
