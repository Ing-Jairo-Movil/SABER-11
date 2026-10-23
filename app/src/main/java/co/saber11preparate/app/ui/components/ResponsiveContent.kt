package co.saber11preparate.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.saber11preparate.app.core.AppDimens

/**
 * Limita el ancho del contenido en pantallas grandes (tablet / landscape)
 * para conservar legibilidad sin layouts específicos todavía.
 */
@Composable
fun ResponsiveContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val contentModifier = if (maxWidth > AppDimens.maxContentWidth) {
            Modifier.widthIn(max = AppDimens.maxContentWidth)
        } else {
            Modifier.fillMaxWidth()
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Box(modifier = contentModifier) {
                content()
            }
        }
    }
}
