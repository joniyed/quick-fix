package com.example.quickfixjspringboot.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;

/**
 * Created by IntelliJ IDEA.
 * User: joniyed
 * Date: ২১/৫/২৩
 * Time: ১১:২০ PM
 * Email: joniyed.bhuiyan@gmail.com
 */

@Slf4j
@Component
public class MessageCracker extends quickfix.fix42.MessageCracker {
    @Override
    public void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        super.onMessage(message, sessionID);
        crack(message, sessionID);
        log.info("from crack");
    }
}
