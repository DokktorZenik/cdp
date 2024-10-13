package com.taskmanager.cdp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taskmanager.cdp.service.CDPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CDPController {

    private final CDPService cdpService;
    @PostMapping("organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> addTasks(@RequestBody JsonNode request) throws JsonProcessingException {
        try {
            cdpService.createTask(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> updateTasks(@PathVariable String org_name, @PathVariable String project_name,@RequestBody JsonNode request) throws JsonProcessingException {
        try {
            cdpService.updateTask(org_name, project_name, request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
