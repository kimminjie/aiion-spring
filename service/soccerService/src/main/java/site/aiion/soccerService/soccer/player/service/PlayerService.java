package site.aiion.soccerService.soccer.player.service;

import java.util.List;
import site.aiion.soccerService.soccer.player.domain.PlayerModel;
import site.aiion.soccerService.soccer.common.domain.Messenger;

public interface PlayerService {
    public Messenger findById(PlayerModel playerModel);
    public Messenger findAll();
    public Messenger save(PlayerModel playerModel);
    public Messenger saveAll(List<PlayerModel> playerModelList);
    public Messenger update(PlayerModel playerModel);
    public Messenger delete(PlayerModel playerModel);
}
