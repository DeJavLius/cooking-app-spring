package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Result;
import teamproject.capstone.recipe.domain.api.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenAPIErrorTest {
    public final OpenAPIError openApiError = new OpenAPIError();

    @Test
    void testErrorCode_334() {
        // given
        Result re = new Result(null, "ERROR-334");
        OpenRecipe cr = new OpenRecipe(re, null, null);

        // when, then
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            openApiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorCode_200() {
        // given
        Result re = new Result(null, "INFO-200");
        OpenRecipe cr = new OpenRecipe(re, null, null);

        // when, then
        assertThrowsExactly(NoSuchElementException.class, () -> {
            openApiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNull() {
        // given
        OpenRecipe cr = new OpenRecipe(null, null, null);

        // when, then
        assertThrowsExactly(NullPointerException.class, () -> {
            openApiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNormal() {
        // given
        Result re = new Result(null, "INFO-000");
        OpenRecipe cr = new OpenRecipe(re, null, null);

        // when
        OpenRecipe v = openApiError.cookRecipeRightValueCheck(cr);

        // then
        assertThat(v.getResult().getMsg()).isEqualTo(re.getMsg());
    }

    @Test
    void testCookRecipeNoneValueCheck() {
        // given
        Row r = new Row(null, null, null, null, null, null);
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenRecipe cr = new OpenRecipe(null, rs, null);

        // when
        OpenRecipe v = openApiError.cookRecipeValueCheck(cr);

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
        OpenRecipe cr = new OpenRecipe(null, rs, null);

        // when
        OpenRecipe v = openApiError.cookRecipeValueCheck(cr);

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
        OpenRecipe cr = new OpenRecipe(null, rs, null);

        // when, then
        assertThrowsExactly(NullPointerException.class, () -> {
            openApiError.cookRecipeInnerValueCheck(cr);
        }
        );
    }

    @Test
    void testCookRecipeInnerValueCheck() {
        // given
        Row r = new Row("pro", "wei", "na", "seq", "way2", "rcp");
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        OpenRecipe cr = new OpenRecipe(null, rs, null);

        // when
        OpenRecipe v = openApiError.cookRecipeInnerValueCheck(cr);

        // then
        assertThat(v.getRow().get(0).getInfoPro()).isEqualTo(r.getInfoPro());
        assertThat(v.getRow().get(0).getInfoWgt()).isEqualTo(r.getInfoWgt());
        assertThat(v.getRow().get(0).getInfoNa()).isEqualTo(r.getInfoNa());
        assertThat(v.getRow().get(0).getRcpSeq()).isEqualTo(r.getRcpSeq());
        assertThat(v.getRow().get(0).getRcpWay2()).isEqualTo(r.getRcpWay2());
        assertThat(v.getRow().get(0).getRcpPartsDtls()).isEqualTo(r.getRcpPartsDtls());
    }
}