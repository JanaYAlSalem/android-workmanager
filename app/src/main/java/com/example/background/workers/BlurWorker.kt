package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI
import com.example.background.R

class BlurWorker(context: Context,workerParameters: WorkerParameters ): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val applicationContext = applicationContext
            makeStatusNotification("JANA",applicationContext)

        val resorureUri = inputData.getString(KEY_IMAGE_URI)

        return try {
    val resolver = applicationContext.contentResolver

            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resorureUri))) //`openInputStream(Uri.parse(resourceUri)))

            val outPut = blurBitmap(picture,applicationContext)
            val outputUri = writeBitmapToFile(applicationContext, outPut)
            makeStatusNotification("out put save in Temporary file", applicationContext)

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
        Result.success(outputData)
        }
        catch (e: Exception) {

            Result.failure()
        }
    }
}