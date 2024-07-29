package com.example.todo_Backend.User.Todo.Service;

import com.example.todo_Backend.User.Todo.Dto.individual.in.patchReqDto;
import com.example.todo_Backend.User.Todo.Dto.individual.in.saveIndividualReqDto;
import com.example.todo_Backend.User.Todo.Dto.individual.out.getTodoResDto;
import com.example.todo_Backend.User.Todo.Dto.individual.out.saveIndividualResDto;
//import com.example.todo_Backend.User.Todo.Dto.individual.out.savedPatchResDto;
//import com.example.todo_Backend.User.Todo.Entity.IndividualModel;
//import com.example.todo_Backend.User.Todo.Repository.TodoIndividualRepository;
//import lombok.RequiredArgsConstructor;
import com.example.todo_Backend.User.Todo.Repository.TodoIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoIndividualService {

    private final TodoIndividualRepository todoIndividualRepository;
    public saveIndividualResDto savetodo(Long memId, saveIndividualReqDto saveReqDto, int goalId) {

        IndividualModel individualModel=IndividualModel.builder()
                .done(true)
                .title(saveReqDto.getTitle())
                .updateAt(LocalDateTime.now())
                .userId(memId)
                .createAt(LocalDateTime.now())
                                //goal추가
                .build();

        IndividualModel savemodel=todoIndividualRepository.save(individualModel);

        return saveIndividualResDto.of(savemodel.getNoteId(),
                savemodel.isDone(),
                savemodel.getTitle(),
                savemodel.getUserId(),
                savemodel.getUpdateAt(),
                savemodel.getCreateAt());


    }

    public List<IndividualModel> getAlltodo() {

        return todoIndividualRepository.findAll();
    }

    public getTodoResDto gettodo(Long memberId) {
    }

    public savedPatchResDto patchtodo(Long memberId, int goalId, int todoId, patchReqDto patchReqDto) {
    }

    public void delelteTodo(Long memberId, int goalId, int todoId) {
        todoIndividualRepository.deleteAllById(memberId,goalId,todoId);
    }
}
