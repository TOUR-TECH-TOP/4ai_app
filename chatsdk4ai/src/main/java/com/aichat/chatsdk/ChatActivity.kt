package com.aichat.chatsdk

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ChatActivity : ComponentActivity() {

    companion object{
        const val SITE_ID = "site_id"
        fun start(context: Context, message: String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(SITE_ID, message)
            context.startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val siteId = intent.getStringExtra(SITE_ID)

        setContent {

            checkAndRequestAudioPermission(this)
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebViewScreen(siteId)
                }
            }
        }
    }
}

private fun checkAndRequestAudioPermission(context: ChatActivity) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            context, arrayOf(Manifest.permission.RECORD_AUDIO), 10
        )
    }
}


@Composable
fun WebViewScreen(siteId: String?) {
    val context = LocalContext.current
    var htmlContent = loadAndModifyHtml(context, "embedchat.html", siteId.toString())



    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                        super.onPermissionRequestCanceled(request)
                        Toast.makeText(context,"Canceled", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRequest(request: PermissionRequest?) {
                        val requestedResources = request?.resources
                        if (requestedResources != null) {
                            for (r in requestedResources) {
                                if (r == PermissionRequest.RESOURCE_AUDIO_CAPTURE) {
                                    request.grant(arrayOf(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
                                    break
                                }
                            }
                        }
                    }
                }
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.allowFileAccess = true
                settings.allowContentAccess = true
            //                settings.useWideViewPort = true
//                settings.builtInZoomControls = true
//                settings.displayZoomControls = false
            }
        },
        update = { webView ->
            webView.loadUrl("https://4ai.chat/mobile-script?siteId=${siteId}")
//            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
        }
    )

}



fun loadAndModifyHtml(context: Context, filename: String, siteId: String): String {
    val inputStream = context.assets.open(filename)
    val html = inputStream.bufferedReader().use { it.readText() }
    return html.replace("SITE_ID_PLACEHOLDER", siteId)
}