package Sprint1SmartStudy;

public class Task {

    private String taskName;
    private String priority;
    private String dueDate;
    private String status;
    private String createdAt;

    public Task(String taskName, String priority,
                String dueDate, String status,
                String createdAt) {

        this.taskName  = taskName;
        this.priority  = priority;
        this.dueDate   = dueDate;
        this.status    = status;
        this.createdAt = createdAt;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // CONVERTS TASK TO DISPLAY STRING FOR JLIST
    public String toDisplayString() {

        String display = priority + " - "
                + taskName
                + " (Due: " + dueDate + ")";

        if (status.equals("Done")) {
            display = display + " [Done]";
        }

        return display;
    }
}
