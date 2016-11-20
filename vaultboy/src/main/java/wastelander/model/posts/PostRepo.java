package wastelander.model.posts;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

/**
 * Created by john on 11/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface PostRepo extends PagingAndSortingRepository<Post, UUID> {
}
