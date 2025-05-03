package com.skillshare.controller;

import com.skillshare.model.LearningPlan;
import com.skillshare.repository.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class LearningPlanController {

    @Autowired
    private LearningPlanRepository planRepository;

    @GetMapping
    public List<LearningPlan> getAllPlans() {
        return planRepository.findAll();
    }

    @PostMapping
    public LearningPlan createPlan(@RequestBody LearningPlan plan) {
        return planRepository.save(plan);
    }

    @PutMapping("/{id}")
    public LearningPlan updatePlan(@PathVariable String id, @RequestBody LearningPlan updated) {   
        return planRepository.findById(id).map(plan -> {
            plan.setUsername(updated.getUsername());
            plan.setTopic(updated.getTopic());
            plan.setResources(updated.getResources());
            plan.setDeadline(updated.getDeadline());
            plan.setStatus(updated.getStatus());
            return planRepository.save(plan);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable String id) {
        planRepository.deleteById(id);
    }
}
