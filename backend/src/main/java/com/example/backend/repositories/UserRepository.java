package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUsersByEmail(String email);

    Optional<User> findUsersByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO user_interest (user_id, topic_id) VALUES (:userId, :postTypeId)", nativeQuery = true)
    void addInterest(@Param("userId") long userId, @Param("postTypeId") long postTypeId);


    @Modifying
    @Query(value = "DELETE FROM user_interest WHERE user_id = :userId", nativeQuery = true)
    void removeInterest(@Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM user_interest WHERE user_id = :userId ", nativeQuery = true)
    void removeInterestsBatch(@Param("userId") long userId);

    @Modifying
    @Query(value = "SELECT topic_id FROM user_interest WHERE user_id = :userId ", nativeQuery = true)
    List<Long> getAllInterests(@Param("userId") long userId);
}