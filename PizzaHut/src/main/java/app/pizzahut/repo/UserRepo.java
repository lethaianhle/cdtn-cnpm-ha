package app.pizzahut.repo;

import app.pizzahut.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {

    UserModel findByMobile(String mobile);

    Long countAllByMobile(String mobile);
}
