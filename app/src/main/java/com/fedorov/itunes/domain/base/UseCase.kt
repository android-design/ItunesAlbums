package com.fedorov.itunes.domain.base

interface UseCase<P, R> {
    suspend fun execute(parameter: P): R
}