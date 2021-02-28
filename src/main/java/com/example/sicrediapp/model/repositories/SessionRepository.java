package com.example.sicrediapp.model.repositories;

import com.example.sicrediapp.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
