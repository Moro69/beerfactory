package com.moro.dao.test;

import com.moro.dao.interfaces.BeerDao;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-db-spring.xml",
        "classpath:test-dao.xml", "classpath:dao.xml"})
@Rollback
@Transactional
public class BeerDaoImplTest {

    @Autowired
    private BeerDao beerDao;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static final Beer BEER =
            new Beer("Zhigulevskoe", 4.6, "Good beer", 105, "zhigulevskoe");

    @Test
    public void getAllBeers() {
        Collection<BeerDto> beers =
                beerDao.getAllBeers();

        Assert.assertNotNull(beers);
        Assert.assertFalse(beers.isEmpty());
    }

    @Test
    public void getBeerById() {
        Beer beer = beerDao.getBeerById(1);

        Assert.assertNotNull(beer);
        Assert.assertEquals(1, (int) beer.getBeerId());
        Assert.assertEquals("Hamovniki", beer.getBeerTitle());
        Assert.assertTrue(beer.getBeerAbv() == 5.6);
        Assert.assertEquals("Pretty good beer", beer.getDescription());
        Assert.assertEquals(120, beer.getPrice());

    }

    @Test
    public void addBeer() {
        Collection<BeerDto> beers =
                beerDao.getAllBeers();
        int sizeBefore = beers.size();

        Beer newBeer = beerDao.addBeer(BEER);

        Assert.assertNotNull(newBeer.getBeerId());
        Assert.assertTrue(newBeer.getBeerTitle().equals(BEER.getBeerTitle()));
        Assert.assertTrue(newBeer.getBeerAbv() == BEER.getBeerAbv());
        Assert.assertTrue(newBeer.getDescription().equals(BEER.getDescription()));
        Assert.assertTrue(newBeer.getPrice() == BEER.getPrice());
        Assert.assertTrue((sizeBefore + 1) == beerDao.getAllBeers().size());
    }

    @Test
    public void addSameBeer () {
        exception.expect(DuplicateKeyException.class);

        beerDao.addBeer(BEER);
        beerDao.addBeer(BEER);
    }

    @Test
    public void updateBeer() {

        Beer newBeer = beerDao.addBeer(BEER);

        newBeer.setPrice(115);
        newBeer.setDescription("Probably the best beer in the world");

        beerDao.updateBeer(newBeer);

        Beer updatedBeer = beerDao.getBeerById(newBeer.getBeerId());

        Assert.assertNotNull(updatedBeer);
        Assert.assertTrue((updatedBeer.getBeerId()).equals(newBeer.getBeerId()));
        Assert.assertTrue(updatedBeer.getPrice() == newBeer.getPrice());
        Assert.assertTrue(updatedBeer.getDescription().equals(newBeer.getDescription()));
    }

    @Test
    public void deleteBeerById() {

        Beer newBeer = beerDao.addBeer(BEER);
        int sizeBefore = beerDao.getAllBeers().size();

        beerDao.deleteBeerById(newBeer.getBeerId());

        Assert.assertTrue((sizeBefore - 1) == beerDao.getAllBeers().size());

        exception.expect(EmptyResultDataAccessException.class);
        beerDao.getBeerById(newBeer.getBeerId());
    }
}
