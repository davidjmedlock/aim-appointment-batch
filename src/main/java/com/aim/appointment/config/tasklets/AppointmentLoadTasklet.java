package com.aim.appointment.config.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppointmentLoadTasklet implements Tasklet {
    private final Logger log = LoggerFactory.getLogger(AppointmentLoadTasklet.class);

    private JdbcTemplate jdbcTemplate;

    public AppointmentLoadTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        jdbcTemplate.execute("SELECT appointment_load_001_load_appt_records()");
        jdbcTemplate.execute("SELECT appointment_load_002_name_npi_address_match()");
        jdbcTemplate.execute("SELECT appointment_load_003_npi_address_match()");
        jdbcTemplate.execute("SELECT appointment_load_004_npi_partial_address_match()");
        jdbcTemplate.execute("SELECT appointment_load_005_provider_address_xref_match()");
        jdbcTemplate.execute("SELECT appointment_load_998_hp_npi_only_match()");
        return RepeatStatus.FINISHED;
    }
}
