package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Row;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenAPIParserTest {
    public OpenAPIProvider openAPIProvider = OpenAPIProvider.getInstance();
    public OpenAPIParser apiParser = OpenAPIParser.getInstance();

    @Test
    void parseURLToCookRecipe() {
        // given
        String total_count = "1061";
        Row row = new Row("7.8", "159.6", "198.3", "3298", "볶기", "[ 2인분 ] 토마토(2개), 양파(¼개), 감자(¼개), 노랑 파프리카(¼개), 브로콜리(¼개), 새우(4마리) 고추기름(1Ts), 후춧가루(0.3Ts), 다진 마늘(0.3Ts), 플레인 요거트(½컵), 고추장(0.3Ts)");
        openAPIProvider.urlIndexRangeScan(1061, 1061);

        try {
            // when
            OpenRecipe openRecipe = apiParser.parseURLToCookRecipe(openAPIProvider.getApi().getAPIUrl());

            // then
            log.info("value test of url to Object parser : {}", openRecipe);
            assertThat(openRecipe.getTotalCount()).isEqualTo(total_count);
            assertThat(openRecipe.getRow().get(0).getInfoPro()).isEqualTo(row.getInfoPro());
            assertThat(openRecipe.getRow().get(0).getInfoWgt()).isEqualTo(row.getInfoWgt());
            assertThat(openRecipe.getRow().get(0).getInfoNa()).isEqualTo(row.getInfoNa());
            assertThat(openRecipe.getRow().get(0).getRcpSeq()).isEqualTo(row.getRcpSeq());
            assertThat(openRecipe.getRow().get(0).getRcpWay2()).isEqualTo(row.getRcpWay2());
            assertThat(openRecipe.getRow().get(0).getRcpPartsDtls()).isEqualTo(row.getRcpPartsDtls());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}