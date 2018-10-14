package me.makarov.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.evaluateString
import java.util.*

private const val IGNORE_ANNOTATION = "org.junit.Ignore"

//UAST works on top of PSI model
class NoReasonIgnore : Detector(), Detector.UastScanner {

    internal companion object {
        val ISSUE: Issue = Issue.create(
                id = "NoReasonIgnore",
                briefDescription = "Provide reason for ignore",
                explanation = "Long custom Explanation",
                severity = Severity.ERROR,
                implementation = Implementation(
                        NoReasonIgnore::class.java,
                        EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)  // it runs also on Kotlin files
                )
        )
    }

    //Types for handle
    override fun getApplicableUastTypes(): List<Class<out UElement>>? = listOf(UClass::class.java, UMethod::class.java)

    // Element handler
    override fun createUastHandler(context: JavaContext): UElementHandler = UastHandler(context)


    private class UastHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitClass(node: UClass) {
            handleAnnotation(node, node.annotations)
        }

        override fun visitMethod(node: UMethod) {
            handleAnnotation(node, node.annotations)
        }

        private fun handleAnnotation(element: UElement, annotations: List<UAnnotation>) {
            annotations
                    .firstOrNull { it.qualifiedName == IGNORE_ANNOTATION }
                    ?.let {
                        // find attrs in annotation
                        if (it.findDeclaredAttributeValue("value")?.evaluateString().isNullOrBlank()) {
                            // if value is not presented -> report

                            // issue should be in kotlin and java (we used UAST)
                            context.report(
                                    ISSUE,
                                    element,
                                    context.getNameLocation(element),
                                    "Ignore requires explanation"
                            )
                        }
                    }
        }
    }
}