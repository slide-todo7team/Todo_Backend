package com.example.todo_Backend.User.Member.service;

import com.example.todo_Backend.Common.security.signin.service.JwtService;
import com.example.todo_Backend.User.Member.Dto.MemberFinalSignUpResDto;
import com.example.todo_Backend.User.Member.Dto.MemberSignUpDto;
import com.example.todo_Backend.User.Member.Dto.MemberTokenResDto;
import com.example.todo_Backend.User.Member.entity.Member;
import com.example.todo_Backend.User.Member.exception.DuplicationMemberEmailException;
import com.example.todo_Backend.User.Member.exception.NotEmailFormException;
import com.example.todo_Backend.User.Member.exception.NotFoundException;
import com.example.todo_Backend.User.Member.exception.NotFoundRefreshToken;
import com.example.todo_Backend.User.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public MemberFinalSignUpResDto createMember(MemberSignUpDto memberSignUpDto) {

        // 이메일 형식 체크
        if (!isValidEmail(memberSignUpDto.getEmail())) {
            throw new NotEmailFormException();
        }

        // 이메일 중복
        if(memberRepository.existsByEmail(memberSignUpDto.getEmail()))
            throw new DuplicationMemberEmailException(memberSignUpDto.getEmail());

        Member member = memberSignUpDto.toEntity();
        member.passwordEncode(encoder);

        Member savemember=memberRepository.save(member);

        return MemberFinalSignUpResDto.builder()
                .id(savemember.getMemId())
                .email(savemember.getEmail())
                .name(savemember.getName())
                .createdAt(savemember.getCreateDt())
                .updatedAt(savemember.getUpdateDt())
                        .build();

    }



    public MemberFinalSignUpResDto getMember(String username) {

        Optional<Member> member=memberRepository.findByEmail(username);
        member.orElseThrow(
                ()-> new NotFoundException(username)
        );

        return MemberFinalSignUpResDto.builder()
                .id(member.get().getMemId())
                .email(member.get().getEmail())
                .name(member.get().getName())
                .createdAt(member.get().getCreateDt())
                .updatedAt(member.get().getUpdateDt())
                .build();

    }

    public MemberTokenResDto getTokens(String refreshToken, String email) {

        Optional<Member> member= memberRepository.findByRefreshToken(refreshToken);

        member.orElseThrow(NotFoundRefreshToken::new
        );

        String accessToken = jwtService.createAccessToken(email
        );
        

        return
                MemberTokenResDto
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(member.get().getRefreshToken())
                        .build();


    }

    // 이메일 형식 검증 메서드
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // 기본 이메일 정규 표현식
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
