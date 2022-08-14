package com.seetaram.learn.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seetaram.learn.database.entities.Country;
import com.seetaram.learn.database.entities.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/s3")
public class MyRestController {

    @Value("${cloud.aws.s3.bucketName}")
    String bucketName;



    @Autowired
    CountryRepository repository;

    @Autowired
    AmazonS3 amazonS3;

    @GetMapping("/upload")
    public String generateAndStoreReportInS3() throws IOException {
        Iterable<Country> countries = repository.findAll();



        List<String> results = StreamSupport.stream(countries.spliterator(), false)
                .map(country -> country.getId() + ","+country.getName()+"\n")
                .collect(Collectors.toList());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        results.forEach(result->{
            try {
                baos.write(result.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        byte[] bytes = baos.toByteArray();

        InputStream targetStream = new ByteArrayInputStream(bytes);

        String fileName = "reports-"+System.currentTimeMillis()+".csv";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, targetStream, new ObjectMetadata());

        amazonS3.putObject(putObjectRequest);

        return "File Uploaded: "+fileName;
    }
}
