package com.taskmanager.cdp.config;

public class ProjectContextHolder {
    private static final ThreadLocal<ProjectContext> projectContext = new ThreadLocal<>();

    public static void setContext(ProjectContext context) {
        projectContext.set(context);
    }

    public static ProjectContext getContext() {
        return projectContext.get();
    }

    public static void clearContext() {
        projectContext.remove();
    }
}
