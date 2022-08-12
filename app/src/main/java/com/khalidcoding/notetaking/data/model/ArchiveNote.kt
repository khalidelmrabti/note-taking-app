package com.khalidcoding.notetaking.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khalidcoding.notetaking.utils.NOTE_ARCHIVE_TABLE
import java.util.*

@Entity(tableName = NOTE_ARCHIVE_TABLE)
data class ArchiveNote(
    @PrimaryKey
    val id:Int,
    val title:String,
    val body: String,
    @ColumnInfo(name = "entry_date")
    val entryDate: Date,
    @ColumnInfo(name = "is_updated")
    val isUpdated: Boolean
)
