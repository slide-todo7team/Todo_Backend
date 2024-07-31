package com.example.todo_Backend.goal.service;

import com.example.todo_Backend.todo.entity.GroupTodo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalProgressService {

   //그룹 목표의 할일 완료 퍼센트
    public Integer calCompletedPercent(List<GroupTodo> groupTodos){
        Integer totalCount = groupTodos.size();
        Integer doneCount = (int) groupTodos.stream()
                .filter(todo -> todo.getDone() == 1)
                .count();

        if(totalCount == 0){
            return 0;
        }

        return (doneCount * 100) / totalCount;
    }

    //목표 내의 userId 의 기여 퍼센트
    public Integer calContributionPercent(List<GroupTodo> groupTodos, Long userId){
        Integer totalDoneCount = groupTodos.size();
        Integer contributionCount = (int) groupTodos.stream()
                .filter(todo -> todo.getDone() == 1 && todo.getUserId() == userId)
                .count();

        if(totalDoneCount == 0){
            return 0;
        }

        return (contributionCount * 100) / totalDoneCount;

    }
}
