package com.example.todo_Backend.group.dto;
import com.example.todo_Backend.group.entity.Group;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class GroupInfoDto {
    private Long id;
    private String title;
    private String createUser;
    private String secretCode;
    private List<GroupMemberDto> members;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Getter
    @Setter
    @Builder
    public static class GroupMemberDto {
        private Long id;
        private Integer isLeader;
        private String name;
        private String color;
        private Integer contributionRank;
    }

    public GroupInfoDto(Group group) {
        this.id = group.getId();
        this.title = group.getTitle();
        this.createUser = group.getCreateUser();
        this.secretCode = group.getSecretCode();
        this.createdAt = group.getCreatedAt();
        this.updatedAt = group.getUpdatedAt();
    }
}
