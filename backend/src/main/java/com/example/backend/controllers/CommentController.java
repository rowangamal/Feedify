package com.example.backend.controllers;
import com.example.backend.dtos.CommentDTO;
import com.example.backend.exceptions.CommentNotFoundException;
import com.example.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> addComment(@RequestBody CommentDTO commentDTO){
        try{
            commentService.addComment(commentDTO);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch(NullPointerException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during addComment: "+ e);
            return ResponseEntity.badRequest().body("Unexpected error occurred");
        }
        return ResponseEntity.ok().body("Comment added");
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteComment(@RequestParam(value = "id") long id){
        try{
            commentService.deleteComment(id);
        }
        catch(CommentNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during deleteComment: "+ e);
            return ResponseEntity.badRequest().body("Unexpected error occurred");
        }
        return ResponseEntity.ok().body("Comment deleted");
    }

    @GetMapping
    public ResponseEntity<Object> getCommentsByPostId(@RequestParam(value = "postId") long postId){
        try{
            return ResponseEntity.ok().body(commentService.getCommentsByPostId(postId));
        }
        catch(CommentNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during getCommentsByPostId: "+ e);
            return ResponseEntity.badRequest().body("Unexpected error occurred");
        }
    }
}
