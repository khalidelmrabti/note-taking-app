package com.khalidcoding.notetaking.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khalidcoding.notetaking.utils.NOTE_TABEl
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity(tableName = NOTE_TABEl)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Int ? = null,
    var title:String,
    var body: String,
    @ColumnInfo(name = "is_updated")
    var isUpdated: Boolean,
    @ColumnInfo(name = "entry_date")
    var entryDate: Date,
)
