package com.taskmanager.cdp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taskmanager.cdp.config.ProjectContext;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.taskmanager.cdp.config.ProjectContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class CDPService {

    public final RabbitTemplate rabbitTemplate;

    public void createTask(JsonNode request){
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();

        ProjectContext context = getContext();

        if(!request.get("tasks").isArray()){
            return;
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator)
                    .put("org_id", context.getOrgId())
                    .put("project_id", context.getProjectId());

            rabbitTemplate.convertAndSend("task-exchange","task",
                    (Object) mapper.createObjectNode()
                    .put("method","CREATE")
                    .set("body",fullTask));

        }
    }

    public void updateTask(JsonNode request){
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();

        ProjectContext context = getContext();

        if(!request.get("tasks").isArray()){
            return;
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator)
                    .put("org_id", context.getOrgId())
                    .put("project_id", context.getProjectId());
            rabbitTemplate.convertAndSend("task-exchange","task",
                    (Object) mapper.createObjectNode()
                            .put("method","UPDATE")
                            .set("body",fullTask));
        }

    }

    public void deleteTask(JsonNode request){
        ObjectMapper mapper = new ObjectMapper();

        if(!request.get("tasks").isArray()){
            return;
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        for (JsonNode task : tasks) {
            ObjectNode fullTask = mapper.createObjectNode()
                    .put("id",task.get("id").asLong());
            rabbitTemplate.convertAndSend("task-exchange","task",
                    (Object) mapper.createObjectNode()
                            .put("method","DELETE")
                            .set("body",fullTask));
        }

    }
}