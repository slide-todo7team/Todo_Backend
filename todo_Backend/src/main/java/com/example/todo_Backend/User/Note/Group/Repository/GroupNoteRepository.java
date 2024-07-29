package com.example.todo_Backend.User.Note.Group.Repository;

import com.example.todo_Backend.User.Note.Group.Entity.GroupNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupNoteRepository extends JpaRepository<GroupNote,Long> {
}
