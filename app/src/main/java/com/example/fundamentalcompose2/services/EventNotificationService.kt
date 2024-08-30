import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.fundamentalcompose2.MainActivity
import com.example.fundamentalcompose2.R
import kotlin.random.Random

class EventNotificationService(
    private val context: Context,
    private val contentTitle: String,
    private val contentText: String,
    private val imageUrl: String? = null, // URL gambar dari API
    private val eventId: Int // ID atau data lain untuk detail
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("event_id", eventId) // Kirim data yang diperlukan ke DetailActivity
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Tambahkan flag IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "event_notification")
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logodicoding)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set PendingIntent
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    suspend fun showExpandableNotification() {
        val bitmap = imageUrl?.let { loadBitmap(it) }

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("event_id", eventId) // Kirim data yang diperlukan ke DetailActivity
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Tambahkan flag IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, "event_notification")
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logodicoding)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set PendingIntent

        if (bitmap != null) {
            notificationBuilder.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
            )
        }

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }

    private suspend fun loadBitmap(url: String): Bitmap? {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        return (loader.execute(request) as? SuccessResult)?.drawable?.toBitmap()
    }
}
