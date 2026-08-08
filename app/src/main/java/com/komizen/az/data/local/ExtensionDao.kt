package com.komizen.az.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.komizen.az.data.model.Extension
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtensionDao {
    @Query("SELECT * FROM extensions ORDER BY name ASC")
    fun getAllExtensions(): Flow<List<Extension>>

    @Query("SELECT * FROM extensions WHERE isInstalled = 1 ORDER BY name ASC")
    fun getInstalledExtensions(): Flow<List<Extension>>

    @Query("SELECT * FROM extensions WHERE id = :id LIMIT 1")
    suspend fun getExtensionById(id: String): Extension?

    @Query("SELECT * FROM extensions WHERE category = :category ORDER BY name ASC")
    fun getExtensionsByCategory(category: String): Flow<List<Extension>>

    @Query("SELECT * FROM extensions WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchExtensions(query: String): Flow<List<Extension>>

    @Query("SELECT COUNT(*) FROM extensions")
    fun getExtensionCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM extensions WHERE isInstalled = 1")
    fun getInstalledCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtensions(extensions: List<Extension>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extension: Extension)

    @Update
    suspend fun update(extension: Extension)

    @Delete
    suspend fun delete(extension: Extension)

    @Query("DELETE FROM extensions")
    suspend fun deleteAll()
}