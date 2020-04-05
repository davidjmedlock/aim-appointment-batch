package com.aim.appointment.config;

import com.aim.appointment.config.tasklets.AppointmentLoadResetTasklet;
import com.aim.appointment.config.tasklets.AppointmentLoadTasklet;
import com.aim.appointment.config.tasklets.AppointmentScrubTasklet;
import com.aim.appointment.model.TmpAppointmentLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class AppointmentLoadConfiguration {
    private final Logger log = LoggerFactory.getLogger(AppointmentLoadConfiguration.class);

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    private static final String[] NAMES = {"first_name", "last_name", "npi", "address_1", "address_2",
                                            "address_3", "city", "state", "zip", "phone_number",
                                            "appointment_type", "extraction_date_time", "next_available_appt"};

    private static final String SQL = "INSERT INTO tmp_appointment_load (first_name, last_name, npi, address_1, " +
                                        "address_2, address_3, city, state, zip, phone_number, " +
                                        "appointment_type, extraction_date_time, next_available_appt) " +
                                        "VALUES (:firstName, :lastName, :npi, :address1, :address2, " +
                                        ":address3, :city, :state, :zip, :phoneNumber, :appointmentType, " +
                                        ":extractionDateTime, :nextAvailableAppt)";

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    @StepScope
    public FlatFileItemReader<TmpAppointmentLoad> reader(@Value("#{jobParameters[pathToFile]}") String pathToFile) {
        log.info("Building FlatFileItemReader for file: {}", pathToFile);
        FlatFileItemReader<TmpAppointmentLoad> reader = new FlatFileItemReaderBuilder<TmpAppointmentLoad>()
                .name("tmpAppointmentLoadReader")
                .resource(new FileSystemResource(pathToFile))
                .delimited()
                .delimiter("|")
                .names(AppointmentLoadConfiguration.NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TmpAppointmentLoad>() {{
                    setTargetType(TmpAppointmentLoad.class);
                }})
                .build();
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<TmpAppointmentLoad> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TmpAppointmentLoad>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(AppointmentLoadConfiguration.SQL)
                .dataSource(dataSource)
                .build();
    }

    @Bean
    @StepScope
    public AppointmentLoadResetTasklet appointmentLoadResetTasklet(@Value("#{jobParameters[tenantHashKey]}") String tenantHashKey) {
        return new AppointmentLoadResetTasklet(this.jdbcTemplate, tenantHashKey);
    }

    @Bean
    @StepScope
    public AppointmentScrubTasklet appointmentScrubTasklet(
            @Value("#{jobParameters[pathToFile]}") String pathToFile,
            @Value("#{jobParameters[tenantHashKey]}") String tenantHashKey) {
        log.info("Building Appointment Scrub Tasklet for file: {}", pathToFile);
        return new AppointmentScrubTasklet(this.jdbcTemplate, pathToFile, tenantHashKey);
    }

    @Bean
    @StepScope
    public AppointmentLoadTasklet appointmentLoadTasklet() {
        return new AppointmentLoadTasklet(this.jdbcTemplate);
    }

    @Bean
    public Job importTmpAppointmentLoadJob(JobCompletionNotificationListener listener, Step loadAppointmentFile) {
        return jobBuilderFactory.get("importTmpAppointmentLoadJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(appointmentLoadReset())
                .next(loadAppointmentFile)
                .next(appointmentScrub())
                .next(appointmentLoad())
                .build();
    }

    @Bean
    public Step loadAppointmentFile(JdbcBatchItemWriter<TmpAppointmentLoad> writer) {
        return stepBuilderFactory.get("loadAppointmentFile")
                .<TmpAppointmentLoad, TmpAppointmentLoad> chunk(10)
                .reader(reader(AppointmentLoadConfiguration.OVERRIDDEN_BY_EXPRESSION))
                .writer(writer)
                .build();
    }

    @Bean
    public Step appointmentLoadReset() {
        return stepBuilderFactory.get("appointmentLoadReset")
                .tasklet(appointmentLoadResetTasklet(AppointmentLoadConfiguration.OVERRIDDEN_BY_EXPRESSION)).build();
    }

    @Bean
    public Step appointmentScrub() {
        return stepBuilderFactory.get("appointmentScrub")
                .tasklet(appointmentScrubTasklet(AppointmentLoadConfiguration.OVERRIDDEN_BY_EXPRESSION, AppointmentLoadConfiguration.OVERRIDDEN_BY_EXPRESSION)).build();
    }

    @Bean
    public Step appointmentLoad() {
        return stepBuilderFactory.get("providerLoad")
                .tasklet(appointmentLoadTasklet()).build();
    }
}
