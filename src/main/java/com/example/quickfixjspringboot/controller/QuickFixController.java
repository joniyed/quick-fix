package com.example.quickfixjspringboot.controller;

import com.example.quickfixjspringboot.models.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;

import java.util.ArrayList;

/**
 * Project: quickfixj-spring-boot
 * Author: Joniyed Bhuiyan
 * Email: joniyed.bhuiyan@gmail.com
 * Date: ৮/৫/২৩
 * Day & Time: Monday, ৬:৫৬ AM
 */

@AllArgsConstructor
@RestController
@RequestMapping(value = "")
public class QuickFixController {

    private final ThreadedSocketInitiator threadedSocketInitiator;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {

        // Create a new QuickFIX/J NewOrderSingle message
        NewOrderSingle newOrder = new NewOrderSingle(
                new ClOrdID(orderRequest.getClientOrderId()),
                new Side(orderRequest.getSide()),
                new TransactTime(),
                new OrdType(orderRequest.getOrderType())
        );

        // Set additional fields on the message
        newOrder.set(new Symbol(orderRequest.getSymbol()));
        newOrder.set(new OrderQty(orderRequest.getQuantity()));
        newOrder.set(new TimeInForce('1')); // Day order
        newOrder.set(new Price(orderRequest.getPrice()));
        newOrder.set(new HandlInst(HandlInst.AUTOMATED_EXECUTION_ORDER_PRIVATE_NO_BROKER_INTERVENTION));

        ArrayList<SessionID> sessions = threadedSocketInitiator.getSessions();
        SessionID sessionID = sessions.get(0);
        Session session = Session.lookupSession(sessionID);
        boolean send = session.send(newOrder);

        return new ResponseEntity<>(newOrder.toString(), HttpStatus.OK);
    }


    @GetMapping("/retrieve-order")
    public OrderRequest createOrder(@RequestParam(value = "message") String newOrderSingleText) {
        // Parse the QuickFIX/J NewOrderSingle message from the text
        NewOrderSingle newOrderSingle = new NewOrderSingle();
        try {
            newOrderSingle.fromString(newOrderSingleText, null, false);
            // Create a new instance of your custom Order class
            OrderRequest order = new OrderRequest();

            // Map fields from the NewOrderSingle message to the Order object
            order.setClientOrderId(newOrderSingle.get(new ClOrdID()).getValue());
            order.setSymbol(newOrderSingle.get(new Symbol()).getValue());
            order.setSide(newOrderSingle.get(new Side()).getValue());
            order.setQuantity(newOrderSingle.get(new OrderQty()).getValue());
            order.setPrice(newOrderSingle.get(new Price()).getValue());
            order.setOrderType(newOrderSingle.get(new OrdType()).getValue());

            return order;
        } catch (InvalidMessage | FieldNotFound e) {
            throw new RuntimeException(e);
        }

    }

}
