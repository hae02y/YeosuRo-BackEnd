package greenjangtanji.yeosuro.feed.mapper;

import greenjangtanji.yeosuro.feed.dto.FeedDto;
import greenjangtanji.yeosuro.feed.dto.FeedResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-07T21:11:53+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.11 (Azul Systems, Inc.)"
)
@Component
public class FeedMapperImpl implements FeedMapper {

    @Override
    public FeedResponseDto feedToFeedResponseDto(Feed feed) {
        if ( feed == null ) {
            return null;
        }

        FeedResponseDto feedResponseDto = new FeedResponseDto();

        return feedResponseDto;
    }

    @Override
    public Feed feedDtoToFeed(FeedDto.Post feedDto) {
        if ( feedDto == null ) {
            return null;
        }

        Feed feed = new Feed();

        return feed;
    }
}
