package com.groupthree.myhomesbe.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static final String IMAGES_PROPERTY_PATH = "src/main/resources/uploads/property/";

    public static boolean saveFile(String uploadDir, String fileName,
                                   MultipartFile multipartFile)  {
        Path uploadPath = Paths.get(uploadDir);
        boolean result = false;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            result = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return result;
    }

    public static boolean isMultipartFileEmpty(MultipartFile file) {
        return file.getOriginalFilename() == "";
    }

    public static String getMultipartFileExtention(MultipartFile file) {
        String extension = "";
        try {
            extension = file.getOriginalFilename().split("\\.")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extension;
    }

    public static byte[] getFileFromFileSystem(String dirPath, String filename) throws IOException {
        Path resolvedPath = Paths.get(dirPath);
        byte[] image = new byte[0];

        image = FileUtils.readFileToByteArray(new File(resolvedPath + "/" + filename));

        return image;
    }
}