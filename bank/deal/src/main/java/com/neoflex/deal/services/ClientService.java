package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import com.neoflex.deal.model.dto.LoanStatementRequestDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Employment;
import com.neoflex.deal.model.entities.Passport;
import com.neoflex.deal.repositories.ClientRepository;
import com.neoflex.deal.repositories.EmploymentRepository;
import com.neoflex.deal.repositories.PassportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.neoflex.deal.model.enumFilds.GenderEnum.MALE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final PassportRepository passportRepository;
    private final EmploymentRepository employmentRepository;
    private final ModelMapper modelMapper;

    public Client createClient(LoanStatementRequestDto loanStatementRequestDto) {
        Passport passport = modelMapper.map(loanStatementRequestDto, Passport.class);
        Client client = modelMapper.map(loanStatementRequestDto, Client.class);

        Passport passportSave = passportRepository.save(passport);
        client.setPassport(passportSave);
        Client clientSave = clientRepository.save(client);

        log.info("passportSave - {}", passportSave);
        log.info("clientSave - {}", clientSave);

        return clientSave;
    }

    public Client updateClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Employment employment = modelMapper.map(finishRegistrationRequestDto.getEmployment(), Employment.class);
        System.out.println("updateClient, employment - " + employment);
        Employment employmentSave = employmentRepository.save(employment);
        log.info("employmentSave - {}", employmentSave);
        client.setEmployment(employmentSave);

        Passport passport = client.getPassport();
        modelMapper.map(finishRegistrationRequestDto, passport);
        Passport passportUpdadte = passportRepository.save(passport);
        log.info("passportUpdadte - {}", passportUpdadte);
        client.setPassport(passport);

        Client updateClient = clientRepository.save(client);
        return updateClient;
    }
}
