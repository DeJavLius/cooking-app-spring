package teamproject.capstone.recipe.utils.api.openApi;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.parts.Row;

public class OpenAPIDelegator {
    public static OpenRecipe rowToOpenRecipe(Row row) {
        return OpenRecipe.builder()
                .attFileNoMain(insteadEmptyString(row.getAttFileNoMain())).attFileNoMk(insteadEmptyString(row.getAttFileNoMk()))
                .hashTag(insteadEmptyString(row.getHashTag()))
                .infoCar(doubleFormatting(row.getInfoCar())).infoEng(doubleFormatting(row.getInfoEng()))
                .infoFat(doubleFormatting(row.getInfoFat())).infoNa(doubleFormatting(row.getInfoNa()))
                .infoPro(doubleFormatting(row.getInfoPro())).infoWgt(doubleFormatting(row.getInfoWgt()))
                .manual01(insteadEmptyString(row.getManual01())).manual02(insteadEmptyString(row.getManual02())).manual03(insteadEmptyString(row.getManual03())).manual04(insteadEmptyString(row.getManual04())).manual05(insteadEmptyString(row.getManual05()))
                .manual06(insteadEmptyString(row.getManual06())).manual07(insteadEmptyString(row.getManual07())).manual08(insteadEmptyString(row.getManual08())).manual09(insteadEmptyString(row.getManual09())).manual10(insteadEmptyString(row.getManual10()))
                .manual11(insteadEmptyString(row.getManual11())).manual12(insteadEmptyString(row.getManual12())).manual13(insteadEmptyString(row.getManual13())).manual14(insteadEmptyString(row.getManual14())).manual15(insteadEmptyString(row.getManual15()))
                .manual16(insteadEmptyString(row.getManual16())).manual17(insteadEmptyString(row.getManual17())).manual18(insteadEmptyString(row.getManual18())).manual19(insteadEmptyString(row.getManual19())).manual20(insteadEmptyString(row.getManual20()))
                .manualImg01(insteadEmptyString(row.getManualImg01())).manualImg02(insteadEmptyString(row.getManualImg02())).manualImg03(insteadEmptyString(row.getManualImg03())).manualImg04(insteadEmptyString(row.getManualImg04()))
                .manualImg05(insteadEmptyString(row.getManualImg05())).manualImg06(insteadEmptyString(row.getManualImg06())).manualImg07(insteadEmptyString(row.getManualImg07())).manualImg08(insteadEmptyString(row.getManualImg08()))
                .manualImg09(insteadEmptyString(row.getManualImg09())).manualImg10(insteadEmptyString(row.getManualImg10())).manualImg11(insteadEmptyString(row.getManualImg11())).manualImg12(insteadEmptyString(row.getManualImg12()))
                .manualImg13(insteadEmptyString(row.getManualImg13())).manualImg14(insteadEmptyString(row.getManualImg14())).manualImg15(insteadEmptyString(row.getManualImg15())).manualImg16(insteadEmptyString(row.getManualImg16()))
                .manualImg17(insteadEmptyString(row.getManualImg17())).manualImg18(insteadEmptyString(row.getManualImg18())).manualImg19(insteadEmptyString(row.getManualImg19())).manualImg20(insteadEmptyString(row.getManualImg20()))
                .rcpNm(insteadEmptyString(row.getRcpNm())).rcpSeq(Long.parseLong(row.getRcpSeq())).rcpWay2(insteadEmptyString(row.getRcpWay2())).rcpPat2(insteadEmptyString(row.getRcpPat2())).rcpPartsDtls(insteadEmptyString(row.getRcpPartsDtls()))
                .build();
    }

    private static String imageCheckString(String value) {
        String defaultImage = "http://www.foodsafetykorea.go.kr/";
        String check = insteadEmptyString(value);

        if (check.equals(defaultImage)) {
            return "";
        }
        return check;
    }

    private static String insteadEmptyString(String value) {
        if (value.isEmpty()) {
            return "";
        } else {
            return value;
        }
    }

    private static Double doubleFormatting(String value) {
        boolean isContains = value.contains(".");
        boolean isEmpty = (value.length() == 0);
        if (isEmpty) {
            return 0.0;
        }

        if (!isContains) {
            return (double) Integer.parseInt(value);
        }
        else {
            return Double.parseDouble(value);
        }
    }
}
