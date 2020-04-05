package com.aim.appointment.config.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppointmentLoadResetTasklet implements Tasklet {
    private final Logger log = LoggerFactory.getLogger(AppointmentLoadResetTasklet.class);

    private JdbcTemplate jdbcTemplate;

    private String tenantHashKey;

    public AppointmentLoadResetTasklet(JdbcTemplate jdbcTemplate, String tenantHashKey) {
        this.jdbcTemplate = jdbcTemplate;
        this.tenantHashKey = tenantHashKey;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info("Resetting the Appointment Load table.");
        String sql = "DELETE FROM tmp_appointment_load WHERE tenant_hash_key = ?";
        Object[] args = new Object[] {tenantHashKey};

        jdbcTemplate.update(sql, args);

        return RepeatStatus.FINISHED;
    }
}
