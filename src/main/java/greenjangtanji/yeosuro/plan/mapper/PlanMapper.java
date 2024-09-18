package greenjangtanji.yeosuro.plan.mapper;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.dto.PlanReviewDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    Plan planPostDtoToPlan(PlanDto.PlanPostDto planPostDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "sites", target = "siteList")
    @Mapping(source = "id", target = "planId")
    PlanDto.PlanResponseDto planToPlanResponseDto(Plan plan);

    List<PlanDto.PlanResponseDto> planListToPlanResponseDtoList(List<Plan> plans);


    PlanReview planReviewPostDtoToPlanReview(PlanReviewDto.PlanReviewPostDto planReviewDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "siteReviews", target = "siteReviewList")
    @Mapping(source = "id", target = "planReviewId")
    PlanReviewDto.PlanReviewResponseDto planReviewToPlanReviewResponseDto(PlanReview planReview);

    List<PlanReviewDto.PlanReviewResponseDto> planReviewListToPlanReviewDtoList(List<PlanReview> planReviews);
}

