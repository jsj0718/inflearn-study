package me.jsj.inflearnthejavatest;

public class Study {

    private int limit;
    private StudyStatus status = StudyStatus.DRAFT;
    private String name;

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "limit=" + limit +
                ", status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
