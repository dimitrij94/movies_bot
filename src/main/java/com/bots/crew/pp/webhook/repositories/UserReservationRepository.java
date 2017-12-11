package com.bots.crew.pp.webhook.repositories;

import com.bots.crew.pp.webhook.enteties.db.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;

public interface UserReservationRepository extends JpaRepository<UserReservation, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_reservation(movie_id, user_psid, is_activated,timestamp) VALUES (?1,?2, FALSE, timestamp(NOW()))", nativeQuery = true)
    void createSetMovie(int movieId, String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  user_reservation SET cinema_id=?1 WHERE id=?2", nativeQuery = true)
    void updateSetCinema(int cinemaId, int reservationId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_reservation AS u SET u.session_id=?2 WHERE u.id=?1", nativeQuery = true)
    void updateSetSession(int id, int sessionId);

    @Query(value = "SELECT r.* FROM user_reservation AS r WHERE r.user_psid = ?1 AND r.is_activated=FALSE ORDER BY r.timestamp LIMIT 1", nativeQuery = true)
    UserReservation findLastReservationOfTheUser(String psid);

    /*
        @Query(value = "UPDATE user_reservation AS r SET r.cinema_id=?1 where r.id = " +
                "(SELECT ur.id from user_reservation as ur group by r.user_psid having max(r.timestamp) and r.user_psid=?2)", nativeQuery = true)
        UserReservation updateSetCinema(int cinema, String psid);
    */

}
