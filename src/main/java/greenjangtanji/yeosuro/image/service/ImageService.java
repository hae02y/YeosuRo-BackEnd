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

    // 여러개 파일 s3 업로드
    public List<String> uploadFile(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            } catch (IOException e) {
                // IOException 발생 시 예외 처리
                throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);
            } catch (AmazonS3Exception e) {
                // AmazonS3Exception 발생 시 예외 처리
                System.err.println("Failed to upload file to S3: " + e.getMessage());
                throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);
            }

            String fileUrl = s3Client.getUrl(bucket, fileName).toString();
            fileNameList.add(fileUrl);  // 업로드된 파일의 URL을 반환
        });

        return fileNameList;
    }

    // S3 버킷에서 파일 삭제
    public void deleteFile(String fileName) {
        try {
            // S3 버킷에서 파일 삭제
            s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
            System.out.println("Successfully deleted file: " + fileName);
        } catch (AmazonS3Exception e) {
            System.err.println("Failed to delete file from S3: " + e.getErrorMessage());
            throw new BusinessLogicException(ExceptionCode.FILE_DELETE_ERROR);
        }
    }

    // 파일 삭제
//    public void deleteFile(String fileName) {
//        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
//    }

    // 파일명 중복 방지 (UUID)
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_ERROR);
        }
        List<String> fileValidate = List.of(".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new BusinessLogicException(ExceptionCode.FILE_FORMAT_ERROR);
        }
        return idxFileName;
    }

    // 이미지 db 저장
    public void saveImages(List<String> imageUrls, Long referenceId, ImageType imageType) {
        for (String imageUrl : imageUrls) {
            Image image = Image.builder()
                    .imageUrl(imageUrl)
                    .imageType(imageType)
                    .referenceId(referenceId)
                    .build();
            imageRepository.save(image);
        }
    }

    // 이미지 수정
    public void updateImages(List<String> imageUrls, Long referenceId, ImageType imageType) {
        //s3에서 기존 이미지 삭제하고 다시 업로드하는 로직 필요?
        imageRepository.deleteByReferenceIdAndImageType(referenceId, imageType);
        saveImages(imageUrls, referenceId, imageType);
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
}
