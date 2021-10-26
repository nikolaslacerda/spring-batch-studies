package com.springbatch.enviopromocoesemail.config;

import com.springbatch.enviopromocoesemail.job.EnvioPromocoesClientesScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder
                .newJob(EnvioPromocoesClientesScheduleJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder =
                SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(60)
                        .withRepeatCount(2);
        return TriggerBuilder
                .newTrigger()
                .forJob(quartzJobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }
}
