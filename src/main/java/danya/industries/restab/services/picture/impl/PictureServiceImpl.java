package danya.industries.restab.services.picture.impl;

import danya.industries.restab.services.picture.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void savePicture(Long fileId, MultipartFile file) {
        //CompletableFuture.runAsync(() -> )
    }
}
