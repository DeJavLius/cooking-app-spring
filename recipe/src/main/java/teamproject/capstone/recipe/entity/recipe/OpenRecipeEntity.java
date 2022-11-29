package teamproject.capstone.recipe.entity.recipe;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "open_recipe")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long rcpSeq;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL")
    private String rcpNm;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL")
    private String rcpPat2;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL")
    private String rcpWay2;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL")
    private String rcpPartsDtls;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL")
    private String hashTag;
    @Column
    private Double infoFat;
    @Column
    private Double infoEng;
    @Column
    private Double infoCar;
    @Column
    private Double infoPro;
    @Column
    private Double infoWgt;
    @Column
    private Double infoNa;
    @Column
    private String attFileNoMk;
    @Column
    private String attFileNoMain;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual01;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual02;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual03;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual04;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual05;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual06;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual07;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual08;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual09;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual10;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual11;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual12;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual13;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual14;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual15;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual16;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual17;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual18;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual19;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manual20;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg01;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg02;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg03;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg04;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg05;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg06;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg07;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg08;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg09;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg10;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg11;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg12;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg13;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg14;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg15;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg16;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg17;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg18;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg19;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL")
    private String manualImg20;
}
