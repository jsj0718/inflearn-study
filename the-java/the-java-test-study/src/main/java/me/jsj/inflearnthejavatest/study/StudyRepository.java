package me.jsj.inflearnthejavatest.study;

import me.jsj.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
