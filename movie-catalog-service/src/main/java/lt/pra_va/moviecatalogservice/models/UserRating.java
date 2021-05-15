package lt.pra_va.moviecatalogservice.models;

import java.util.List;

public class UserRating {
    private List<Rating> userRating;

    public UserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }

    public UserRating() {
    }

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
