package com.example.quickfixjspringboot.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;

@Slf4j
@Component
public class ClientFixApp implements Application {

    @Override
    public void onCreate(SessionID sessionID) {
        log.info("--------- onCreate ---------");
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.info("--------- onLogon ---------");
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.info("--------- onLogout ---------");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        log.info("--------- toAdmin --------->" + message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("--------- fromAdmin --------->" + message);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.info("--------- toApp --------->" + message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) {
        log.info("--------- fromApp --------->" + message);
        logTagValueWithTitle(message, 34, "MsgSeqNum");
        logTagValueWithTitle(message, 35, "MsgType");
        logTagValueWithTitle(message, 45, "RefSeqNum");
        logTagValueWithTitle(message, 49, "SenderCompID");
        logTagValueWithTitle(message, 52, "SendingTime");
        logTagValueWithTitle(message, 56, "TargetCompID");
        logTagValueWithTitle(message, 58, "Text");
        logTagValueWithTitle(message, 39, "OrdStatus");
        log.info("----------------------------------------");
        log.info("--------------- fromApp ----------------" );
    }

    private static void logTagValueWithTitle(Message message, int tag, String title) {
        try {
            String tagValue = message.getHeader().getString(tag);
            log.info("({}){}: {}", tag, title, tagValue);
        } catch (FieldNotFound e) {
            log.error("Field {} not found in message", tag);
        }
    }
}
