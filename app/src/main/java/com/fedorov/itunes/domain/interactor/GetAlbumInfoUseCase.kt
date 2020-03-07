package com.fedorov.itunes.domain.interactor

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.domain.base.UseCase
import com.fedorov.itunes.domain.repository.Repository
import javax.inject.Inject

class GetAlbumInfoUseCase @Inject constructor(
    private val repository: Repository
):UseCase<Int, Entity> {
    override suspend fun execute(parameter: Int) = repository.getTracks(collectionId = parameter)
}