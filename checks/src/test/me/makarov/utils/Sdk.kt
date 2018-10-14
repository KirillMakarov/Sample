package me.makarov.utils

import com.android.testutils.TestUtils
import com.android.tools.lint.checks.infrastructure.TestLintTask
import me.makarov.utils.Util.sdk
import java.io.File


private object Util {
    val lo = TestUtils.getSdk()
    val sdk = File("/Users/kirillmakarov/Library/Android/sdk") //todo: import from system env
}

fun sdkLint(): TestLintTask {
    val task = TestLintTask.lint()
    task.sdkHome(sdk)
    return task
}