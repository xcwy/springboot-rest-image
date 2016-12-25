package io.rai.controller;

import io.rai.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

/**
 * Created by rai on 16/12/25.
 */
@RestController
public class ImageController {
  private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

  @Autowired
  private ImageService imageService;

  @PostMapping("/images")
  public byte[] saveImage(@NotNull @RequestParam MultipartFile file) {

    LOG.debug("file content type : {}", file.getContentType());
    LOG.debug("file name : {}", file.getName());
    LOG.debug("file orginal name : {}",file.getOriginalFilename());
    LOG.debug("file size : {}" ,file.getSize());

    File localFile = new File("/Users/rai/it/resource/image/"+file.getOriginalFilename());
    try {
      file.transferTo(localFile);
    } catch (IOException e) {
      LOG.debug("upload image fail : ", e);
    }
    return null;
  }

  @GetMapping("/images/{fileName}")
  public byte[] getImage(@PathVariable("fileName") String fileName) {
    return null;
  }
}
