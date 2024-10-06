package TestAI.openAI.kci.service;

import TestAI.openAI.kci.abstractInfo.KciArticleAbstract;
import TestAI.openAI.kci.xmlResponse.MetaData;
import TestAI.openAI.kci.xmlResponse.Record;
import TestAI.openAI.kci.xmlResponse.recode.articleInfo.AbstractContent;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class KciAbstractService {
    @Value("${kci.base-api-url}")
    private String BASE_API_URL;

    @Value("${kci.api-key}")
    private String API_KEY;

    private RestTemplate restTemplate = new RestTemplate();

    public List<KciArticleAbstract> getAllAbstract(String title, String affiliation) {
        URI uri = buildUri(title, affiliation);  // 메서드 호출로 URI 구성
        List<KciArticleAbstract> abstractInfoList = new ArrayList<>();

        // RestTemplate을 사용하여 XML 응답 받기
        String xmlResponse = restTemplate.getForObject(uri, String.class);

        // XML 응답 파싱
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MetaData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            MetaData metaData = (MetaData) unmarshaller.unmarshal(new StringReader(xmlResponse));

            if (metaData.getOutputData() != null && metaData.getOutputData().getRecords() != null) {
                for (Record record : metaData.getOutputData().getRecords()) {
                    if (record.getArticleInfo() != null && record.getArticleInfo().getAbstractGroup() != null) {
                        KciArticleAbstract abstractInfo = new KciArticleAbstract();
                        abstractInfo.setArticleId(record.getArticleInfo().getArticleId());
                        // 논문 제목 설정
                        abstractInfo.setArticleTitle(record.getArticleInfo().getOriginalTitle());
                        // 저자 목록 설정
                        abstractInfo.setAuthors(extractAuthors(record));
                        // 초록 설정
                        abstractInfo.setAbstractCt(extractOriginalAbstract(record));
                        // URL 설정
                        abstractInfo.setUrl(record.getArticleInfo().getUrl());
                        // 출판 년도 설정
                        abstractInfo.setPubYear(record.getJournalInfo().getPubYear());

                        abstractInfoList.add(abstractInfo);
                    }
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return abstractInfoList;
    }

    // 저자 리스트 추출 메서드
    private List<String> extractAuthors(Record record) {
        List<String> authors = new ArrayList<>();
        if (record.getArticleInfo().getAuthorGroup() != null) {
            record.getArticleInfo().getAuthorGroup().getAuthors().forEach(author -> authors.add(author.getName()));
        }
        return authors;
    }

    // 원본 초록 추출 메서드
    private String extractOriginalAbstract(Record record) {
        if (record.getArticleInfo().getAbstractGroup() != null) {
            for (AbstractContent abs : record.getArticleInfo().getAbstractGroup().getAbstracts()) {
                if ("original".equals(abs.getLang())) {
                    return abs.getContent();
                }
            }
        }
        return "Original Abstract not found";
    }

    // uri 동적으로 생성
    private URI buildUri(String title, String affiliation) {
        return UriComponentsBuilder.fromHttpUrl(BASE_API_URL)
                .queryParam("apiCode", "articleSearch")
                .queryParam("key", API_KEY)
                .queryParam("title", title)
                .queryParam("affiliation", affiliation)
                .build()
                .encode()
                .toUri();
    }
}
