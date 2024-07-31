package com.example.todo_Backend.Common.security.signin.service;


import com.example.todo_Backend.User.Member.entity.Member;
import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Member.exception.NotFoundMemberByEmailException;
import com.example.todo_Backend.User.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberByEmailException(email));

        return MemberDetails.builder()
                .member(member)
                .build();
    }
}
