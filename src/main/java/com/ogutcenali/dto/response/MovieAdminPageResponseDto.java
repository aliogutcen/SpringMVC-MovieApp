package com.ogutcenali.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieAdminPageResponseDto {
    private Long id;
    private  String language;
    private  String name;
    private LocalDate premiered;


}
