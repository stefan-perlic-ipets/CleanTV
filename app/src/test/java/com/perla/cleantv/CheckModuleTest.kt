package com.perla.cleantv

import com.perla.cleantv.app.appModule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModuleTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify()
    }
}