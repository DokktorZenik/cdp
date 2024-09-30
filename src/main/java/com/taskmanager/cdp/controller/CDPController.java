package com.taskmanager.cdp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class CDPController {

    @PostMapping("organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> addTasks(@PathVariable String org_name, @PathVariable String project_name,@RequestBody JsonNode request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();


        if(!request.get("tasks").isArray()){
            return ResponseEntity.badRequest().body("Tasks must be an array");
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        List<JsonNode> tasksCreateList = new ArrayList<>();
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator); //TODO: Access Metadata service for project ID and org ID
            tasksCreateList.add(
                    mapper.createObjectNode()
                            .put("method","CREATE")
                            .set("body",fullTask));
        }


        return ResponseEntity.ok().build();
    }
    @PutMapping("organizations/{org_name}/projects/{project_name}/tasks")
    public ResponseEntity<?> updateTasks(@PathVariable String org_name, @PathVariable String project_name,@RequestBody JsonNode request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();


        if(!request.get("tasks").isArray()){
            return ResponseEntity.badRequest().body("Tasks must be an array");
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        List<JsonNode> tasksUpdateList = new ArrayList<>();
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator); //TODO: Access Metadata service for project ID and org ID
            tasksUpdateList.add(
                    mapper.createObjectNode()
                            .put("method","UPDATE")
                            .set("body",fullTask));
        }

        return ResponseEntity.ok().build();
    }
}
