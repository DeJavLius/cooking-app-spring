package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.service.recipe.FavoriteRankService;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.recipe.OpenRecipePageWithSearchService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeService;
import teamproject.capstone.recipe.service.user.UserService;

@RequestMapping("/api/v1/ajax")
@RequiredArgsConstructor
@Slf4j
@Controller
public class FavoriteAPIController {
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final OpenRecipeService openRecipeService;

    @PostMapping("/favorite")
    public String webOneFavoriteRecipe(Favorite favorite, Model model) {
        boolean isFavorite = false;

        if (userService.isUserExist(favorite.getUserEmail())) {
            OpenRecipe recipe = openRecipeService.findRecipe(favorite.getRecipeId());
            Favorite find = favoriteService.findRecipe(recipe.getRcpSeq(), favorite.getUserEmail());

            if (find.getId() == 0L) {
                Favorite saved = favoriteService.create(favoriteRecipeValue(favorite, recipe));
                isFavorite = true;
                log.info("check saved values : {}", saved);
            } else {
                favoriteService.delete(find);
            }
        }

        log.info("check Favorite values : {}", favorite);
        log.info("check is Favorite : {}", isFavorite);

        model.addAttribute("isFavorite", isFavorite);
        return "recipe/recipeDetail :: #favoriteCheck";
    }

    private Favorite favoriteRecipeValue(Favorite favorite, OpenRecipe openRecipe) {
        favorite.setRecipeId(openRecipe.getId());
        favorite.setRecipeSeq(openRecipe.getRcpSeq());
        favorite.setRecipeName(openRecipe.getRcpNm());
        favorite.setRecipeMainImage(openRecipe.getAttFileNoMain());
        favorite.setRecipeWay(openRecipe.getRcpWay2());
        favorite.setRecipePart(openRecipe.getRcpPat2());

        return favorite;
    }
}
