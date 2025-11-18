package site.aiion.soccerService.soccer.team.controller;

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
import site.aiion.soccerService.soccer.team.domain.TeamModel;
import site.aiion.soccerService.soccer.team.service.TeamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody TeamModel teamModel) {
        return teamService.findById(teamModel);
    }

    @GetMapping
    public Messenger findAll() {
        // 명확한 시작 표시 - 이 로그만 보이면 TeamController만 호출된 것
        System.out.println("\n\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("▶▶▶ [TEAM CONTROLLER ONLY] /team 요청 - TEAM 테이블만 출력 시작 ◀◀◀");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("시간: " + java.time.LocalDateTime.now());
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n");
        
        Messenger result = teamService.findAll();
        
        // 터미널에 테이블 데이터 출력
        if (result.getData() instanceof java.util.List) {
            @SuppressWarnings("unchecked")
            java.util.List<site.aiion.soccerService.soccer.team.domain.TeamModel> teams = 
                (java.util.List<site.aiion.soccerService.soccer.team.domain.TeamModel>) result.getData();
            
            System.out.println("\n📊 TEAM 테이블 데이터 (총 " + teams.size() + "개):");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-6s %-15s %-20s %-25s %-15s%n", 
                "ID", "Team_UK", "Team_Name", "E_Team_Name", "Region_Name");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            
            for (var team : teams) {
                System.out.printf("%-6d %-15s %-20s %-25s %-15s%n",
                    team.id != null ? team.id : 0,
                    team.team_uk != null ? team.team_uk : "-",
                    team.team_name != null ? team.team_name : "-",
                    team.e_team_name != null ? team.e_team_name : "-",
                    team.region_name != null ? team.region_name : "-"
                );
            }
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
            System.out.println("▶▶▶ [TEAM CONTROLLER ONLY] TEAM 테이블 데이터 출력 완료 ◀◀◀");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n\n");
        }
        
        return result;
    }

    @PostMapping
    public Messenger save(@RequestBody TeamModel teamModel) {
        return teamService.save(teamModel);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<TeamModel> teamModelList) {
        return teamService.saveAll(teamModelList);
    }

    @PutMapping
    public Messenger update(@RequestBody TeamModel teamModel) {
        return teamService.update(teamModel);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody TeamModel teamModel) {
        return teamService.delete(teamModel);
    }

}
