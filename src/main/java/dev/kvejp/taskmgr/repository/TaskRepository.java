package dev.kvejp.taskmgr.repository;

import dev.kvejp.taskmgr.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    List<Task> findAll();
}
