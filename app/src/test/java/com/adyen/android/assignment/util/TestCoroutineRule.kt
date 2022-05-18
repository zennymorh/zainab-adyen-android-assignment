package com.adyen.android.assignment.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.EmptyCoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher(), TestCoroutineScope by createTestCoroutineScope(
    TestCoroutineDispatcher() + TestCoroutineExceptionHandler() + EmptyCoroutineContext
) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}