package site.aiion.soccerService.soccer.search.service;

import site.aiion.soccerService.soccer.common.domain.Messenger;

public interface SearchService {
    Messenger search(String keyword);
}

