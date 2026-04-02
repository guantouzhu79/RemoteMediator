package com.rip.remotemediator.repo

interface DataMaper<I,O> {
    fun map(input: I): O

}