package com.master.myexchanges.data.model

import androidx.room.*
import com.master.myexchanges.domain.Search
import com.master.myexchanges.domain.User
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = UserData::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("userId"),
    onDelete = ForeignKey.CASCADE
)],
    indices = [Index(value = ["userId"])]
)
data class SearchData(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo val userId: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") val date: Date?,
    @Embedded(prefix = "source_") val sourceCurrency: CountryCurrencyData,
    @ColumnInfo val sourceValue: Double,
    @Embedded(prefix = "dest_") val destinationCurrency: CountryCurrencyData,
    @ColumnInfo val destinationValue: Double
)

fun List<SearchData>.toSearchList(user: User) =
    this.map { s -> Search(user, s.date, s.sourceValue, s.sourceCurrency.toCountryCurrency(),
        s.destinationValue, s.destinationCurrency.toCountryCurrency()) }