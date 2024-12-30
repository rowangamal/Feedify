CREATE TRIGGER IF NOT EXISTS increment_like_count
    AFTER INSERT ON likes
    FOR EACH ROW
BEGIN
    UPDATE post
    SET likes_count = likes_count + 1
    WHERE id = NEW.post_id;
END;

CREATE TRIGGER IF NOT EXISTS decrement_like_count
    AFTER DELETE ON likes
    FOR EACH ROW
BEGIN
    UPDATE post
    SET likes_count = likes_count - 1
    WHERE id = OLD.post_id;
END;