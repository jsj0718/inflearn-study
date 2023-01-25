package me.jsj.inflearnthejavatest.study;

import me.jsj.inflearnthejavatest.domain.Member;
import me.jsj.inflearnthejavatest.domain.Study;
import me.jsj.inflearnthejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        //실패 시 AssertException 발생
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
/*
        //방법 1
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member doesn't exist for id: " + memberId);
        }
        study.setOwner(member.get());

*/
        //방법 2
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: " + memberId)));

        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
//        memberService.notify(member.get());
        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = studyRepository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;

    }
}
