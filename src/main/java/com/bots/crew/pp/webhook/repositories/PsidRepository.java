package com.bots.crew.pp.webhook.repositories;


import com.bots.crew.pp.webhook.enteties.db.MessengerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsidRepository extends JpaRepository<MessengerUser, String>{
}
