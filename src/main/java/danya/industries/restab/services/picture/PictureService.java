package danya.industries.restab.services.picture;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    void savePicture(Long fileId, MultipartFile file);
}
