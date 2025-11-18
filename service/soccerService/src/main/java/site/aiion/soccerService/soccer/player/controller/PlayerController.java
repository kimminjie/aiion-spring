package site.aiion.soccerService.soccer.player.controller;

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
import site.aiion.soccerService.soccer.player.domain.PlayerModel;
import site.aiion.soccerService.soccer.player.service.PlayerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/findById")
    public Messenger findById(@RequestBody PlayerModel playerModel) {
        return playerService.findById(playerModel);
    }

    @GetMapping
    public Messenger findAll() {
        // 명확한 시작 표시 - 이 로그만 보이면 PlayerController만 호출된 것
        System.out.println("\n\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("▶▶▶ [PLAYER CONTROLLER ONLY] /players 요청 - PLAYER 테이블만 출력 시작 ◀◀◀");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("시간: " + java.time.LocalDateTime.now());
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════\n");

        Messenger result = playerService.findAll();

        // 터미널에 테이블 데이터 출력
        if (result.getData() instanceof java.util.List) {
            @SuppressWarnings("unchecked")
            java.util.List<site.aiion.soccerService.soccer.player.domain.PlayerModel> players = (java.util.List<site.aiion.soccerService.soccer.player.domain.PlayerModel>) result
                    .getData();

            System.out.println("\n📊 PLAYER 테이블 데이터 (총 " + players.size() + "개):");
            System.out.println(
                    "──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-6s %-15s %-20s %-25s %-15s %-10s%n",
                    "ID", "Player_UK", "Player_Name", "E_Player_Name", "Position", "Nation");
            System.out.println(
                    "──────────────────────────────────────────────────────────────────────────────────────────────");

            for (var player : players) {
                System.out.printf("%-6d %-15s %-20s %-25s %-15s %-10s%n",
                        player.id != null ? player.id : 0,
                        player.player_uk != null ? player.player_uk : "-",
                        player.player_name != null ? player.player_name : "-",
                        player.e_player_name != null ? player.e_player_name : "-",
                        player.position != null ? player.position : "-",
                        player.nation != null ? player.nation : "-");
            }
            System.out.println(
                    "──────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════");
            System.out.println("▶▶▶ [PLAYER CONTROLLER ONLY] PLAYER 테이블 데이터 출력 완료 ◀◀◀");
            System.out
                    .println("═══════════════════════════════════════════════════════════════════════════════════\n\n");
        }

        return result;
    }

    @PostMapping
    public Messenger save(@RequestBody PlayerModel playerModel) {
        return playerService.save(playerModel);
    }

    @PostMapping("/saveAll")
    public Messenger saveAll(@RequestBody List<PlayerModel> playerModelList) {
        return playerService.saveAll(playerModelList);
    }

    @PutMapping
    public Messenger update(@RequestBody PlayerModel playerModel) {
        return playerService.update(playerModel);
    }

    @DeleteMapping
    public Messenger delete(@RequestBody PlayerModel playerModel) {
        return playerService.delete(playerModel);
    }

}
