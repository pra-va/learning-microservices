package lt.pra_va.moviecatalogservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lt.pra_va.moviecatalogservice.models.CatalogItem;
import lt.pra_va.moviecatalogservice.models.Movie;
import lt.pra_va.moviecatalogservice.models.Rating;
import lt.pra_va.moviecatalogservice.models.UserRating;
import lt.pra_va.moviecatalogservice.services.MovieInfo;
import lt.pra_va.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        // get all rated movie IDs
        // for each movie ID, call movie info service and get details
        // put them all together

        UserRating ratings = userRatingInfo.getUserRating(userId);

        // Rest template method (to be deprecated in near future)
        return ratings.getUserRating().stream().map(movieInfo::getCatalogItem).collect(Collectors.toList());
    }

}

/*
            Using web client builder
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
*/