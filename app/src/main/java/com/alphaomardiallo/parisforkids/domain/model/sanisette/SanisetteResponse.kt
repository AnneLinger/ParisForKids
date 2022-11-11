package com.alphaomardiallo.parisforkids.domain.model.sanisette

data class SanisetteResponse(
    val facet_groups: List<FacetGroup>,
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Record>
)