package hu.attilakillin.startrekrelations.model

data class PagedList<T>(
    val content: List<T>,
    val pageNumber: Int,
    val firstPage: Boolean,
    val lastPage: Boolean
)
