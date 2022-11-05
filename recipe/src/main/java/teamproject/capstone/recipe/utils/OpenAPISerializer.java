package teamproject.capstone.recipe.utils;

import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Row;

public class OpenAPISerializer {
    public static OpenRecipe rowToOpenRecipe(Row row) {
        return OpenRecipe.builder()
                .attFileNoMain(row.getAttFileNoMain()).attFileNoMk(row.getAttFileNoMk())
                .hashTag(row.getHashTag())
                .infoCar(doubleFormatting(row.getInfoCar())).infoEng(doubleFormatting(row.getInfoEng()))
                .infoFat(doubleFormatting(row.getInfoFat())).infoNa(doubleFormatting(row.getInfoNa()))
                .infoPro(doubleFormatting(row.getInfoPro())).infoWgt(doubleFormatting(row.getInfoWgt()))
                .manual01(row.getManual01()).manual02(row.getManual02()).manual03(row.getManual03()).manual04(row.getManual04()).manual05(row.getManual05())
                .manual06(row.getManual06()).manual07(row.getManual07()).manual08(row.getManual08()).manual09(row.getManual09()).manual10(row.getManual10())
                .manual11(row.getManual11()).manual12(row.getManual12()).manual13(row.getManual13()).manual14(row.getManual14()).manual15(row.getManual15())
                .manual16(row.getManual16()).manual17(row.getManual17()).manual18(row.getManual18()).manual19(row.getManual19()).manual20(row.getManual20())
                .manualImg01(row.getManualImg01()).manualImg02(row.getManualImg02()).manualImg03(row.getManualImg03()).manualImg04(row.getManualImg04())
                .manualImg05(row.getManualImg05()).manualImg06(row.getManualImg06()).manualImg07(row.getManualImg07()).manualImg08(row.getManualImg08())
                .manualImg09(row.getManualImg09()).manualImg10(row.getManualImg10()).manualImg11(row.getManualImg11()).manualImg12(row.getManualImg12())
                .manualImg13(row.getManualImg13()).manualImg14(row.getManualImg14()).manualImg15(row.getManualImg15()).manualImg16(row.getManualImg16())
                .manualImg17(row.getManualImg17()).manualImg18(row.getManualImg18()).manualImg19(row.getManualImg19()).manualImg20(row.getManualImg20())
                .rcpNm(row.getRcpNm()).rcpSeq(Long.parseLong(row.getRcpSeq())).rcpWay2(row.getRcpWay2()).rcpPat2(row.getRcpPat2()).rcpPartsDtls(row.getRcpPartsDtls())
                .build();
    }

    private static Double doubleFormatting(String value) {
        boolean isContains = value.contains(".");
        if (!isContains) {
            value += ".0";
        }
        return stringToDouble(value);
    }

    private static Double stringToDouble(String value) {
        return Double.parseDouble(value);
    }
}
