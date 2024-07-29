package com.example.todo_Backend.group.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_user")
public class GroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "group_id")
//    private Long groupId;
//
//    @Column(name = "member_id")
//    private Long memberId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Setter
    @Column(name = "member_id")
    private Long memberId;

    @Setter
    @Column(name = "is_leader")
    private Integer isLeader = 0;

    @Column(name = "contribution_rank")
    private Integer contributionRank = 10;

    @Setter
    private String color;

    //    public GroupUser(Long groupId, Long memberId) {
//        this.groupId = groupId;
//        this.memberId = memberId;
//    }

}
