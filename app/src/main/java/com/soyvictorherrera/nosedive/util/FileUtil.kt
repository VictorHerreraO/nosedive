package com.soyvictorherrera.nosedive.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.soyvictorherrera.nosedive.BuildConfig
import org.apache.commons.io.IOUtils
import java.io.*
import java.net.URI
import java.util.*

private const val FILE_MODE_READ_ONLY = "r"

class FileUtil(private val context: Context) {

    /**
     * Generates an URI pointing to a temp file
     *
     * @return URI for a tmp file
     */
    fun getTmpImageUri(): URI {
        val tmpFile = File.createTempFile("img_", ".tmp", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return getURIForFile(tmpFile)
    }

    /**
     * Receives an URI and copies the content to the cache dir of the application.
     *
     * @param fileUri URI location of the original file
     * @return copied File from URI
     * @throws IOException if an I/O error occurs
     * @see <a href="https://medium.com/@sriramaripirala/android-10-open-failed-eacces-permission-denied-da8b630a89df">Medium post</a>
     */
    @Throws(IOException::class)
    fun importFileFromUri(fileUri: URI): File? {
        val uri = Uri.parse(fileUri.toString())
        val descriptor = context.contentResolver
            .openFileDescriptor(
                uri, FILE_MODE_READ_ONLY,
                null
            )
        return if (descriptor != null) {
            val fileName: String = UUID.randomUUID().toString()
            val inputStream: InputStream = FileInputStream(descriptor.fileDescriptor)
            val file = File(context.cacheDir, fileName)
            val outputStream: OutputStream = FileOutputStream(file)
            IOUtils.copy(inputStream, outputStream)
            file
        } else null
    }

    /**
     * Generates a URI for the provided file using the app's FileProvider
     *
     * @param file File to get uri for
     * @return URI for the provided file
     */
    fun getURIForFile(file: File): URI {
        return URI(
            FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.provider",
                file
            ).toString()
        )
    }
}
