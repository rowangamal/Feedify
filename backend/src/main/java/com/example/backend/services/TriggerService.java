package com.example.backend.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TriggerService {

    @PersistenceContext
    private EntityManager entityManager;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void createTriggersOnStartup() {
        createLikeTrigger();
        createCommentTrigger();
        removeLikeTrigger();
        removeCommentTrigger();
    }

    @Transactional
    public void createLikeTrigger() {
        String sql = """
                    CREATE TRIGGER IF NOT EXISTS increment_like_count
                        AFTER INSERT ON likes
                        FOR EACH ROW
                    BEGIN
                        UPDATE post
                        SET likes_count = likes_count + 1
                        WHERE id = NEW.post_id;
                    END;
        """;
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void removeLikeTrigger() {
        String sql = """
                    CREATE TRIGGER IF NOT EXISTS decrement_like_count
                        AFTER DELETE ON likes
                        FOR EACH ROW
                    BEGIN
                        UPDATE post
                        SET likes_count = likes_count - 1
                        WHERE id = OLD.post_id;
                    END;
        """;
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void createCommentTrigger() {
        String sql = """
                    CREATE TRIGGER IF NOT EXISTS increment_comment_count
                        AFTER INSERT ON comment
                        FOR EACH ROW
                    BEGIN
                        UPDATE post
                        SET comments_count = comments_count + 1
                        WHERE id = NEW.post_id;
                    END;
        """;
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void removeCommentTrigger() {
        String sql = """
                    CREATE TRIGGER IF NOT EXISTS decrement_comment_count
                        AFTER DELETE ON comment
                        FOR EACH ROW
                    BEGIN
                        UPDATE post
                        SET comments_count = comments_count - 1
                        WHERE id = OLD.post_id;
                    END;
        """;
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}