package io.ylab.intensive.lesson05.messagefilter.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.filterword.FilterWord;
import io.ylab.intensive.lesson05.messagefilter.filterword.FilterWordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageImpl implements Message {

    private ConnectionFactory connectionFactory;
    private FilterWordDao filterWordDao;

    @Autowired
    public MessageImpl(ConnectionFactory connectionFactory, FilterWordDao filterWordDao) {
        this.connectionFactory = connectionFactory;
        this.filterWordDao = filterWordDao;
    }

    @Override
    public void sendMessage(String message, String queue) throws IOException, TimeoutException {
        if (message != null) {
            try (Connection connection = this.connectionFactory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(queue, true, false, false, null);
                channel.basicPublish("", queue, null, message.getBytes());
            }
        }
    }

    @Override
    public String getMessage(Channel channel, String queue) throws IOException {
        GetResponse message = channel.basicGet(queue, true);
        if (message != null) {
            return new String(message.getBody());
        }

        return null;
    }

    @Override
    public String filteringMessage(String inputMessage) throws SQLException {
        if (inputMessage == null) {
            return null;
        }

        String resultStr = inputMessage;
        String[] arrWords = inputMessage.split("[ .,;:?!\\n]");
        for (String word : arrWords) {
            FilterWord filterWord = this.filterWordDao.findWord(word);
            if (filterWord != null) {
                String filteringWord = replaceCharsRule(word, '*');
                resultStr = resultStr.replaceFirst(word, filteringWord);
            }
        }

        return resultStr;
    }

    private String replaceCharsRule(String word, char censChar) {
        char[] arrChars = word.toCharArray();
        if (arrChars.length > 2) {
            for (int i = 1; i < arrChars.length - 1; i++) {
                arrChars[i] = censChar;
            }
        }

        return new String(arrChars);
    }
}
