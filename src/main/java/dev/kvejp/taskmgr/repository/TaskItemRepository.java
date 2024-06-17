package dev.kvejp.taskmgr.repository;

import dev.kvejp.taskmgr.entity.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {
    @Override
    List<TaskItem> findAll();
}
