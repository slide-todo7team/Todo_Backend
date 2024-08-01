package com.example.todo_Backend.goal.controller;

import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.goal.dto.*;
import com.example.todo_Backend.goal.service.GroupGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "그룹 목표 API")
@RequestMapping("/api/group")

public class GroupGoalController {

    private final GroupGoalService groupGoalService;

    @PostMapping("/{groupId}/goals")
    @Operation(summary = "그룹 목표 생성")
    public ResponseEntity<GroupGoalDto> createGroupGoal(@PathVariable Long groupId, @RequestBody GoalTitleDto goalTitleDto, @AuthenticationPrincipal MemberDetails memberDetails) {
        Long userId = memberDetails.getMemberId();
        return ResponseEntity.ok(groupGoalService.createGroupGoal(groupId,goalTitleDto.getTitle(),userId));
    }

    @GetMapping("/{groupId}/goals")
    @Operation(summary = "그룹 목표 리스트 조회")
    public ResponseEntity<?> getGroupGoals(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupGoalService.getGroupGoals(groupId));
    }

//    @GetMapping("/goals/todos/{groupId}")
//    @Operation(summary = "그룹 목표 & 할일 리스트 조회")
//    public ResponseEntity<GroupGoalTodoResponseDto> getAllGroupGoalTodo(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long groupId){
//        Long userId = memberDetails.getMemberId();
//        return ResponseEntity.ok(groupGoalService.getAllGroupGoalTodo(userId,groupId));
//
//    }

    @GetMapping("/goals/todos/{groupId}")
    @Operation(summary = "그룹 목표 & 할일 리스트 조회")
    public ResponseEntity<CursorResponseDto<GroupGoalTodoDto>> getAllGroupGoalTodo(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable Long groupId, @RequestParam Long cursor, @RequestParam Integer limit){
        Long userId = memberDetails.getMemberId();
        return ResponseEntity.ok(groupGoalService.getAllGroupGoalTodo(userId,groupId,cursor,limit));

    }

    @PatchMapping("/{groupId}/goals/{goalId}")
    @Operation(summary = "그룹 목표 수정")
    public ResponseEntity<GroupGoalDto> updateGroupGoal(@PathVariable Long groupId, @PathVariable Long goalId, @RequestBody GoalTitleDto goalTitleDto) {
        return ResponseEntity.ok(groupGoalService.updateGroupGoal(groupId,goalId,goalTitleDto.getTitle()));
    }

    @DeleteMapping("/{groupId}/goals/{goalId}")
    @Operation(summary = "그룹 목표 삭제")
    public ResponseEntity<GoalResponse> deleteGroupGoal(@PathVariable Long groupId, @PathVariable Long goalId) {
        return ResponseEntity.ok(groupGoalService.deleteGroupGoal(groupId,goalId));
    }
}
