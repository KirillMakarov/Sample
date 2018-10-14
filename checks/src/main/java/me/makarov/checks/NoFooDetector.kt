package me.makarov.checks

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class NoFooDetector : Detector(), Detector.UastScanner {
    internal companion object {
        val ISSUE: Issue = Issue.create(
                id = "NoFoo",
                briefDescription = "don't use foo",
                explanation = "foo?",
                severity = Severity.WARNING,
                category = Category.MESSAGES,
                implementation = Implementation(
                        NoFooDetector::class.java,
                        Scope.JAVA_FILE_SCOPE // it runs also on Kotlin files
                )
        )
    }


    override fun getApplicableMethodNames(): List<String> = listOf("foo")

    override fun visitMethod(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        context.report(ISSUE, node, context.getLocation(node), "foo usage!")
    }
}