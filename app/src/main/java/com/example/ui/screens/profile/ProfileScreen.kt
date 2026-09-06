package com.example.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PromptItem
import com.example.ui.components.GlowingCard
import com.example.ui.components.NeonBadge
import com.example.ui.components.NeonButton
import com.example.ui.components.PromptCard
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderHighlight
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.viewmodel.PromptViewModel

enum class ProfileSubTab {
  BOOKMARKS,
  CUSTOM_VAULT,
  HISTORY
}

@Composable
fun ProfileScreen(
  viewModel: PromptViewModel,
  onPromptClick: (PromptItem) -> Unit,
  onCopyPrompt: (PromptItem) -> Unit,
  onBookmarkClick: (PromptItem) -> Unit,
  onLikeClick: (PromptItem) -> Unit,
  onReplaySplash: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val bookmarkedPrompts by viewModel.bookmarkedPrompts.collectAsState()
  val customPrompts by viewModel.customCreatedPrompts.collectAsState()
  val historyList by viewModel.historyFlow.collectAsState()

  var selectedSubTab by remember { mutableStateOf(ProfileSubTab.BOOKMARKS) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("profile_screen"),
    contentPadding = PaddingValues(bottom = 110.dp)
  ) {
    // 1. User Profile Orange Card
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(horizontal = 20.dp, vertical = 14.dp)
      ) {
        GlowingCard(
          modifier = Modifier.fillMaxWidth(),
          borderColor = OrangeBorder,
          backgroundColor = DarkCardBg,
          glowColor = OrangeDim
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
              // Glowing Avatar
              Box(
                modifier = Modifier
                  .size(56.dp)
                  .clip(CircleShape)
                  .background(
                    Brush.linearGradient(
                      colors = listOf(OrangePrimary, OrangeLight, Color(0xFFFF9E00))
                    )
                  )
                  .padding(2.dp)
                  .clip(CircleShape)
                  .background(DarkBg),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Filled.Person,
                  contentDescription = null,
                  tint = OrangePrimary,
                  modifier = Modifier.size(32.dp)
                )
              }

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Text(
                    text = strings.profileTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextMain,
                    fontWeight = FontWeight.Black
                  )
                  Icon(
                    imageVector = Icons.Filled.VerifiedUser,
                    contentDescription = "Verified",
                    tint = OrangePrimary,
                    modifier = Modifier.size(16.dp)
                  )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                  text = "PRMTLY PRO // ARCHITECT TIER",
                  style = MaterialTheme.typography.labelSmall,
                  color = OrangePrimary,
                  fontSize = 10.sp,
                  letterSpacing = 0.8.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats Row
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkCardElevated)
                .padding(12.dp),
              horizontalArrangement = Arrangement.SpaceAround
            ) {
              StatCounter(label = strings.statSaved, value = "${bookmarkedPrompts.size}", color = OrangePrimary)
              StatCounter(label = strings.statCustom, value = "${customPrompts.size}", color = OrangeLight)
              StatCounter(label = strings.statCopies, value = "${historyList.size}", color = AccentGreen)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Admin Panel Access Button
            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { viewModel.openAdminPanel() }
                .testTag("admin_panel_button"),
              shape = RoundedCornerShape(10.dp),
              color = Color(0x33FF5500),
              border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.6f))
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Filled.Security,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Column {
                    Text(
                      text = strings.adminPanelTitle,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = OrangePrimary,
                        letterSpacing = 0.5.sp
                      )
                    )
                    Text(
                      text = strings.adminPanelSubtitle,
                      style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                      )
                    )
                  }
                }

                NeonBadge(text = strings.manage, accentColor = OrangePrimary)
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Splash Screen Intro Replay Button
            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { onReplaySplash() }
                .testTag("replay_splash_button"),
              shape = RoundedCornerShape(10.dp),
              color = Color(0x22FFA000),
              border = BorderStroke(1.dp, Color(0x55FFA000))
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = OrangeLight,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Column {
                    Text(
                      text = strings.splashIntroBtnTitle,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = OrangeLight,
                        letterSpacing = 0.5.sp,
                        fontSize = 11.sp
                      )
                    )
                    Text(
                      text = strings.splashIntroBtnSub,
                      style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                      )
                    )
                  }
                }

                NeonBadge(text = strings.splashIntroPlay, accentColor = OrangeLight)
              }
            }
          }
        }
      }
    }

    // 2. Sub-Tab Switcher (Bookmarks, Custom Vault, History)
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(DarkCardBg)
          .border(BorderStroke(1.dp, DarkCardBorder), RoundedCornerShape(12.dp))
          .padding(4.dp)
      ) {
        SubTabButton(
          title = "${strings.tabSaved} (${bookmarkedPrompts.size})",
          isSelected = selectedSubTab == ProfileSubTab.BOOKMARKS,
          onClick = { selectedSubTab = ProfileSubTab.BOOKMARKS },
          accentColor = OrangePrimary,
          modifier = Modifier.weight(1f)
        )
        SubTabButton(
          title = "${strings.tabVault} (${customPrompts.size})",
          isSelected = selectedSubTab == ProfileSubTab.CUSTOM_VAULT,
          onClick = { selectedSubTab = ProfileSubTab.CUSTOM_VAULT },
          accentColor = OrangePrimary,
          modifier = Modifier.weight(1f)
        )
        SubTabButton(
          title = "${strings.tabHistory} (${historyList.size})",
          isSelected = selectedSubTab == ProfileSubTab.HISTORY,
          onClick = { selectedSubTab = ProfileSubTab.HISTORY },
          accentColor = OrangePrimary,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Tab Content
    when (selectedSubTab) {
      ProfileSubTab.BOOKMARKS -> {
        if (bookmarkedPrompts.isEmpty()) {
          item {
            EmptyStateView(
              title = strings.noSavedBookmarks,
              subtitle = strings.noSavedBookmarksSub
            )
          }
        } else {
          val chunked = bookmarkedPrompts.chunked(2)
          items(chunked) { pair ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              for (prompt in pair) {
                Box(modifier = Modifier.weight(1f)) {
                  PromptCard(
                    prompt = prompt,
                    onCardClick = { onPromptClick(prompt) },
                    onCopyClick = { onCopyPrompt(prompt) },
                    onBookmarkClick = { onBookmarkClick(prompt) },
                    onLikeClick = { onLikeClick(prompt) }
                  )
                }
              }
              if (pair.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
          }
        }
      }

      ProfileSubTab.CUSTOM_VAULT -> {
        if (customPrompts.isEmpty()) {
          item {
            EmptyStateView(
              title = strings.vaultEmpty,
              subtitle = strings.vaultEmptySub
            )
          }
        } else {
          items(customPrompts) { prompt ->
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
              GlowingCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DarkCardBg,
                borderColor = DarkCardBorder
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    NeonBadge(
                      text = prompt.model.displayName,
                      accentColor = prompt.model.tagColor
                    )
                    IconButton(
                      onClick = { viewModel.deleteCustomPrompt(prompt.id) },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = AccentRed,
                        modifier = Modifier.size(18.dp)
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = prompt.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextMain,
                    fontWeight = FontWeight.Bold
                  )

                  Text(
                    text = prompt.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                  )

                  Spacer(modifier = Modifier.height(10.dp))

                  // Template snippet
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DarkCardElevated,
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Text(
                      text = prompt.promptTemplate,
                      style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        color = TextMain,
                        fontSize = 11.sp
                      ),
                      modifier = Modifier.padding(10.dp),
                      maxLines = 3
                    )
                  }

                  Spacer(modifier = Modifier.height(12.dp))

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                  ) {
                    NeonButton(
                      text = strings.openDetail,
                      onClick = { onPromptClick(prompt) },
                      icon = Icons.Filled.AutoAwesome,
                      isPrimary = false,
                      accentColor = OrangePrimary,
                      modifier = Modifier.weight(1f)
                    )

                    NeonButton(
                      text = strings.copyPrompt,
                      onClick = { onCopyPrompt(prompt) },
                      icon = Icons.Filled.ContentCopy,
                      isPrimary = true,
                      accentColor = OrangePrimary,
                      modifier = Modifier.weight(1f)
                    )
                  }
                }
              }
            }
          }
        }
      }

      ProfileSubTab.HISTORY -> {
        if (historyList.isEmpty()) {
          item {
            EmptyStateView(
              title = strings.noHistory,
              subtitle = strings.noHistorySub
            )
          }
        } else {
          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = strings.recentCopied,
                style = MaterialTheme.typography.labelSmall,
                color = OrangePrimary,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = strings.clearHistory + " ✕",
                style = MaterialTheme.typography.labelSmall,
                color = AccentRed,
                modifier = Modifier.clickable { viewModel.clearHistory() }
              )
            }
          }

          items(historyList) { item ->
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
              GlowingCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DarkCardBg,
                borderColor = DarkCardBorder
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = item.promptTitle,
                      style = MaterialTheme.typography.titleSmall,
                      color = TextMain,
                      fontWeight = FontWeight.Bold,
                      maxLines = 1
                    )
                    NeonBadge(text = item.modelName, accentColor = OrangePrimary)
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = item.compiledPrompt,
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontFamily = FontFamily.Monospace,
                      color = TextMuted,
                      fontSize = 11.sp
                    ),
                    maxLines = 2
                  )

                  Spacer(modifier = Modifier.height(8.dp))

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                  ) {
                    Surface(
                      onClick = { viewModel.copyRawText(item.compiledPrompt, item.promptTitle) },
                      shape = RoundedCornerShape(6.dp),
                      color = OrangeDim,
                      border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.3f))
                    ) {
                      Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                      ) {
                        Icon(
                          imageVector = Icons.Filled.ContentCopy,
                          contentDescription = "Copy Again",
                          tint = OrangePrimary,
                          modifier = Modifier.size(12.dp)
                        )
                        Text(
                          text = strings.copyAgain,
                          color = OrangePrimary,
                          fontSize = 10.sp,
                          fontWeight = FontWeight.Bold,
                          fontFamily = FontFamily.Monospace
                        )
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun StatCounter(label: String, value: String, color: Color) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      style = MaterialTheme.typography.titleLarge,
      color = color,
      fontWeight = FontWeight.Black
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = TextMuted,
      fontSize = 9.sp,
      letterSpacing = 0.8.sp
    )
  }
}

@Composable
private fun SubTabButton(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(8.dp),
    color = if (isSelected) OrangePrimary else Color.Transparent,
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(vertical = 8.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = title,
        color = if (isSelected) DarkBg else TextMuted,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        fontSize = 10.5.sp
      )
    }
  }
}

@Composable
private fun EmptyStateView(title: String, subtitle: String) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(40.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = Icons.Filled.Bookmark,
      contentDescription = null,
      tint = TextSubtle,
      modifier = Modifier.size(44.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.titleSmall,
      color = TextMuted,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = subtitle,
      style = MaterialTheme.typography.bodySmall,
      color = TextSubtle,
      lineHeight = 18.sp
    )
  }
}

