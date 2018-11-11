package com.moro.camel.integration.mbeans;


import org.slf4j.LoggerFactory;

public class Direction implements DirectionMBean {

    private String direction;

    private static final String REST_DIRECTION = "rest";
    private static final String SOAP_DIRECTION = "soap";

    public Direction(String direction) {
        this.direction = direction;
    }

    public Direction() {
        direction = REST_DIRECTION;
    }

    @Override
    public void setDirection(String direction) {
        if(REST_DIRECTION.equals(direction)) {
            this.direction = direction;
        } else if (SOAP_DIRECTION.equals(direction)) {
            this.direction = SOAP_DIRECTION;
        } else throw new IllegalArgumentException("No such direction. Available: rest, soap.");

        LoggerFactory.getLogger(Direction.class).info("DIRECTION WAS SET TO " + direction);
    }

    @Override
    public String getDirection() {
        return direction;
    }

    public String getRestDirection() {
        return REST_DIRECTION;
    }

    public String getSoapDirection() {
        return SOAP_DIRECTION;
    }
}
