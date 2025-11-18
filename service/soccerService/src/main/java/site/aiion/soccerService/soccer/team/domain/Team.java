package site.aiion.soccerService.soccer.team.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.soccerService.soccer.player.domain.Player;
import site.aiion.soccerService.soccer.stadium.domain.Stadium;

@Entity
@Table(name = "teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String team_uk;

    private String region_name;

    private String team_name;

    private String e_team_name;

    private String orig_yyyy;

    private String zip_code1;

    private String zip_code2;

    private String address;

    private String ddd;

    private String tel;

    private String fax;

    private String homepage;

    private String owner;

    private String stadium_uk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_uk", referencedColumnName = "stadium_uk", insertable = false, updatable = false)
    private Stadium stadium;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Player> players;
}