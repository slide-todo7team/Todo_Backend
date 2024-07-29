package com.example.todo_Backend.User.Note.Indiviual.Controller;

import com.example.todo_Backend.User.Member.entity.MemberDetails;
import com.example.todo_Backend.User.Note.Group.Dto.in.SaveNoteReqDto;
import com.example.todo_Backend.User.Note.Group.Dto.out.GetNotesResDto;
import com.example.todo_Backend.User.Note.Group.Dto.out.PatchNoteResDto;
import com.example.todo_Backend.User.Note.Group.Dto.out.SaveNoteResDto;
import com.example.todo_Backend.User.Note.Group.Entity.GroupNote;
import com.example.todo_Backend.User.Note.Group.Service.GroupNoteService;
import com.example.todo_Backend.User.Note.Indiviual.Service.IndividualService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "그룹 노트 API")
@RequestMapping("/api/note/individual")
public class IndiviualNoteController {


    private final IndividualService individualService;

    @RequestMapping("/savenote")
    public ResponseEntity<?> savenote(
                                      @RequestParam int goalId,
                                      @RequestParam int todoId,
                                      @RequestBody SaveNoteReqDto saveNoteReqDto,
                                      @AuthenticationPrincipal MemberDetails memberDetails

    ){

        SaveNoteResDto saveNoteResDto=groupNoteService.save(goalId,todoId,saveNoteReqDto,memberDetails.getMemberId());
        return ResponseEntity.ok(saveNoteResDto);
    }

    @GetMapping("/getnotes")
    public ResponseEntity<?> getnotes(@RequestParam int goalId,
                                      @AuthenticationPrincipal MemberDetails memberDetails){


        GetNotesResDto getNotesResDt=groupNoteService.getNotes(individualId,goalId,memberDetails.getMemberId());


    }

    @GetMapping("/getnote")
    public ResponseEntity<?> getnote(@RequestParam int goalId,@RequestParam int todoId,@AuthenticationPrincipal MemberDetails memberDetails){


        GroupNote groupNote=groupNoteService.getNote(goalId,todoId,memberDetails.getMemberId());

    }


    @PatchMapping("/patchnote")
    public ResponseEntity<?> patchnote(@RequestParam int goalId,
                                       @RequestParam int todoId,
                                       @RequestParam int noteId,@AuthenticationPrincipal MemberDetails memberDetails){

        PatchNoteResDto patchNoteResDto=groupNoteService.patchnote(groupId,goalId,todoId,noteId,memberDetails);

        return ResponseEntity.ok(patchNoteResDto);


    }


    @DeleteMapping("/deletenote")
    public ResponseEntity<?> deletenote(@RequestParam int goalId,
                                        @RequestParam int todoId,
                                        @RequestParam int noteId,@AuthenticationPrincipal MemberDetails memberDetails){


        groupNoteService.delete(groupId,goalId,todoId,noteId,memberDetails.getMemberId());

        return ResponseEntity.ok("삭제");


    }
}
