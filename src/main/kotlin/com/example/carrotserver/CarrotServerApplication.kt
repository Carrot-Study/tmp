package com.example.carrotserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarrotServerApplication

fun main(args: Array<String>) {
    runApplication<CarrotServerApplication>(*args)
}
