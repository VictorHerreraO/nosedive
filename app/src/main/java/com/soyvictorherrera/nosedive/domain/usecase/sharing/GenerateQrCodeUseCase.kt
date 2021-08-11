package com.soyvictorherrera.nosedive.domain.usecase.sharing

import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import net.glxn.qrgen.android.QRCode
import java.net.URI

class GenerateQrCodeUseCase(
    private val fileUtil: FileUtil
) : BaseUseCase<Result<URI>>() {
    override val dispatcher = Dispatchers.Default
    private val qrCodeWidth = 500
    private val qrCodeHeight = qrCodeWidth
    var qrContent = ""

    override suspend fun buildFlow(): Flow<Result<URI>> {
        val content = qrContent
        if (content.isEmpty()) return flowOf(
            Result.Error(
                IllegalArgumentException("qrContent must not be empty")
            )
        )

        return flow {
            emit(Result.Loading)

            try {
                emit(
                    Result.Success(
                        data = fileUtil.getURIForFile(
                            QRCode.from(content)
                                .withSize(qrCodeWidth, qrCodeHeight)
                                .file()
                        )
                    )
                )
            } catch (ex: Exception) {
                emit(Result.Error(exception = ex))
            }
        }
    }

}