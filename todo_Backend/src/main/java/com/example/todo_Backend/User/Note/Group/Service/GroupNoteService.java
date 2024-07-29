package com.example.todo_Backend.User.Note.Group.Service;

import com.example.todo_Backend.User.Note.Group.Dto.in.SaveNoteReqDto;
import com.example.todo_Backend.User.Note.Group.Dto.out.SaveNoteResDto;
import com.example.todo_Backend.User.Note.Group.Entity.GroupNote;
import com.example.todo_Backend.User.Note.Group.Repository.GroupNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GroupNoteService {


    private  final GroupNoteRepository groupNoteRepository;

    public SaveNoteResDto save(int groupId, int goalId, int todoId, SaveNoteReqDto saveNoteReqDto, Long memberId) {

        GroupNote groupNote=GroupNote.builder()
//                .groupModel()
                .title(saveNoteReqDto.getTitle())
                .updateAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .linkUrl(saveNoteReqDto.getLinkUrl())
                .content(saveNoteReqDto.getContent())
                .memId(memberId)
                .build();

        GroupNote savegroupNote=groupNoteRepository.save(groupNote);

        return  // return 함수 설정
    }






}
