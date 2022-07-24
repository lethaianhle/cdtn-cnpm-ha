package app.pizzahut.repo;

import app.pizzahut.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Long> {

    Optional<RoleModel> findRoleModelByName(String name);

}
