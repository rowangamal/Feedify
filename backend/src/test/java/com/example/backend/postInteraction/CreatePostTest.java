package com.example.backend.postInteraction;

import com.example.backend.entities.Post;
import com.example.backend.entities.PostType;
import com.example.backend.exceptions.PostOutOfLimitException;
import com.example.backend.exceptions.PostWithNoType;
import com.example.backend.exceptions.PostWithZeroContent;
import com.example.backend.postInteractions.CreatePostCommand;
import com.example.backend.repositories.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreatePostTest {
    @InjectMocks
    CreatePostCommand createPostCommandMocked;
    @Mock
    PostRepository postRepository;
    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
    }
    @Test
    public void emptyPost() {

        Post post = new Post();
        CreatePostCommand createPostCommand = new CreatePostCommand(post, null);

        assertThrows(NullPointerException.class, createPostCommand::execute);
    }
    @Test
    public void emptyContentPost() {

        Post post = new Post();
        post.setContent("");
        CreatePostCommand createPostCommand = new CreatePostCommand(post, null);

        var msg = assertThrows(PostWithZeroContent.class, createPostCommand::execute);
        assertEquals("Post content is empty", msg.getMessage());
    }
    @Test
    public void emptyTypePost() {
        Post post = new Post();
        post.setContent("I am Rafy");
        post.setPostTypes(new ArrayList<>());
        CreatePostCommand createPostCommand = new CreatePostCommand(post, null);

        var msg = assertThrows(PostWithNoType.class, createPostCommand::execute);
        assertEquals("Post type is empty", msg.getMessage());
    }
    @Test
    public void normalPost() {

        Post post = new Post();
        PostType postType = new PostType(1);
        List<PostType> postTypes = new ArrayList<>();
        postTypes.add(postType);
        post.setContent("I am Rafy");
        post.setPostTypes(postTypes);

        when(postRepository.save(post)).thenReturn(post);
        CreatePostCommand createPostCommand = new CreatePostCommand(post, postRepository);
        createPostCommand.execute();

        verify(postRepository ,times(1)).save(post);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(mocks != null) {
            mocks.close();
        }
    }
        @Test
    public void outOfBoundPost() {
        Post post = new Post();
        post.setContent("How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries." +
                "How to convert characters to words?\n" +
                "There is a box present known as a character box in which you have to enter a number. We have designed the tool in such a way that it will show an error if you type anything apart from numbers in the character box.\n" +
                "\n" +
                "In simple language, it does not accept alphabets and unique symbols. Once you enter characters in this box you will be able to see words according to the number of characters you fill in. Our tool will give you a range of two numbers. Your word will fall in between these two numbers.\n" +
                "\n" +
                "For example – If you “type the number 14” in your character box. Then our tool will give you a range between “2-3 words” for the character.\n" +
                "\n" +
                "There are around 5 to 6 characters in a word or text which includes spaces as well as punctuation. The average character count for every word and sentence is different and varies in different countries.");
        CreatePostCommand createPostCommand = new CreatePostCommand(post, null);
        var msg = assertThrows(PostOutOfLimitException.class, createPostCommand::execute);
        assertEquals("Post content is out of limit", msg.getMessage());
        }


}
