package com.e.golgirview.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.e.golgirview.model.ItemModel

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemModel: ItemModel): Long

    @Delete
    suspend fun delete(itemModel: ItemModel)

    @Query("DELETE FROM item_model")
    suspend fun deleteAll()

    @Query(
        """
        SELECT * FROM item_model
        ORDER BY pk ASC
        """
    )
    fun getAll(): LiveData<List<ItemModel>>

    @Query(
        """
        SELECT * FROM item_model
        where pk =:pk
        """
    )
    fun getItem(pk:Int): LiveData<ItemModel>


}