package com.soyvictorherrera.nosedive.domain.mapper

/**
 * Convertir cualquier objeto en un objeto de dominio
 * @param T clase de capa de repositorio
 * @param DomainModel clase de capa de dominio
 */
abstract class DomainMapper<T, DomainModel> {

    abstract fun toDomainModel(value: T): DomainModel

    abstract fun fromDomainModel(model: DomainModel): T

    fun toDomainModelList(valueList: List<T>): List<DomainModel> {
        return valueList.map(this::toDomainModel)
    }

    fun fromDomainModelList(modelList: List<DomainModel>): List<T> {
        return modelList.map(this::fromDomainModel)
    }

}