package greenjangtanji.yeosuro.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    // 여러 개 파일 S3 업로드
    public List<String> uploadFile(List<MultipartFile> multipartFile, ImageType type) {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(type, file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                // S3에 파일 업로드
                s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            } catch (IOException e) {
                throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);

            } catch (AmazonS3Exception e) {
                throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);
            }

            // S3에 저장된 파일의 URL 생성
            String fileUrl = s3Client.getUrl(bucket, fileName).toString();
            fileNameList.add(fileUrl);  // 업로드된 파일의 URL을 반환
        });

        saveImages(fileNameList);
        return fileNameList;
    }

    // 파일명 중복 방지 (UUID) 및 타입별 디렉토리 생성
    private String createFileName(ImageType type, String fileName) {
        return type + "/" + UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }


    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);
        }
        List<String> fileValidate = List.of(".jpg", ".jpeg", ".JPG", ".JPEG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new BusinessLogicException(ExceptionCode.FILE_FORMAT_ERROR);
        }
        return idxFileName;
    }

    // 이미지 DB 저장 (최초에는 URL만 저장)
    public void saveImages(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            Image image = Image.builder()
                    .imageUrl(imageUrl)
                    .build();
            imageRepository.save(image);
        }
    }

//    // 이미지 수정
//    public void updateImages(List<String> imageUrls, Long referenceId, ImageType imageType) {
//        // s3에서 기존 이미지 삭제하고 다시 업로드하는 로직 필요?
//        imageRepository.deleteByReferenceIdAndImageType(referenceId, imageType);
//        for (String imageUrl : imageUrls) {
//            Image image = Image.builder()
//                    .imageUrl(imageUrl)
//                    .imageType(imageType)
//                    .referenceId(referenceId)
//                    .build();
//            imageRepository.save(image);
//        }
//    }

    // 이미지 테이블 업데이트 메서드
    public void updateImageReferences(Long referenceId, ImageType imageType, List<String> imageUrls) {
        // 해당 URL 목록을 사용하여 이미지 테이블의 레퍼런스 정보 업데이트
        for (String imageUrl : imageUrls) {
            Image image = imageRepository.findByImageUrl(imageUrl);
            if (image != null) {
                image.updateImage(imageType, referenceId);
                imageRepository.save(image);
            }
        }
    }

    /**
     *   게시글,리뷰,프로필에 연관된 이미지 조회
     *   referenceId 에 조회하고자하는 것의 ID
     *   imageType 에 조회하고자하는 것의 타입
     */
    public List<String> getImagesByReferenceIdAndType(Long referenceId, ImageType imageType) {
        return imageRepository.findByReferenceIdAndImageType(referenceId, imageType)
                .stream()
                .map(Image::getImageUrl)
                .toList();
    }

    // S3 버킷에서 파일 삭제
    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));

        } catch (AmazonS3Exception e) {
            throw new BusinessLogicException(ExceptionCode.FILE_DELETE_ERROR);
        }
    }

}
