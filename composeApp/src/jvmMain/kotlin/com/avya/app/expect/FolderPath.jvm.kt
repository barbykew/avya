package com.avya.app.expect

actual fun getDownloadFolderPath(): String = System.getProperty("user.home") + "/Downloads"