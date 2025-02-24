package com.groupeisi.cours.dto.responses;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoursDtoResponse {

    private Long id;
    private String nom;
    private String description;
    private boolean archive;
}
