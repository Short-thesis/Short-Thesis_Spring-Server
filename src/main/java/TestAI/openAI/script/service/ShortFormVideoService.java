package TestAI.openAI.script.service;

import TestAI.openAI.script.dto.CreateVideoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortFormVideoService {
    private final ScriptRetrievalService scriptRetrievalService ;
    private final WebClient webClient; // WebClient 인스턴스 생성

    private final String tag = "thesis";
    private final String baseApiUrl = "https://widely-select-polliwog.ngrok-free.app/movie";

    public void reqShortFormScripts(CreateVideoDto scriptInfo) {
        String requestUrl = "movie?text=" + scriptInfo.getShortFormScript() + "&tag=" + tag + "&id=" + scriptInfo.getArticleId();
        webClient.post()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)  // 응답 본문을 String으로 변환
                .flatMap(responseBody -> {
                    log.info("200 OK 응답을 받았습니다: {}", responseBody);
                    // 추가 로직을 여기에 작성하세요
                    return Mono.empty();  // 작업 완료 후 Mono.empty() 반환
                })
                .doOnError(e -> log.error("요청 실패: {}", e.getMessage())).subscribe();
    }


    public void sendShortForm(){
        CreateVideoDto videoDto = scriptRetrievalService.createVideoDto();
        reqShortFormScripts(videoDto);
    }

}
