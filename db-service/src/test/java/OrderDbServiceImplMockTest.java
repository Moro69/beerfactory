import com.moro.dao.interfaces.OrderDao;
import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import org.junit.After;
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
public class OrderDbServiceImplMockTest {

    @Autowired
    private OrderDao mockOrderDao;
    @Autowired
    private OrderService orderService;

    private static final String FROMDATE = "2017-05-23";
    private static final String TODATE = "2018-06-23";

    private static final int ID = 1;
    private static final Order ORDER = new Order(TODATE, 1, 10, 2000);

    @After
    public void tearDown() {
        verify(mockOrderDao);
        reset(mockOrderDao);
    }

    @Test
    public void getAllOrders() {
        Collection<Order> orders =
                new ArrayList<>();

        expect(mockOrderDao.getAllOrders())
                .andReturn(orders);
        replay(mockOrderDao);

        orderService.getAllOrders();
    }

    @Test
    public void getOrdersByDate() {
        Collection<Order> orders =
                new ArrayList<>();

        expect(mockOrderDao.getOrdersByDate(FROMDATE, TODATE))
                .andReturn(orders);
        replay(mockOrderDao);

        orderService.getOrdersByDate(FROMDATE, TODATE);
    }

    @Test
    public void getOrderById() {
        expect(mockOrderDao.getOrderById(ID))
                .andReturn(ORDER);
        replay(mockOrderDao);

        orderService.getOrderById(ID);
    }

    @Test
    public void addOrder() {
        expect(mockOrderDao.addOrder(ORDER))
                .andReturn(ORDER);
        replay(mockOrderDao);

        orderService.addOrder(ORDER);
    }

    @Test
    public void deleteOrderById() {
        mockOrderDao.deleteOrderById(ID);

        expectLastCall();
        replay(mockOrderDao);

        orderService.deleteOrderById(ID);
    }
}
