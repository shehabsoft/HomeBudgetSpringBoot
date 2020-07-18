package com.homeBudget.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shehab.tarek on 7/1/2020.
 */
public class SimpleJob implements Job {

    private final Logger log = LoggerFactory.getLogger(SimpleJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleJob executed!");
    }
}
