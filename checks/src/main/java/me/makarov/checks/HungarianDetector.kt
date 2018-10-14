package me.makarov.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UField

class HungarianDetector : Detector(), Detector.UastScanner {
    internal companion object {
        val ISSUE: Issue = Issue.create(
                id = "Hungarian",
                briefDescription = "don't use mFields",
                explanation = "mOk?",
                severity = Severity.ERROR,
                implementation = Implementation(
                        HungarianDetector::class.java,
                        Scope.JAVA_FILE_SCOPE // it runs also on Kotlin files
                )
        )
    }

    // nodes which visitor should visit
    override fun getApplicableUastTypes(): List<Class<out UElement>>? = listOf(UField::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler = IssueHandler(context)

    private class IssueHandler(val context: JavaContext) : UElementHandler() {
        override fun visitField(node: UField) {
            val name = node.name.toCharArray()
            if (node.isStatic) {
                if (!node.isFinal && name[0] == 's' && name[1].isUpperCase())
                    reportIssue(node, node.name, context)
            } else if (name[0] == 'm' && name[1].isUpperCase()) {
                reportIssue(node, node.name, context)
            }
        }

        private fun reportIssue(node: UField, name: String, context: JavaContext) {
            context.report(ISSUE, context.getLocation(node), "Hungarian is prohibited here")
        }

    }

}