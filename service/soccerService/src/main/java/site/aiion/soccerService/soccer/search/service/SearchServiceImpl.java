package site.aiion.soccerService.soccer.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.player.domain.Player;
import site.aiion.soccerService.soccer.player.domain.PlayerModel;
import site.aiion.soccerService.soccer.player.repository.PlayerRepository;
import site.aiion.soccerService.soccer.schedule.domain.Schedule;
import site.aiion.soccerService.soccer.schedule.domain.ScheduleModel;
import site.aiion.soccerService.soccer.schedule.repository.ScheduleRepository;
import site.aiion.soccerService.soccer.stadium.domain.Stadium;
import site.aiion.soccerService.soccer.stadium.domain.StadiumModel;
import site.aiion.soccerService.soccer.stadium.repository.StadiumRepository;
import site.aiion.soccerService.soccer.team.domain.Team;
import site.aiion.soccerService.soccer.team.domain.TeamModel;
import site.aiion.soccerService.soccer.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Messenger search(String keyword) {
        try {
            Map<String, Object> results = new HashMap<>();
            int totalCount = 0;

            // Player 검색
            try {
                List<Player> players = playerRepository.findAll().stream()
                        .filter(p -> containsKeyword(p, keyword))
                        .toList();
                List<PlayerModel> playerModels = players.stream()
                        .map(this::playerToModel)
                        .toList();
                results.put("players", playerModels);
                totalCount += playerModels.size();
            } catch (Exception e) {
                System.err.println("Player search error: " + e.getMessage());
                e.printStackTrace();
                results.put("players", new ArrayList<>());
            }

            // Team 검색
            try {
                List<Team> teams = teamRepository.findAll().stream()
                        .filter(t -> containsKeyword(t, keyword))
                        .toList();
                List<TeamModel> teamModels = teams.stream()
                        .map(this::teamToModel)
                        .toList();
                results.put("teams", teamModels);
                totalCount += teamModels.size();
            } catch (Exception e) {
                System.err.println("Team search error: " + e.getMessage());
                e.printStackTrace();
                results.put("teams", new ArrayList<>());
            }

            // Stadium 검색
            try {
                List<Stadium> stadiums = stadiumRepository.findAll().stream()
                        .filter(s -> containsKeyword(s, keyword))
                        .toList();
                List<StadiumModel> stadiumModels = stadiums.stream()
                        .map(this::stadiumToModel)
                        .toList();
                results.put("stadiums", stadiumModels);
                totalCount += stadiumModels.size();
            } catch (Exception e) {
                System.err.println("Stadium search error: " + e.getMessage());
                e.printStackTrace();
                results.put("stadiums", new ArrayList<>());
            }

            // Schedule 검색
            try {
                List<Schedule> schedules = scheduleRepository.findAll().stream()
                        .filter(sc -> containsKeyword(sc, keyword))
                        .toList();
                List<ScheduleModel> scheduleModels = schedules.stream()
                        .map(this::scheduleToModel)
                        .toList();
                results.put("schedules", scheduleModels);
                totalCount += scheduleModels.size();
            } catch (Exception e) {
                System.err.println("Schedule search error: " + e.getMessage());
                e.printStackTrace();
                results.put("schedules", new ArrayList<>());
            }

            results.put("totalCount", totalCount);
            results.put("keyword", keyword);

            return Messenger.builder()
                    .Code(200)
                    .message("검색 완료: 총 " + totalCount + "개 결과")
                    .data(results)
                    .build();
        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
            e.printStackTrace();
            return Messenger.builder()
                    .Code(500)
                    .message("검색 중 오류가 발생했습니다: " + e.getMessage())
                    .data(new HashMap<>())
                    .build();
        }
    }

    private boolean containsKeyword(Player player, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String lowerKeyword = keyword.toLowerCase();
        return (player.getPlayer_name() != null && player.getPlayer_name().toLowerCase().contains(lowerKeyword)) ||
                (player.getE_player_name() != null && player.getE_player_name().toLowerCase().contains(lowerKeyword)) ||
                (player.getNickname() != null && player.getNickname().toLowerCase().contains(lowerKeyword)) ||
                (player.getPosition() != null && player.getPosition().toLowerCase().contains(lowerKeyword)) ||
                (player.getNation() != null && player.getNation().toLowerCase().contains(lowerKeyword));
    }

    private boolean containsKeyword(Team team, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String lowerKeyword = keyword.toLowerCase();
        return (team.getTeam_name() != null && team.getTeam_name().toLowerCase().contains(lowerKeyword)) ||
                (team.getE_team_name() != null && team.getE_team_name().toLowerCase().contains(lowerKeyword)) ||
                (team.getRegion_name() != null && team.getRegion_name().toLowerCase().contains(lowerKeyword)) ||
                (team.getOrig_yyyy() != null && team.getOrig_yyyy().contains(keyword)) ||
                (team.getStadium_uk() != null && team.getStadium_uk().toLowerCase().contains(lowerKeyword));
    }

    private boolean containsKeyword(Stadium stadium, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String lowerKeyword = keyword.toLowerCase();
        return (stadium.getStadium_name() != null && stadium.getStadium_name().toLowerCase().contains(lowerKeyword)) ||
                (stadium.getStadium_uk() != null && stadium.getStadium_uk().toLowerCase().contains(lowerKeyword)) ||
                (stadium.getAddress() != null && stadium.getAddress().toLowerCase().contains(lowerKeyword)) ||
                (stadium.getHometeam_uk() != null && stadium.getHometeam_uk().toLowerCase().contains(lowerKeyword));
    }

    private boolean containsKeyword(Schedule schedule, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String lowerKeyword = keyword.toLowerCase();
        String homeTeamName = schedule.getHometeam() != null && schedule.getHometeam().getTeam_name() != null
                ? schedule.getHometeam().getTeam_name().toLowerCase()
                : "";
        String awayTeamName = schedule.getAwayteam() != null && schedule.getAwayteam().getTeam_name() != null
                ? schedule.getAwayteam().getTeam_name().toLowerCase()
                : "";
        String stadiumName = schedule.getStadium() != null && schedule.getStadium().getStadium_name() != null
                ? schedule.getStadium().getStadium_name().toLowerCase()
                : "";

        return homeTeamName.contains(lowerKeyword) ||
                awayTeamName.contains(lowerKeyword) ||
                stadiumName.contains(lowerKeyword) ||
                (schedule.getSche_date() != null && schedule.getSche_date().contains(keyword)) ||
                (schedule.getHometeam_uk() != null && schedule.getHometeam_uk().toLowerCase().contains(lowerKeyword)) ||
                (schedule.getAwayteam_uk() != null && schedule.getAwayteam_uk().toLowerCase().contains(lowerKeyword)) ||
                (schedule.getStadium_uk() != null && schedule.getStadium_uk().toLowerCase().contains(lowerKeyword));
    }

    private PlayerModel playerToModel(Player entity) {
        return PlayerModel.builder()
                .id(entity.getId())
                .player_uk(entity.getPlayer_uk())
                .player_name(entity.getPlayer_name())
                .e_player_name(entity.getE_player_name())
                .nickname(entity.getNickname())
                .join_yyyy(entity.getJoin_yyyy())
                .position(entity.getPosition())
                .back_no(entity.getBack_no())
                .nation(entity.getNation())
                .birth_date(entity.getBirth_date())
                .solar(entity.getSolar())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .team_uk(entity.getTeam_uk())
                .build();
    }

    private TeamModel teamToModel(Team entity) {
        return TeamModel.builder()
                .id(entity.getId())
                .team_uk(entity.getTeam_uk())
                .region_name(entity.getRegion_name())
                .team_name(entity.getTeam_name())
                .e_team_name(entity.getE_team_name())
                .orig_yyyy(entity.getOrig_yyyy())
                .zip_code1(entity.getZip_code1())
                .zip_code2(entity.getZip_code2())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .fax(entity.getFax())
                .homepage(entity.getHomepage())
                .owner(entity.getOwner())
                .stadium_uk(entity.getStadium_uk())
                .build();
    }

    private StadiumModel stadiumToModel(Stadium entity) {
        return StadiumModel.builder()
                .id(entity.getId())
                .stadium_uk(entity.getStadium_uk())
                .stadium_name(entity.getStadium_name())
                .hometeam_uk(entity.getHometeam_uk())
                .seat_count(entity.getSeat_count())
                .address(entity.getAddress())
                .ddd(entity.getDdd())
                .tel(entity.getTel())
                .build();
    }

    private ScheduleModel scheduleToModel(Schedule entity) {
        return ScheduleModel.builder()
                .id(entity.getId())
                .sche_date(entity.getSche_date())
                .stadium_uk(entity.getStadium_uk())
                .gubun(entity.getGubun())
                .hometeam_uk(entity.getHometeam_uk())
                .awayteam_uk(entity.getAwayteam_uk())
                .home_score(entity.getHome_score())
                .away_score(entity.getAway_score())
                .build();
    }
}
