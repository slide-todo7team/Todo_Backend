package com.example.todo_Backend.group.controller;

import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Member.service.MemberService;
import com.example.todo_Backend.group.dto.*;
import com.example.todo_Backend.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "그룹 API")
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "그룹 생성",description = "그룹 생성 API")
    public ResponseEntity<GroupCreateResponseDto> createGroup(@RequestBody GroupTitleDto groupTitleDto, @AuthenticationPrincipal MemberDetails memberDetails) {

        String userName = memberService.findByEmail(memberDetails.getUsername());
        Long memberId = memberDetails.getMemberId();

        GroupCreateRequestDto groupCreateRequestDto = GroupCreateRequestDto.builder()
                .title(groupTitleDto.getTitle())
                .memberId(memberId)
                .createUser(userName)
                .build();

        return ResponseEntity.ok(groupService.createGroup(groupCreateRequestDto));
    }

    @PostMapping("/members")
    @Operation(summary = "그룹 참여 하기",description = "그룹 참여 API")
    public ResponseEntity<GroupCreateResponseDto> enterGroup(@RequestBody GroupCodeDto groupCodeDto, @AuthenticationPrincipal MemberDetails memberDetails){
        Long memberId = memberDetails.getMemberId();
        return ResponseEntity.ok(groupService.enterGroup(groupCodeDto.getSecretCode(),memberId));
    }

    @GetMapping("")
    @Operation(summary = "그룹 리스트 조회", description = "자기가 속한 그룹 리스트 조회 API")
    public ResponseEntity<List<GroupDto>> getGroupList(@AuthenticationPrincipal MemberDetails memberDetails){
        Long memberId = memberDetails.getMemberId();
        return ResponseEntity.ok(groupService.getGroupsByMemberId(memberId));
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "그룹 상세 조회", description = "그룹 상세 조회 API")
    public ResponseEntity<?> getGroupInfo(@PathVariable Long groupId){
        return ResponseEntity.ok(groupService.getGroupInfo(groupId));
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "그룹 삭제",description = "그룹 삭제 API (방장만 가능)")
    public ResponseEntity<GroupResponse> deleteGroup(@PathVariable Long groupId){
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }

    @DeleteMapping("/{groupId}/members/{memberId}")
    @Operation(summary = "그룹 탈퇴",description = "그룹 탈퇴 API (구성원)")
    public ResponseEntity<GroupResponse> deleteMember(@PathVariable Long groupId, @PathVariable Long memberId){
        return ResponseEntity.ok(groupService.leaveGroup(groupId,memberId));
    }

    @GetMapping("/code/{groupId}")
    @Operation(summary = "초대코드 재생성", description = "초대 코드 재생성 API")
    public ResponseEntity<GroupCodeDto> getNewSecretCode(@PathVariable Long groupId){
        return ResponseEntity.ok(groupService.getNewSecretCode(groupId));
    }

    @PatchMapping("/code/{groupId}")
    @Operation(summary = "새로운 초대코드 저장")
    public ResponseEntity<GroupInfoDto> updateSecretCode(@PathVariable Long groupId, @RequestBody GroupCodeDto groupCodeDto){
        return ResponseEntity.ok(groupService.updateSecretcode(groupId,groupCodeDto.getSecretCode()));
    }


}
