package hr.perisic.luka.data.remote.model

data class Sparkline(
    val currency: String,
    val timestamps: List<String>,
    val prices: List<String>
)