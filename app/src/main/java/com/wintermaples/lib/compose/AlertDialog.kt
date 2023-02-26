package com.wintermaples.lib.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

data class AlertDialogDesignModifier(
  val dialogVerticalPadding: Dp = 24.dp,
  val contentHorizontalPadding: Dp = 32.dp,
  val contentVerticalPadding: Dp = 16.dp,
  val contentVerticalSpacing: Dp = contentVerticalPadding,
  val buttonsHorizontalPadding: Dp = 8.dp,
  val buttonsHorizontalSpacing: Dp = buttonsHorizontalPadding,
  val buttonsVerticalSpacing: Dp = 12.dp,
)

/**
 * AlertDialog that can contains scrollable contents.
 *
 * ## Dependencies:
 * - com.google.accompanist:accompanist-flowlayout
 */
@Composable
fun AlertDialog(
  onDismissRequest: () -> Unit,
  confirmButton: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  dismissButton: @Composable (() -> Unit)? = null,
  title: @Composable (() -> Unit)? = null,
  text: @Composable (() -> Unit)? = null,
  shape: Shape = MaterialTheme.shapes.medium,
  backgroundColor: Color = MaterialTheme.colors.surface,
  contentColor: Color = contentColorFor(backgroundColor),
  properties: DialogProperties = DialogProperties(),
  designModifier: AlertDialogDesignModifier = AlertDialogDesignModifier()
) {
  Dialog(
    onDismissRequest = onDismissRequest,
    properties = properties,
  ) {
    Surface(
      modifier = modifier
        .padding(0.dp, designModifier.dialogVerticalPadding)
        .clip(shape)
        .background(MaterialTheme.colors.surface),
      contentColor = contentColor
    ) {
      Column {
        FlowColumn(
          modifier = Modifier
            .padding(designModifier.contentHorizontalPadding, designModifier.contentVerticalPadding)
            .verticalScroll(rememberScrollState())
            .weight(1f, fill = false)
            .fillMaxWidth(),
          mainAxisSpacing = designModifier.contentVerticalSpacing,
          mainAxisAlignment = MainAxisAlignment.SpaceAround,
        ) {
          title?.let {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
              val textStyle = MaterialTheme.typography.subtitle1
              ProvideTextStyle(textStyle, title)
            }
          }

          text?.let {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
              val textStyle = MaterialTheme.typography.body2
              ProvideTextStyle(textStyle, text)
            }
          }
        }

        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(designModifier.buttonsHorizontalPadding, 0.dp),
          mainAxisSpacing = designModifier.buttonsHorizontalSpacing,
          crossAxisSpacing = designModifier.buttonsVerticalSpacing,
          mainAxisAlignment = MainAxisAlignment.End,
        ) {
          dismissButton?.invoke()
          confirmButton()
        }

      }
    }
  }
}
