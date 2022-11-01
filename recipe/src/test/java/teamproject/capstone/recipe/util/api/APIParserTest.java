package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.CookRecipe;
import teamproject.capstone.recipe.domain.api.Row;
import teamproject.capstone.recipe.util.api.values.ConstValues;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class APIParserTest {
    public APIParser apiParser = new APIParser();
    public String VALUE = "{\"COOKRCP01\":{\"total_count\":\"1061\",\"row\":[{\"RCP_PARTS_DTLS\":\"[ 2인분 ] 토마토(2개), 양파(¼개), 감자(¼개), 노랑 파프리카(¼개), 브로콜리(¼개), 새우(4마리) 고추기름(1Ts), 후춧가루(0.3Ts), 다진 마늘(0.3Ts), 플레인 요거트(½컵), 고추장(0.3Ts)\",\"RCP_WAY2\":\"볶기\",\"MANUAL_IMG20\":\"\",\"MANUAL20\":\"\",\"RCP_SEQ\":\"3298\",\"INFO_NA\":\"198.3\",\"INFO_WGT\":\"159.6\",\"INFO_PRO\":\"7.8\",\"MANUAL_IMG13\":\"\",\"MANUAL_IMG14\":\"\",\"MANUAL_IMG15\":\"\",\"MANUAL_IMG16\":\"\",\"MANUAL_IMG10\":\"\",\"MANUAL_IMG11\":\"\",\"MANUAL_IMG12\":\"\",\"MANUAL_IMG17\":\"\",\"MANUAL_IMG18\":\"\",\"MANUAL_IMG19\":\"\",\"INFO_FAT\":\"3.7\",\"HASH_TAG\":\"\",\"MANUAL_IMG02\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9sVf\",\"MANUAL_IMG03\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9sYb\",\"RCP_PAT2\":\"반찬\",\"MANUAL_IMG04\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9sa8\",\"MANUAL_IMG05\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9seb\",\"MANUAL_IMG01\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9sTx\",\"MANUAL01\":\"1. 토마토는 윗부분을 약간 자른 뒤 속을 파낸다.\",\"ATT_FILE_NO_MK\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9nOn\",\"MANUAL_IMG06\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9sgw\",\"MANUAL_IMG07\":\"\",\"MANUAL_IMG08\":\"\",\"MANUAL_IMG09\":\"\",\"MANUAL08\":\"\",\"MANUAL09\":\"\",\"MANUAL06\":\"6. 토마토 속에 ④의 볶은 재료를 담고, 고추장 요거트 드레싱을 곁들여 마무리한다.\",\"MANUAL07\":\"\",\"MANUAL04\":\"4. 새우와 후춧가루를 넣은 뒤 고루 섞으며 볶는다.\",\"MANUAL05\":\"5. 고추장 요거트 드레싱을 만든다.\",\"MANUAL02\":\"2. 양파, 감자, 노랑 파프리카, 브로콜리, 새우는 1×1cm 크기로 썬다.\",\"MANUAL03\":\"3. 중간 불로 달군 팬에 고추기름을 두르고, 손질한 채소를 넣어 양파가 투명해질 때까지 볶는다.\",\"ATT_FILE_NO_MAIN\":\"https://www.foodsafetykorea.go.kr/common/ecmFileView.do?ecm_file_no\\u003d1NxSkgr9nNW\",\"MANUAL11\":\"\",\"MANUAL12\":\"\",\"MANUAL10\":\"\",\"INFO_CAR\":\"6.1\",\"MANUAL19\":\"\",\"INFO_ENG\":\"88.9\",\"MANUAL17\":\"\",\"MANUAL18\":\"\",\"RCP_NM\":\"매운요거트 토마토샐러드\",\"MANUAL15\":\"\",\"MANUAL16\":\"\",\"MANUAL13\":\"\",\"MANUAL14\":\"\"}],\"RESULT\":{\"MSG\":\"정상처리되었습니다.\",\"CODE\":\"INFO-000\"}}}";

    @Test
    void parseURLToCookRecipe() throws MalformedURLException {
        // given
        String total_count = "1061";
        Row row = new Row("7.8", "159.6", "198.3", "3298", "볶기", "[ 2인분 ] 토마토(2개), 양파(¼개), 감자(¼개), 노랑 파프리카(¼개), 브로콜리(¼개), 새우(4마리) 고추기름(1Ts), 후춧가루(0.3Ts), 다진 마늘(0.3Ts), 플레인 요거트(½컵), 고추장(0.3Ts)");
        OpenAPI api = new OpenAPI(new URL(ConstValues.RECIPE_OPEN_API + "api/" + ConstValues.API_KEY +
                "COOKRCP01/json/" + 1061 + "/" + 1061));

        try {
            // when
            CookRecipe cookRecipe = apiParser.parseURLToCookRecipe();

            // taken
            assertThat(cookRecipe.getTotalCount()).isEqualTo(total_count);
            assertThat(cookRecipe.getRow().get(0).getInfoPro()).isEqualTo(cookRecipe.getRow().get(0).getInfoPro());
            assertThat(cookRecipe.getRow().get(0).getInfoWgt()).isEqualTo(cookRecipe.getRow().get(0).getInfoWgt());
            assertThat(cookRecipe.getRow().get(0).getInfoNa()).isEqualTo(cookRecipe.getRow().get(0).getInfoNa());
            assertThat(cookRecipe.getRow().get(0).getRcpSeq()).isEqualTo(cookRecipe.getRow().get(0).getRcpSeq());
            assertThat(cookRecipe.getRow().get(0).getRcpWay2()).isEqualTo(cookRecipe.getRow().get(0).getRcpWay2());
            assertThat(cookRecipe.getRow().get(0).getRcpPartsDtls()).isEqualTo(cookRecipe.getRow().get(0).getRcpPartsDtls());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}