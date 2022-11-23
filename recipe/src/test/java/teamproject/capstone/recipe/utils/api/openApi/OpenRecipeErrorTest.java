package teamproject.capstone.recipe.utils.api.openApi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.utils.api.json.Result;
import teamproject.capstone.recipe.utils.api.json.Row;
import teamproject.capstone.recipe.utils.errors.OpenAPIErrorHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenRecipeErrorTest {
    public final OpenAPIErrorHandler openApiErrorHandler = new OpenAPIErrorHandler();

    @Test
    void testErrorCode_334() {
        // given
        Result re = new Result(null, "ERROR-334");
        OpenAPIRecipe cr = new OpenAPIRecipe(re, null, null);

        // when, then
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            openApiErrorHandler.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorCode_200() {
        // given
        Result re = new Result(null, "INFO-200");
        OpenAPIRecipe cr = new OpenAPIRecipe(re, null, null);

        // when, then
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            openApiErrorHandler.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNull() {
        // given
        OpenAPIRecipe cr = new OpenAPIRecipe(null, null, null);

        // when, then
        assertThrowsExactly(NullPointerException.class, () -> {
            openApiErrorHandler.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNormal() {
        // given
        Result re = new Result(null, "INFO-000");
        OpenAPIRecipe cr = new OpenAPIRecipe(re, null, null);

        // when
        OpenAPIRecipe v = openApiErrorHandler.cookRecipeRightValueCheck(cr);

        // then
        assertThat(v.getResult().getMsg()).isEqualTo(re.getMsg());
    }

    @Test
    void testCookRecipeNoneValueCheck() {
        // given
        Row r = new Row(null, null, null, null, null, null);
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenAPIRecipe cr = new OpenAPIRecipe(null, rs, null);

        // when
        OpenAPIRecipe v = openApiErrorHandler.cookRecipeValueCheck(cr);

        // then
        assertThat(v.getRow().get(0).getInfoPro()).isNull();
        assertThat(v.getRow().get(0).getInfoWgt()).isNull();
        assertThat(v.getRow().get(0).getInfoNa()).isNull();
        assertThat(v.getRow().get(0).getRcpSeq()).isNull();
        assertThat(v.getRow().get(0).getRcpWay2()).isNull();
        assertThat(v.getRow().get(0).getRcpPartsDtls()).isNull();
    }

    @Test
    void testCookRecipeValueCheck() {
        // given
        Row r = new Row("pro", "wei", "na", "seq", "way2", "rcp");
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenAPIRecipe cr = new OpenAPIRecipe(null, rs, null);

        // when
        OpenAPIRecipe v = openApiErrorHandler.cookRecipeValueCheck(cr);

        // then
        assertThat(v.getRow().get(0).getInfoPro()).isEqualTo(r.getInfoPro());
        assertThat(v.getRow().get(0).getInfoWgt()).isEqualTo(r.getInfoWgt());
        assertThat(v.getRow().get(0).getInfoNa()).isEqualTo(r.getInfoNa());
        assertThat(v.getRow().get(0).getRcpSeq()).isEqualTo(r.getRcpSeq());
        assertThat(v.getRow().get(0).getRcpWay2()).isEqualTo(r.getRcpWay2());
        assertThat(v.getRow().get(0).getRcpPartsDtls()).isEqualTo(r.getRcpPartsDtls());
    }

    @Test
    void testCookRecipeInnerNoneValueCheck() {
        // given
        Row r = new Row(null, null, null, null, null, null);
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenAPIRecipe cr = new OpenAPIRecipe(null, rs, null);

        // when, then
        assertThrowsExactly(NullPointerException.class, () -> {
            openApiErrorHandler.cookRecipeInnerValueCheck(cr);
        }
        );
    }

    @Test
    void testCookRecipeInnerValueCheck() {
        // given
        Row r = new Row("pro", "wei", "na", "seq", "way2", "rcp");
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenAPIRecipe cr = new OpenAPIRecipe(null, rs, null);

        // when
        OpenAPIRecipe v = openApiErrorHandler.cookRecipeInnerValueCheck(cr);

        // then
        assertThat(v.getRow().get(0).getInfoPro()).isEqualTo(r.getInfoPro());
        assertThat(v.getRow().get(0).getInfoWgt()).isEqualTo(r.getInfoWgt());
        assertThat(v.getRow().get(0).getInfoNa()).isEqualTo(r.getInfoNa());
        assertThat(v.getRow().get(0).getRcpSeq()).isEqualTo(r.getRcpSeq());
        assertThat(v.getRow().get(0).getRcpWay2()).isEqualTo(r.getRcpWay2());
        assertThat(v.getRow().get(0).getRcpPartsDtls()).isEqualTo(r.getRcpPartsDtls());
    }
}