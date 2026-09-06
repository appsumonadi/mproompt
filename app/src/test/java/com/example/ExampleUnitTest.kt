package com.example

import com.example.data.model.PromptCategory
import com.example.ui.i18n.AppStrings
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests verifying internationalization, sound engine, and critical models.
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun appStrings_allLanguagesLoadWithoutVerifyError() {
    val languages = listOf(
      "en", "ko", "ja", "zh", "es", "fr", "de", "pt", "it", "ru", "id", "hi", "tr", "ar", "vi"
    )

    for (code in languages) {
      val strings = AppStrings.forLanguage(code)
      assertNotNull("Language $code strings must not be null", strings)
      assertTrue("Language $code code must be non-empty", strings.languageCode.isNotEmpty())
      assertTrue("Language $code name must be non-empty", strings.languageName.isNotEmpty())
      assertTrue("Language $code navDiscover must be non-empty", strings.navDiscover.isNotEmpty())
      assertTrue("Language $code adminEnableAds must be non-empty", strings.adminEnableAds.isNotEmpty())
      assertTrue("Language $code watermarkSub must be non-empty", strings.watermarkSub.isNotEmpty())
      assertTrue("Language $code splashSoundOn must be non-empty", strings.splashSoundOn.isNotEmpty())
      val catTitle = strings.getCategoryTitle(PromptCategory.ANIME)
      assertTrue("Category title must be non-empty for $code", catTitle.isNotEmpty())
    }
  }
}

