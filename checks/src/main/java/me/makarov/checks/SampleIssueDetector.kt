package me.makarov.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

// uast scanner for scanning kotlin and java source files
class SampleIssueDetector : Detector(), Detector.UastScanner {

    internal companion object {
        val ISSUE: Issue = Issue.create(
                id = "NamingPattern",
                briefDescription = "Names should be well named",
                explanation = "Long custom Explanation",
                severity = Severity.ERROR,
                implementation = Implementation(
                        SampleIssueDetector::class.java,
                        Scope.JAVA_FILE_SCOPE // it runs also on Kotlin files
                )
        )
    }

    // nodes which visitor should visit
    override fun getApplicableUastTypes(): List<Class<out UElement>>? = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler = NamingPatternHandler(context)


    private class NamingPatternHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitClass(node: UClass) {
            if (node.name?.isDefinedCamelCase() == false) {

                val lintFix = LintFix
                        .create()
                        .name("It is custom name for quickfix")
                        .replace()
                        .text(node.name)
                        .with("SampleName") //todo: calculate dynamic
                        .build()

                context.report(
                        ISSUE,
                        node,
                        context.getNameLocation(node),
                        "Not named in defined camel case.",
                        lintFix
                )
            }
        }

        private fun String.isDefinedCamelCase(): Boolean {
            val charArray = toCharArray()
            return charArray
                    .mapIndexed { index, current ->
                        current to charArray.getOrNull(index + 1)
                    }
                    .none {
                        it.first.isUpperCase() && it.second?.isUpperCase() ?: false
                    }
        }
    }


}