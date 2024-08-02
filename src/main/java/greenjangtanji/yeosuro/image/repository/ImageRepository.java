package greenjangtanji.yeosuro.image.repository;

import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.image.entity.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

    // referenceId와 imageType으로 이미지를 조회하는 메소드
    List<Image> findByReferenceIdAndImageType(Long referenceId, ImageType imageType);

    // referenceId와 imageType으로 이미지를 삭제하는 메소드
    void deleteByReferenceIdAndImageType(Long referenceId, ImageType imageType);

}
