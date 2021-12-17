package me.jsj.inflearnthejavatest.study;

import me.jsj.inflearnthejavatest.domain.Member;
import me.jsj.inflearnthejavatest.domain.Study;
import me.jsj.inflearnthejavatest.domain.StudyStatus;
import me.jsj.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //@Mock을 사용하기 위한 확장팩
class StudyServiceTest {

    //mock 객체 생성 방법 2. Annotation 활용 (전역 변수)
    @Mock MemberService memberServiceV1;

    @Mock StudyRepository studyRepositoryV1;

    @Test
    void createStudyServiceV1() {
        //mock 객체 생성 방법 1. mock 메서드 활용
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberServiceV1, studyRepositoryV1);
        assertNotNull(studyService);
    }

    //mock 객체 생성 방법 3. 해당 메서드에서만 사용
    @Test
    void createStudyServiceV2(@Mock MemberService memberServiceV2,
                              @Mock StudyRepository studyRepositoryV2) {

        StudyService studyService = new StudyService(memberServiceV2, studyRepositoryV2);
        assertNotNull(studyService);
    }

    @Test
    void createNewStudyV1(@Mock MemberService memberServiceV2,
                        @Mock StudyRepository studyRepositoryV2) {
/*
        //null 반환 (Optional 타입은 Optinal.empty 리턴)
        Optional<Member> optional = memberServiceV2.findById(1L);
        assertNotNull(optional);

        //void 타입은 아무 일도 발생 X
        memberServiceV2.validate(1L);
*/
        
        StudyService studyService = new StudyService(memberServiceV2, studyRepositoryV2);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(2L);
        member.setEmail("test@email.com");

        //mock 객체 stubbing (행동 조작)
        //when : ~이라면, then : 이렇게 하라
//        when(memberServiceV2.findById(1L)).thenReturn(Optional.of(member));

        //ArgumentMatchers로 범용적으로 작성 가능 -> any()
        when(memberServiceV2.findById(any())).thenReturn(Optional.of(member));

        //파라미터가 1L인 경우 RuntimeException 발생
        when(memberServiceV2.findById(1L)).thenThrow(new RuntimeException());

        //void 메서드는 doThrow로 설정 가능
        doThrow(new IllegalArgumentException()).when(memberServiceV2).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
            memberServiceV2.validate(1L);
        });

        Study study = new Study(10, "java");

        //stubbing 검증
        assertThrows(RuntimeException.class, () -> {
            memberServiceV2.findById(1L);
        });
        Optional<Member> findMember = memberServiceV2.findById(2L);
        assertEquals("test@email.com", findMember.get().getEmail());

        studyService.createNewStudy(2L, study);

    }

    @Test
    void createNewStudyV2(@Mock MemberService memberService,
                          @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member)) //첫 번째 호출될 때
                .thenThrow(new RuntimeException()) //두 번째 호출될 때
                .thenReturn(Optional.empty()); //세 번째 호출될 때

        assertEquals("test@email.com", memberService.findById(1L).get().getEmail());
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });
        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void stubbingTest(@Mock MemberService memberService,
                      @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
    }

    @Test
    void mockVerify(@Mock MemberService memberService,
                    @Mock StudyRepository studyRepository) {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        //When
        studyService.createNewStudy(1L, study);

        //Then
        assertEquals(member, study.getOwner());

        //mock 객체의 메서드 호출 여부, 횟수 확인
        verify(memberService, times(1)).notify(study); //memberService가 notify()를 무조건 1번 실행해야 한다. (안하면 오류 발생)
//        verifyNoMoreInteractions(memberService); //memberService의 action이 이후에 발생할 경우 에러 발생
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any()); //validate()가 한 번도 호출 X

        //mock 객체의 메서드 순서 확인
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }

    /**
     * BDD : Behavior Driven Development
     * given, when, then 형식으로 작성
     * 기존 테스트에서 when -> given, verify -> then 으로 수정하면 된다. (BddMockito 클래스)
     */
    @Test
    void bddTest(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //When
        studyService.createNewStudy(1L, study);

        //Then
        assertEquals(member, study.getOwner());
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy(@Mock MemberService memberService,
                   @Mock StudyRepository studyRepository) {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "The Java, Test");
        assertNull(study.getOpenedDateTime());
        given(studyRepository.save(study)).willReturn(study);

        //When
        studyService.openStudy(study);

        //Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should(times(1)).notify(study);
    }
}