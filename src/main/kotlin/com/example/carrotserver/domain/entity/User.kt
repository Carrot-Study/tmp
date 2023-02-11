package com.example.carrotserver.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "carrot_user")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val mail: String,
    val password: String,

    @OneToMany(mappedBy = "user")
    val users: MutableList<UserChat> = arrayListOf(),
) {
    constructor(mail: String, password: String): this(
        id = null,
        mail = mail,
        password = password
    )
}