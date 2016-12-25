package io.rai.controller;

import io.rai.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by rai on 16/12/25.
 */
@RestController
public class ImageController {
  private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

  private static final String IMAGE_PATH = "/Users/rai/it/resource/image/";

  private static final String SUFFIX = ".JPG";

  @Autowired
  private ImageService imageService;

  @PostMapping("/images")
  public byte[] saveImage(@NotNull @RequestParam MultipartFile file) {

    LOG.debug("file content type : {}", file.getContentType());
    LOG.debug("file name : {}", file.getName());
    LOG.debug("file orginal name : {}", file.getOriginalFilename());
    LOG.debug("file size : {}", file.getSize());

    File localFile = new File(IMAGE_PATH + file.getOriginalFilename());
    try {
      file.transferTo(localFile);
    } catch (IOException e) {
      LOG.debug("upload image fail : ", e);
    }
    return null;
  }

  @GetMapping("/images/{fileName}")
  public byte[] getImage(@PathVariable("fileName") String fileName)
      throws IOException {
    String realPath = IMAGE_PATH + fileName + SUFFIX;
    try {
      InputStream is = new FileInputStream(realPath);
      BufferedImage img = ImageIO.read(is);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(img, "jpg", bos);
      return bos.toByteArray();
    } catch (FileNotFoundException e) {
      return null; //todo: return safe photo instead
    } catch (IOException e) {
      return null;  //todo: return safe photo instead
    }
  }
}
