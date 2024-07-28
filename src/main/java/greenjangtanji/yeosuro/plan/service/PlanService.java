package greenjangtanji.yeosuro.plan.service;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.repository.PlanRepository;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository){
        this.planRepository = planRepository;
    }

    public void savePlan(Plan savedPlan) {
        planRepository.save(savedPlan);
    }

    public List<Plan> getAllPlan() {
        return planRepository.findAll();
    }

    public List<Plan> getMyPlans(User user) {
        return planRepository.getPlansByUser(user);
    }
}
