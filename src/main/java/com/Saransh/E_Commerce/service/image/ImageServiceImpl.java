package com.Saransh.E_Commerce.service.image;

import com.Saransh.E_Commerce.Model.Image;
import com.Saransh.E_Commerce.Model.Product;
import com.Saransh.E_Commerce.dto.ImageDTO;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.repository.ImageRepository;
import com.Saransh.E_Commerce.repository.ProductRepository;
import com.Saransh.E_Commerce.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final ProductServiceImpl productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No image available with id: "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw  new ResourceNotFoundException("No such image exists! "+id);
        });
    }

    @Override
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files) {
        Product product=productService.getProductById(productId);

        List<ImageDTO> saveImagesDto=new ArrayList<>();
        for(MultipartFile file:files)
        {
           try
           {
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl=buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage=imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO=new ImageDTO();
                imageDTO.setId(savedImage.getId());
                imageDTO.setFileName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                saveImagesDto.add(imageDTO);
           }
           catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
           }
        }
        return saveImagesDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e)
        {
            throw  new RuntimeException(e.getMessage());
        }
    }
}
