package com.ogutcenali.controller;

import com.ogutcenali.dto.response.LoginResponseDto;
import com.ogutcenali.repository.entity.Movie;
import com.ogutcenali.repository.entity.User;
import com.ogutcenali.service.GenreService;
import com.ogutcenali.service.MovieService;
import com.ogutcenali.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final GenreService genreService;
    private final MovieService movieService;

    private final UserService userService;

    @GetMapping("")
    public ModelAndView getMoviePage(LoginResponseDto responseDto,String [] genre){
        ModelAndView modelAndView=new ModelAndView();
        List<Movie> movieList;
        if (genre==null){
            movieList=movieService.findAll();
        }else{
            movieList=movieService.findAllByGenres_NameIn(genre);
        }
        modelAndView.addObject("genres",genreService.findAll());
        modelAndView.addObject("user",responseDto);
        modelAndView.addObject("movies",movieList);
        modelAndView.setViewName("movies");
        return  modelAndView;
    }


    @GetMapping("/findbyid")
    public ModelAndView getById(Long id,Long userId){
        User user =null;
        if (userId==null){
            user=User.builder().build();
        }else{
            user =userService.findById(userId).get();
        }
        Movie movie=movieService.findbyId(id);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("movie",movie);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("moviesDetail");
        return modelAndView;
    }



}
