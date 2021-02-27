package com.example.sicrediapp.repositories;

import com.example.sicrediapp.domains.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
