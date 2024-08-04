package greenjangtanji.yeosuro.plan.mapper;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    Plan planPostDtoToPlan(PlanDto.PlanPostDto planPostDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "createAt", target = "createAt")
    PlanDto.PlanResponseDto planToPlanResponseDto(Plan plan);

    List<PlanDto.PlanResponseDto> planListToPlanResponseDtoList(List<Plan> plans);
}

