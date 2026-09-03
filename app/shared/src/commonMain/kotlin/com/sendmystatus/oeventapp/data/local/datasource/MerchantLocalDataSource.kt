package com.sendmystatus.oeventapp.data.local.datasource

import com.sendmystatus.oeventapp.data.model.event.Merchant
import com.sendmystatus.oeventapp.data.model.event.MerchantEventCatalog
import kotlinx.coroutines.flow.Flow

interface MerchantLocalDataSource {
    fun getMerchants(): Flow<List<Merchant>>
    suspend fun saveMerchant(merchant: Merchant)
    
    fun getMerchantCatalog(merchantId: String): Flow<List<MerchantEventCatalog>>
    suspend fun saveMerchantCatalog(catalog: MerchantEventCatalog)
}
