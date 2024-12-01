package com.example.capjpa.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "LENGTH(password) >= 7 AND password ~ '^(?=.[a-zA-Z])(?=.\\d).*$'")
@Check(constraints = "email ~ '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$'")
@Check(constraints = "role IN ('Admin', 'Customer')")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iduser;

    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 5, max = 10, message = "The size of username must be between 5 and 10 characters.")
    @Column(columnDefinition = "varchar(10) not null")
    private String username;

    @Pattern(regexp = "^(?=.[a-zA-Z])(?=.\\d).{7,}$", message = "Password must be at least 7 characters long and include letters and numbers.")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Must be a valid email address.")
    @Column(columnDefinition = "varchar(255) not null")
    private String email;

    @NotEmpty(message = "Role cannot be empty!")
    @Pattern(regexp = "^(Admin|Customer)$", message = "You must choose either 'Admin' or 'Customer'.")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;

    @NotNull(message = "The balance cannot be null!")
    @Positive(message = "Balance must be positive.")
    @Column(columnDefinition = "int not null")
    private Integer balance;
}
