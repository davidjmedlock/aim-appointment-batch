package com.aim.appointment.config.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppointmentScrubTasklet implements Tasklet {
    private final Logger log = LoggerFactory.getLogger(AppointmentScrubTasklet.class);

    private JdbcTemplate jdbcTemplate;
    private String fileName;
    private String tenantHashKey;

    public AppointmentScrubTasklet(JdbcTemplate jdbcTemplate, String fileName, String tenantHashKey) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileName = fileName;
        this.tenantHashKey = tenantHashKey;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Integer i = jdbcTemplate.queryForObject("SELECT appointment_load_000_init(?, ?)", new Object[] {fileName, tenantHashKey},
                (rs, row) -> rs.getInt(1));
        log.info("Updated {} rows with file name and tenant hash key: {} : {}", i, fileName, tenantHashKey);

        /* This will just run all the functions we need to run sequentially */
        jdbcTemplate.execute("SELECT appointment_scrub_01_first_name()");
        jdbcTemplate.execute("SELECT appointment_scrub_02_last_name()");
        jdbcTemplate.execute("SELECT appointment_scrub_03_npi()");
        jdbcTemplate.execute("SELECT appointment_scrub_04_address_1()");
        jdbcTemplate.execute("SELECT appointment_scrub_05_city()");
        jdbcTemplate.execute("SELECT appointment_scrub_06_state()");
        jdbcTemplate.execute("SELECT appointment_scrub_07_zip()");
        jdbcTemplate.execute("SELECT appointment_scrub_08_phone_number()");
        jdbcTemplate.execute("SELECT appointment_scrub_09_appointment_type()");
        jdbcTemplate.execute("SELECT appointment_scrub_10_extraction_date_time()");
        jdbcTemplate.execute("SELECT appointment_scrub_11_next_available_appt()");
        jdbcTemplate.execute("SELECT appointment_scrub_12_city_state_zip()");
        jdbcTemplate.execute("SELECT appointment_scrub_13_invalid_appt_type()");
        jdbcTemplate.execute("SELECT appointment_scrub_14_invalid_extraction_date_time()");
        jdbcTemplate.execute("SELECT appointment_scrub_15_invalid_next_available_appt()");
        jdbcTemplate.execute("SELECT appointment_scrub_16_extraction_date_gt_current()");
        jdbcTemplate.execute("SELECT appointment_scrub_17_extraction_date_gt_next_appt()");
        jdbcTemplate.execute("SELECT appointment_scrub_18_duplicate_appt()");

        // clean up procedures
        jdbcTemplate.execute("SELECT appointment_scrub_998_post_scrub()");
        jdbcTemplate.execute("SELECT appointment_scrub_999_flag_error_records()");

        return RepeatStatus.FINISHED;
    }
}
