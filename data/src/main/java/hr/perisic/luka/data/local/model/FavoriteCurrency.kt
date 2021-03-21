package hr.perisic.luka.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_currencies")
data class FavoriteCurrency(
    @PrimaryKey val id: String,
    val name: String
)