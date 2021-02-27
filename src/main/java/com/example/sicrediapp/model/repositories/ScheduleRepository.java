package com.example.sicrediapp.model.repositories;

import com.example.sicrediapp.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
