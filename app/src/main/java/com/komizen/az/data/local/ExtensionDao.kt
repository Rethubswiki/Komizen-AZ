package com.komizen.az.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.komizen.az.data.model.Extension
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtensionDao {

    @Query("SELECT * FROM extensions ORDER BY name ASC")
    fun getAll(): Flow<List<Extension>>

    @Query("SELECT * FROM extensions WHERE isInstalled = 1 ORDER BY name ASC")
    fun getInstalled(): Flow<List<Extension>>

    @Query("SELECT * FROM extensions WHERE hasUpdate = 1 ORDER BY name ASC")
    suspend fun getUpdatable(): List<Extension>

    @Query("SELECT * FROM extensions WHERE pkg = :pkg LIMIT 1")
    suspend fun getByPkg(pkg: String): Extension?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(extensions: List<Extension>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extension: Extension)

    @Update
    suspend fun update(extension: Extension)

    @Query("DELETE FROM extensions WHERE pkg = :pkg")
    suspend fun deleteByPkg(pkg: String)

    @Query("DELETE FROM extensions")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM extensions")
    suspend fun count(): Int
}
