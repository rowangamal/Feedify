package com.example.backend.Feed.Criterias;

import com.example.backend.entities.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileCriteriaTest {
    @Test
    void testMeetCriteria() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Root<Post> root = mock(Root.class);
        Path<Object> userPath = mock(Path.class);
        Path<Object> idPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("user")).thenReturn(userPath);
        when(userPath.get("id")).thenReturn(idPath);
        when(cb.equal(idPath, 1)).thenReturn(predicate);

        UserProfileCriteria userProfileCriteria = new UserProfileCriteria(1);

        Predicate result = userProfileCriteria.meetCriteria(cb, root);

        assertNotNull(result, "Predicate should not be null");
        assertEquals(predicate, result, "Should return the expected predicate");

        verify(root).get("user");
        verify(userPath).get("id");
        verify(cb).equal(idPath, 1);
    }

}