package site.aiion.soccerService.soccer.player.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.player.domain.PlayerModel;
import site.aiion.soccerService.soccer.player.domain.Player;
import site.aiion.soccerService.soccer.player.repository.PlayerRepository;
import site.aiion.soccerService.soccer.team.repository.TeamRepository;
import site.aiion.soccerService.soccer.team.domain.Team;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private PlayerModel entityToModel(Player entity) {
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

    private Player ModelToEntity(PlayerModel Model) {
        Team team = null;
        if (Model.team_uk != null) {
            team = teamRepository.findByTeam_uk(Model.team_uk).orElse(null);
        }
        return Player.builder()
                .id(Model.id)
                .player_uk(Model.player_uk)
                .player_name(Model.player_name)
                .e_player_name(Model.e_player_name)
                .nickname(Model.nickname)
                .join_yyyy(Model.join_yyyy)
                .position(Model.position)
                .back_no(Model.back_no)
                .nation(Model.nation)
                .birth_date(Model.birth_date)
                .solar(Model.solar)
                .height(Model.height)
                .weight(Model.weight)
                .team_uk(Model.team_uk)
                .team(team)
                .build();
    }

    @Override
    public Messenger findById(PlayerModel playerModel) {
        Optional<Player> entity = playerRepository.findById(playerModel.id);
        if (entity.isPresent()) {
            PlayerModel Model = entityToModel(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findAll() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ [PLAYER SERVICE] findAll() 호출 - PLAYERS 테이블만 조회합니다                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        
        // 명시적으로 players 테이블만 조회 (연관 관계 로드 안 함)
        List<Player> entities = playerRepository.findAll();
        
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ [PLAYER SERVICE] players 테이블 조회 완료: " + entities.size() + "개                   ║");
        System.out.println("║ 연관 관계(Team)는 접근하지 않으므로 추가 쿼리 없음                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
        
        // 연관 관계를 절대 접근하지 않고 기본 필드만 사용
        List<PlayerModel> ModelList = entities.stream()
                .map(player -> {
                    // 연관 관계(team)를 절대 접근하지 않음
                    return entityToModel(player);
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
    public Messenger save(PlayerModel playerModel) {
        Player entity = ModelToEntity(playerModel);
        Player saved = playerRepository.save(entity);
        PlayerModel Model = entityToModel(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getId())
                .data(Model)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<PlayerModel> playerModelList) {
        List<Player> entities = playerModelList.stream()
                .map(this::ModelToEntity)
                .collect(Collectors.toList());
        
        List<Player> saved = playerRepository.saveAll(entities);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.size() + " 개")
                .build();
    }

    @Override
    @Transactional
    public Messenger update(PlayerModel playerModel) {
        Optional<Player> optionalEntity = playerRepository.findById(playerModel.id);
        if (optionalEntity.isPresent()) {
            Player existing = optionalEntity.get();
            Team team = playerModel.team_uk != null 
                    ? teamRepository.findByTeam_uk(playerModel.team_uk).orElse(existing.getTeam()) 
                    : existing.getTeam();
            
            Player updated = Player.builder()
                    .id(existing.getId())
                    .player_uk(playerModel.player_uk != null ? playerModel.player_uk : existing.getPlayer_uk())
                    .player_name(playerModel.player_name != null ? playerModel.player_name : existing.getPlayer_name())
                    .e_player_name(playerModel.e_player_name != null ? playerModel.e_player_name : existing.getE_player_name())
                    .nickname(playerModel.nickname != null ? playerModel.nickname : existing.getNickname())
                    .join_yyyy(playerModel.join_yyyy != null ? playerModel.join_yyyy : existing.getJoin_yyyy())
                    .position(playerModel.position != null ? playerModel.position : existing.getPosition())
                    .back_no(playerModel.back_no != null ? playerModel.back_no : existing.getBack_no())
                    .nation(playerModel.nation != null ? playerModel.nation : existing.getNation())
                    .birth_date(playerModel.birth_date != null ? playerModel.birth_date : existing.getBirth_date())
                    .solar(playerModel.solar != null ? playerModel.solar : existing.getSolar())
                    .height(playerModel.height != null ? playerModel.height : existing.getHeight())
                    .weight(playerModel.weight != null ? playerModel.weight : existing.getWeight())
                    .team_uk(playerModel.team_uk != null ? playerModel.team_uk : existing.getTeam_uk())
                    .team(team)
                    .build();
            
            Player saved = playerRepository.save(updated);
            PlayerModel Model = entityToModel(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + playerModel.id)
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(PlayerModel playerModel) {
        Optional<Player> optionalEntity = playerRepository.findById(playerModel.id);
        if (optionalEntity.isPresent()) {
            playerRepository.deleteById(playerModel.id);
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + playerModel.id)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 선수를 찾을 수 없습니다.")
                    .build();
        }
    }

}

