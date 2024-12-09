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
    @PostMapping("v1/organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> addTasks(@RequestBody JsonNode request) {
        try {
            cdpService.createTask(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(201).body("TASK CREATED SUCCESSFULLY!");
    }
    @PutMapping("v1/organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> updateTasks(@RequestBody JsonNode request) {
        try {
            cdpService.updateTask(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(201).body("TASK UPDATED SUCCESSFULLY!");
    }
    @DeleteMapping("v1/organizations/{org_name}/projects/{project_name}/tasks/{task_id}")
    public ResponseEntity<?> deleteTasks(@PathVariable Long task_id) {
        try {
            cdpService.deleteTask(task_id);
            return ResponseEntity.status(200).body("TASK DELETED SUCCESSFULLY!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}