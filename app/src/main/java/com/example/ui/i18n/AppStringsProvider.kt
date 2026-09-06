package com.example.ui.i18n

object AppStrings {
  val English = EnglishStrings
  val Korean = KoreanStrings
  val Japanese = JapaneseStrings
  val Chinese = ChineseStrings
  val Spanish = SpanishStrings
  val French = FrenchStrings
  val German = GermanStrings
  val Portuguese = PortugueseStrings
  val Italian = ItalianStrings
  val Russian = RussianStrings
  val Indonesian = IndonesianStrings
  val Hindi = HindiStrings
  val Turkish = TurkishStrings
  val Arabic = ArabicStrings
  val Vietnamese = VietnameseStrings

  fun forLanguage(code: String): AppLanguageStrings {
    return when (code.lowercase()) {
      "ko" -> Korean
      "ja" -> Japanese
      "zh" -> Chinese
      "es" -> Spanish
      "fr" -> French
      "de" -> German
      "pt" -> Portuguese
      "it" -> Italian
      "ru" -> Russian
      "id" -> Indonesian
      "hi" -> Hindi
      "tr" -> Turkish
      "ar" -> Arabic
      "vi" -> Vietnamese
      else -> English
    }
  }
}
