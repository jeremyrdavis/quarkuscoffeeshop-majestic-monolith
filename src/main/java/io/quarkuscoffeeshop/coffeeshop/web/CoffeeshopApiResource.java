package io.quarkuscoffeeshop.coffeeshop.web;


import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeshopApiResource {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeshopApiResource.class);

    @Inject
    OrderService orderService;

    @Inject
    EventBus eventBus;

    @POST
    @Path("/order")
    @Transactional
    public Uni<Response> placeOrder(final PlaceOrderCommand placeOrderCommand) {

        logger.info("PlaceOrderCommand received: {}", placeOrderCommand);
        return orderService.onOrderIn(placeOrderCommand)
                .onItem()
                .transform(order -> {
                    return Response.accepted().build();
                })
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @POST
    @Path("/message")
    public void sendMessage(final String message) {
//        webUpdater.send(message);
        logger.debug("received message: {}", message);
        logger.debug("sending to web-updates: {}", message);
        eventBus.sendAndForget("web-updates", message);
    }

}
