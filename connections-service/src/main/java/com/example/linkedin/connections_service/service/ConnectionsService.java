package com.example.linkedin.connections_service.service;

import com.example.linkedin.connections_service.entity.Person;
import com.example.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId) {
        return personRepository.getFirstDegreeConnections(userId);
    }
}
