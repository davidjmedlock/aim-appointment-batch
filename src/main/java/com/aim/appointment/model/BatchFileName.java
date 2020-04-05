package com.aim.appointment.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BatchFileName {
    private final Logger log = LoggerFactory.getLogger(BatchFileName.class);

    private static final List<String> VALID_FILE_TYPES = Collections.unmodifiableList(Arrays.asList("APPOINTMENT"));

    private String fileName;

    private String providerGroupBatchCode;

    private String dateStamp;

    private String fileType;

    public BatchFileName(String fileName) {
        this.fileName = fileName.toUpperCase();
        parseFileName();
    }

    private void parseFileName() {
        String[] fileNameNoExt = fileName.split("\\.");
        if (fileNameNoExt.length < 1) {
            log.error("Invalid file name identified : {}", fileName);
            throw new RuntimeException("File name is invalid. Must be formatted as [BATCHCODE]_[DATESEGMENT]_[FILETYPE].csv");
        }

        String[] fileSegments = fileNameNoExt[0].split("_");
        if (fileSegments.length < 3) {
            log.error("Invalid file name identified (missing elements) : {}", fileName);
            throw new RuntimeException("File name is invalid. Must be formatted as [BATCHCODE]_[DATESTAMP]_[FILETYPE].csv");
        }

        providerGroupBatchCode = fileSegments[0];
        dateStamp = fileSegments[1];

        fileType = fileSegments[2];
        if (!BatchFileName.VALID_FILE_TYPES.contains(fileType)) {
            log.error("Invalid file type identified : {}", fileType);
            throw new RuntimeException("Specified file type is invalid.");
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProviderGroupBatchCode() {
        return providerGroupBatchCode;
    }

    public void setProviderGroupBatchCode(String providerGroupBatchCode) {
        this.providerGroupBatchCode = providerGroupBatchCode;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "BatchFileName{" +
                "fileName='" + fileName + '\'' +
                ", healthPlanBatchCode='" + providerGroupBatchCode + '\'' +
                ", dateStamp='" + dateStamp + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
