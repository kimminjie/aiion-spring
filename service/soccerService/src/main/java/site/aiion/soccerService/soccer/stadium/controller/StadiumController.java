package site.aiion.soccerService.soccer.stadium.controller;

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
import site.aiion.soccerService.soccer.stadium.domain.StadiumModel;
import site.aiion.soccerService.soccer.stadium.service.StadiumService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadiums")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody StadiumModel stadiumModel) {
        return stadiumService.findById(stadiumModel);
    }

    @GetMapping
    public Messenger findAll() {
        // 명확한 시작 표시 - 이 로그만 보이면 StadiumController만 호출된 것
        System.out.println("\n\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("▶▶▶ [STADIUM CONTROLLER ONLY] /stadiums 요청 - STADIUM 테이블만 출력 시작 ◀◀◀");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("시간: " + java.time.LocalDateTime.now());
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n");
        
        Messenger result = stadiumService.findAll();
        
        // 터미널에 테이블 데이터 출력
        if (result.getData() instanceof java.util.List) {
            @SuppressWarnings("unchecked")
            java.util.List<site.aiion.soccerService.soccer.stadium.domain.StadiumModel> stadiums = 
                (java.util.List<site.aiion.soccerService.soccer.stadium.domain.StadiumModel>) result.getData();
            
            System.out.println("\n📊 STADIUM 테이블 데이터 (총 " + stadiums.size() + "개):");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-6s %-15s %-25s %-30s%n", 
                "ID", "Stadium_UK", "Stadium_Name", "Address");
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            
            for (var stadium : stadiums) {
                System.out.printf("%-6d %-15s %-25s %-30s%n",
                    stadium.id != null ? stadium.id : 0,
                    stadium.stadium_uk != null ? stadium.stadium_uk : "-",
                    stadium.stadium_name != null ? stadium.stadium_name : "-",
                    stadium.address != null ? stadium.address : "-"
                );
            }
            System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
            System.out.println("▶▶▶ [STADIUM CONTROLLER ONLY] STADIUM 테이블 데이터 출력 완료 ◀◀◀");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n\n");
        }
        
        return result;
    }

    @PostMapping
    public Messenger save(@RequestBody StadiumModel stadiumModel) {
        return stadiumService.save(stadiumModel);
    }

    @PostMapping("/all")
    public Messenger saveAll(@RequestBody List<StadiumModel> stadiumModelList) {
        return stadiumService.saveAll(stadiumModelList);
    }

    @PutMapping
    public Messenger update(@RequestBody StadiumModel stadiumModel) {
        return stadiumService.update(stadiumModel);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody StadiumModel stadiumModel) {
        return stadiumService.delete(stadiumModel);
    }

}
