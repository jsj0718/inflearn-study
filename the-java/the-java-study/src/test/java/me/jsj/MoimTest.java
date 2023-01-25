package me.jsj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MoimTest {

    @Test
    void isFull() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 100;
        moim.numberOfCurrentEnrollment = 10;
        Assertions.assertFalse(moim.isEnrollmentFull());
    }
}