package greenjangtanji.yeosuro.point.service;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.point.entity.Point;
import greenjangtanji.yeosuro.point.entity.PointCategory;
import greenjangtanji.yeosuro.point.repository.PointRepository;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    //여정 작성 포인트 지급
    public void planPoint (Long userId, Plan plan){

    }

    //여정 후기 작성 포인트 지급
    public void reviewPoint (Long userId){

    }


    //게시글 작성 포인트 지급
    public void feedPoint (Long userId, Feed feed){
        int feedPoint = 250;

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저 정보가 없습니다.")
        );

        Point point = Point.createPoint(feedPoint, feed.getTitle(), PointCategory.FEED, user);
        pointRepository.save(point);
    }


    //좋아요 포인트 지급
    public void likePoint (Long userId){

    }

}
