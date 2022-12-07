package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.user.UserService;

@RequestMapping("/api/v1/ajax")
@RequiredArgsConstructor
@Slf4j
@Controller
public class FavoriteAPIController {
    private final UserService userService;

    @PostMapping("/favorite")
    public String webOneFavoriteRecipe(Favorite favorite, Model model) {
        boolean isFavorite = false;

//        if (userService.isUserExist(favorite.getUserEmail())) {
//            Favorite result = openRecipeFavoriteService.provideFavorite(favorite.getUserEmail(), favorite.getRecipeSeq());

//            if (favoriteService.isFavoriteNotExist(result)) {
//                favoriteService.create(result);
//                isFavorite = true;
//            } else {
//                favoriteService.delete(favorite.getUserEmail(), favorite.getRecipeSeq());
//            }
//        }

//        log.info("check is Favorite : {}", isFavorite);
//
//        model.addAttribute("isFavorite", isFavorite);
        return "recipe/recipeDetail :: #favoriteCheck";
    }
}
