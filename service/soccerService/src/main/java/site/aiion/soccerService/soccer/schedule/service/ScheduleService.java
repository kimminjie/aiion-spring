package site.aiion.soccerService.soccer.schedule.service;

import java.util.List;
import site.aiion.soccerService.soccer.schedule.domain.ScheduleModel;
import site.aiion.soccerService.soccer.common.domain.Messenger;

public interface ScheduleService {
    public Messenger findById(ScheduleModel scheduleModel);
    public Messenger findAll();
    public Messenger save(ScheduleModel scheduleModel);
    public Messenger saveAll(List<ScheduleModel> scheduleModelList);
    public Messenger update(ScheduleModel scheduleModel);
    public Messenger delete(ScheduleModel scheduleModel);
}

