package com.example.todo_Backend.User.Todo.Controller;


import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Todo.Dto.Group.in.saveReqDto;
//import com.example.todo_Backend.User.Todo.Dto.Group.out.getAllGroupResDto;
import com.example.todo_Backend.User.Todo.Dto.Group.out.saveGroupResDto;
//import com.example.todo_Backend.User.Todo.Service.TodoGroupsService;
import com.example.todo_Backend.User.Todo.Service.TodoGroupsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "할일 API")
@RequestMapping("/api/group")
public class TodoGroupsController {

    private final TodoGroupsService todoGroupsService;


    @PostMapping("/{groupId}/goals/{goalId}/todos")
    @Operation(summary = "{그룹} 목표 ID에 대한 할일 저장", description = "그룹 할일 저장 API")
    public ResponseEntity<?> savetodos(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @RequestBody saveReqDto saveReqDto,
                                       @PathVariable ("groupId") int groupId ,
                                       @PathVariable ("goalId") int goalId){

        //골정보 및 그 밑에 todos 호출
        saveGroupResDto saveGroupResDto=todoGroupsService.save(memberDetails.getMemberId(),
                saveReqDto,groupId,goalId);

        return ResponseEntity.ok(saveGroupResDto);

    }


    @GetMapping("/{groupId}/goals/{goalId}/todos")
    @Operation(summary = "{그룹} 할일 조회", description = "그룹 할일 조회 API")
    public ResponseEntity<?> gettodos(@AuthenticationPrincipal MemberDetails memberDetails){


        getAllGroupResDto getAllGroupResDto=todoGroupsService.getAll(memberDetails.getMemberId());


        return ResponseEntity.ok(getAllGroupResDto);


        //골정보 및 그 밑에 todos 호출


    }


    @PatchMapping("/{groupId}/{goalId}/todos/{todoId}")
    @Operation(summary = "{그룹} 할일  수정 조회", description = "그룹 할일 수정 조회 API")
    public ResponseEntity<?> savetodos(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody saveReqDto saveReqDto){

        //골정보 및 그 밑에 todos 호출


    }

    @DeleteMapping("/{groupId}/{goalId}/todos/{todoId}")
    @Operation(summary = "{그룹} 할일  삭제 조회", description = "그룹 할일 삭제 조회 API")
    public ResponseEntity<?> deleltetodos(@AuthenticationPrincipal MemberDetails memberDetails,
                                          @PathVariable ("groupId") int groupId ,
                                          @PathVariable ("goalId") int goalId){

        todoGroupsService.deletetodos(memberDetails.getMemberId(),groupId,goalId);

    }



}

