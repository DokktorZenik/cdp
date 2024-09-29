package com.taskmanager.cdp.model;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long organisation_id;
    private Long project_id;
    private String title;
    private String priority;
    private String status;
    private String estimate;
    private List<JsonNode> tags;
    private Integer progress;
    private Long timestamp;
    private Date start_date;
    private Date end_date;
    private Long assignee_id;
    private Long created_by;

}
