package com.ogutcenali.mapper;

import com.ogutcenali.dto.response.MovieAdminPageResponseDto;
import com.ogutcenali.repository.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMovieMapper {

    IMovieMapper INSTANCE= Mappers.getMapper(IMovieMapper.class);

   List<MovieAdminPageResponseDto> toMovieAdminPageResponseDto(final List<Movie> movies);


}
