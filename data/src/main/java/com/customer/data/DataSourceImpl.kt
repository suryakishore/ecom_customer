package com.customer.data

import android.content.Context
import com.customer.data.preference.PreferenceManager
import com.customer.data.preference.PreferenceManagerImpl
import com.customer.remote.http.NetworkManager
import com.customer.remote.http.NetworkManagerImpl
import com.data.cache.CustomerDatabase
import com.data.cache.DatabaseManager
import com.data.cache.DatabaseManagerImpl
import javax.inject.Inject

/**
 * @author 3Embed
 * for implementing dataSource
 * @since 1.0 (23-Aug-2019)
 */

class DataSourceImpl @Inject private constructor(context: Context) : DataSource {

    private var databaseManager: DatabaseManager =
            DatabaseManagerImpl(CustomerDatabase.getDatabaseInstance(context.applicationContext))
    private var preferenceManager: PreferenceManager =
            PreferenceManagerImpl(context)

    override fun preference(): PreferenceManager {
        return preferenceManager
    }

    private var networkManager: NetworkManager = NetworkManagerImpl(
            context.applicationContext
    )

    override fun db(): DatabaseManager {
        return databaseManager
    }

    companion object : SingletonHolder<DataSource, Context>(::DataSourceImpl)

    override fun api(): NetworkManager {
        return networkManager
    }
}