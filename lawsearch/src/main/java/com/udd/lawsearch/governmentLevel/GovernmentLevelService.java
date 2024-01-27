package com.udd.lawsearch.governmentLevel;

import com.udd.lawsearch.exceptions.EntityNotFoundException;

public interface GovernmentLevelService {
    GovernmentLevel findById(Long id) throws EntityNotFoundException;
}
