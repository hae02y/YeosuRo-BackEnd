package greenjangtanji.yeosuro.feed.mapper;

import greenjangtanji.yeosuro.feed.dto.FeedDto;
import greenjangtanji.yeosuro.feed.dto.FeedResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    FeedResponseDto feedToFeedResponseDto (Feed feed);

    Feed feedDtoToFeed (FeedDto.Post feedDto);
}
