package com.taskmanager.cdp.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProjectContext {
    private final Long orgId;
    private final Long projectId;
}
