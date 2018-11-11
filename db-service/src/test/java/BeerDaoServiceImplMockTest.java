import com.moro.dao.interfaces.BeerDao;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.service.interfaces.BeerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-service-mock-test.xml"})
@Rollback
public class BeerDaoServiceImplMockTest {


    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerDao mockBeerDao;

    private static final int ID = 1;
    private static final Beer BEER = new Beer("White bear",
            6.8, "Full of power", 20, "white-bear");

    @After
    public void tearDown() {
        verify(mockBeerDao);
        reset(mockBeerDao);
    }

    @Test
    public void getAllBeers() {
        Collection<BeerDto> beers =
                new ArrayList<>();

        expect(mockBeerDao.getAllBeers())
                .andReturn(beers);
        replay(mockBeerDao);

        beerService.getAllBeers();

    }

    @Test
    public void getBeerById() {
        expect(mockBeerDao.getBeerById(ID))
                .andReturn(BEER);
        replay(mockBeerDao);

        Beer beer1 = beerService.getBeerById(ID);
        Assert.assertEquals(beer1, BEER);
    }

    @Test
    public void addBeer() {
        expect(mockBeerDao.addBeer(BEER))
                .andReturn(BEER);
        replay(mockBeerDao);

        Beer beer1 = beerService.addBeer(BEER);
        Assert.assertEquals(beer1, BEER);
    }

    @Test
    public void updateBeer() {
        mockBeerDao.updateBeer(BEER);
        expectLastCall();
        replay(mockBeerDao);

        beerService.updateBeer(BEER);
    }

    @Test
    public void deleteBeerById() {
        mockBeerDao.deleteBeerById(ID);
        expectLastCall();

        replay(mockBeerDao);

        beerService.deleteBeerById(ID);
    }
}

