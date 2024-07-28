package greenjangtanji.yeosuro.plan.mapper;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    Plan PlanPostDtoToPlan(PlanDto.PlanPostDto planPostDto);
}
