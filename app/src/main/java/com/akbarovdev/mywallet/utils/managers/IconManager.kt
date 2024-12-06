package com.akbarovdev.mywallet.utils.managers

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

object IconManager {
    fun getIconName(icon: ImageVector): String {
        return icon.name.split(".")[1]
    }

    fun iconByName(name: String): ImageVector {
        val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
        val method = cl.declaredMethods.first()
        return method.invoke(null, Icons.Filled) as ImageVector
    }
}