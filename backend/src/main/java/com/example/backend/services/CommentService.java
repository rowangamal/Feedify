package com.example.backend.services;

import com.example.backend.dtos.CommentDTO;
import com.example.backend.dtos.CommentsDTO;
import com.example.backend.entities.Comment;
import com.example.backend.exceptions.CommentNotFoundException;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.repositories.CommentRepository;
import com.example.backend.timeCreation.TimeCreationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(CommentDTO commentDTO) {
        try{
            commentRepository.addComment(
                    commentDTO.getContent(),
                    new Timestamp(System.currentTimeMillis()),
                    commentDTO.getPostId(),
                    commentDTO.getUserId()
            );
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Post or User not found", e);
        }
        catch(NullPointerException e){
            throw new NullPointerException("Invalid data format");
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during addComment: "+ e);
            throw new RuntimeException("Unexpected error occurred");
        }
    }

    public void deleteComment(long id) {
        try{
            int rowsAffected = commentRepository.removeComment(id);
            if (rowsAffected == 0)
                throw new CommentNotFoundException("No comment found with id: " + id);
        }
        catch(CommentNotFoundException e){
            throw e;
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Invalid data format", e);
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during deleteComment: "+ e);
            throw new RuntimeException("Unexpected error occurred");
        }
    }

    public CommentsDTO[] getCommentsByPostId(long postId) {
        try{
            List<Optional<Comment>> comments = commentRepository.getCommentsByPostId(postId);

            List<CommentsDTO> commentsDTOs = comments.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(comment -> new CommentsDTO(
                            comment.getId(),
                            comment.getContent(),
                            comment.getUser().getUsername(),
                            comment.getUser().getPictureURL(),
                            TimeCreationBuilder.getCreationTime(comment.getCreatedAt())
                    ))
                    .toList();

            return commentsDTOs.toArray(new CommentsDTO[0]);
        }
        catch(DataRetrievalFailureException e){
            throw new PostNoFoundException("Post not found");
        }
        catch(Exception e){
            System.out.println("Unexpected error occurred during getCommentsByPostId: "+ e);
            throw new RuntimeException("Unexpected error occurred");
        }
    }
}
