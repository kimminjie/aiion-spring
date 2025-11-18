package site.aiion.soccerService.soccer.schedule.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.aiion.soccerService.soccer.schedule.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    /**
     * Search schedules by keyword (date, category, team name)
     */
    @Query("SELECT s FROM Schedule s WHERE " +
            "COALESCE(s.sche_date, '') LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(COALESCE(s.gubun, '')) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "COALESCE(s.hometeam_uk, '') LIKE CONCAT('%', :keyword, '%') OR " +
            "COALESCE(s.awayteam_uk, '') LIKE CONCAT('%', :keyword, '%')")
    List<Schedule> findByKeyword(@Param("keyword") String keyword);
}
