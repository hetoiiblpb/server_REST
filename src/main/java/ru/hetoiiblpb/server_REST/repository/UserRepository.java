package ru.hetoiiblpb.server_REST.repository;

import com.sun.tracing.dtrace.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.server_REST.model.User;

import javax.persistence.QueryHint;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u join fetch u.roles where u.username=:username")
    User findByUsername(String username);

    @Override
    @Query("select distinct u from User u join fetch u.roles order by u.id")
    List<User> findAll();
}
