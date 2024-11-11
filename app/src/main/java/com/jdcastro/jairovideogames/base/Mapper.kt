package com.jdcastro.jairovideogames.base

interface Mapper <I, O> {
    fun map(input: I): O
}