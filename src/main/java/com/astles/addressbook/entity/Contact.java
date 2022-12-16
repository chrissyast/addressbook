package com.astles.addressbook.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Valid
    @NotBlank(message="Please supply a name")
    private String name;

    @Pattern(message = "Number supplied is not valid", regexp = "\\+?\\d+\\b")
    @Valid
    @NotBlank(message="Please supply a phone number")
    private String phoneNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Transient
    private User user;

    @Column(name="user_id")
    private Integer userId;

    public Contact() {
    }
}
