package com.example.todo_Backend.User.Member.Repository;


import com.example.todo_Backend.User.Member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {


    boolean existsByEmail(String email);


    Optional<Member> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken WHERE m.memId = :memId")
    void updateRefreshToken(@Param("memId") Long memId,@Param("refreshToken") String refreshToken);

    @Query("select m from Member m where m.refreshToken=refreshToken")
    Optional<Member> findByRefreshToken(@Param("refreshToken") String refreshToken);

    Member save(Member member);
}
