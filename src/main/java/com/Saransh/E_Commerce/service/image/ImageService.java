package com.Saransh.E_Commerce.service.image;

import com.Saransh.E_Commerce.Model.Image;
import com.Saransh.E_Commerce.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,Long imageId);
}
