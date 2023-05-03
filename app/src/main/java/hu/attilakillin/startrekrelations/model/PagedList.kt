package hu.attilakillin.startrekrelations.model

data class PagedList<T>(
    val content: List<T>,
    val pageNumber: Int,
    val firstPage: Boolean,
    val lastPage: Boolean
) {
    companion object
}

fun <T> PagedList.Companion.empty() = PagedList<T>(
    content = listOf<T>(),
    pageNumber = 0,
    firstPage = true,
    lastPage = true
)
