public class Task {
    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setDone(boolean inputBoolean) {
        this.isDone = inputBoolean;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
