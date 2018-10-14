package me.makarov.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue


class SampleIssueRegistry : IssueRegistry() {

    // visible issues in client code
    override val issues: List<Issue>
        get() = listOf(SampleIssueDetector.ISSUE, HungarianDetector.ISSUE, NoFooDetector.ISSUE, NoReasonIgnore.ISSUE)

    override val minApi: Int
        get() = 1

    // need to correct work in studio
    override val api: Int = com.android.tools.lint.detector.api.CURRENT_API
}