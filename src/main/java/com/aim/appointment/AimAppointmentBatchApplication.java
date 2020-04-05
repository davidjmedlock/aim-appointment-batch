package com.aim.appointment;

import com.aim.appointment.model.BatchFileName;
import com.aim.appointment.repository.TenantKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

@SpringBootApplication
public class AimAppointmentBatchApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(AimAppointmentBatchApplication.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importTmpAppointmentLoadJob")
    Job job;

    @Autowired
    TenantKeyRepository tenantKeyRepository;

    public static void main(String[] args) {
        SpringApplication.run(AimAppointmentBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            throw new RuntimeException("No input file provided.");
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            throw new FileNotFoundException("The input file provided was not found.");
        }

        BatchFileName batchFileName = new BatchFileName(file.getName());
        String tenantHashKey = tenantKeyRepository.findByBatchCode(batchFileName.getProviderGroupBatchCode())
                .orElseThrow(NoSuchElementException::new).getTenantHashKey();

        JobParameters params = new JobParametersBuilder()
                .addString("pathToFile", file.getAbsolutePath())
                .addString("providerGroupBatchCode", batchFileName.getProviderGroupBatchCode())
                .addString("tenantHashKey", tenantHashKey)
                .addLong("executionTime", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, params);
    }

}
