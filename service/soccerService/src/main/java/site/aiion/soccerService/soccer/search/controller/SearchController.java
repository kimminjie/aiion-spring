package site.aiion.soccerService.soccer.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.aiion.soccerService.soccer.common.domain.Messenger;
import site.aiion.soccerService.soccer.search.service.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public Messenger search(@RequestParam(required = false) String keyword) {
        // 터미널에 로그 출력 (서버 사이드이므로 Docker 로그에 찍힘)
        System.out.println("========================================");
        System.out.println("🔍 [SEARCH CONTROLLER] 검색 요청 받음");
        System.out.println("📝 입력한 검색어: " + keyword);
        System.out.println("⏰ 시간: " + java.time.LocalDateTime.now());
        System.out.println("========================================");

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("❌ 검색어가 없습니다.");
            return Messenger.builder()
                    .Code(400)
                    .message("검색어를 입력해주세요.")
                    .build();
        }

        String trimmedKeyword = keyword.trim();

        System.out.println("✅ 검색어: " + trimmedKeyword + " - 검색 시작");
        Messenger result = searchService.search(trimmedKeyword);
        System.out.println("✅ 검색 완료: " + result.getMessage());
        System.out.println("========================================");

        return result;
    }
}
