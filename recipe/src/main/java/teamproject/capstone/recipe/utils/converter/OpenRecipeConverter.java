package teamproject.capstone.recipe.utils.converter;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;

public class OpenRecipeConverter {
    public static OpenRecipe entityToDto(OpenRecipeEntity openAPIRecipeEntity) {
        return OpenRecipe.builder().id(openAPIRecipeEntity.getId())
                .attFileNoMain(openAPIRecipeEntity.getAttFileNoMain()).attFileNoMk(openAPIRecipeEntity.getAttFileNoMk())
                .hashTag(openAPIRecipeEntity.getHashTag())
                .infoCar(openAPIRecipeEntity.getInfoCar()).infoEng(openAPIRecipeEntity.getInfoEng())
                .infoFat(openAPIRecipeEntity.getInfoFat()).infoNa(openAPIRecipeEntity.getInfoNa())
                .infoPro(openAPIRecipeEntity.getInfoPro()).infoWgt(openAPIRecipeEntity.getInfoWgt())
                .manual01(openAPIRecipeEntity.getManual01()).manual02(openAPIRecipeEntity.getManual02()).manual03(openAPIRecipeEntity.getManual03()).manual04(openAPIRecipeEntity.getManual04()).manual05(openAPIRecipeEntity.getManual05())
                .manual06(openAPIRecipeEntity.getManual06()).manual07(openAPIRecipeEntity.getManual07()).manual08(openAPIRecipeEntity.getManual08()).manual09(openAPIRecipeEntity.getManual09()).manual10(openAPIRecipeEntity.getManual10())
                .manual11(openAPIRecipeEntity.getManual11()).manual12(openAPIRecipeEntity.getManual12()).manual13(openAPIRecipeEntity.getManual13()).manual14(openAPIRecipeEntity.getManual14()).manual15(openAPIRecipeEntity.getManual15())
                .manual16(openAPIRecipeEntity.getManual16()).manual17(openAPIRecipeEntity.getManual17()).manual18(openAPIRecipeEntity.getManual18()).manual19(openAPIRecipeEntity.getManual19()).manual20(openAPIRecipeEntity.getManual20())
                .manualImg01(openAPIRecipeEntity.getManualImg01()).manualImg02(openAPIRecipeEntity.getManualImg02()).manualImg03(openAPIRecipeEntity.getManualImg03()).manualImg04(openAPIRecipeEntity.getManualImg04())
                .manualImg05(openAPIRecipeEntity.getManualImg05()).manualImg06(openAPIRecipeEntity.getManualImg06()).manualImg07(openAPIRecipeEntity.getManualImg07()).manualImg08(openAPIRecipeEntity.getManualImg08())
                .manualImg09(openAPIRecipeEntity.getManualImg09()).manualImg10(openAPIRecipeEntity.getManualImg10()).manualImg11(openAPIRecipeEntity.getManualImg11()).manualImg12(openAPIRecipeEntity.getManualImg12())
                .manualImg13(openAPIRecipeEntity.getManualImg13()).manualImg14(openAPIRecipeEntity.getManualImg14()).manualImg15(openAPIRecipeEntity.getManualImg15()).manualImg16(openAPIRecipeEntity.getManualImg16())
                .manualImg17(openAPIRecipeEntity.getManualImg17()).manualImg18(openAPIRecipeEntity.getManualImg18()).manualImg19(openAPIRecipeEntity.getManualImg19()).manualImg20(openAPIRecipeEntity.getManualImg20())
                .rcpNm(openAPIRecipeEntity.getRcpNm()).rcpSeq(openAPIRecipeEntity.getRcpSeq()).rcpWay2(openAPIRecipeEntity.getRcpWay2()).rcpPat2(openAPIRecipeEntity.getRcpPat2()).rcpPartsDtls(openAPIRecipeEntity.getRcpPartsDtls())
                .build();
    }

    public static OpenRecipeEntity dtoToEntity(OpenRecipe openRecipe) {
        return OpenRecipeEntity.builder().id(openRecipe.getId())
                .attFileNoMain(openRecipe.getAttFileNoMain()).attFileNoMk(openRecipe.getAttFileNoMk())
                .hashTag(openRecipe.getHashTag())
                .infoCar(openRecipe.getInfoCar()).infoEng(openRecipe.getInfoEng())
                .infoFat(openRecipe.getInfoFat()).infoNa(openRecipe.getInfoNa())
                .infoPro(openRecipe.getInfoPro()).infoWgt(openRecipe.getInfoWgt())
                .manual01(openRecipe.getManual01()).manual02(openRecipe.getManual02()).manual03(openRecipe.getManual03()).manual04(openRecipe.getManual04()).manual05(openRecipe.getManual05())
                .manual06(openRecipe.getManual06()).manual07(openRecipe.getManual07()).manual08(openRecipe.getManual08()).manual09(openRecipe.getManual09()).manual10(openRecipe.getManual10())
                .manual11(openRecipe.getManual11()).manual12(openRecipe.getManual12()).manual13(openRecipe.getManual13()).manual14(openRecipe.getManual14()).manual15(openRecipe.getManual15())
                .manual16(openRecipe.getManual16()).manual17(openRecipe.getManual17()).manual18(openRecipe.getManual18()).manual19(openRecipe.getManual19()).manual20(openRecipe.getManual20())
                .manualImg01(openRecipe.getManualImg01()).manualImg02(openRecipe.getManualImg02()).manualImg03(openRecipe.getManualImg03()).manualImg04(openRecipe.getManualImg04())
                .manualImg05(openRecipe.getManualImg05()).manualImg06(openRecipe.getManualImg06()).manualImg07(openRecipe.getManualImg07()).manualImg08(openRecipe.getManualImg08())
                .manualImg09(openRecipe.getManualImg09()).manualImg10(openRecipe.getManualImg10()).manualImg11(openRecipe.getManualImg11()).manualImg12(openRecipe.getManualImg12())
                .manualImg13(openRecipe.getManualImg13()).manualImg14(openRecipe.getManualImg14()).manualImg15(openRecipe.getManualImg15()).manualImg16(openRecipe.getManualImg16())
                .manualImg17(openRecipe.getManualImg17()).manualImg18(openRecipe.getManualImg18()).manualImg19(openRecipe.getManualImg19()).manualImg20(openRecipe.getManualImg20())
                .rcpNm(openRecipe.getRcpNm()).rcpSeq(openRecipe.getRcpSeq()).rcpWay2(openRecipe.getRcpWay2()).rcpPat2(openRecipe.getRcpPat2()).rcpPartsDtls(openRecipe.getRcpPartsDtls())
                .build();
    }
}
