package com.example.todo_Backend.User.Member.Controller;


import com.example.todo_Backend.User.Member.Dto.MemberResponse;
import com.example.todo_Backend.User.Member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 API")
@RequestMapping("/api/admin")
public class AdminController {


}
