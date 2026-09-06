package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

@Composable
fun PaginationControl(
  currentPage: Int,
  totalPages: Int,
  onPageChange: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val safeTotalPages = totalPages.coerceAtLeast(1)
  val safeCurrentPage = currentPage.coerceIn(1, safeTotalPages)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Badge: ● PAGE 1 / 18
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = DarkCardElevated,
      border = BorderStroke(1.dp, DarkCardBorder)
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Box(
          modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(OrangePrimary)
        )
        Text(
          text = "PAGE $safeCurrentPage / $safeTotalPages",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
          fontSize = 11.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Pagination Circle Buttons: < 1 2 3 4 ... 18 >
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Previous Arrow Button
      val canGoBack = safeCurrentPage > 1
      PaginationCircleButton(
        onClick = { if (canGoBack) onPageChange(safeCurrentPage - 1) },
        enabled = canGoBack,
        content = {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous Page",
            tint = if (canGoBack) TextMain else TextSubtle.copy(alpha = 0.4f),
            modifier = Modifier.size(18.dp)
          )
        }
      )

      // Compute which page numbers to show (e.g. 1, 2, 3, 4, ..., totalPages)
      val pageItems = calculatePageItems(safeCurrentPage, safeTotalPages)

      for (item in pageItems) {
        when (item) {
          is PageItem.Number -> {
            val isSelected = item.page == safeCurrentPage
            PaginationNumberButton(
              page = item.page,
              isSelected = isSelected,
              onClick = { onPageChange(item.page) }
            )
          }
          is PageItem.Ellipsis -> {
            Text(
              text = "...",
              color = TextSubtle,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 4.dp)
            )
          }
        }
      }

      // Next Arrow Button
      val canGoNext = safeCurrentPage < safeTotalPages
      PaginationCircleButton(
        onClick = { if (canGoNext) onPageChange(safeCurrentPage + 1) },
        enabled = canGoNext,
        content = {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Next Page",
            tint = if (canGoNext) TextMain else TextSubtle.copy(alpha = 0.4f),
            modifier = Modifier.size(18.dp)
          )
        }
      )
    }
  }
}

@Composable
private fun PaginationNumberButton(
  page: Int,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .size(38.dp)
      .clip(CircleShape)
      .background(if (isSelected) OrangePrimary else DarkCardElevated)
      .then(
        if (!isSelected) {
          Modifier.clickable(onClick = onClick)
        } else Modifier
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = page.toString(),
      color = if (isSelected) Color.Black else TextMain,
      fontSize = 13.sp,
      fontWeight = FontWeight.ExtraBold
    )
  }
}

@Composable
private fun PaginationCircleButton(
  onClick: () -> Unit,
  enabled: Boolean,
  content: @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .size(38.dp)
      .clip(CircleShape)
      .background(DarkCardElevated)
      .then(
        if (enabled) {
          Modifier.clickable(onClick = onClick)
        } else Modifier
      ),
    contentAlignment = Alignment.Center
  ) {
    content()
  }
}

private sealed interface PageItem {
  data class Number(val page: Int) : PageItem
  data object Ellipsis : PageItem
}

private fun calculatePageItems(currentPage: Int, totalPages: Int): List<PageItem> {
  if (totalPages <= 6) {
    return (1..totalPages).map { PageItem.Number(it) }
  }

  val list = mutableListOf<PageItem>()
  val maxMiddle = 4.coerceAtMost(totalPages - 1)

  for (i in 1..maxMiddle) {
    list.add(PageItem.Number(i))
  }

  if (maxMiddle < totalPages - 1) {
    list.add(PageItem.Ellipsis)
  }

  list.add(PageItem.Number(totalPages))
  return list
}
