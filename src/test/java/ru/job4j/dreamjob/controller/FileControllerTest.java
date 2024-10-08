package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    public void init() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("image", new byte[]{1, 2, 3});
    }

    @Test
    public void whetGetId() throws Exception {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());

        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.of(fileDto));

        var fileResp = fileController.getById(any(Integer.class));
        assertThat(fileResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenGetNotId() {
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.empty());

        var fileResp = fileController.getById(any(Integer.class));
        assertThat(fileResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}