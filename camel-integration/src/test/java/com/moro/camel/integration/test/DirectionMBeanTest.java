package com.moro.camel.integration.test;

import com.moro.camel.integration.mbeans.Direction;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:camel-spring-test.xml"})
public class DirectionMBeanTest {

    @Autowired
    private Direction direction;

    @Test
    public void defaultDirection()  {
        Assert.assertEquals(direction.getDirection(), direction.getRestDirection());
    }

    @Test
    public void setSoapDirection() {
        direction.setDirection("soap");
        Assert.assertEquals(direction.getDirection(), direction.getSoapDirection());
    }

    @Test
    public void ConstructorWithArguments() {
        Direction dir = new Direction("soap");
        Assert.assertEquals("soap", dir.getDirection());
        dir.setDirection("rest");
        Assert.assertEquals("rest", dir.getDirection());
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void setWrongDirection() {
        exception.expect(IllegalArgumentException.class);
        direction.setDirection("heaven");
    }
}
