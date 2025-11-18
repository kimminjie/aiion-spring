package site.aiion.soccerService.soccer.team.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.team.domain.TeamModel;
import site.aiion.soccerService.soccer.team.domain.Team;
import site.aiion.soccerService.soccer.team.repository.TeamRepository;
import site.aiion.soccerService.soccer.stadium.repository.StadiumRepository;
import site.aiion.soccerService.soccer.stadium.domain.Stadium;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    private TeamModel entityToModel(Team entity) {
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

    private Team ModelToEntity(TeamModel Model) {
        Stadium stadium = null;
        if (Model.stadium_uk != null) {
            stadium = stadiumRepository.findByStadium_uk(Model.stadium_uk).orElse(null);
        }
        return Team.builder()
                .id(Model.id)
                .team_uk(Model.team_uk)
                .region_name(Model.region_name)
                .team_name(Model.team_name)
                .e_team_name(Model.e_team_name)
                .orig_yyyy(Model.orig_yyyy)
                .zip_code1(Model.zip_code1)
                .zip_code2(Model.zip_code2)
                .address(Model.address)
                .ddd(Model.ddd)
                .tel(Model.tel)
                .fax(Model.fax)
                .homepage(Model.homepage)
                .owner(Model.owner)
                .stadium_uk(Model.stadium_uk)
                .stadium(stadium)
                .build();
    }

    @Override
    public Messenger findById(TeamModel teamModel) {
        Optional<Team> entity = teamRepository.findById(teamModel.id);
        if (entity.isPresent()) {
            TeamModel Model = entityToModel(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("팀을 찾을 수 없습니다.")
                    .build();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Messenger findAll() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ [TEAM SERVICE] findAll() 호출 - TEAMS 테이블만 조회합니다                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        
        // 명시적으로 teams 테이블만 조회 (연관 관계 로드 안 함)
        List<Team> entities = teamRepository.findAll();
        
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ [TEAM SERVICE] teams 테이블 조회 완료: " + entities.size() + "개                      ║");
        System.out.println("║ 연관 관계(Stadium, Player)는 접근하지 않으므로 추가 쿼리 없음                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        
        // 연관 관계를 절대 접근하지 않고 기본 필드만 사용
        List<TeamModel> ModelList = entities.stream()
                .map(team -> {
                    // 연관 관계(stadium, players)를 절대 접근하지 않음
                    TeamModel model = TeamModel.builder()
                            .id(team.getId())
                            .team_uk(team.getTeam_uk())
                            .region_name(team.getRegion_name())
                            .team_name(team.getTeam_name())
                            .e_team_name(team.getE_team_name())
                            .orig_yyyy(team.getOrig_yyyy())
                            .zip_code1(team.getZip_code1())
                            .zip_code2(team.getZip_code2())
                            .address(team.getAddress())
                            .ddd(team.getDdd())
                            .tel(team.getTel())
                            .fax(team.getFax())
                            .homepage(team.getHomepage())
                            .owner(team.getOwner())
                            .stadium_uk(team.getStadium_uk())
                            .build();
                    // team.getStadium()이나 team.getPlayers() 호출 절대 금지!
                    return model;
                })
                .collect(Collectors.toList());
        
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + ModelList.size() + "개")
                .data(ModelList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(TeamModel teamModel) {
        Team entity = ModelToEntity(teamModel);
        Team saved = teamRepository.save(entity);
        TeamModel Model = entityToModel(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getId())
                .data(Model)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<TeamModel> teamModelList) {
        List<Team> entities = teamModelList.stream()
                .map(this::ModelToEntity)
                .collect(Collectors.toList());
        
        List<Team> saved = teamRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.size() + " 개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(TeamModel teamModel) {
        Optional<Team> optionalEntity = teamRepository.findById(teamModel.id);
        if (optionalEntity.isPresent()) {
            Team existing = optionalEntity.get();
            Stadium stadium = teamModel.stadium_uk != null 
                    ? stadiumRepository.findByStadium_uk(teamModel.stadium_uk).orElse(existing.getStadium()) 
                    : existing.getStadium();
            
            Team updated = Team.builder()
                    .id(existing.getId())
                    .team_uk(teamModel.team_uk != null ? teamModel.team_uk : existing.getTeam_uk())
                    .region_name(teamModel.region_name != null ? teamModel.region_name : existing.getRegion_name())
                    .team_name(teamModel.team_name != null ? teamModel.team_name : existing.getTeam_name())
                    .e_team_name(teamModel.e_team_name != null ? teamModel.e_team_name : existing.getE_team_name())
                    .orig_yyyy(teamModel.orig_yyyy != null ? teamModel.orig_yyyy : existing.getOrig_yyyy())
                    .zip_code1(teamModel.zip_code1 != null ? teamModel.zip_code1 : existing.getZip_code1())
                    .zip_code2(teamModel.zip_code2 != null ? teamModel.zip_code2 : existing.getZip_code2())
                    .address(teamModel.address != null ? teamModel.address : existing.getAddress())
                    .ddd(teamModel.ddd != null ? teamModel.ddd : existing.getDdd())
                    .tel(teamModel.tel != null ? teamModel.tel : existing.getTel())
                    .fax(teamModel.fax != null ? teamModel.fax : existing.getFax())
                    .homepage(teamModel.homepage != null ? teamModel.homepage : existing.getHomepage())
                    .owner(teamModel.owner != null ? teamModel.owner : existing.getOwner())
                    .stadium_uk(teamModel.stadium_uk != null ? teamModel.stadium_uk : existing.getStadium_uk())
                    .stadium(stadium)
                    .players(existing.getPlayers())
                    .build();
            
            Team saved = teamRepository.save(updated);
            TeamModel Model = entityToModel(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + teamModel.id)
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                        .message("수정할 팀을 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(TeamModel teamModel) {
        Optional<Team> optionalEntity = teamRepository.findById(teamModel.id);
        if (optionalEntity.isPresent()) {
            teamRepository.deleteById(teamModel.id);
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + teamModel.id)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 팀을 찾을 수 없습니다.")
                    .build();
        }
    }

}

