package io.kopring.multimodule.kopringmultimodule.demo.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Demo (
    val name : String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
){
}