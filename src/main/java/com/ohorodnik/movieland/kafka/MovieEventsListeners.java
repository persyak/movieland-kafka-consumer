package com.ohorodnik.movieland.kafka;

import com.ohorodnik.movieland.dto.events.MovieEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovieEventsListeners {

    @KafkaListener(topics = "movietopic-int")
    public void onMessage(ConsumerRecord<Integer, MovieEventDto> consumerRecord) {

        MovieEventDto movieEventDto = consumerRecord.value();

        log.info("Message received successfully with key: {}, value: {}, partition: {}, offset: {}",
                consumerRecord.key(),
                movieEventDto,
                consumerRecord.partition(),
                consumerRecord.offset());
    }
}
