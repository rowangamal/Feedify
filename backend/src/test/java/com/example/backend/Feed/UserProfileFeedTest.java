//package com.example.backend.Feed;
//
//import com.example.backend.entities.Post;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class UserProfileFeedTest {
//
//    @Test
//    void testFilter() {
//        // Mock EntityManager and Criteria API components
//        EntityManager em = mock(EntityManager.class);
//        CriteriaBuilder cb = mock(CriteriaBuilder.class);
//        CriteriaQuery<Post> cq = mock(CriteriaQuery.class);
//        Root<Post> postRoot = mock(Root.class);
//        Predicate predicate = mock(Predicate.class);
//        TypedQuery<Post> query = mock(TypedQuery.class);
//
//        // Mock data
//        Post post1 = new Post(); // Set properties if needed
//        Post post2 = new Post();
//        List<Post> mockPosts = Arrays.asList(post1, post2);
//
//        // Define mock behavior
//        when(em.getCriteriaBuilder()).thenReturn(cb);
//        when(cb.createQuery(Post.class)).thenReturn(cq);
//        when(cq.from(Post.class)).thenReturn(postRoot);
//        when(cb.equal(postRoot.get("user").get("id"), 1)).thenReturn(predicate);
//        when(cq.where(predicate)).thenReturn(cq);
//        when(em.createQuery(cq)).thenReturn(query);
//        when(query.getResultList()).thenReturn(mockPosts);
//
//        // Create the class under test
//        UserProfileFeed userProfileFeed = new UserProfileFeed();
//
//        // Call the method
//        List<Post> result = userProfileFeed.filter(null, 1, em);
//
//        // Assertions
//        assertEquals(2, result.size(), "Should return 2 posts");
//        assertEquals(mockPosts, result, "Should return the expected posts");
//
//        // Verify interactions
//        verify(em).getCriteriaBuilder();
//        verify(cb).createQuery(Post.class);
//        verify(cq).from(Post.class);
//        verify(cb).equal(postRoot.get("user").get("id"), 1);
//        verify(cq).where(predicate);
//        verify(em).createQuery(cq);
//        verify(query).getResultList();
//    }
//}
