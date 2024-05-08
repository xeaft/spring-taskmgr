package dev.kvejp.taskmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.kvejp.taskmgr.entity.UserDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {

    @Override
    List<UserDTO> findAll();
    Optional<UserDTO> findByUsername(String username);
}
