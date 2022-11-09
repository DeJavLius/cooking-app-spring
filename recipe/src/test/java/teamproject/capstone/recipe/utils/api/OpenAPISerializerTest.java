package teamproject.capstone.recipe.utils.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.repository.api.OpenAPIRepository;
import teamproject.capstone.recipe.utils.OpenAPISerializer;
import teamproject.capstone.recipe.utils.api.json.Row;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OpenAPISerializerTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenAPIRepository openAPIRepository;

    @Test
    void rowToOpenRecipe() {
        // given
        Row r = Row.builder()
                .attFileNoMain("1").attFileNoMk("2").hashTag("3")
                .infoCar("4").infoEng("5").infoFat("6")
                .infoNa("7").infoPro("8").infoWgt("9")
                .manual01("10").manual02("11").manual03("12").manual04("13").manual05("14")
                .manual06("15").manual07("16").manual08("17").manual09("18").manual10("19")
                .manual11("20").manual12("21").manual13("22").manual14("23").manual15("24")
                .manual16("25").manual17("26").manual18("27").manual19("28").manual20("29")
                .manualImg01("30").manualImg02("31").manualImg03("32").manualImg04("33")
                .manualImg05("34").manualImg06("35").manualImg07("36").manualImg08("37")
                .manualImg09("38").manualImg10("39").manualImg11("40").manualImg12("41")
                .manualImg13("42").manualImg14("43").manualImg15("44").manualImg16("45")
                .manualImg17("46").manualImg18("47").manualImg19("48").manualImg20("49")
                .rcpNm("50").rcpSeq("51").rcpWay2("52").rcpPat2("53").rcpPartsDtls("54")
                .build();

        OpenRecipe ope = OpenRecipe.builder()
                .attFileNoMain("1").attFileNoMk("2").hashTag("3")
                .infoCar(4.0).infoEng(5.0).infoFat(6.0)
                .infoNa(7.0).infoPro(8.0).infoWgt(9.0)
                .manual01("10").manual02("11").manual03("12").manual04("13").manual05("14")
                .manual06("15").manual07("16").manual08("17").manual09("18").manual10("19")
                .manual11("20").manual12("21").manual13("22").manual14("23").manual15("24")
                .manual16("25").manual17("26").manual18("27").manual19("28").manual20("29")
                .manualImg01("30").manualImg02("31").manualImg03("32").manualImg04("33")
                .manualImg05("34").manualImg06("35").manualImg07("36").manualImg08("37")
                .manualImg09("38").manualImg10("39").manualImg11("40").manualImg12("41")
                .manualImg13("42").manualImg14("43").manualImg15("44").manualImg16("45")
                .manualImg17("46").manualImg18("47").manualImg19("48").manualImg20("49")
                .rcpNm("50").rcpSeq(51L).rcpWay2("52").rcpPat2("53").rcpPartsDtls("54")
                .build();

        // when
        OpenRecipe openRecipe = OpenAPISerializer.rowToOpenRecipe(r);

        // then
        assertThat(ope.toString()).isEqualTo(openRecipe.toString());
    }
}