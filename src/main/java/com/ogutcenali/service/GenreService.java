package com.ogutcenali.service;


import com.ogutcenali.repository.IGenreRepository;
import com.ogutcenali.repository.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
/*
String name dýsardan alýnan veri (Drama) bu veriye uygun bir metot findbyname genre name ine
göre bir genre getiren metotu repositoryde olusturalým

 */
public class GenreService {

    private final IGenreRepository genreRepository;

    public List<Genre> createGenresWithNames(List<String> genres) {
        List<Genre> genresList=new ArrayList<>();

        for (String name:genres){
            Optional<Genre> genre=genreRepository.findOptionalByName(name);
            if(genre.isPresent()){
                genresList.add(genre.get()); //databasedeki nesneyi ekleyecegiz
            }else{
                Genre genre1= Genre.builder().name(name).build();
                genreRepository.save(genre1);
             //   genresList.add(genreRepository.save(Genre.builder().name(name).build()));
                genresList.add(genre1); //yeni bir genre ollustrup onu ekleyecegiz
            }
        }


        return  genresList;
    }


    public List<Genre> findAll() {

        return genreRepository.findAll();
    }
}
