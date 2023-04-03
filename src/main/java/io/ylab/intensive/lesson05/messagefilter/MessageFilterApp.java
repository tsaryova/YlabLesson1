package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.filterword.FilterWordDao;
import io.ylab.intensive.lesson05.messagefilter.filterword.WordFilterDbUtil;
import io.ylab.intensive.lesson05.messagefilter.message.Message;
import io.ylab.intensive.lesson05.messagefilter.parsing.file.ParsingWordsFile;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {

    private static final String NAME_INPUT_QUEUE = "input";
    private static final String NAME_OUTPUT_QUEUE = "output";

    public static void main(String[] args) throws IOException, SQLException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        //создание / очищение таблицы filter_word
        WordFilterDbUtil wordFilterDbUtil = applicationContext.getBean(WordFilterDbUtil.class);
        wordFilterDbUtil.createTable();
        wordFilterDbUtil.clearTable();

        //заполнение таблицы путём считывания слов из файла
        File file = new File("src/main/resources/lessons/lesson5/filter_words.txt");
        ParsingWordsFile parsingWordsFile = applicationContext.getBean(ParsingWordsFile.class);
        List<String> filterWordList = parsingWordsFile.getWordList(file, "\n");

        FilterWordDao filterWordDao = applicationContext.getBean(FilterWordDao.class);
        filterWordDao.saveBatchWords(filterWordList);

        //отправка и фильтрация сообщений
        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        Message message = applicationContext.getBean(Message.class);

        String testMessage = "Привет хуй! Ты как гавно уродливое!";
        message.sendMessage(testMessage, NAME_INPUT_QUEUE);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            while (!Thread.currentThread().isInterrupted()) {
                String unfilteringMessage = message.getMessage(channel, NAME_INPUT_QUEUE);
                message.sendMessage(message.filteringMessage(unfilteringMessage), NAME_OUTPUT_QUEUE);
                String filteringMessage = message.getMessage(channel, NAME_OUTPUT_QUEUE);
                if (filteringMessage != null) {
                    System.out.println(filteringMessage);
                }
            }

        }

        applicationContext.stop();
    }
}
