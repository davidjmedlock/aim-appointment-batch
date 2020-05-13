package com.aim.appointment.config.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppointmentRuleProcessTasklet implements Tasklet {
    private final Logger log = LoggerFactory.getLogger(AppointmentRuleProcessTasklet.class);

    private JdbcTemplate jdbcTemplate;

    public AppointmentRuleProcessTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // run appointment rule processing functions
        jdbcTemplate.execute("SELECT appointment_rule_proc_000_init()");
        jdbcTemplate.execute("SELECT appointment_rule_proc_001_remove_for_changed_rules()");
        jdbcTemplate.execute("SELECT appointment_rule_proc_002_process_avail_rules()");
        jdbcTemplate.execute("SELECT appointment_rule_proc_999_post_process()");

        return RepeatStatus.FINISHED;
    }
}
