package com.example.todo_Backend.goal.service;

import com.example.todo_Backend.goal.dto.*;
import com.example.todo_Backend.goal.entity.GroupGoal;
import com.example.todo_Backend.goal.exception.NotFoundGoalException;
import com.example.todo_Backend.goal.exception.TitleLengthException;
import com.example.todo_Backend.goal.repository.GroupGoalRepository;
import com.example.todo_Backend.group.entity.Group;
import com.example.todo_Backend.group.entity.GroupUser;
import com.example.todo_Backend.group.repository.GroupRepository;
import com.example.todo_Backend.group.repository.GroupUserRepository;
import com.example.todo_Backend.todo.entity.GroupTodo;
import com.example.todo_Backend.todo.repository.GroupTodoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupGoalService {
    private final GroupGoalRepository groupGoalRepository;
    private final GroupTodoRepository groupTodoRepository;
    private final GroupRepository groupRepository;
    private final GoalProgressService goalProgressService;
    private final GroupUserRepository groupUserRepository;

    public GroupGoalService(GroupGoalRepository groupGoalRepository, GroupTodoRepository groupTodoRepository, GroupRepository groupRepository, GoalProgressService goalProgressService, GroupUserRepository groupUserRepository) {
        this.groupGoalRepository = groupGoalRepository;
        this.groupTodoRepository = groupTodoRepository;
        this.groupRepository = groupRepository;
        this.goalProgressService = goalProgressService;
        this.groupUserRepository = groupUserRepository;
    }

    public GroupGoalDto createGroupGoal(Long groupId, String title, Long userId) {
        checkTitleLength(title);
        GroupGoalDto groupGoalDto = GroupGoalDto.builder()
                .title(title)
                .groupId(groupId)
                .build();

        GroupGoal groupGoal = new GroupGoal(groupGoalDto);
        GroupGoal saveGroupGoal = groupGoalRepository.save(groupGoal);

        return new GroupGoalDto(saveGroupGoal);
    }

    public Map<String, List<GroupGoalDto>> getGroupGoals(Long groupId) {
        List<GroupGoal> groupGoals = groupGoalRepository.findByGroupId(groupId);

        List<GroupGoalDto> groupGoalDtos =  groupGoals.stream()
                .map(goal-> new GroupGoalDto(
                        goal.getTitle(),
                        goal.getId(),
                        goal.getGroupId(),
                        goal.getCreatedAt(),
                        goal.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        Map<String, List<GroupGoalDto>> map = new HashMap<>();
        map.put("groupGoals", groupGoalDtos);

        return map;
    }

    public CursorResponseDto<GroupGoalTodoDto> getAllGroupGoalTodo(Long userId, Long groupId, Long cursor, int limit) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        // 그룹 목표 조회
        List<GroupGoal> groupGoals;
        if (cursor != null) {
            // 커서가 null이 아닐 때, 커서를 기준으로 목표를 조회
            groupGoals = groupGoalRepository.findAllByGroupIdAndIdGreaterThan(groupId, cursor, PageRequest.of(0, limit));
        } else {
            // 처음 시작할 때
            groupGoals = groupGoalRepository.findAllByGroupId(groupId, PageRequest.of(0, limit));
        }

        List<GroupGoalTodoDto> groupGoalDtos = new ArrayList<>();

        // 그룹 멤버 정보 가져오기
        List<GroupUser> groupUsers = groupUserRepository.findAllByGroupId(groupId);

        for (GroupGoal groupGoal : groupGoals) {
            // 그룹 목표와 연관된 할일들 조회
            List<GroupTodo> groupTodos = groupTodoRepository.findAllByGoalId(groupGoal.getId());

            List<GroupProgressDto.GroupGoalMemDto> groupGoalMemDtos = groupUsers.stream()
                    .map(user -> {
                        Integer contributionPercent = goalProgressService.calContributionPercent(groupTodos, user.getId());
                        return GroupProgressDto.GroupGoalMemDto.builder()
                                .name(user.getMemberName())
                                .contributionPercent(contributionPercent)
                                .color(user.getColor())
                                .build();
                    })
                    .collect(Collectors.toList());

            GroupProgressDto groupProgressDto = GroupProgressDto.builder()
                    .completedPercent(goalProgressService.calCompletedPercent(groupTodos))
                    .memebers(groupGoalMemDtos)
                    .build();

            // 할일->DTO 로 변환
            List<GroupGoalTodoDto.GroupTodoDto> groupTodoDtos = groupTodos.stream()
                    .map(todo -> GroupGoalTodoDto.GroupTodoDto.builder()
                            .noteId(todo.getNoteId())
                            .done(todo.getDone())
                            .title(todo.getTitle())
                            .id(todo.getId())
                            .build())
                    .toList();

            GroupGoalTodoDto groupGoalTodoDto = GroupGoalTodoDto.builder()
                    .title(groupGoal.getTitle())
                    .id(groupGoal.getId())
                    .updatedAt(groupGoal.getUpdatedAt())
                    .createdAt(groupGoal.getCreatedAt())
                    .todos(groupTodoDtos)
                    .progress(groupProgressDto)
                    .build();

            groupGoalDtos.add(groupGoalTodoDto); // to do 리스트에 추가
        }

        // 다음 커서를 설정하기 위해 마지막 목표의 ID를 가져옵니다.
        Long nextCursor = groupGoals.isEmpty() ? null : groupGoals.get(groupGoals.size() - 1).getId();

        return CursorResponseDto.<GroupGoalTodoDto>builder()
                .nextCursor(nextCursor)
                .totalCount(groupGoalRepository.countByGroupId(groupId)) // 전체 목표 수
                .individualGoalDtos(groupGoalDtos)
                .build();
    }

    public GroupGoalDto updateGroupGoal(Long groupId, Long goalId, String title) {
        checkTitleLength(title);

        GroupGoal groupGoal = groupGoalRepository.findByIdAndGroupId(goalId,groupId).orElseThrow(() -> new NotFoundGoalException(goalId));

        groupGoal.setTitle(title);
        GroupGoal saveGroupGoal = groupGoalRepository.save(groupGoal);
        return new GroupGoalDto(saveGroupGoal);
    }

    public GoalResponse deleteGroupGoal(Long groupId, Long goalId) {
        GroupGoal groupGoal = groupGoalRepository.findByIdAndGroupId(goalId,groupId).orElseThrow(() -> new NotFoundGoalException(goalId));
        groupGoalRepository.delete(groupGoal);
        return GoalResponse.ok();
    }

    public void checkTitleLength(String title){
        if(title.length() > 30){
            throw new TitleLengthException(title);
        }
    }
}
