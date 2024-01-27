package com.udd.lawsearch.government;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GovernmentServiceImpl implements GovernmentService {
    private final GovernmentRepository governmentRepository;
    @Override
    public void create(Government government) {
        governmentRepository.save(government);
    }
}
