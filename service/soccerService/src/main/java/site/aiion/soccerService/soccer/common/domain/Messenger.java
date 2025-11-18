package site.aiion.soccerService.soccer.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messenger {
    private int Code;
    private String message;
    private Object data;
}
