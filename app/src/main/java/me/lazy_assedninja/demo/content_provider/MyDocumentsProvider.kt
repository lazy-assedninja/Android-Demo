package me.lazy_assedninja.demo.content_provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Point
import android.os.CancellationSignal
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.DocumentsProvider
import android.util.Log
import android.webkit.MimeTypeMap
import me.lazy_assedninja.demo.R
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

private const val DOCUMENT_PROVIDER = "documents_provider"

private const val ROOT = "root"

private const val MAX_SEARCH_RESULTS = 20
private const val INITIAL_CAPACITY = 5
private const val MAX_LAST_MODIFIED = 5

/**
 * A custom content provider that allows a storage service (such as Google Drive) to reveal
 * the files I provided.
 */
@SuppressLint("LogNotTimber")
class MyDocumentsProvider : DocumentsProvider() {

    // Use these as the default columns to return information about a root if no specific
    // columns are requested in a query.
    private val defaultRootProjection = arrayOf(
        DocumentsContract.Root.COLUMN_ROOT_ID,
        DocumentsContract.Root.COLUMN_FLAGS,
        DocumentsContract.Root.COLUMN_ICON,
        DocumentsContract.Root.COLUMN_TITLE,
        DocumentsContract.Root.COLUMN_SUMMARY, // Optional
        DocumentsContract.Root.COLUMN_DOCUMENT_ID,
        DocumentsContract.Root.COLUMN_AVAILABLE_BYTES, // Optional
        DocumentsContract.Root.COLUMN_MIME_TYPES, // Optional
//        DocumentsContract.Root.COLUMN_CAPACITY_BYTES, // Optional, SDK = 24
//        DocumentsContract.Root.COLUMN_QUERY_ARGS // Optional, SDK = 29
    )

    // Use these as the default columns to return information about a document if no specific
    // columns are requested in a query.
    private val defaultDocumentProjection = arrayOf(
        DocumentsContract.Document.COLUMN_DOCUMENT_ID,
        DocumentsContract.Document.COLUMN_MIME_TYPE,
        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
        DocumentsContract.Document.COLUMN_LAST_MODIFIED,
        DocumentsContract.Document.COLUMN_FLAGS,
        DocumentsContract.Document.COLUMN_SIZE,
//        DocumentsContract.Document.COLUMN_SUMMARY, // Optional
//        DocumentsContract.Document.COLUMN_ICON, // Optional
    )

    private lateinit var baseDir: File

    override fun onCreate(): Boolean {
        context?.let {
            baseDir = it.filesDir
            writeDummyFilesToStorage(it)
        }
        return true
    }

    /**
     * Preload sample files packaged in the apk into the internal storage directory.  This is a
     * dummy function specific to this demo.  The MyCloud mock cloud service doesn't actually
     * have a backend, so it simulates by reading content from the device's internal storage.
     */
    private fun writeDummyFilesToStorage(context: Context) {
        baseDir.list()?.let {
            if (it.isNotEmpty()) return
        }

        val imageResIds = getResourceIDArray(context, R.array.array_img_ids)
        for (resId in imageResIds) {
            writeFileToInternalStorage(context, resId, ".jpeg")
        }

        val textResIds = getResourceIDArray(context, R.array.array_txt_ids)
        for (resId in textResIds) {
            writeFileToInternalStorage(context, resId, ".txt")
        }
    }

