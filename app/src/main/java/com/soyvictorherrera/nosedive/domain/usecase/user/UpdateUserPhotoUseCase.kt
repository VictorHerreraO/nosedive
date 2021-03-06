package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.ImageCompressor
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI

class UpdateUserPhotoUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val fileUtil: FileUtil
) : BaseUseCase<Result<URI>>() {

    var fileUri: URI? = null

    @FlowPreview
    override suspend fun buildFlow(): Flow<Result<URI>> {
        val safeUri = fileUri ?: return flowOf(
            Result.Error(IllegalArgumentException("{fileUri} must not be null"))
        )

        return flowOf(safeUri)
            .map { uri ->
                // Read file
                getFileFromUri(uri) ?: throw FileNotFoundException("unable to read file")
            }
            .map { file ->
                compressFile(file)
                return@map file
            }
            .flatMapMerge { file ->
                // Assign to user
                authRepository.getCurrentAuthentication()
                    .map { authResult ->
                        // Get current user ID
                        when (authResult) {
                            is Result.Success -> {
                                authResult.data.userId!!
                            }
                            is Result.Error -> {
                                throw authResult.exception
                            }
                            else -> {
                                throw IllegalStateException("authResult must be success or error")
                            }
                        }
                    }
                    .flatMapMerge { userId ->
                        // Update user photo
                        userRepository.updateUserPhoto(
                            userId = userId,
                            photo = file
                        )
                    }
            }
            .catch { t ->
                emit(
                    Result.Error(
                        Exception("unable to update profile photo", t)
                    )
                )
            }
    }

    @Throws(IOException::class)
    private fun getFileFromUri(sourceUri: URI): File? {
        return fileUtil.importFileFromUri(sourceUri)
    }

    private fun compressFile(file: File) {
        return ImageCompressor.compressCurrentBitmapFile(file)
    }

}
