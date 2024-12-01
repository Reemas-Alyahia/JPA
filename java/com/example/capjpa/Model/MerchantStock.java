package com.example.capjpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")

    @NotNull(message = "productid cannot be Empty!..")
    private Integer productid;
    @NotNull(message = "merchantid cannot be Empty!..")
    @Column(columnDefinition = "int not null")
    private Integer merchantid;
    @Column(columnDefinition = "int not null")
    @NotNull(message = "stock cannot be Empty!..")
    @Min(value = 10,message = "stock have to be more than 10 at start")
    private Integer stock;
}
