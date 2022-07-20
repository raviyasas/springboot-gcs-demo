package com.app.service;

import com.app.dto.FileDto;
import com.app.entity.InputFile;
import com.app.exception.InvalidFileTypeException;
import com.app.repository.FileRepository;
import com.app.util.DataBucketUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;
    private final DataBucketUtil dataBucketUtil;

    public List<InputFile> uploadFiles(MultipartFile[] files) {

        LOGGER.debug("Start file uploading process");
        List<InputFile> inputFiles = new ArrayList<>();

        Arrays.asList(files).forEach(file -> {
            Path path = new File(file.getOriginalFilename()).toPath();

            try {
                String contentType = Files.probeContentType(path);
                FileDto fileDto = dataBucketUtil.uploadFile(file, file.getOriginalFilename(), contentType);

                if (fileDto != null) {
                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
                    LOGGER.debug("Uploaded file details, file name {} and url {}",fileDto.getFileName(), fileDto.getFileUrl() );
                }
            } catch (Exception e) {
                LOGGER.error("Error occurred while uploading. Error: ", e);
                throw new InvalidFileTypeException("");
            }
        });

        fileRepository.saveAll(inputFiles);
        LOGGER.debug("File uploaded successfully");
        return inputFiles;
    }
}
