package com.aim.appointment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED !!!");
            jdbcTemplate.query("SELECT COUNT(*) FROM tmp_appointment_load",
                    (rs, row) -> rs.getInt(1))
                    .forEach(count -> log.info("Found {} appointment load records in the database.", count));

            jdbcTemplate.query("SELECT COUNT(*) FROM tmp_appointment_load_error",
                    (rs, row) -> rs.getInt(1))
                    .forEach(count -> log.info("Found {} appointment error records in the database.", count));

            jdbcTemplate.query("SELECT COUNT(*) FROM tmp_appointment_load_hp_error",
                    (rs, row) -> rs.getInt(1))
                    .forEach(count -> log.info("Found {} appointment load HP error records in the database.", count));
        }
    }
}
