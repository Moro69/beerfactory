package com.moro.camel.integration.rest;

import com.moro.camel.integration.mbeans.Direction;
import com.moro.camel.integration.processor.SoapExecutorProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.management.*;
import java.lang.management.ManagementFactory;


public class CamelRestRoute extends RouteBuilder {

    private MBeanServer server;
    private final Direction direction;
    private final SoapExecutorProcessor soapExecutor;

    private static final String DIRECTION_PROPERTY = "direction";
    private static final String GET_DIRECTION_METHOD = "getDirection";

    @Value("${camel.endpoint}")
    private String camelEndpoint;
    @Value("${rest.endpoint}")
    private String restEndpoint;

    @Autowired
    public CamelRestRoute(final SoapExecutorProcessor soapExecutor, final Direction direction)
            throws MalformedObjectNameException,
            NotCompliantMBeanException,
            InstanceAlreadyExistsException,
            MBeanException {

        this.direction = direction;
        server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(direction,
                new ObjectName("com.moro", "name", "directionBean"));

        this.soapExecutor = soapExecutor;
    }

    @Override
    public void configure() {
            from(camelEndpoint)
                    .setProperty(DIRECTION_PROPERTY, method(direction, GET_DIRECTION_METHOD))
                    .choice()
                        .when(exchangeProperty(DIRECTION_PROPERTY).isEqualToIgnoreCase(direction.getRestDirection()))
                            .log("REDIRECT TO THE REST ENDPOINT")
                            .to(restEndpoint).id("rest")

                        .when(exchangeProperty(DIRECTION_PROPERTY).isEqualToIgnoreCase(direction.getSoapDirection()))
                            .to("log:REDIRECT TO THE SOAP ENDPOINT").id("soap")
                            .process(soapExecutor)
                            .marshal().json(JsonLibrary.Jackson)
                            .to("log:PROCESSING IS COMPLETE")

                        .otherwise()
                            .throwException(RuntimeException.class, "No such direction. Available: rest, soap.")
                    .end();
    }
}
