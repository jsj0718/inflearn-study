package me.jsj;

public class Moim {

    int maxNumberOfAttendees;
    int numberOfCurrentEnrollment;

    public boolean isEnrollmentFull() {
        if (maxNumberOfAttendees == 0) {
            return false;
        }

        if (maxNumberOfAttendees > numberOfCurrentEnrollment) {
            return false;
        }

        return true;
    }
}
