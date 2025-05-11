package com.skillshare.controller;

import com.skillshare.model.Progress;
import com.skillshare.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressRepository progressRepository;

    @GetMapping  
    public List<Progress> getAll() {
        return progressRepository.findAll();
    }

    @PostMapping
    public Progress create(@RequestBody Progress progress) {
        return progressRepository.save(progress);
    }

    @PutMapping("/{id}")
    public Progress updateProgress(@PathVariable String id, @RequestBody Progress updated) {
        return progressRepository.findById(id).map(p -> {
            p.setUsername(updated.getUsername());
            p.setType(updated.getType());
            p.setMessage(updated.getMessage());
            return progressRepository.save(p);
        }).orElseThrow();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        progressRepository.deleteById(id);
    }
}
