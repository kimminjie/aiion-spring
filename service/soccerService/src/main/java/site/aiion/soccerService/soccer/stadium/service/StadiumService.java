package site.aiion.soccerService.soccer.stadium.service;

import java.util.List;
import site.aiion.soccerService.soccer.stadium.domain.StadiumModel;
import site.aiion.soccerService.soccer.common.domain.Messenger;

public interface StadiumService {
    public Messenger findById(StadiumModel stadiumModel);
    public Messenger findAll();
    public Messenger save(StadiumModel stadiumModel);
    public Messenger saveAll(List<StadiumModel> stadiumModelList);
    public Messenger update(StadiumModel stadiumModel);
    public Messenger delete(StadiumModel stadiumModel);
}
