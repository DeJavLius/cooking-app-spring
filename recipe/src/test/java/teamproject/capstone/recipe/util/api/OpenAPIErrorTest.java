package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.CookRecipe;
import teamproject.capstone.recipe.domain.api.Result;
import teamproject.capstone.recipe.domain.api.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenAPIErrorTest {
    public final APIError apiError = new APIError();

    @Test
    void testErrorCode_334() {
        // given
        Result re = new Result(null, "ERROR-334");
        CookRecipe cr = new CookRecipe(re, null, null);

        // when, taken
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            apiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorCode_200() {
        // given
        Result re = new Result(null, "INFO-200");
        CookRecipe cr = new CookRecipe(re, null, null);

        // when, taken
        assertThrowsExactly(NoSuchElementException.class, () -> {
            apiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNull() {
        // given
        CookRecipe cr = new CookRecipe(null, null, null);

        // when, taken
        assertThrowsExactly(NullPointerException.class, () -> {
            apiError.cookRecipeRightValueCheck(cr);
        });
    }

    @Test
    void testErrorNormal() {
        // given
        Result re = new Result(null, "INFO-000");
        CookRecipe cr = new CookRecipe(re, null, null);

        // when
        CookRecipe v = apiError.cookRecipeRightValueCheck(cr);

        // taken
        assertThat(v.getResult().getMsg()).isEqualTo(re.getMsg());
    }

    @Test
    void testCookRecipeNoneValueCheck() {
        // given
        Row r = new Row(null, null, null, null, null, null);
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        CookRecipe cr = new CookRecipe(null, rs, null);

        // when
        CookRecipe v = apiError.cookRecipeValueCheck(cr);

        // taken
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
        CookRecipe cr = new CookRecipe(null, rs, null);

        // when
        CookRecipe v = apiError.cookRecipeValueCheck(cr);

        // taken
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
        CookRecipe cr = new CookRecipe(null, rs, null);

        // when, taken
        assertThrowsExactly(NullPointerException.class, () -> {
            apiError.cookRecipeInnerValueCheck(cr);
        }
        );
    }

    @Test
    void testCookRecipeInnerValueCheck() {
        // given
        Row r = new Row("pro", "wei", "na", "seq", "way2", "rcp");
        List<Row> rs = new ArrayList<>();
        rs.add(r);
        CookRecipe cr = new CookRecipe(null, rs, null);

        // when
        CookRecipe v = apiError.cookRecipeInnerValueCheck(cr);

        // taken
        assertThat(v.getRow().get(0).getInfoPro()).isEqualTo(r.getInfoPro());
        assertThat(v.getRow().get(0).getInfoWgt()).isEqualTo(r.getInfoWgt());
        assertThat(v.getRow().get(0).getInfoNa()).isEqualTo(r.getInfoNa());
        assertThat(v.getRow().get(0).getRcpSeq()).isEqualTo(r.getRcpSeq());
        assertThat(v.getRow().get(0).getRcpWay2()).isEqualTo(r.getRcpWay2());
        assertThat(v.getRow().get(0).getRcpPartsDtls()).isEqualTo(r.getRcpPartsDtls());
    }
}