package com.ogutcenali.controller;


import com.ogutcenali.dto.request.FavMoviesRequestDto;
import com.ogutcenali.dto.request.LoginRequestDto;
import com.ogutcenali.dto.request.UserRegisterRequestDto;
import com.ogutcenali.dto.response.LoginResponseDto;
import com.ogutcenali.dto.response.MovieAdminPageResponseDto;
import com.ogutcenali.dto.response.UserFindAllResponseDto;
import com.ogutcenali.dto.response.UserRegisterResponseDto;
import com.ogutcenali.mapper.IMovieMapper;
import com.ogutcenali.repository.entity.UserType;
import com.ogutcenali.service.GenreService;
import com.ogutcenali.service.MovieService;
import com.ogutcenali.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final GenreService genreService;
    private final MovieService movieService;

    private final  MovieController movieController;

private  final UserService userService;

@GetMapping("/register")
public ModelAndView getRegisterPage(){
    ModelAndView modelAndView=new ModelAndView();
    modelAndView.addObject("error","");
    modelAndView.setViewName("register");
    return  modelAndView;
}

@PostMapping("/register")
public ModelAndView register(UserRegisterRequestDto dto){
    String error="";
    ModelAndView modelAndView=new ModelAndView();
    UserRegisterResponseDto userRegisterResponseDto=null;
    try {
     userRegisterResponseDto=userService.register2(dto);
        System.out.println("dto==>"+userRegisterResponseDto);
        modelAndView.addObject("email",userRegisterResponseDto.getEmail());  //1.yöntem
       modelAndView.setViewName("redirect:login");
   //     return  getLoginPage(dto.getEmail());  //2.yöntem
    }catch (Exception e){
        error=e.getMessage();
        modelAndView.addObject("error",error);
//    modelAndView.setViewName("redirect:register");
        modelAndView.setViewName("register");
    }

    return modelAndView;
}


   @GetMapping("/login")
    public ModelAndView getLoginPage(String email){
    ModelAndView modelAndView=new ModelAndView();
    modelAndView.addObject("email",email);
    modelAndView.setViewName("login");
        return modelAndView;
}


    @PostMapping("/login")
    public ModelAndView login(LoginRequestDto dto){

        ModelAndView modelAndView=new ModelAndView();
        try {
         LoginResponseDto responseDto=userService.login(dto);

         if (responseDto.getUserType().equals(UserType.ADMIN)){
                 return  adminPage();
         }
            //  modelAndView.addObject("result","Giriş Başarılı");
//            modelAndView.addObject("id",responseDto.getId());//1.yontem
//            modelAndView.setViewName("redirect:/movie");
       return   movieController.getMoviePage(responseDto,null);//2.yontem
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("result",e.getMessage());
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }
    @PostMapping("/admin")
    private ModelAndView adminPage() {
        ModelAndView modelAndView=new ModelAndView();
        List<UserFindAllResponseDto> users=
                userService.findAll().stream()
                        .filter(x->!x.getUserType().equals(UserType.ADMIN)).collect(Collectors.toList());
        modelAndView.addObject("users",users);
        List<MovieAdminPageResponseDto> movies=IMovieMapper.
                INSTANCE.toMovieAdminPageResponseDto(movieService.findAll());
        modelAndView.addObject("movies", movies);
        modelAndView.setViewName("admin");
    return modelAndView;

    }

    @GetMapping("/addfavmovies")
    public  ModelAndView  addFavMovies(FavMoviesRequestDto dto){
        ModelAndView modelAndView=new ModelAndView();
        userService.addFavMovies(dto);
        modelAndView.addObject("id",dto.getMovieId());
        modelAndView.addObject("userId",dto.getUserId());
        modelAndView.setViewName("redirect:/movie/findbyid");
        // modelAndView.setViewName("redirect:/movie/findbyid?id="+dto.getMovieId()+"&userId="+dto.getUserId());

        return modelAndView;
    }

    @GetMapping("/removefavmovies")
    public  ModelAndView  removeFavMovies(FavMoviesRequestDto dto){
        ModelAndView modelAndView=new ModelAndView();
        userService.removeFavMovies(dto);
        modelAndView.addObject("id",dto.getMovieId());
        modelAndView.addObject("userId",dto.getUserId());
        modelAndView.setViewName("redirect:/movie/findbyid");
        // modelAndView.setViewName("redirect:/movie/findbyid?id="+dto.getMovieId()+"&userId="+dto.getUserId());

        return modelAndView;
    }
}
