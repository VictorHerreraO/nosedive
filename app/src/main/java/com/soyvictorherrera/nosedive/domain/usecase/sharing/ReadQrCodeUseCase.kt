package com.soyvictorherrera.nosedive.domain.usecase.sharing

import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.json.JSONObject


class ReadQrCodeUseCase(

) : BaseUseCase<Result<String>>() {
    override val dispatcher = Dispatchers.Default

    var rawValue = ""

    override suspend fun buildFlow(): Flow<Result<String>> {
        val value = rawValue
        return try {
            val jsonObject = JSONObject(value)
            if (!isValidJSON(jsonObject)) {
                throw IllegalArgumentException("not a valid json")
            }
            flowOf(Result.Success(jsonObject.optString("u")))
        } catch (ex: Exception) {
            flowOf(Result.Error(ex))
        }
    }

    private fun isValidJSON(jsonObject: JSONObject): Boolean {
        return !jsonObject.optString("u").isNullOrEmpty() && jsonObject.optInt("v", -1) >= 1
    }

}
