package site.aiion.soccerService.soccer.team.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.aiion.soccerService.soccer.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
    @Query("SELECT t FROM Team t WHERE t.team_uk = :teamUk")
    Optional<Team> findByTeam_uk(@Param("teamUk") String teamUk);

    /**
     * Search teams by keyword (team name, region, owner)
     */
    @Query("SELECT t FROM Team t WHERE " +
            "LOWER(COALESCE(t.team_name, '')) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(COALESCE(t.e_team_name, '')) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(COALESCE(t.region_name, '')) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(COALESCE(t.owner, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Team> findByKeyword(@Param("keyword") String keyword);
}
