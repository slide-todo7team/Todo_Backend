package com.example.todo_Backend.goal.controller;
import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.goal.dto.*;
import com.example.todo_Backend.goal.service.IndividualGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "개인 목표 API")
@RequestMapping("/api/individual/goals")
public class IndividualGoalController {

    private final IndividualGoalService individualGoalService;

    @PostMapping("")
    @Operation(summary = "개인 목표 생성")
    public ResponseEntity<IndividualGoalDto> createIndividualGoal(@RequestBody GoalTitleDto goalTitleDto, @AuthenticationPrincipal MemberDetails memberDetails){
        Long userId = memberDetails.getMemberId();
        return ResponseEntity.ok(individualGoalService.createGoal(goalTitleDto,userId));
    }

    @GetMapping("")
    @Operation(summary = "(개인) 내 목표 리스트 조회")
    public ResponseEntity<?> getAllIndividualGoals(@AuthenticationPrincipal MemberDetails memberDetails){
        Long userId = memberDetails.getMemberId();
        return ResponseEntity.ok(individualGoalService.getAllIndividualGoals(userId));
    }

    @GetMapping("/todos")
    @Operation(summary = "(개인) 내 할일 리스트 조회")
    public ResponseEntity<CursorResponseDto<IndividualGoalTodoDto>> getAllIndividualGoalsTodos(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam(defaultValue =  "0") Long cursor, @RequestParam Integer limit){
        Long userId = memberDetails.getMemberId();
        return ResponseEntity.ok(individualGoalService.getAllIndividualGoalsTodos(userId,cursor,limit));
    }

    @PatchMapping("/{goalId}")
    @Operation(summary = "(개인) 목표 수정")
    public ResponseEntity<IndividualGoalDto> updateIndividualGoal(@RequestBody GoalTitleDto goalTitleDto, @PathVariable Long goalId){
        return ResponseEntity.ok(individualGoalService.updateIndividualGoal(goalTitleDto.getTitle(),goalId));
    }

    @DeleteMapping("/{goalId}")
    @Operation(summary = "(개인) 목표 삭제")
    public ResponseEntity<GoalResponse> deleteIndividualGoal(@PathVariable Long goalId){
        return ResponseEntity.ok(individualGoalService.deleteIndividualGoal(goalId));
    }

}
