package com.groupeisi.cours.service;

import com.groupeisi.cours.dto.requests.CoursDtoRequest;
import com.groupeisi.cours.dto.responses.CoursDtoResponse;

import java.util.List;
import java.util.Optional;

public interface CoursService {

    Optional<CoursDtoResponse> saveCours(CoursDtoRequest coursDtoRequest);
    Optional<List<CoursDtoResponse>> getAllCours();
    Optional<CoursDtoResponse> getCoursById(Long id);
    Optional<CoursDtoResponse> updateCours(CoursDtoRequest coursDtoRequest);
    boolean deleteCours(Long id);
}
