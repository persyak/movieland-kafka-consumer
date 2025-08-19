package com.ohorodnik.movieland;

import com.ohorodnik.movieland.dto.CountryDto;
import com.ohorodnik.movieland.dto.GenreDto;
import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.kafka.MovieEventsListeners;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Year;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(topics = "movietopic-int", partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class KafkaConsumerITest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private KafkaTemplate<Integer, MovieDetailsDto> kafkaTemplate;
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @MockitoSpyBean
    private MovieEventsListeners movieEventsListeners;

    @BeforeEach
    public void setup(){
        // Start the Kafka listener container before each test
        kafkaListenerEndpointRegistry.getListenerContainers().forEach(messageListenerContainer -> {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        });
    }

    @Test
    public void publishNewMovieDetailsEvent() throws ExecutionException, InterruptedException {
        MovieDetailsDto testMovieDetailsDto = createMovieDetailsDto();
        kafkaTemplate.sendDefault(testMovieDetailsDto).get();

        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);

        verify(movieEventsListeners, times(1)).onMessage(isA(ConsumerRecord.class));
    }

    private MovieDetailsDto createMovieDetailsDto() {
        return MovieDetailsDto.builder()
                .id(1)
                .nameUa("Тестовий фільм")
                .nameNative("Test Movie")
                .yearOfRelease(Year.of(2024))
                .description("This is a test movie description.")
                .rating(0.0)
                .price(203.0)
                .picturePath("test/path/to/picture.jpg")
                .countries(List.of(CountryDto.builder().id(1).name("США").build(),
                        CountryDto.builder().id(2).name("Україна").build()))
                .genres(List.of(GenreDto.builder().id(1).name("genre1").build(),
                        GenreDto.builder().id(2).name("genre2").build())).build();
    }
}
