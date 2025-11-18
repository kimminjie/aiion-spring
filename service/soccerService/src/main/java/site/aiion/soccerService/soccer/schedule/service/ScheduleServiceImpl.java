package site.aiion.soccerService.soccer.schedule.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.schedule.domain.ScheduleModel;
import site.aiion.soccerService.soccer.schedule.domain.Schedule;
import site.aiion.soccerService.soccer.schedule.repository.ScheduleRepository;
import site.aiion.soccerService.soccer.stadium.repository.StadiumRepository;
import site.aiion.soccerService.soccer.team.repository.TeamRepository;
import site.aiion.soccerService.soccer.stadium.domain.Stadium;
import site.aiion.soccerService.soccer.team.domain.Team;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StadiumRepository stadiumRepository;
    private final TeamRepository teamRepository;

    private ScheduleModel entityToModel(Schedule entity) {
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

    private Schedule ModelToEntity(ScheduleModel Model) {
        Stadium stadium = null;
        Team hometeam = null;
        Team awayteam = null;
        
        if (Model.stadium_uk != null) {
            stadium = stadiumRepository.findByStadium_uk(Model.stadium_uk).orElse(null);
        }
        if (Model.hometeam_uk != null) {
            hometeam = teamRepository.findByTeam_uk(Model.hometeam_uk).orElse(null);
        }
        if (Model.awayteam_uk != null) {
            awayteam = teamRepository.findByTeam_uk(Model.awayteam_uk).orElse(null);
        }
        
        return Schedule.builder()
                .id(Model.id)
                .sche_date(Model.sche_date)
                .stadium_uk(Model.stadium_uk)
                .gubun(Model.gubun)
                .hometeam_uk(Model.hometeam_uk)
                .awayteam_uk(Model.awayteam_uk)
                .home_score(Model.home_score)
                .away_score(Model.away_score)
                .stadium(stadium)
                .hometeam(hometeam)
                .awayteam(awayteam)
                .build();
    }

    @Override
    public Messenger findById(ScheduleModel scheduleModel) {
        Optional<Schedule> entity = scheduleRepository.findById(scheduleModel.id);
        if (entity.isPresent()) {
            ScheduleModel Model = entityToModel(entity.get());
            return Messenger.builder()
                    .Code(200)
                    .message("조회 성공")
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("경기를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    public Messenger findAll() {
        List<Schedule> entities = scheduleRepository.findAll();
        List<ScheduleModel> ModelList = entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("전체 조회 성공: " + ModelList.size() + "개")
                .data(ModelList)
                .build();
    }

    @Override
    @Transactional
    public Messenger save(ScheduleModel scheduleModel) {
        Schedule entity = ModelToEntity(scheduleModel);
        Schedule saved = scheduleRepository.save(entity);
        ScheduleModel Model = entityToModel(saved);
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + saved.getId())
                .data(Model)
                .build();
    }

    @Override
    @Transactional
    public Messenger saveAll(List<ScheduleModel> scheduleModelList) {
        List<Schedule> entities = scheduleModelList.stream()
                .map(Model -> {
                    Stadium stadium = null;
                    Team hometeam = null;
                    Team awayteam = null;
                    
                    if (Model.stadium_uk != null) {
                        stadium = stadiumRepository.findByStadium_uk(Model.stadium_uk).orElse(null);
                    }
                    if (Model.hometeam_uk != null) {
                        hometeam = teamRepository.findByTeam_uk(Model.hometeam_uk).orElse(null);
                    }
                    if (Model.awayteam_uk != null) {
                        awayteam = teamRepository.findByTeam_uk(Model.awayteam_uk).orElse(null);
                    }
                    
                    return Schedule.builder()
                            .id(Model.id)
                            .sche_date(Model.sche_date)
                            .stadium_uk(Model.stadium_uk)
                            .gubun(Model.gubun)
                            .hometeam_uk(Model.hometeam_uk)
                            .awayteam_uk(Model.awayteam_uk)
                            .home_score(Model.home_score)
                            .away_score(Model.away_score)
                            .stadium(stadium)
                            .hometeam(hometeam)
                            .awayteam(awayteam)
                            .build();
                })
                .collect(Collectors.toList());
        
        List<Schedule> saved = scheduleRepository.saveAll(entities);
        List<ScheduleModel> ModelList = saved.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return Messenger.builder()
                .Code(200)
                .message("저장 성공: " + ModelList.size() + " 개")
                .data(ModelList)
                .build();
    }

    @Override
    @Transactional
    public Messenger update(ScheduleModel scheduleModel) {
        Optional<Schedule> optionalEntity = scheduleRepository.findById(scheduleModel.id);
        if (optionalEntity.isPresent()) {
            Schedule existing = optionalEntity.get();
            
            Stadium stadium = scheduleModel.stadium_uk != null 
                    ? stadiumRepository.findByStadium_uk(scheduleModel.stadium_uk).orElse(existing.getStadium()) 
                    : existing.getStadium();
            Team hometeam = scheduleModel.hometeam_uk != null 
                    ? teamRepository.findByTeam_uk(scheduleModel.hometeam_uk).orElse(existing.getHometeam()) 
                    : existing.getHometeam();
            Team awayteam = scheduleModel.awayteam_uk != null 
                    ? teamRepository.findByTeam_uk(scheduleModel.awayteam_uk).orElse(existing.getAwayteam()) 
                    : existing.getAwayteam();
            
            Schedule updated = Schedule.builder()
                    .id(existing.getId())
                    .sche_date(scheduleModel.sche_date != null ? scheduleModel.sche_date : existing.getSche_date())
                    .stadium_uk(scheduleModel.stadium_uk != null ? scheduleModel.stadium_uk : existing.getStadium_uk())
                    .gubun(scheduleModel.gubun != null ? scheduleModel.gubun : existing.getGubun())
                    .hometeam_uk(scheduleModel.hometeam_uk != null ? scheduleModel.hometeam_uk : existing.getHometeam_uk())
                    .awayteam_uk(scheduleModel.awayteam_uk != null ? scheduleModel.awayteam_uk : existing.getAwayteam_uk())
                    .home_score(scheduleModel.home_score != null ? scheduleModel.home_score : existing.getHome_score())
                    .away_score(scheduleModel.away_score != null ? scheduleModel.away_score : existing.getAway_score())
                    .stadium(stadium)
                    .hometeam(hometeam)
                    .awayteam(awayteam)
                    .build();
            
            Schedule saved = scheduleRepository.save(updated);
            ScheduleModel Model = entityToModel(saved);
            return Messenger.builder()
                    .Code(200)
                    .message("수정 성공: " + scheduleModel.id)
                    .data(Model)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("수정할 경기를 찾을 수 없습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger delete(ScheduleModel scheduleModel) {
        Optional<Schedule> optionalEntity = scheduleRepository.findById(scheduleModel.id);
        if (optionalEntity.isPresent()) {
            scheduleRepository.deleteById(scheduleModel.id);
            return Messenger.builder()
                    .Code(200)
                    .message("삭제 성공: " + scheduleModel.id)
                    .build();
        } else {
            return Messenger.builder()
                    .Code(404)
                    .message("삭제할 경기를 찾을 수 없습니다.")
                    .build();
        }
    }

}

