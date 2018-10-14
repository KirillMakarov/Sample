package me.makarov.checks

import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import me.makarov.utils.sdkLint
import org.junit.Test

class SampleIssueDetectorTest {

    @Test
    fun correctClassName() {
        sdkLint()
                .files(kt("""
       |package foo;
       |
       |class XMLHTTPRequest""".trimMargin()))
                .issues(SampleIssueDetector.ISSUE)
                .run()
                .expect("""
       |src/foo/XMLHTTPRequest.kt:3: Error: Not named in defined camel case. [NamingPattern]
       |class XMLHTTPRequest
       |      ~~~~~~~~~~~~~~
       |1 errors, 0 warnings""".trimMargin())
    }

    @Test
    fun sampleTes2t() {
        sdkLint()
                .files(java("""
        |package foo;
        |
        |class XmlHttpRequest {
        |}""".trimMargin()))
                .issues(SampleIssueDetector.ISSUE)
                .run()
                .expectClean()
    }

    @Test
    fun incorrectClassName() {
        sdkLint()
                .files(java("""
        |package foo;
        |
        |class XMLHTTPRequest {
        |}""".trimMargin()))
                .issues(SampleIssueDetector.ISSUE)
                .run()
                .expect("""
        |src/foo/XMLHTTPRequest.java:3: Error: Not named in defined camel case. [NamingPattern]
        |class XMLHTTPRequest {
        |      ~~~~~~~~~~~~~~
        |1 errors, 0 warnings""".trimMargin())
    }


}