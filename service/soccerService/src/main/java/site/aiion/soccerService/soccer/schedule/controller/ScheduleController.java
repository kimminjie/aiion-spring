package site.aiion.soccerService.soccer.schedule.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.schedule.domain.ScheduleModel;
import site.aiion.soccerService.soccer.schedule.service.ScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody ScheduleModel scheduleModel) {
        return scheduleService.findById(scheduleModel);
    }

    @GetMapping
    public Messenger findAll() {
        // 명확한 시작 표시 - 이 로그만 보이면 ScheduleController만 호출된 것
        System.out.println("\n\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("▶▶▶ [SCHEDULE CONTROLLER ONLY] /schedules 요청 - SCHEDULE 테이블만 출력 시작 ◀◀◀");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("시간: " + java.time.LocalDateTime.now());
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n");
        
        Messenger result = scheduleService.findAll();
        
        // 터미널에 테이블 데이터 출력
        if (result.getData() instanceof java.util.List) {
            @SuppressWarnings("unchecked")
            java.util.List<site.aiion.soccerService.soccer.schedule.domain.ScheduleModel> schedules = 
                (java.util.List<site.aiion.soccerService.soccer.schedule.domain.ScheduleModel>) result.getData();
            
            System.out.println("\n📊 SCHEDULE 테이블 데이터 (총 " + schedules.size() + "개):");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-6s %-12s %-15s %-15s %-10s %-10s%n", 
                "ID", "Sche_Date", "Hometeam_UK", "Awayteam_UK", "Home_Score", "Away_Score");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            
            for (var schedule : schedules) {
                System.out.printf("%-6d %-12s %-15s %-15s %-10s %-10s%n",
                    schedule.id != null ? schedule.id : 0,
                    schedule.sche_date != null ? schedule.sche_date : "-",
                    schedule.hometeam_uk != null ? schedule.hometeam_uk : "-",
                    schedule.awayteam_uk != null ? schedule.awayteam_uk : "-",
                    schedule.home_score != null ? schedule.home_score : "-",
                    schedule.away_score != null ? schedule.away_score : "-"
                );
            }
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
            System.out.println("▶▶▶ [SCHEDULE CONTROLLER ONLY] SCHEDULE 테이블 데이터 출력 완료 ◀◀◀");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n\n");
        }
        
        return result;
    }

    @PostMapping
    public Messenger save(@RequestBody ScheduleModel scheduleModel) {
        return scheduleService.save(scheduleModel);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<ScheduleModel> scheduleModelList) {
        return scheduleService.saveAll(scheduleModelList);
    }

    @PutMapping
    public Messenger update(@RequestBody ScheduleModel scheduleModel) {
        return scheduleService.update(scheduleModel);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody ScheduleModel scheduleModel) {
        return scheduleService.delete(scheduleModel);
    }

}
