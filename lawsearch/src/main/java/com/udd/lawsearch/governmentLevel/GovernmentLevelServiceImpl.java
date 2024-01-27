package com.udd.lawsearch.governmentLevel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.udd.lawsearch.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GovernmentLevelServiceImpl implements GovernmentLevelService{
    private  final GovernmentLevelRepository governmentLevelRepository;
    private final String entityName = "Government Type";
    public GovernmentLevel findById(Long id) throws EntityNotFoundException{
        return governmentLevelRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }
}
