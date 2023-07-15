package com.groupthree.myhomesbe.property.controller;

import com.groupthree.myhomesbe.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/upload/")
public class ImageController {

    @PostMapping(value="documents")
    public ResponseEntity<Void> uploadPolicyDocument(@RequestParam("document") List<MultipartFile> multipartFile)
    {
        // Note the trailing \\ characters
//        String OUT_PATH = Paths.get(FileUtil.IMAGES_PROPERTY_PATH).toAbsolutePath().toString().replace("\\", "\\\\") + "\\\\";
        Path OUT_PATH = Paths.get(FileUtil.IMAGES_PROPERTY_PATH);

        if (!Files.exists(OUT_PATH)) {
            try {
                Files.createDirectories(OUT_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("IMAGE UPLOADs PATH " + OUT_PATH);

//        return ResponseEntity.ok().build();
        try {


            for(MultipartFile mf: multipartFile)
            {
                byte[] bytes = mf.getBytes();
                System.out.println(mf.getOriginalFilename());
                Path path = Paths.get(FileUtil.IMAGES_PROPERTY_PATH + mf.getOriginalFilename());
                Files.write(path, bytes);
            }

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {

        byte[] image ;
        try {
            image = FileUtil.getFileFromFileSystem(FileUtil.IMAGES_PROPERTY_PATH, filename);
        } catch (IOException error) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}