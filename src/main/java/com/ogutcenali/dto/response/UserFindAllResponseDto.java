package com.ogutcenali.dto.response;


import com.ogutcenali.repository.entity.Genre;
import com.ogutcenali.repository.entity.Movie;
import com.ogutcenali.repository.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFindAllResponseDto {
    private Long id;
    private String name;
    private String surName;
    private String email;
    private String phone;
    private UserType userType;
    private List<Movie> favMovies;
    private List<Genre> genres;
    private List<String> movieCommentsContent;
    private Map<String,String> commentMap;


}
