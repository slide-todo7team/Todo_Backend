package com.example.todo_Backend.goal.service;

import com.example.todo_Backend.goal.dto.*;
import com.example.todo_Backend.goal.entity.IndividualGoal;
import com.example.todo_Backend.goal.exception.NotFoundGoalException;
import com.example.todo_Backend.goal.exception.TitleLengthException;
import com.example.todo_Backend.goal.repository.IndividualGoalRepository;
import com.example.todo_Backend.todo.dto.IndividualTodoDto;
import com.example.todo_Backend.todo.entity.IndividualTodo;
import com.example.todo_Backend.todo.repository.IndividualTodoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndividualGoalService {

    private final IndividualGoalRepository individualGoalRepository;
    private final IndividualTodoRepository individualTodoRepository;

    public IndividualGoalService(IndividualGoalRepository individualGoalRepository, IndividualTodoRepository individualTodoRepository) {
        this.individualGoalRepository = individualGoalRepository;
        this.individualTodoRepository = individualTodoRepository;
    }

    public IndividualGoalDto createGoal(GoalTitleDto goalTitleDto, Long userId){

        checkTitleLength(goalTitleDto.getTitle());

        IndividualGoalDto individualGoalDto = IndividualGoalDto.builder()
                .title(goalTitleDto.getTitle())
                .userId(userId)
                .build();

        IndividualGoal individualGoal = new IndividualGoal(individualGoalDto);
        IndividualGoal saveGoal = individualGoalRepository.save(individualGoal);

        return new IndividualGoalDto(saveGoal);
    }

    public Map<String, List<IndividualGoalDto>> getAllIndividualGoals(Long userId) {
        List<IndividualGoal> individualGoals = individualGoalRepository.findAllByUserId(userId);
        List<IndividualGoalDto> individualGoalDtos = new ArrayList<>();
        for (IndividualGoal individualGoal : individualGoals) {
            individualGoalDtos.add(new IndividualGoalDto(individualGoal));
        }

        Map<String, List<IndividualGoalDto>> goalList = new HashMap<>();
        goalList.put("individualGoals", individualGoalDtos);

        return goalList;
    }

    public IndividualGoalDto updateIndividualGoal(String goalTitle, Long goalId) {

        checkTitleLength(goalTitle);

        Optional<IndividualGoal> optionalGoal = individualGoalRepository.findById(goalId);

        if(optionalGoal.isEmpty()){
            throw new NotFoundGoalException(goalId);
        }


        IndividualGoal individualGoal = optionalGoal.get();

        individualGoal.setTitle(goalTitle);
        individualGoalRepository.save(individualGoal);
        return new IndividualGoalDto(individualGoal);
    }

    public GoalResponse deleteIndividualGoal(Long goalId) {
        Optional<IndividualGoal> optionalGoal = individualGoalRepository.findById(goalId);
        if(optionalGoal.isEmpty()){
            throw new NotFoundGoalException(goalId);
        }
        IndividualGoal individualGoal = optionalGoal.get();
        individualGoalRepository.delete(individualGoal);

        return GoalResponse.ok();
    }

    public CursorResponseDto<IndividualGoalTodoDto> getAllIndividualGoalsTodos(Long userId, Long cursor, int pageSize) {
        // 개인 목표 entity 조회
        List<IndividualGoal> individualGoals = individualGoalRepository.findAllByUserIdAndIdGreaterThan(userId, cursor, PageRequest.of(0, pageSize));

        List<IndividualGoalTodoDto> individualGoalDtos = new ArrayList<>();
        Long nextCursor = null;

        for (IndividualGoal individualGoal : individualGoals) {
            IndividualGoalTodoDto individualGoalTodoDto = IndividualGoalTodoDto.builder()
                    .title(individualGoal.getTitle())
                    .id(individualGoal.getId())
                    .userId(userId)
                    .updatedAt(individualGoal.getUpdatedAt())
                    .createdAt(individualGoal.getCreatedAt())
                    .progress(individualGoal.getProgress())
                    .build();

            // 할 일 리스트
            List<IndividualTodo> individualTodos = individualTodoRepository.findAllByGoalId(individualGoal.getId());

            List<IndividualGoalTodoDto.IndividualTodoDto> todoDtos = individualTodos.stream()
                    .map(todo -> IndividualGoalTodoDto.IndividualTodoDto.builder()
                            .id(todo.getId())
                            .noteId(todo.getNoteId())
                            .title(todo.getTitle())
                            .done(todo.getDone())
                            .build())
                    .collect(Collectors.toList());

            // 할 일 리스트 설정
            individualGoalTodoDto.setTodos(todoDtos);
            // DTO 리스트에 추가
            individualGoalDtos.add(individualGoalTodoDto);

            // 다음 커서 업데이트
            nextCursor = individualGoal.getId();
        }

        Long totalCount = individualGoalRepository.countByUserId(userId);

        // 다음 커서를 설정합니다. 목표가 없으면 null로 설정
        nextCursor = individualGoals.isEmpty() ? null : nextCursor;

        return new CursorResponseDto<>(nextCursor, totalCount, individualGoalDtos);
    }

//    public List<IndividualGoalTodoDto> getAllIndividualGoalsTodos(Long userId) {
//
//        //개인 목표 entity 조회
//        List<IndividualGoal> individualGoals = individualGoalRepository.findAllByUserId(userId);
//
//        List<IndividualGoalTodoDto> individualGoalDtos = new ArrayList<>();
//
//        for (IndividualGoal individualGoal : individualGoals) {
//            IndividualGoalTodoDto individualGoalTodoDto = IndividualGoalTodoDto.builder()
//                    .title(individualGoal.getTitle())
//                    .id(individualGoal.getId())
//                    .userId(userId)
//                    .updatedAt(individualGoal.getUpdatedAt())
//                    .createdAt(individualGoal.getCreatedAt())
//                    .progress(individualGoal.getProgress())
//                    .build();
//
//            //할 일 리스트
//            List<IndividualTodo> individualTodos = individualTodoRepository.findAllByGoalId(individualGoal.getId());
//
//            List<IndividualGoalTodoDto.IndividualTodoDto> todoDtos = individualTodos.stream()
//                    .map(todo -> IndividualGoalTodoDto.IndividualTodoDto.builder()
//                            .id(todo.getId())
//                            .noteId(todo.getNoteId())
//                            .title(todo.getTitle())
//                            .done(todo.getDone())
//                            .build())
//                    .collect(Collectors.toList());
//
//            // 할일 리스트 설정
//            individualGoalTodoDto.setTodos(todoDtos);
//            // DTO 리스트에 추가
//            individualGoalDtos.add(individualGoalTodoDto);
//        }
//        return individualGoalDtos;
//    }


    public void checkTitleLength(String title){
        if(title.length() > 30){
            throw new TitleLengthException(title);
        }
    }


}
