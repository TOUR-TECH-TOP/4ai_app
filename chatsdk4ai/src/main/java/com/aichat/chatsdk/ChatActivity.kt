package com.aichat.chatsdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

class ChatActivity : ComponentActivity() {

    companion object{
        const val SITE_ID = "site_id"
        fun start(context: Context, message: String) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(SITE_ID, message)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val siteId = intent.getStringExtra(SITE_ID)

        setContent {
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

@Composable
fun WebViewScreen(siteId: String?) {
    val context = LocalContext.current
    var htmlContent = loadAndModifyHtml(context, "embedchat.html", "NEFJQ0hBVEtFWTEw")



    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }
                webChromeClient = WebChromeClient()
                settings.javaScriptEnabled = true
//                settings.loadWithOverviewMode = true
//                settings.useWideViewPort = true
//                settings.builtInZoomControls = true
//                settings.displayZoomControls = false
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
        }
    )

}



fun loadAndModifyHtml(context: Context, filename: String, siteId: String): String {
    val inputStream = context.assets.open(filename)
    val html = inputStream.bufferedReader().use { it.readText() }
    return html.replace("SITE_ID_PLACEHOLDER", siteId)
}