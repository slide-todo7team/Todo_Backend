package com.example.todo_Backend.User.Todo.Service;

//import com.example.todo_Backend.User.Todo.Dto.Group.in.saveReqDto;
//import com.example.todo_Backend.User.Todo.Dto.Group.out.getAllGroupResDto;
//import com.example.todo_Backend.User.Todo.Dto.Group.out.saveGroupResDto;
//import com.example.todo_Backend.User.Todo.Entity.GroupModel;
//import com.example.todo_Backend.User.Todo.Repository.TodoGroupRepository;
import com.example.todo_Backend.User.Todo.Dto.Group.in.saveReqDto;
import com.example.todo_Backend.User.Todo.Dto.Group.out.saveGroupResDto;
import com.example.todo_Backend.User.Todo.Entity.GroupModel;
import com.example.todo_Backend.User.Todo.Repository.TodoGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoGroupsService {

    private final TodoGroupRepository todoGroupRepository;
    public saveGroupResDto save(Long memberId, saveReqDto saveReqDto, int groupId, int goalId) {

        GroupModel groupModel= GroupModel.builder()
                .groupId(groupId)
                .title(saveReqDto.getTitle())
                .createAt(LocalDateTime.now())
                .memId(memberId)
                .updateAt(LocalDateTime.now())
                .build();
        //progress 물어보고 추가

        GroupModel saveGroupModel=todoGroupRepository.save(groupModel);

        return saveGroupResDto.of(saveGroupModel.getId(),true,saveGroupModel.getTitle(),
                saveGroupModel.getMemId(),saveGroupModel.getUpdateAt(),saveGroupModel.getCreateAt());
    }

    public getAllGroupResDto getAll(Long memberId) {


        List<GroupModel> groupModels=todoGroupRepository.findAllByMemberId(memberId);

        //goal이랑 group 들 return




    }

    public void deletetodos(Long memberId, int groupId, int goalId) {
    }
}

