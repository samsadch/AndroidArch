package com.samsad.kotlinmarsnetwork

import android.app.Application
import android.os.Environment
import com.bosphere.filelogger.FL
import com.bosphere.filelogger.FLConfig
import com.bosphere.filelogger.FLConst
import com.bosphere.filelogger.FileFormatter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FL.init(
            FLConfig.Builder(this)
                .defaultTag("KotlinMars") // customise default tag
                .minLevel(FLConst.Level.V) // customise minimum logging level
                .logToFile(true) // enable logging to file
                .dir(
                    File(
                        Environment.getExternalStorageDirectory(),"KotlinMars"
                    )
                ) // customise directory to hold log files
                .formatter(object : FileFormatter {
                    override fun formatLine(
                        timeInMillis: Long,
                        level: String,
                        tag: String,
                        log: String
                    ): String {
                        val dateFormat = SimpleDateFormat("dd-MM HH:mm:ss.SSS")
                        return dateFormat.format(Date(timeInMillis))
                            .toString() + " " + level + "/" + tag + ": " + log
                    }

                    override fun formatFileName(timeInMillis: Long): String {
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                        return "Log_" + dateFormat.format(Date(timeInMillis)).toString() + ".txt"
                    }
                })
                .retentionPolicy(FLConst.RetentionPolicy.FILE_COUNT) // customise retention strategy
                .maxFileCount(FLConst.DEFAULT_MAX_FILE_COUNT) // customise how many log files to keep if retention by file count
                .maxTotalSize(FLConst.DEFAULT_MAX_TOTAL_SIZE) // customise how much space log files can occupy if retention by total size
                .build()
        )
        FL.setEnabled(true)
    }

}