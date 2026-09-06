package com.example.ui.screens.languages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

data class AppLanguage(
  val code: String,
  val nativeName: String,
  val englishName: String,
  val flagEmoji: String,
  val region: String
)

@Composable
fun LanguagesScreen(
  currentLanguageCode: String,
  onLanguageSelected: (AppLanguage) -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val allLanguages = remember {
    listOf(
      AppLanguage("en", "English", "English (US/Global)", "🇺🇸", "Global Default"),
      AppLanguage("es", "Español", "Spanish", "🇪🇸", "Spain & Latin America"),
      AppLanguage("fr", "Français", "French", "🇫🇷", "France & International"),
      AppLanguage("de", "Deutsch", "German", "🇩🇪", "Germany & Austria"),
      AppLanguage("ja", "日本語", "Japanese", "🇯🇵", "Japan"),
      AppLanguage("ko", "한국어", "Korean", "🇰🇷", "South Korea"),
      AppLanguage("zh", "简体中文", "Chinese (Simplified)", "🇨🇳", "China"),
      AppLanguage("pt", "Português", "Portuguese", "🇧🇷", "Brazil & Portugal"),
      AppLanguage("id", "Bahasa Indonesia", "Indonesian", "🇮🇩", "Indonesia"),
      AppLanguage("hi", "हिन्दी", "Hindi", "🇮🇳", "India"),
      AppLanguage("it", "Italiano", "Italian", "🇮🇹", "Italy"),
      AppLanguage("ru", "Русский", "Russian", "🇷🇺", "Eastern Europe"),
      AppLanguage("tr", "Türkçe", "Turkish", "🇹🇷", "Turkey"),
      AppLanguage("ar", "العربية", "Arabic", "🇸🇦", "Middle East"),
      AppLanguage("vi", "Tiếng Việt", "Vietnamese", "🇻🇳", "Vietnam")
    )
  }

  var searchFilter by remember { mutableStateOf("") }

  val filteredLanguages = remember(searchFilter, allLanguages) {
    if (searchFilter.isBlank()) allLanguages
    else allLanguages.filter {
      it.nativeName.contains(searchFilter, ignoreCase = true) ||
        it.englishName.contains(searchFilter, ignoreCase = true) ||
        it.code.contains(searchFilter, ignoreCase = true)
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBg)
      .statusBarsPadding()
      .testTag("languages_screen"),
    contentPadding = PaddingValues(bottom = 100.dp)
  ) {
    // 1. Header Section
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        // Badge
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = OrangeDim,
          border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.Public,
              contentDescription = null,
              tint = OrangePrimary,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = strings.languagesBadge,
              color = OrangePrimary,
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.8.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = strings.languagesTitle,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Black,
            fontSize = 26.sp,
            letterSpacing = (-0.5).sp
          ),
          color = TextMain
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = strings.languagesSubtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextMuted,
          fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Input
        OutlinedTextField(
          value = searchFilter,
          onValueChange = { searchFilter = it },
          modifier = Modifier.fillMaxWidth(),
          placeholder = {
            Text(
              text = strings.searchLanguagesPlaceholder,
              color = TextSubtle,
              fontSize = 13.5.sp
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search",
              tint = OrangePrimary
            )
          },
          trailingIcon = {
            if (searchFilter.isNotEmpty()) {
              IconButton(onClick = { searchFilter = "" }) {
                Icon(
                  imageVector = Icons.Filled.Clear,
                  contentDescription = "Clear",
                  tint = TextMuted,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = OrangePrimary,
            unfocusedBorderColor = DarkCardBorder,
            focusedContainerColor = DarkCardBg,
            unfocusedContainerColor = DarkCardBg,
            cursorColor = OrangePrimary
          )
        )

        Spacer(modifier = Modifier.height(12.dp))
      }
    }

    // 2. Languages List
    items(filteredLanguages) { language ->
      val isSelected = language.code == currentLanguageCode

      Surface(
        onClick = { onLanguageSelected(language) },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) OrangeDim.copy(alpha = 0.35f) else DarkCardBg,
        border = BorderStroke(
          1.dp,
          if (isSelected) OrangePrimary else DarkCardBorder
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 5.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            // Flag Box
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkCardElevated),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = language.flagEmoji,
                fontSize = 22.sp
              )
            }

            Column {
              Text(
                text = language.nativeName,
                color = if (isSelected) OrangePrimary else TextMain,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "${language.englishName} • ${language.region}",
                color = TextSubtle,
                fontSize = 12.sp
              )
            }
          }

          if (isSelected) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(OrangePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Selected",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }
  }
}
