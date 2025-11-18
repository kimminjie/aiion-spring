package site.aiion.soccerService.soccer.stadium.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumModel {
    public Long id;
    public String stadium_uk;
    public String stadium_name;
    public String hometeam_uk;
    public String seat_count;
    public String address;
    public String ddd;
    public String tel;
}
