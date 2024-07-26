package com.example.todo_Backend.User.Member.Controller;

import com.example.todo_Backend.User.Member.Dto.*;
import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;



    @PostMapping("/user")
    @Operation(summary = "회원 가입", description = "회원 가입 API")
    public ResponseEntity<MemberFinalSignUpResDto> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {

        MemberFinalSignUpResDto finalSignUpResDto=memberService.createMember(memberSignUpDto);


        return ResponseEntity.ok(finalSignUpResDto);

    }

    @GetMapping("/user")
    @Operation(summary = "유저 찾기", description = "유저 찾기 API")
    public ResponseEntity<MemberFinalSignUpResDto> getUser(@AuthenticationPrincipal MemberDetails memberDetails) {

        MemberFinalSignUpResDto finalSignUpResDto=memberService.getMember(memberDetails.getUsername());

        return ResponseEntity.ok(finalSignUpResDto);

    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "회원 로그인 API")
    public ResponseEntity<MemberResponse> signIn(@RequestBody MemberSignInDto memberSignInDto) {
        
        return ResponseEntity.ok(MemberResponse.ok(""));
    }

    @GetMapping("/tokens")
    @Operation(summary = "회원 토큰 요청", description = "회원 정보 API")
    public ResponseEntity<MemberTokenResDto> getTokens(
            @AuthenticationPrincipal MemberDetails memberDetails,@RequestBody String refreshToken
    ) {

        MemberTokenResDto memberDto = memberService.getTokens(refreshToken,memberDetails.getUsername());

        return ResponseEntity.ok(memberDto);
    }


}
