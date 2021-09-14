package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Api(tags = {"Upload controller"}, description = "Upload data from file")
public class UploadController {

    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    @ApiOperation(value = "Upload product from csv file")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "File upload successful"),
            @ApiResponse(code = 400, message = "File read error")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> uploadData(@RequestParam(name = "file") MultipartFile file) {
        uploadService.uploadData(file);
        return ResponseEntity.noContent().build();
    }
}
