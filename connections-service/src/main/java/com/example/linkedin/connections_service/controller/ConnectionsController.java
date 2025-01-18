package com.example.linkedin.connections_service.controller;

import com.example.linkedin.connections_service.entity.Person;
import com.example.linkedin.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> firstDegreeConnections(@PathVariable Long userId) {
        List<Person> connections = connectionsService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(connections);
    }
}
