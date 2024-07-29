package com.example.todo_Backend.User.Todo.Controller;


import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Todo.Dto.individual.in.patchReqDto;
import com.example.todo_Backend.User.Todo.Dto.individual.in.saveIndividualReqDto;
import com.example.todo_Backend.User.Todo.Dto.individual.out.getTodoResDto;
import com.example.todo_Backend.User.Todo.Dto.individual.out.saveIndividualResDto;
import com.example.todo_Backend.User.Todo.Dto.individual.out.savedPatchResDto;
//import com.example.todo_Backend.User.Todo.Entity.IndividualModel;
//import com.example.todo_Backend.User.Todo.Service.TodoIndividualService;
import com.example.todo_Backend.User.Todo.Service.TodoIndividualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
@RequestMapping("/api/individual")
public class TodoIndividualCotnroller {

    private final TodoIndividualService todoIndividualService;

    @PostMapping("/goals/{goalId}/todos")
    @Operation(summary = "목표 ID에 대한 할일 저장", description = "할일 저장 API")
    public ResponseEntity<?> savetodos(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @RequestBody saveIndividualReqDto saveReqDto,
                                       @PathVariable ("goalId") int goalId){

        saveIndividualResDto saveresdto=todoIndividualService.savetodo(memberDetails.getMemberId(),saveReqDto,goalId);

        return ResponseEntity.ok(saveresdto);
    }


    @GetMapping("/goals/{goalid}/todos")
    @Operation(summary = "목표 ID에 대한 할일 조회", description = "할일 조회 API")
    public ResponseEntity<?> gettodos(@AuthenticationPrincipal MemberDetails memberDetails){


        getTodoResDto getTodoResDto=todoIndividualService.gettodo(memberDetails.getMemberId());

        return ResponseEntity.ok(getTodoResDto);
        //골정보 및 그 밑에 todos 호출


    }



    @GetMapping("/todos")
    @Operation(summary = "모든 할일 조회", description = "모든 할일 조회 API")
    public ResponseEntity<?> savetodos(@AuthenticationPrincipal MemberDetails memberDetails){
        List<IndividualModel> individualModels=todoIndividualService.getAlltodo();
        return ResponseEntity.ok(individualModels);
    }

    @PatchMapping("/goals/{goalId}/todos/{todoId}")
    @Operation(summary = "할일 수정 ", description = "할일 수정  API")
    public ResponseEntity<?> savetodos(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @RequestBody patchReqDto patchReqDto,
                                        @PathVariable ("goalId") int goalId,
                                       @PathVariable ("todoId") int todoId){

        //골정보 및 그 밑에 todos 호출

        savedPatchResDto savedPatchResDto = todoIndividualService.patchtodo(memberDetails.getMemberId(),goalId,todoId,patchReqDto);


    }


    @DeleteMapping("/goals/{goalId}/todos/{todoId}")
    @Operation(summary = "할일 삭제 ", description = "할일 삭제 API")
    public ResponseEntity<?> deleltetodos(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @PathVariable ("goalId") int goalId,
                                          @PathVariable ("todoId") int todoId){

        //골정보 및 그 밑에 todos 호출
        todoIndividualService.delelteTodo(memberDetails.getMemberId(),goalId,todoId);

        return ResponseEntity.ok("삭제완료");


    }








}

