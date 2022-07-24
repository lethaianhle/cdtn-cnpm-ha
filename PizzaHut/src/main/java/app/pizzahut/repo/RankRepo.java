package app.pizzahut.repo;

import app.pizzahut.model.RankModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankRepo extends JpaRepository<RankModel, Long> {

    Optional<RankModel> findRankModelByName(String name);

}
