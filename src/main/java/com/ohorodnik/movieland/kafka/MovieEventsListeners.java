package com.ohorodnik.movieland.kafka;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
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
    public void onMessage(ConsumerRecord<Integer, MovieDetailsDto> consumerRecord) {

        MovieDetailsDto movieDetailsDto = consumerRecord.value();

        // Logic to handle the incoming message from Kafka
        System.out.println("Received message: " + movieDetailsDto);
    }
}