    /**
     * Write a file to internal storage.  Used to set up our dummy "cloud server".
     *
     * @param resId the resource ID of the file to write to internal storage
     * @param extension the file extension (ex. .png, .mp3)
     */
    private fun writeFileToInternalStorage(context: Context, resId: Int, extension: String) {
        val ins = context.resources.openRawResource(resId)
        val outputStream = ByteArrayOutputStream()
        var size: Int
        var buffer = ByteArray(1024)
        try {
            while (ins.read(buffer, 0, 1024).also { size = it } >= 0) {
                outputStream.write(buffer, 0, size)
            }
            ins.close()

            val filename = context.resources.getResourceEntryName(resId) + extension
            val fos = context.openFileOutput(filename, Context.MODE_PRIVATE)
            buffer = outputStream.toByteArray()
            fos.write(buffer)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getResourceIDArray(context: Context, arrayResId: Int): IntArray {
        val ar = context.resources.obtainTypedArray(arrayResId)
        val len = ar.length()
        val resIds = IntArray(len)
        for (i in 0 until len) {
            resIds[i] = ar.getResourceId(i, 0)
        }
        ar.recycle()
        return resIds
    }

    override fun queryRoots(projection: Array<out String>?): Cursor {

        // Use a MatrixCursor to build a cursor
        // with either the requested fields, or the default
        // projection if "projection" is null.
        val result = MatrixCursor(projection ?: defaultRootProjection)

        // If user is not logged in, return an empty root cursor.  This removes our
        // provider from the list entirely.
        Timber.v(DOCUMENT_PROVIDER, "isDocumentsProviderOpened: ${isDocumentsProviderOpened()}")
        if (!isDocumentsProviderOpened()) {
            return result
        }

        // It's possible to have multiple roots (e.g. for multiple accounts in the
        // same app) -- just add multiple cursor rows.
        context?.let {
            result.newRow().apply {
                add(DocumentsContract.Root.COLUMN_ROOT_ID, ROOT)

                // FLAG_SUPPORTS_CREATE means at least one directory under the root supports
                // creating documents. FLAG_SUPPORTS_RECENTS means your application's most
                // recently used documents will show up in the "Recents" category.
                // FLAG_SUPPORTS_SEARCH allows users to search all documents the application
                // shares.
                add(
                    DocumentsContract.Root.COLUMN_FLAGS,
                    DocumentsContract.Root.FLAG_SUPPORTS_CREATE or
                            DocumentsContract.Root.FLAG_SUPPORTS_RECENTS or
                            DocumentsContract.Root.FLAG_SUPPORTS_SEARCH
                )

                add(DocumentsContract.Root.COLUMN_ICON, R.mipmap.icon)

                // COLUMN_TITLE is the root title (e.g. Gallery, Drive).
                add(
                    DocumentsContract.Root.COLUMN_TITLE,
                    it.getString(R.string.title_documents_provider)
                )

                // You can provide an optional summary, which helps distinguish roots
                // with the same title. You can also use this field for displaying an
                // user account name.
                add(DocumentsContract.Root.COLUMN_SUMMARY, it.getString(R.string.summary_root))

                // This document id cannot change after it's shared.
                add(DocumentsContract.Root.COLUMN_DOCUMENT_ID, getDocumentIDForFile(baseDir))

                add(DocumentsContract.Root.COLUMN_AVAILABLE_BYTES, baseDir.freeSpace)

                // The child MIME types are used to filter the roots and only present to the
                // user those roots that contain the desired type somewhere in their file hierarchy.
                add(DocumentsContract.Root.COLUMN_MIME_TYPES, getChildMimeTypes())
            }
        }
        return result
    }

    /**
     * Function to determine whether the documents provider is opened.
     */
    private fun isDocumentsProviderOpened(): Boolean {
        return context?.getSharedPreferences(
            context?.getString(R.string.storage_name),
            Context.MODE_PRIVATE
        )?.getString(DOCUMENT_PROVIDER, "false").toBoolean()
    }

    @Throws(FileNotFoundException::class)
    override fun queryDocument(documentId: String, projection: Array<out String>?): Cursor {

        // Create a cursor with the requested projection, or the default projection.
        return MatrixCursor(projection ?: defaultDocumentProjection).apply {
            includeFile(this, documentId, null)
        }
    }

    @Throws(FileNotFoundException::class)
    override fun queryChildDocuments(
        parentDocumentId: String,
        projection: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return MatrixCursor(projection ?: defaultDocumentProjection).apply {
            val parent: File = getFileForDocumentID(parentDocumentId)
            parent.listFiles()?.forEach { file ->
                includeFile(this, null, file)
            }
        }
    }

    /**
     * This example implementation walks a local file structure to find the most recently
     * modified files.
     * Other implementations might include making a network call to query a server.
     */
    @Throws(FileNotFoundException::class)
    override fun queryRecentDocuments(rootId: String, projection: Array<out String>?): Cursor {

        // Create a cursor with the requested projection, or the default projection.
        val result = MatrixCursor(projection ?: defaultDocumentProjection)

        val parent: File = getFileForDocumentID(rootId)

        // Create a queue to store the most recent documents,
        // which orders by last modified.
        val lastModifiedFiles = PriorityQueue(
            INITIAL_CAPACITY,
            Comparator<File> { i, j ->
                compareValues(i.lastModified(), j.lastModified())
            }
        )

        // Iterate through all files and directories
        // in the file structure under the root.  If
        // the file is more recent than the least
        // recently modified, add it to the queue,
        // limiting the number of results.
        val pending: MutableList<File> = mutableListOf()

        // Start by adding the parent to the list of files to be processed
        pending.add(parent)

        // Do while we still have unexamined files
        while (pending.isNotEmpty()) {

            // Take a file from the list of unprocessed files
            val file: File = pending.removeFirst()
            if (file.isDirectory) {

                // If it's a directory, add all its children to the unprocessed list
                file.listFiles()?.let {
                    pending += it
                }
            } else {

                // If it's a file, add it to the ordered queue.
                lastModifiedFiles.add(file)
            }
        }

        // Add the most recent files to the cursor, not exceeding the max number of results.
        for (i in 0 until (MAX_LAST_MODIFIED + 1).coerceAtMost(lastModifiedFiles.size)) {
            includeFile(result, null, lastModifiedFiles.remove())
        }
        return result
    }

    /**
     * This example implementation searches file names for the query and doesn't rank search
     * results, so we can stop as soon as we find a sufficient number of matches.
     * Other implementations might use other data about files, rather than the file name, to
     * produce a match; it might also require a network call to query a remote server.
     */
    // END_INCLUDE(query_recent_documents)
    @Throws(FileNotFoundException::class)
    override fun querySearchDocuments(
        rootId: String,
        query: String,
        projection: Array<String?>?
    ): Cursor {

        // Create a cursor with the requested projection, or the default projection.
        val result = MatrixCursor(projection ?: defaultDocumentProjection)
        val parent: File = getFileForDocumentID(rootId)

        // Iterate through all files in the file structure under the root until we reach the
        // desired number of matches.
        val pending = LinkedList<File>()

        // Start by adding the parent to the list of files to be processed
        pending.add(parent)

        // Do while we still have unexamined files, and fewer than the max search results
        while (!pending.isEmpty() && result.count < MAX_SEARCH_RESULTS) {

            // Take a file from the list of unprocessed files
            val file = pending.removeFirst()
            if (file.isDirectory) {

                // If it's a directory, add all its children to the unprocessed list
                file.listFiles()?.let {
                    Collections.addAll(pending, *it)
                }
            } else {

                // If it's a file and it matches, add it to the result cursor.
                if (file.name.lowercase(Locale.getDefault()).contains(query)) {
                    includeFile(result, null, file)
                }
            }
        }
        return result
    }

    @Throws(FileNotFoundException::class)
    override fun openDocumentThumbnail(
        documentId: String,
        sizeHint: Point?,
        signal: CancellationSignal?
    ): AssetFileDescriptor {
        val file = getFileForDocumentID(documentId)
        val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        return AssetFileDescriptor(pfd, 0, AssetFileDescriptor.UNKNOWN_LENGTH)
    }

    /**
     * It's OK to do network operations in this method to download the document,
     * as long as you periodically check the CancellationSignal.
     * If you have an extremely large file to transfer from the network, a better solution may
     * be pipes or sockets (see ParcelFileDescriptor for helper methods).
     */
    @Throws(FileNotFoundException::class)
    override fun openDocument(
        documentId: String,
        mode: String,
        signal: CancellationSignal?
    ): ParcelFileDescriptor {
        val file: File = getFileForDocumentID(documentId)
        val accessMode: Int = ParcelFileDescriptor.parseMode(mode)

        val isWrite: Boolean = mode.contains("w")
        return if (isWrite) {
            val handler = context?.mainLooper?.let {
                Handler(it)
            }

            // Attach a close listener if the document is opened in write mode.
            try {
                ParcelFileDescriptor.open(file, accessMode, handler) {

                    // Update the file with the cloud server. The client is done writing.
                    Timber.i(
                        DOCUMENT_PROVIDER,
                        "A file with id $documentId has been closed! Time to update the server."
                    )
                }
            } catch (e: IOException) {
                throw FileNotFoundException(
                    "Failed to open document with id $documentId and mode $mode"
                )
            }
        } else {
            ParcelFileDescriptor.open(file, accessMode)
        }
    }

    @Throws(FileNotFoundException::class)
    override fun createDocument(documentId: String, mimeType: String, displayName: String): String {
        val parent: File = getFileForDocumentID(documentId)
        val file: File = try {
            File(parent.path, displayName).apply {
                createNewFile()
                setWritable(true)
                setReadable(true)
            }
        } catch (e: IOException) {
            throw FileNotFoundException(
                "Failed to create document with name $displayName and documentId $documentId"
            )
        }

        return getDocumentIDForFile(file)
    }

    // END_INCLUDE(create_document)
    @Throws(FileNotFoundException::class)
    override fun deleteDocument(documentId: String) {
        val file: File = getFileForDocumentID(documentId)
        if (file.delete()) {
            Timber.i(
                DOCUMENT_PROVIDER,
                "Deleted file with id $documentId"
            )
        } else {
            throw FileNotFoundException("Failed to delete document with id $documentId")
        }
    }

    @Throws(FileNotFoundException::class)
    override fun getDocumentType(documentId: String): String {
        val file: File = getFileForDocumentID(documentId)
        return getTypeForFile(file)
    }

    /**
     * Gets a string of unique MIME data types a directory supports, separated by newlines.  This
     * should not change.
     *
     * @return a string of the unique MIME data types the parent directory supports
     */
    private fun getChildMimeTypes(): String {
        val mimeTypes: MutableSet<String> = HashSet()
        mimeTypes.add("image/*")
        mimeTypes.add("text/*")

        // Flatten the list into a string and insert newlines between the MIME type strings.
        val mimeTypesString = StringBuilder()
        for (mimeType in mimeTypes) {
            mimeTypesString.append(mimeType).append("\n")
        }
        return mimeTypesString.toString()
    }

    /**
     * Get the document ID given a File.  The document id must be consistent across time.  Other
     * applications may save the ID and use it to reference documents later.
     *
     *
     * This implementation is specific to this demo.  It assumes only one root and is built
     * directly from the file structure.  However, it is possible for a document to be a child of
     * multiple directories (for example "android" and "images"), in which case the file must have
     * the same consistent, unique document ID in both cases.
     *
     * @param file the File whose document ID you want
     * @return the corresponding document ID
     */
    private fun getDocumentIDForFile(file: File): String {
        var path = file.absolutePath

        // Start at first char of path under root
        val rootPath = baseDir.path
        path = when {
            rootPath == path -> ""
            rootPath.endsWith("/") -> path.substring(rootPath.length)
            else -> path.substring(rootPath.length + 1)
        }
        return "root:$path"
    }

    /**
     * Translate your custom URI scheme into a File object.
     *
     * @param documentId the document ID representing the desired file
     * @return a File represented by the given document ID
     * @throws java.io.FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    private fun getFileForDocumentID(documentId: String): File {
        if (documentId == ROOT) return baseDir

        var target = baseDir
        val splitIndex = documentId.indexOf(':', 1)
        return if (splitIndex < 0) {
            throw FileNotFoundException("Missing root for $documentId")
        } else {
            val path = documentId.substring(splitIndex + 1)
            target = File(target, path)
            if (!target.exists()) throw FileNotFoundException("Missing file for $documentId at $target")
            target
        }
    }

    /**
     * Get a file's MIME type.
     *
     * @param file the File object whose type we want
     * @return the MIME type of the file
     */
    private fun getTypeForFile(file: File): String {
        val lastDot: Int = file.name.lastIndexOf('.')
        return if (file.isDirectory) {
            DocumentsContract.Document.MIME_TYPE_DIR
        } else {
            if (lastDot >= 0) MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(file.name.substring(lastDot + 1)) ?: ""
            else "application/octet-stream"
        }
    }

    /**
     * Add a representation of a file to a cursor.
     *
     * @param result the cursor to modify
     * @param docId  the document ID representing the desired file (may be null if given file)
     * @param f the File object representing the desired file (may be null if given docID)
     * @throws java.io.FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    private fun includeFile(result: MatrixCursor, docId: String?, f: File?) {
        var documentId = docId
        var file = f
        if (documentId == null) documentId = getDocumentIDForFile(file!!)
        else file = getFileForDocumentID(documentId)

        var flags = 0
        if (file.isDirectory) {

            // Request the folder to lay out as a grid rather than a list. This also allows a larger
            // thumbnail to be displayed for each image.
            flags = flags or DocumentsContract.Document.FLAG_DIR_PREFERS_GRID

            // Add FLAG_DIR_SUPPORTS_CREATE if the file is a writable directory.
            if (file.isDirectory && file.canWrite()) {
                flags = flags or DocumentsContract.Document.FLAG_DIR_SUPPORTS_CREATE
            }
        } else if (file.canWrite()) {

            // If the file is writable set FLAG_SUPPORTS_WRITE and FLAG_SUPPORTS_DELETE.
            flags = flags or DocumentsContract.Document.FLAG_SUPPORTS_WRITE
            flags = flags or DocumentsContract.Document.FLAG_SUPPORTS_DELETE
        }
        val displayName = file.name
        val mimeType: String = getTypeForFile(file)
        if (mimeType.startsWith("image/")) {

            // Allow the image to be represented by a thumbnail rather than an icon.
            flags = flags or DocumentsContract.Document.FLAG_SUPPORTS_THUMBNAIL
        }
        result.newRow().apply {
            add(DocumentsContract.Document.COLUMN_DOCUMENT_ID, documentId)
            add(DocumentsContract.Document.COLUMN_DISPLAY_NAME, displayName)
            add(DocumentsContract.Document.COLUMN_SIZE, file.length())
            add(DocumentsContract.Document.COLUMN_MIME_TYPE, mimeType)
            add(DocumentsContract.Document.COLUMN_LAST_MODIFIED, file.lastModified())
            add(DocumentsContract.Document.COLUMN_FLAGS, flags)

            // Add a custom icon
            add(DocumentsContract.Document.COLUMN_ICON, R.mipmap.icon)
        }
    }
}