import com.moro.dao.interfaces.ReviewDao;
import com.moro.model.Review;
import com.moro.service.interfaces.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-service-mock-test.xml"})
@Rollback
public class ReviewDbServiceImplMockTest {

    @Autowired
    private ReviewDao mockReviewDao;

    @Autowired
    private ReviewService reviewService;

    private static final Review REVIEW = new Review(1, "Full of power", 4);

    @Test
    public void addReview() {
        expect(mockReviewDao.addReview(REVIEW))
                .andReturn(REVIEW);
        replay(mockReviewDao);

        reviewService.addReview(REVIEW);

        verify(mockReviewDao);
        reset(mockReviewDao);

    }
}