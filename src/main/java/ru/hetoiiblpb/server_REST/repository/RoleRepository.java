package ru.hetoiiblpb.server_REST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.server_REST.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Transactional
    Role findByName(String name);

}
