package com.ogutcenali.service;


import com.ogutcenali.dto.request.FavMoviesRequestDto;
import com.ogutcenali.dto.request.LoginRequestDto;
import com.ogutcenali.dto.request.UserRegisterRequestDto;
import com.ogutcenali.dto.response.LoginResponseDto;
import com.ogutcenali.dto.response.UserFindAllResponseDto;
import com.ogutcenali.dto.response.UserRegisterResponseDto;
import com.ogutcenali.mapper.IUserMapper;
import com.ogutcenali.repository.IUserRepository;
import com.ogutcenali.repository.entity.Movie;
import com.ogutcenali.repository.entity.User;
import com.ogutcenali.repository.entity.UserType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private  IUserRepository userRepository;

    private  MovieService movieService;

    public UserService(IUserRepository userRepository, @Lazy MovieService movieService) {
        this.userRepository = userRepository;
        this.movieService = movieService;
    }

    public User createUser(String name, String surname, String email, String phone, String password, String userType) {
        UserType userType1=null;
        User user;
       try {
           userType1=UserType.valueOf(userType);
           user= User.builder()
                   .name(name)
                   .surName(surname)
                   .email(email)
                   .phone(phone)
                   .userType(userType1)
                   .password(password)
                   .build();
       }catch (Exception e){
           System.out.println("Boyle bir Kullanıcı turu bulunmamaktadır");
           user= User.builder()
                   .name(name)
                   .surName(surname)
                   .email(email)
                   .phone(phone)
                   .password(password)
                   .build();
       }


       return  userRepository.save(user);

    }



    public List<UserFindAllResponseDto> findAll() {

      return userRepository.findAll().stream().map(x->{
             return UserFindAllResponseDto.builder()
                     .id(x.getId())
                     .name(x.getName())
                     .surName(x.getSurName())
                     .userType(x.getUserType())
                     .phone(x.getPhone())
                     .email(x.getEmail())
                     .favMovies(x.getFavMovies())
                     .genres(x.getFavGenres())
                     .movieCommentsContent(x.getComments().stream()
                             .map(y->y.getContent()).collect(Collectors.toList()))
                     .build();
         }).collect(Collectors.toList());
    }

    public void saveAll(List<User> users) {

        userRepository.saveAll(users);


    }

    public  List<User> findAllByOrderByName(){

        return userRepository.findAllByOrderByName();
    }

    public List<User> findAllByNameLike(String name){

        return userRepository.findAllByNameLike(name);
    }

   public List<User> findAllByEmailContainingIgnoreCase(String value){
        return  userRepository.findAllByEmailContainingIgnoreCase(value);
    }

   public List<User> findAllByEmailEndingWith(String value){

        return userRepository.findAllByEmailEndingWith(value);
    }

    public Boolean existsByEmailAndPassword(String email,String password){

        return userRepository.existsByEmailAndPassword(email,password);
    }

    public User findOptionalByEmailAndPassword(String email, String password) throws Exception {

        Optional<User> user=userRepository.findOptionalByEmailAndPassword(email,password);

        if (user.isPresent()){
            return user.get();
        }else{
            throw new Exception("Kullan�c� Bulunamad�");
        }

    }

    public   List<User> passwordLongerThan(int length){

        return userRepository.passwordLongerThan(length);
    }

    public     List<User> passwordControl( int length){

        return  userRepository.passwordControl(length);
    }

    public Optional<User> findById(Long id) {

       Optional<User> user= userRepository.findById(id);

        System.out.println(user.get().getName());
        return user;
    }

    public UserRegisterResponseDto register(UserRegisterRequestDto dto) {
        User user= User.builder()
                .name(dto.getName())
                .surName(dto.getSurName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
       userRepository.save(user);

        return UserRegisterResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surName(user.getSurName())
                .userType(user.getUserType())
                .email(user.getEmail())
                .build();
    }

    public UserRegisterResponseDto register2(UserRegisterRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())){
            throw  new RuntimeException("Kullanici adi zaten kayitli");
        }else{

            if (!dto.getPassword().equals(dto.getRePassword())){

                throw new RuntimeException("Girdiğiniz sifreler uyusmuyor ");
            }

            User user= IUserMapper.INSTANCE.toUser(dto);
            userRepository.save(user);
            return IUserMapper.INSTANCE.toUserRegisterResponseDto(user);
        }
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        Optional<User> user=userRepository
                .findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

          if (user.isPresent()){

                return IUserMapper.INSTANCE.toLoginResponseDto(user.get());
//              return LoginResponseDto.builder()
//                      .userType(user.get().getUserType())
//                      .name(user.get().getName())
//                      .surName(user.get().getSurName())
//                      .email(user.get().getEmail())
//                      .build();


          }else{
              throw new RuntimeException("kullanıcı adı veya şifre hatalı");
          }


    }

    public void addFavMovies(FavMoviesRequestDto dto) {
        Optional<User> user=userRepository.findById(dto.getUserId());
        Movie movie=movieService.findbyId(dto.getMovieId());
        if (user.isPresent()){
            if (!user.get().getFavMovies().contains(movie)){
                user.get().getFavMovies().add(movie);
                userRepository.save(user.get());
            }
        }else {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
    }

    public void removeFavMovies(FavMoviesRequestDto dto) {
        Optional<User> user=userRepository.findById(dto.getUserId());
        Movie movie=movieService.findbyId(dto.getMovieId());
        if (user.isPresent()){
            if (user.get().getFavMovies().contains(movie)){
                user.get().getFavMovies().remove(movie);
                userRepository.save(user.get());
            }
        }else {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
    }
}
