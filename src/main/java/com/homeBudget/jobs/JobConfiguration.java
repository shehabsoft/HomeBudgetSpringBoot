package com.homeBudget.jobs;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {




    @Bean
    public JobDetail UpdateRecovedPateintsJob() {
        return JobBuilder.newJob(UpdateRecovedPateintsJob.class).withIdentity("updateRecovedPateintsJob")
            .storeDurably().build();
    }

    @Bean
    public Trigger UpdateRecovedPateintsJobTrigger(JobDetail UpdateRecovedPateintsJob) {

        return TriggerBuilder.newTrigger().forJob(UpdateRecovedPateintsJob)
            .withIdentity("updateRecovedPateintsJobTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * WED *"))
            .build();
    }







}
