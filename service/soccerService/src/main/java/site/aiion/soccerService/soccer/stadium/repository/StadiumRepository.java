package site.aiion.soccerService.soccer.stadium.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.aiion.soccerService.soccer.stadium.domain.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long>, StadiumRepositoryCustom {
    @Query("SELECT s FROM Stadium s WHERE s.stadium_uk = :stadiumUk")
    Optional<Stadium> findByStadium_uk(@Param("stadiumUk") String stadiumUk);

    /**
     * Search stadiums by keyword (stadium name, address)
     */
    @Query("SELECT s FROM Stadium s WHERE " +
            "LOWER(COALESCE(s.stadium_name, '')) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(COALESCE(s.address, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Stadium> findByKeyword(@Param("keyword") String keyword);
}
