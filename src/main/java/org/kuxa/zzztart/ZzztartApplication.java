package org.kuxa.zzztart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ZzztartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzztartApplication.class, args);
    }

    @Bean
    public SleepTrackerBot initializeBot(SleepTrackerBot sleepTrackerBot) {
        sleepTrackerBot.run();
        return sleepTrackerBot;
    }
}
