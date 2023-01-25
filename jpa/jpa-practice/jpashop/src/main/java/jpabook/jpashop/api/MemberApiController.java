package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    /**
     * 엔티티를 직접 활용 시 문제점
     * 1. 엔티티에 검증이 들어가야 되기 때문에 일관성이 없어지고 코드가 복잡해진다.
     * 2. API Spec이 불안정해진다. (필드명 변경 시 클라이언트에서는 혼동이 올 수 밖에 없다.)
     * 3. 따라서 엔티티 자체는 여러 곳에서 사용되므로 변경될 확률이 높기 때문에 직접 반환하는 것은 좋지 않다.
     * -> 별도의 DTO를 통해 파라미터를 통해서 받는 것이 좋다.
     * -> 엔티티를 파라미터로 받지도 말고, 외부에 노출하지도 말라
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 별도의 클래스(DTO) 사용 시 장점
     * 1. API Spec이 변경되지 않는다.
     * 2. 엔티티의 경우 파라미터가 어떤 것이 넘어올 지 모르는 반면, DTO 사용 시 확실하게 알 수 있다.
     * 3. @Valid 또한 원하는 경우에만 할 수 있다. (상황에 맞게 선택하여 Validation 가능)
     * 4. 즉, DTO만 보더라도 API Spec을 알 수 있다.
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }
}
