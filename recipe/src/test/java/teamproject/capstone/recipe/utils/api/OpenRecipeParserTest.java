package teamproject.capstone.recipe.utils.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.Row;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenRecipeParserTest {
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
            OpenAPIRecipe openAPIRecipe = apiParser.parseURLToCookRecipe(openAPIProvider.getApi().getAPIUrl());

            // then
            log.info("value test of url to Object parser : {}", openAPIRecipe);
            assertThat(openAPIRecipe.getTotalCount()).isEqualTo(total_count);
            assertThat(openAPIRecipe.getRow().get(0).getInfoPro()).isEqualTo(row.getInfoPro());
            assertThat(openAPIRecipe.getRow().get(0).getInfoWgt()).isEqualTo(row.getInfoWgt());
            assertThat(openAPIRecipe.getRow().get(0).getInfoNa()).isEqualTo(row.getInfoNa());
            assertThat(openAPIRecipe.getRow().get(0).getRcpSeq()).isEqualTo(row.getRcpSeq());
            assertThat(openAPIRecipe.getRow().get(0).getRcpWay2()).isEqualTo(row.getRcpWay2());
            assertThat(openAPIRecipe.getRow().get(0).getRcpPartsDtls()).isEqualTo(row.getRcpPartsDtls());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}