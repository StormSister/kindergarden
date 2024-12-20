package com.monika.monischool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.monika.monischool.annotations.FieldsValueMatch;
import com.monika.monischool.annotations.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match"
        )
})
public class Person extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int personId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must not be less than 3 characters")
    private String name;

    @NotBlank(message = "Mobile Number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be exactly 10 digits long")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Confirmed Email must not be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient
    @JsonIgnore
    private String confirmEmail;


    @JsonIgnore
    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must not be less than 5 characters")
    @PasswordValidator
    private String pwd;

    @JsonIgnore
    @NotBlank(message = "Confirm Password must not be blank")
    @Size(min = 5, message = "Confirm Password must not be less than 5 characters")
    @Transient
    private String confirmPwd;

    @OneToOne(fetch=FetchType.EAGER, cascade =CascadeType.PERSIST, targetEntity= Roles.class)
    @JoinColumn(name= "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName ="addressId", nullable = true)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private MoniClass moniClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Courses.class)
    @JoinTable(name = "person_courses",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "personId"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "courseId"))
    private Set<Courses> courses = new HashSet<>();
}
