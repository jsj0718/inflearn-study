package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    String getTeam();

    interface TeamInfo {
        String getName();
    }
}
