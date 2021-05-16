package lt.pra_va.ratingsdataservice.resources;

import lt.pra_va.ratingsdataservice.models.Rating;
import lt.pra_va.ratingsdataservice.models.UserRating;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratings =  Arrays.asList(
//                new Rating("1234", 4),
//                new Rating("5678", 3),
                new Rating("100", 3),
                new Rating("200", 4)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        userRating.setUserId(userId);

        return  userRating;
    }
}
