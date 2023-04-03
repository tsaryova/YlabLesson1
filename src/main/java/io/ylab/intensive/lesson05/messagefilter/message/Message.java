package io.ylab.intensive.lesson05.messagefilter.message;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public interface Message {

    void sendMessage(String message, String queue) throws IOException, TimeoutException;

    String getMessage(Channel channel, String queue) throws IOException;

    String filteringMessage(String inputMessage) throws SQLException;

}
