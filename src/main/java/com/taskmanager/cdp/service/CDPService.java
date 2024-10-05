package com.taskmanager.cdp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CDPService {

    public final RabbitTemplate rabbitTemplate;

    public void createTask(String org_name,String project_name,JsonNode request){
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();


        if(!request.get("tasks").isArray()){
            return;
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator); //TODO: Access Metadata service for project ID and org ID

            rabbitTemplate.convertAndSend("task-exchange","task",
                    (Object) mapper.createObjectNode()
                    .put("method","CREATE")
                    .set("body",fullTask));

        }
    }

    public void updateTask(String org_name,String project_name,JsonNode request){
        ObjectMapper mapper = new ObjectMapper();
        String creator = request.get("created_by").asText();


        if(!request.get("tasks").isArray()){
            return;
        }
        ArrayNode tasks = (ArrayNode) request.get("tasks");
        for (JsonNode task : tasks) {
            ObjectNode fullTask = ((ObjectNode)task)
                    .put("created_by", creator); //TODO: Access Metadata service for project ID and org ID
            rabbitTemplate.convertAndSend("task-exchange","task",
                    (Object) mapper.createObjectNode()
                            .put("method","UPDATE")
                            .set("body",fullTask));
        }

    }
}