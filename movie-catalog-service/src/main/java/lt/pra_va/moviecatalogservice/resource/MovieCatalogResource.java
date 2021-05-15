package lt.pra_va.moviecatalogservice.resource;

import com.netflix.discovery.DiscoveryClient;
import lt.pra_va.moviecatalogservice.models.CatalogItem;
import lt.pra_va.moviecatalogservice.models.Movie;
import lt.pra_va.moviecatalogservice.models.Rating;
import lt.pra_va.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Get instances; do advanced load balancing etc. Better leave it alone, unless you know what you are doing.
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        // get all rated movie IDs
        // for each movie ID, call movie info service and get details
        // put them all together

        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            // Rest template method (to be deprecated in near future)
            Movie movie = restTemplate.getForObject("http://movie-info-service:8082/movies/" + rating.getMovieId(), Movie.class);

            return new CatalogItem(movie.getName(), "Description", rating.getRating());
        }).collect(Collectors.toList());

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