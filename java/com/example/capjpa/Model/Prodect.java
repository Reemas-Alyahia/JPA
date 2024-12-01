package com.example.capjpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prodect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be Empty!")
    @Size(min = 3, max = 10, message = "the size of name 3 to 10")
    @Column(columnDefinition = "varchar(10) not null")

    private String name;
    @NotNull(message = "price cannot be Empty!..")
    @Positive(message = "must be positive number")
    @Column(columnDefinition = "int not null")

    private Integer price;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "the  category ID cannot be Empty!..")
    @Size(min = 2, max = 10, message = "the size of category ID  2 to 10")
    private Integer categoryID;


    ///
    private boolean isHasDiscount=false;
}
