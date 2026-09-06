package com.example.ui.i18n

import com.example.data.model.PromptItem
import com.example.data.model.PromptVariable

/**
 * High-performance Prompt Localization Engine for promptly.
 * Translates prompt titles, descriptions, variable labels, and suggestions across all 15 supported languages.
 */
data class LocalizedPromptData(
  val title: String,
  val description: String,
  val variableLabels: Map<String, String> = emptyMap()
)

object PromptLocalizer {

  private val promptTranslations: Map<String, Map<String, LocalizedPromptData>> = mapOf(
    "prmt_soccer_throne" to mapOf(
      "zh" to LocalizedPromptData(
        title = "照片转足球王座高级体育人物海报AI提示词",
        description = "使用这款AI提示词，将上传照片转换为足球王座高级体育人物海报，呈现戏剧性体育场光效与震撼视觉质感。",
        variableLabels = mapOf("subject" to "运动员姿势与球王气场", "lighting" to "球场灯光与胜利烟火")
      ),
      "en" to LocalizedPromptData(
        title = "Photo to Soccer Throne Sports Poster AI Prompt",
        description = "Transform uploaded photos into an epic soccer throne luxury sports character poster with dramatic arena lighting and trophy majesty.",
        variableLabels = mapOf("subject" to "Athlete Pose", "lighting" to "Stadium Atmosphere")
      ),
      "es" to LocalizedPromptData(
        title = "Foto a Póster Deportivo del Trono de Fútbol IA",
        description = "Convierte fotos subidas en un épico póster deportivo de lujo sobre el trono del fútbol con iluminación dramática de estadio.",
        variableLabels = mapOf("subject" to "Pose del Atleta", "lighting" to "Atmósfera del Estadio")
      ),
      "fr" to LocalizedPromptData(
        title = "Photo vers Affiche Sportive Trône du Football IA",
        description = "Transformez des photos en une affiche sportive luxueuse sur le trône du football avec éclairage dramatique de stade.",
        variableLabels = mapOf("subject" to "Posture de l'Athlète", "lighting" to "Ambiance du Stade")
      ),
      "de" to LocalizedPromptData(
        title = "Foto zu Fußball-Thron Sport-Poster KI",
        description = "Verwandelt hochgeladene Fotos in ein episches Luxus-Sport-Charakterposter auf dem Fußballthron mit dramatischer Stadionbeleuchtung.",
        variableLabels = mapOf("subject" to "Athleten-Pose", "lighting" to "Stadion-Atmosphäre")
      ),
      "ja" to LocalizedPromptData(
        title = "写真からサッカーの玉座スポーツポスターAIプロンプト",
        description = "アップロード写真をドラマチックなスタジアム照明とトロフィーの威厳を備えた豪華なサッカー王座スポーツポスターに変換します。",
        variableLabels = mapOf("subject" to "アスリートのポーズ", "lighting" to "スタジアムの雰囲気")
      ),
      "ko" to LocalizedPromptData(
        title = "사진을 축구 왕좌 스포츠 포스터로 변환하는 AI 프롬프트",
        description = "업로드한 사진을 드라마틱한 경기장 조명과 트로피의 위엄이 돋보이는 축구 왕좌 스포츠 포스터로 변환합니다.",
        variableLabels = mapOf("subject" to "선수 포즈", "lighting" to "경기장 분위기")
      ),
      "ar" to LocalizedPromptData(
        title = "تحويل الصورة إلى ملصق عرش كرة القدم الرياضي بالذكاء الاصطناعي",
        description = "حوّل الصور المرفوعة إلى ملصق رياضي فخم على عرش كرة القدم مع إضاءة استوديو سينمائية دراماتيكية في الملعب.",
        variableLabels = mapOf("subject" to "وضعية الرياضي", "lighting" to "أجواء الملعب")
      ),
      "ru" to LocalizedPromptData(
        title = "Фото в спортивный постер «Футбольный трон»",
        description = "Превращает загруженные фото в эпический премиальный постер спортсмена на троне с эффектным освещением стадиона.",
        variableLabels = mapOf("subject" to "Поза спортсмена", "lighting" to "Атмосфера стадиона")
      ),
      "pt" to LocalizedPromptData(
        title = "Foto para Pôster Esportivo Trono do Futebol IA",
        description = "Transforme fotos enviadas em um pôster esportivo cinematográfico no trono do futebol com iluminação de estádio imersiva.",
        variableLabels = mapOf("subject" to "Pose do Atleta", "lighting" to "Atmosfera do Estádio")
      ),
      "it" to LocalizedPromptData(
        title = "Foto in Poster Sportivo Trono del Calcio IA",
        description = "Trasforma le foto caricate in un epico poster sportivo di lusso sul trono del calcio con luci drammatiche da stadio.",
        variableLabels = mapOf("subject" to "Posa dell'Atleta", "lighting" to "Atmosfera dello Stadio")
      ),
      "id" to LocalizedPromptData(
        title = "Foto ke Poster Olahraga Singgasana Sepak Bola AI",
        description = "Ubah foto yang diunggah menjadi poster karakter olahraga mewah di atas takhta sepak bola dengan pencahayaan stadion dramatis.",
        variableLabels = mapOf("subject" to "Pose Atlet", "lighting" to "Atmosfer Stadion")
      ),
      "hi" to LocalizedPromptData(
        title = "फ़ोटो से फ़ुटबॉल सिंहासन स्पोर्ट्स पोस्टर AI प्रॉम्प्ट",
        description = "अपलोड की गई तस्वीरों को नाटकीय स्टेडियम प्रकाश और ट्रॉफी की महिमा के साथ फुटबॉल सिंहासन पोस्टर में बदलें।",
        variableLabels = mapOf("subject" to "एथलीट पोज़", "lighting" to "स्टेडियम का माहौल")
      ),
      "tr" to LocalizedPromptData(
        title = "Fotoğraftan Futbol Tahtı Spor Posteri Yapay Zeka İstemi",
        description = "Yüklenen fotoğrafları dramatik stadyum ışıkları ve kupa görkemiyle epik bir futbol tahtı spor posterine dönüştürür.",
        variableLabels = mapOf("subject" to "Sporcu Duruşu", "lighting" to "Stadyum Atmosferi")
      ),
      "vi" to LocalizedPromptData(
        title = "Ảnh thành áp phích thể thao Ngai vàng Bóng đá AI",
        description = "Chuyển ảnh tải lên thành áp phích nhân vật thể thao sang trọng trên ngai vàng bóng đá với ánh sáng sân vận động ấn tượng.",
        variableLabels = mapOf("subject" to "Tư thế vận động viên", "lighting" to "Không khí sân vận động")
      )
    ),

    "prmt_shutter_motion_blur" to mapOf(
      "zh" to LocalizedPromptData(
        title = "照片转橙蓝快门拖影时尚人物肖像AI提示词",
        description = "使用这款AI提示词，将上传照片转换为橙蓝快门拖影时尚人物肖像，营造动感双色慢速快门光轨效果。",
        variableLabels = mapOf("subject" to "时尚模特与造型", "lighting" to "光轨与霓虹双色调")
      ),
      "en" to LocalizedPromptData(
        title = "Photo to Orange & Blue Shutter Motion Blur Portrait AI Prompt",
        description = "Transform uploaded portraits into high-fashion dual-tone shutter drag portraits with luminous orange and blue light trails.",
        variableLabels = mapOf("subject" to "Fashion Model Subject", "lighting" to "Light Trail Grading")
      ),
      "es" to LocalizedPromptData(
        title = "Foto a Retrato de Moda con Desenfoque de Obturación Naranja y Azul IA",
        description = "Convierte retratos en fotografías de alta costura con efecto de arrastre de obturador y estelas luminosas azul y naranja.",
        variableLabels = mapOf("subject" to "Modelo de Moda", "lighting" to "Gradación de Estelas de Luz")
      ),
      "fr" to LocalizedPromptData(
        title = "Photo vers Portrait de Mode Flou de Bougé Orange et Bleu IA",
        description = "Transformez des portraits en photos haute couture avec effet de traînée d'obturateur et traînées de lumière orange et bleue.",
        variableLabels = mapOf("subject" to "Modèle de Mode", "lighting" to "Traînées de Lumière")
      ),
      "de" to LocalizedPromptData(
        title = "Foto zu Orange & Blau Verschlussunschärfe Modeporträt KI",
        description = "Verwandelt Porträts in High-Fashion-Aufnahmen mit Verschlussverzögerung und leuchtenden orange-blauen Lichtspuren.",
        variableLabels = mapOf("subject" to "Fotomodell", "lighting" to "Lichtspur-Farbgebung")
      ),
      "ja" to LocalizedPromptData(
        title = "写真からオレンジ＆ブルースローシャッター軌跡ポートレートAIプロンプト",
        description = "アップロードされたポートレートを、鮮やかなオレンジとブルーの光の軌跡を纏ったハイファッションなスローシャッター写真に変換します。",
        variableLabels = mapOf("subject" to "ファッションモデル", "lighting" to "光の軌跡の配色")
      ),
      "ko" to LocalizedPromptData(
        title = "사진을 오렌지 & 블루 셔터 드래그 패션 인물로 변환하는 AI 프롬프트",
        description = "업로드한 인물 사진을 오렌지와 블루의 선명한 빛 궤적이 어우러진 하이패션 슬로우 셔터 포트레이트로 변환합니다.",
        variableLabels = mapOf("subject" to "패션 모델 피사체", "lighting" to "빛 궤적 색조")
      ),
      "ar" to LocalizedPromptData(
        title = "تحويل الصورة إلى بورتريه أزياء بحركة الغالق البرتقالي والأزرق بالذكاء الاصطناعي",
        description = "حوّل الصور الشخصية إلى لقطات أزياء راقية مع تأثير سحب الغالق ومسارات ضوئية باللونين البرتقالي والأزرق.",
        variableLabels = mapOf("subject" to "عارض الأزياء", "lighting" to "تدرج مسار الضوء")
      ),
      "ru" to LocalizedPromptData(
        title = "Фото в модный портрет со смазом затвора в оранжево-синих тонах",
        description = "Превращает портреты в фэшн-снимки с эффектом длинной выдержки и яркими неоновыми световыми шлейфами.",
        variableLabels = mapOf("subject" to "Модель", "lighting" to "Цвет световых следов")
      ),
      "pt" to LocalizedPromptData(
        title = "Foto para Retrato de Moda com Rastro de Obturador Laranja e Azul IA",
        description = "Transforme retratos em fotos de alta costura com efeito de arrasto de obturador e rastros de luz laranja e azul.",
        variableLabels = mapOf("subject" to "Modelo de Moda", "lighting" to "Gradação de Rastros de Luz")
      ),
      "it" to LocalizedPromptData(
        title = "Foto in Ritratto Moda con Scia di Otturatore Arancione e Blu IA",
        description = "Trasforma i ritratti in scatti di alta moda con effetto scia di otturatore e scie luminose arancioni e blu.",
        variableLabels = mapOf("subject" to "Modello di Moda", "lighting" to "Gradazione Scie di Luce")
      ),
      "id" to LocalizedPromptData(
        title = "Foto ke Potret Fashion Motion Blur Shutter Oranye & Biru AI",
        description = "Ubah foto potret menjadi foto mode kelas atas dengan jejak cahaya oranye dan biru dinamis kecepatan rana lambat.",
        variableLabels = mapOf("subject" to "Subjek Model Fashion", "lighting" to "Gradasi Jejak Cahaya")
      ),
      "hi" to LocalizedPromptData(
        title = "फ़ोटो से ऑरेंज और ब्लू शटर मोशन ब्लर पोर्ट्रेट AI प्रॉम्प्ट",
        description = "अपलोड किए गए पोर्ट्रेट को चमकदार नारंगी और नीले प्रकाश ट्रेल्स के साथ उच्च-फैशन शटर ड्रैग पोर्ट्रेट में बदलें।",
        variableLabels = mapOf("subject" to "फ़ैशन मॉडल विषय", "lighting" to "लाइट ट्रेल ग्रेडिंग")
      ),
      "tr" to LocalizedPromptData(
        title = "Fotoğraftan Turuncu ve Mavi Deklanşör İzi Moda Portresi Yapay Zeka İstemi",
        description = "Yüklenen portreleri, turuncu ve mavi ışık izleriyle yüksek moda deklanşör sürükleme portrelerine dönüştürür.",
        variableLabels = mapOf("subject" to "Moda Modeli", "lighting" to "Işık İzi Tonlaması")
      ),
      "vi" to LocalizedPromptData(
        title = "Ảnh thành chân dung thời trang vệt sáng màn trập Cam & Xanh AI",
        description = "Chuyển ảnh chân dung thành ảnh thời trang cao cấp với hiệu ứng vệt sáng cam và xanh dương sống động.",
        variableLabels = mapOf("subject" to "Mẫu thời trang", "lighting" to "Màu sắc vệt sáng")
      )
    ),

    "prmt_ultra_realistic_portrait" to mapOf(
      "zh" to LocalizedPromptData(
        title = "8K 电影感超写实时尚人物肖像",
        description = "8K 特写高定时尚肖像，呈现真实皮肤质感与柔和柔光氛围。",
        variableLabels = mapOf("subject" to "模特角色设定", "lighting" to "摄影棚布光")
      ),
      "en" to LocalizedPromptData(
        title = "Ultra-Realistic Editorial Portrait",
        description = "Cinematic 8k close-up high-fashion editorial portrait with natural skin texture and soft ambient light.",
        variableLabels = mapOf("subject" to "Model Persona", "lighting" to "Studio Light Setup")
      ),
      "es" to LocalizedPromptData(
        title = "Retrato Editorial Ultrarrealista 8K",
        description = "Primer plano cinematográfico de alta costura con textura natural de la piel e iluminación suave.",
        variableLabels = mapOf("subject" to "Personaje del Modelo", "lighting" to "Iluminación de Estudio")
      ),
      "fr" to LocalizedPromptData(
        title = "Portrait Éditorial Ultra-Réaliste 8K",
        description = "Gros plan cinématographique haute couture avec texture naturelle de peau et éclairage doux.",
        variableLabels = mapOf("subject" to "Personnage Modèle", "lighting" to "Éclairage Studio")
      ),
      "de" to LocalizedPromptData(
        title = "Ultrarealistisches Editorial-Porträt 8K",
        description = "Filmische 8K-Nahaufnahme für High-Fashion mit natürlicher Hautstruktur und sanftem Licht.",
        variableLabels = mapOf("subject" to "Model-Persona", "lighting" to "Studio-Lichtaufbau")
      ),
      "ja" to LocalizedPromptData(
        title = "8Kシネマティック超高画質エディトリアルポートレート",
        description = "自然な肌の質感と柔らかなライティングによる8Kシネマティックポートレート。",
        variableLabels = mapOf("subject" to "モデルのペルソナ", "lighting" to "スタジオライティング")
      ),
      "ko" to LocalizedPromptData(
        title = "8K 시네마틱 초현실주의 인물 에디토리얼",
        description = "자연스러운 피부 질감과 부드러운 스튜디오 조명의 8K 초고화질 패션 포트레이트.",
        variableLabels = mapOf("subject" to "모델 페르소나", "lighting" to "스튜디오 조명 설정")
      ),
      "ar" to LocalizedPromptData(
        title = "بورتريه سينمائي واقعي فائق الدقة 8K",
        description = "لقطة مقربة سينمائية راقية مع ملمس طبيعي للبشرة وإضاءة استوديو ناعمة.",
        variableLabels = mapOf("subject" to "شخصية العارض", "lighting" to "إضاءة الاستوديو")
      ),
      "ru" to LocalizedPromptData(
        title = "Ультрареалистичный студийный портрет 8K",
        description = "Кинематографичный крупный план высокой моды с естественной текстурой кожи и мягким светом.",
        variableLabels = mapOf("subject" to "Образ модели", "lighting" to "Студийный свет")
      ),
      "pt" to LocalizedPromptData(
        title = "Retrato Editorial Ultrarrealista 8K",
        description = "Close-up cinematográfico de alta moda com textura natural de pele e luz suave.",
        variableLabels = mapOf("subject" to "Perfil do Modelo", "lighting" to "Iluminação de Estúdio")
      ),
      "it" to LocalizedPromptData(
        title = "Ritratto Editoriale Ultra-Realistico 8K",
        description = "Primo piano cinematografico di alta moda con texture naturale della pelle e luce morbida.",
        variableLabels = mapOf("subject" to "Persona Modello", "lighting" to "Illuminazione Studio")
      ),
      "id" to LocalizedPromptData(
        title = "Potret Editorial Sangat Realistis 8K",
        description = "Close-up sinematik haute-couture dengan tekstur kulit alami dan pencahayaan studio lembut.",
        variableLabels = mapOf("subject" to "Persona Model", "lighting" to "Pencahayaan Studio")
      ),
      "hi" to LocalizedPromptData(
        title = "अल्ट्रा-रियलिस्टिक एडिटोरियल पोर्ट्रेट 8K",
        description = "प्राकृतिक त्वचा बनावट और नरम प्रकाश के साथ उच्च फैशन सिनेमाई क्लोज-अप पोर्ट्रेट।",
        variableLabels = mapOf("subject" to "मॉडल व्यक्तित्व", "lighting" to "स्टूडियो प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "Ultra Gerçekçi Editoryal Portre 8K",
        description = "Doğal cilt dokusu ve yumuşak stüdyo ışığıyla sinematik yüksek moda portresi.",
        variableLabels = mapOf("subject" to "Model Kişiliği", "lighting" to "Stüdyo Işık Düzeni")
      ),
      "vi" to LocalizedPromptData(
        title = "Chân dung thời trang siêu thực 8K",
        description = "Ảnh cận cảnh điện ảnh thời trang cao cấp với kết cấu da tự nhiên và ánh sáng mềm mại.",
        variableLabels = mapOf("subject" to "Hình tượng người mẫu", "lighting" to "Ánh sáng phòng thu")
      )
    ),

    "prmt_gta6_style_heist" to mapOf(
      "zh" to LocalizedPromptData(
        title = "GTA 6 迈阿密霓虹劫案动作大片",
        description = "受 GTA 6 迈阿密霓虹日落启发的动感高对比度街头动作摄影。",
        variableLabels = mapOf("subject" to "角色与装束", "lighting" to "夕阳与霓虹色调")
      ),
      "en" to LocalizedPromptData(
        title = "GTA 6-Style Masked Heist",
        description = "Vibrant high-contrast action shot inspired by GTA 6 neon Miami sunset with dynamic perspective.",
        variableLabels = mapOf("subject" to "Character & Outfit", "lighting" to "Sun & Neon Grading")
      ),
      "es" to LocalizedPromptData(
        title = "Atraco Enmascarado Estilo GTA 6",
        description = "Toma de acción vibrante inspirada en el atardecer de neón de GTA 6 en Miami.",
        variableLabels = mapOf("subject" to "Personaje y Atuendo", "lighting" to "Iluminación de Neón")
      ),
      "fr" to LocalizedPromptData(
        title = "Braquage Masqué Style GTA 6",
        description = "Plan d'action dynamique inspiré par le coucher de soleil néon de Miami dans GTA 6.",
        variableLabels = mapOf("subject" to "Personnage & Tenue", "lighting" to "Ambiance Coucher de Soleil")
      ),
      "de" to LocalizedPromptData(
        title = "Maskierter Überfall im GTA 6-Stil",
        description = "Dynamische Action-Aufnahme inspiriert von Miami-Neon-Sonnenuntergängen à la GTA 6.",
        variableLabels = mapOf("subject" to "Charakter & Outfit", "lighting" to "Sonnen- & Neon-Farbkorrektur")
      ),
      "ja" to LocalizedPromptData(
        title = "GTA 6風 マスク強盗アクションショット",
        description = "GTA 6のネオン輝くマイアミの夕暮れに着想を得た高コントラストアクション。",
        variableLabels = mapOf("subject" to "キャラクターと衣装", "lighting" to "夕日とネオンカラー")
      ),
      "ko" to LocalizedPromptData(
        title = "GTA 6 스타일 복면 액션 샷",
        description = "GTA 6 마이애미 네온 일몰에서 영감을 얻은 역동적인 고대비 액션 촬영.",
        variableLabels = mapOf("subject" to "캐릭터 및 의상", "lighting" to "석양 및 네온 색조")
      ),
      "ar" to LocalizedPromptData(
        title = "سطو مقنع بأسلوب GTA 6",
        description = "لقطة حركة سينمائية مفعمة بالحيوية مستوحاة من غروب ميامي في لعبة GTA 6.",
        variableLabels = mapOf("subject" to "الشخصية والزي", "lighting" to "ألوان الشمس والنيون")
      ),
      "ru" to LocalizedPromptData(
        title = "Ограбление в стиле GTA 6 в неоновом Майами",
        description = "Динамичный снимок с высоким контрастом в стиле неонового заката GTA 6.",
        variableLabels = mapOf("subject" to "Персонаж и экипировка", "lighting" to "Закат и неон")
      ),
      "pt" to LocalizedPromptData(
        title = "Assalto Mascarado Estilo GTA 6",
        description = "Cena de ação vibrante de alto contraste inspirada no pôr do sol neon de Miami no GTA 6.",
        variableLabels = mapOf("subject" to "Personagem e Traje", "lighting" to "Pôr do Sol e Neon")
      ),
      "it" to LocalizedPromptData(
        title = "Rapina Mascherata Stile GTA 6",
        description = "Scatto d'azione vibrante ispirato al tramonto al neon di Miami in stile GTA 6.",
        variableLabels = mapOf("subject" to "Personaggio e Costume", "lighting" to "Tramonto e Neon")
      ),
      "id" to LocalizedPromptData(
        title = "Aksi Perampokan Bergaya GTA 6",
        description = "Bidikan aksi kontras tinggi terinspirasi oleh senja neon Miami ala GTA 6.",
        variableLabels = mapOf("subject" to "Karakter & Pakaian", "lighting" to "Gradasi Senja & Neon")
      ),
      "hi" to LocalizedPromptData(
        title = "GTA 6 शैली मुखौटा डकैती एक्शन शॉट",
        description = "मियामी सूर्यास्त से प्रेरित जीवंत उच्च-विपरीत एक्शन फोटोग्राफी।",
        variableLabels = mapOf("subject" to "चरित्र और पोशाक", "lighting" to "सूर्य और नियॉन प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "GTA 6 Tarzı Maskeli Soygun Aksiyonu",
        description = "GTA 6 Miami neon gün batımından ilham alan canlı yüksek kontrastlı aksiyon çekimi.",
        variableLabels = mapOf("subject" to "Karakter ve Kıyafet", "lighting" to "Gün Batımı ve Neon")
      ),
      "vi" to LocalizedPromptData(
        title = "Vụ cướp đeo mặt nạ phong cách GTA 6",
        description = "Bức ảnh hành động tương phản cao lấy cảm hứng từ hoàng hôn neon Miami kiểu GTA 6.",
        variableLabels = mapOf("subject" to "Nhân vật & Trang phục", "lighting" to "Màu hoàng hôn & Neon")
      )
    ),

    "prmt_hypercar_concept" to mapOf(
      "zh" to LocalizedPromptData(
        title = "概念级碳纤维超级跑车公路追逐",
        description = "侵略性碳纤维原型超跑在落日余晖的沿海公路上极速飞驰。",
        variableLabels = mapOf("subject" to "超级跑车车型", "lighting" to "金色时刻自然光")
      ),
      "en" to LocalizedPromptData(
        title = "High-Realism Concept Hypercar",
        description = "Aggressive carbon-fiber prototype hypercar speeding across coastal highway at golden hour.",
        variableLabels = mapOf("subject" to "Hypercar Subject", "lighting" to "Golden Hour Lighting")
      ),
      "es" to LocalizedPromptData(
        title = "Hypercar Conceptual de Alto Realismo",
        description = "Prototipo agresivo de fibra de carbono a toda velocidad por la costa al atardecer.",
        variableLabels = mapOf("subject" to "Modelo Hypercar", "lighting" to "Luz de Hora Dorada")
      ),
      "fr" to LocalizedPromptData(
        title = "Hypercar Conceptuelle Haute Précision",
        description = "Prototype agressif en fibre de carbone filant sur l'autoroute côtière au coucher du soleil.",
        variableLabels = mapOf("subject" to "Sujet Hypercar", "lighting" to "Lumière Dorée")
      ),
      "de" to LocalizedPromptData(
        title = "Hochrealistisches Konzept-Hypercar",
        description = "Aggressiver Kohlefaser-Prototyp rast bei Sonnenuntergang über die Küstenstraße.",
        variableLabels = mapOf("subject" to "Hypercar-Modell", "lighting" to "Goldene-Stunde-Licht")
      ),
      "ja" to LocalizedPromptData(
        title = "超高精細コンセプト・ハイパーカー",
        description = "夕暮れの海岸ハイウェイを疾走するカーボンファイバー製プロトタイプ。",
        variableLabels = mapOf("subject" to "ハイパーカーの車種", "lighting" to "ゴールデンアワーの光")
      ),
      "ko" to LocalizedPromptData(
        title = "하이퍼리얼리즘 콘셉트 하이퍼카",
        description = "석양 속 해안 고속도로를 질주하는 공격적인 탄소 섬유 프로토타입 하이퍼카.",
        variableLabels = mapOf("subject" to "하이퍼카 모델", "lighting" to "골든 아워 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "سيارة خارقة نموذجية فائقة الواقعية",
        description = "نموذج أولي من ألياف الكربون ينطلق بسرعة على طريق ساحلي عند الغروب.",
        variableLabels = mapOf("subject" to "طراز السيارة", "lighting" to "إضاءة الساعة الذهبية")
      ),
      "ru" to LocalizedPromptData(
        title = "Концептуальный гиперкар высокой четкости",
        description = "Агрессивный прототип из углеволокна мчится по прибрежной трассе на закате.",
        variableLabels = mapOf("subject" to "Модель гиперкара", "lighting" to "Золотой час")
      ),
      "pt" to LocalizedPromptData(
        title = "Hipercarro Conceitual de Alto Realismo",
        description = "Protótipo agressivo de fibra de carbono em alta velocidade na estrada costeira.",
        variableLabels = mapOf("subject" to "Modelo do Hipercarro", "lighting" to "Luz da Hora Dourada")
      ),
      "it" to LocalizedPromptData(
        title = "Hypercar Concettuale ad Alto Realismo",
        description = "Prototipo aggressivo in fibra di carbonio ad alta velocità sull'autostrada costiera.",
        variableLabels = mapOf("subject" to "Modello Hypercar", "lighting" to "Luce dell'Ora d'Oro")
      ),
      "id" to LocalizedPromptData(
        title = "Mobil Super Konsep Sangat Realistis",
        description = "Prototipe serat karbon agresif melaju kencang di jalan raya pesisir saat senja.",
        variableLabels = mapOf("subject" to "Model Hypercar", "lighting" to "Pencahayaan Golden Hour")
      ),
      "hi" to LocalizedPromptData(
        title = "उच्च-यथार्थवादी कॉन्सेप्ट हाइपरकार",
        description = "गोल्डन आवर में तटीय राजमार्ग पर दौड़ता हुआ आक्रामक कार्बन-फाइबर हाइपरकार।",
        variableLabels = mapOf("subject" to "हाइपरकार विषय", "lighting" to "गोल्डन आवर लाइटिंग")
      ),
      "tr" to LocalizedPromptData(
        title = "Yüksek Gerçekçilikte Konsept Hiper Araba",
        description = "Gün batımında sahil yolunda hızla ilerleyen agresif karbon fiber prototip.",
        variableLabels = mapOf("subject" to "Hiper Araba Modeli", "lighting" to "Altın Saat Işığı")
      ),
      "vi" to LocalizedPromptData(
        title = "Siêu xe ý tưởng độ chân thực cao",
        description = "Nguyên mẫu sợi carbon hầm hố tăng tốc trên đường cao tốc ven biển lúc hoàng hôn.",
        variableLabels = mapOf("subject" to "Mẫu siêu xe", "lighting" to "Ánh sáng giờ vàng")
      )
    ),

    "prmt_3d_isometric_diorama" to mapOf(
      "zh" to LocalizedPromptData(
        title = "3D 等轴测悬浮和风庭院微缩世界",
        description = "Blender 3D 风格精美微缩场景，含发光樱花灯笼与锦鲤池塘。",
        variableLabels = mapOf("subject" to "场景主体构造", "lighting" to "氛围灯光环境")
      ),
      "en" to LocalizedPromptData(
        title = "3D Isometric Floating World",
        description = "Stylized 3D voxel diorama featuring a Japanese courtyard with glowing cherry blossom lanterns.",
        variableLabels = mapOf("subject" to "Diorama Core", "lighting" to "Ambient Lighting")
      ),
      "es" to LocalizedPromptData(
        title = "Mundo Flotante Isométrico 3D",
        description = "Diorama 3D estilizado con patio japonés y linternas brillantes de cerezo.",
        variableLabels = mapOf("subject" to "Núcleo del Diorama", "lighting" to "Iluminación Ambiental")
      ),
      "fr" to LocalizedPromptData(
        title = "Monde Flottant Isométrique 3D",
        description = "Diorama 3D stylisé avec cour japonaise et lanternes en fleurs de cerisier lumineuses.",
        variableLabels = mapOf("subject" to "Cœur du Diorama", "lighting" to "Lumière Ambiante")
      ),
      "de" to LocalizedPromptData(
        title = "3D Isometrische Schwebewelt",
        description = "Stilisiertes 3D-Diorama eines japanischen Innenhofs mit leuchtenden Kirschblüten-Laternen.",
        variableLabels = mapOf("subject" to "Diorama-Kern", "lighting" to "Umgebungsbeleuchtung")
      ),
      "ja" to LocalizedPromptData(
        title = "3Dアイソメトリック浮遊ミニチュア世界",
        description = "光る桜の灯籠と錦鯉の池がある和風の中庭を描いたスタイライズド3Dジオラマ。",
        variableLabels = mapOf("subject" to "ジオラマの構造", "lighting" to "アンビエントライティング")
      ),
      "ko" to LocalizedPromptData(
        title = "3D 등각 투영 플로팅 디오라마",
        description = "빛나는 벚꽃 등불과 잉어 연못이 있는 일본식 정원 3D 복셀 디오라마.",
        variableLabels = mapOf("subject" to "디오라마 핵심", "lighting" to "앰비언트 라이팅")
      ),
      "ar" to LocalizedPromptData(
        title = "عالم مجسم ثلاثي الأبعاد عائم",
        description = "ديوراما ثلاثية الأبعاد منمقة تضم فناءً يابانيًا مع فوانيس أزهار الكرز المتوهجة.",
        variableLabels = mapOf("subject" to "عناصر الديوراما", "lighting" to "الإضاءة المحيطة")
      ),
      "ru" to LocalizedPromptData(
        title = "3D-изометрический парящий мир",
        description = "Стилизованная 3D-диорама с японским двориком и светящимися фонариками сакуры.",
        variableLabels = mapOf("subject" to "Центр диорамы", "lighting" to "Окружающий свет")
      ),
      "pt" to LocalizedPromptData(
        title = "Mundo Flutuante Isométrico 3D",
        description = "Diorama 3D estilizado com pátio japonês e lanternas brilhantes de cerejeira.",
        variableLabels = mapOf("subject" to "Núcleo do Diorama", "lighting" to "Iluminação Ambiente")
      ),
      "it" to LocalizedPromptData(
        title = "Mondo Fluttuante Isometrico 3D",
        description = "Diorama 3D stilizzato di un cortile giapponese con lanterne di ciliegio luminose.",
        variableLabels = mapOf("subject" to "Nucleo del Diorama", "lighting" to "Luce Ambientale")
      ),
      "id" to LocalizedPromptData(
        title = "Diorama Mengapung Isometrik 3D",
        description = "Diorama 3D bergaya halaman Jepang dengan lentera bunga sakura yang bersinar.",
        variableLabels = mapOf("subject" to "Inti Diorama", "lighting" to "Pencahayaan Ambien")
      ),
      "hi" to LocalizedPromptData(
        title = "3D आइसोमेट्रिक तैरती दुनिया",
        description = "चमकती चेरी ब्लॉसम लालटेन और कोई तालाब के साथ जापानी आंगन 3D डायरैमा।",
        variableLabels = mapOf("subject" to "डायरैमा कोर", "lighting" to "परिवेश प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "3D İzometrik Yüzen Dünya",
        description = "Işıldayan kiraz çiçeği fenerleri ve koi göletli stilize 3D Japon bahçesi dioramas.",
        variableLabels = mapOf("subject" to "Diorama Temeli", "lighting" to "Ortam Işığı")
      ),
      "vi" to LocalizedPromptData(
        title = "Thế giới nổi đẳng cự 3D",
        description = "Mô hình 3D cách điệu gồm sân vườn Nhật Bản với đèn lồng hoa anh đào rực rỡ.",
        variableLabels = mapOf("subject" to "Trọng tâm mô hình", "lighting" to "Ánh sáng môi trường")
      )
    ),

    "prmt_cyber_samurai" to mapOf(
      "zh" to LocalizedPromptData(
        title = "新东京雨夜赛博朋克武士",
        description = "未来义体改造浪人漫步在雨水浸湿的霓虹小巷中。",
        variableLabels = mapOf("subject" to "赛博武士设定", "lighting" to "霓虹反光与雨雾")
      ),
      "en" to LocalizedPromptData(
        title = "Cyber Samurai in Neo-Tokyo",
        description = "Futuristic cybernetically enhanced ronin walking through a rain-soaked neon cyberpunk alleyway in Neo-Tokyo.",
        variableLabels = mapOf("subject" to "Cyber Ronin", "lighting" to "Neon Rain Atmosphere")
      ),
      "es" to LocalizedPromptData(
        title = "Samurái Cibernético en Neo-Tokio",
        description = "Ronin mejorado cibernéticamente caminando por un callejón de neón bajo la lluvia.",
        variableLabels = mapOf("subject" to "Ronin Cibernético", "lighting" to "Atmósfera de Lluvia Neón")
      ),
      "fr" to LocalizedPromptData(
        title = "Samouraï Cybernétique à Néo-Tokyo",
        description = "Ronin cybernétique marchant dans une ruelle néon sous une pluie battante.",
        variableLabels = mapOf("subject" to "Ronin Cybernétique", "lighting" to "Ambiance Pluie Néon")
      ),
      "de" to LocalizedPromptData(
        title = "Cyber-Samurai in Neo-Tokio",
        description = "Futuristischer Ronin wandert durch eine regennasse Neon-Gasse in Neo-Tokio.",
        variableLabels = mapOf("subject" to "Cyber-Ronin", "lighting" to "Neon-Regen-Atmosphäre")
      ),
      "ja" to LocalizedPromptData(
        title = "ネオ東京のサイバーサムライ",
        description = "雨に濡れたネオ東京の路地を歩くサイボーグ浪人のサイバーパンクアート。",
        variableLabels = mapOf("subject" to "サイバー浪人", "lighting" to "ネオンと雨の情景")
      ),
      "ko" to LocalizedPromptData(
        title = "네오 도쿄의 사이버 사무라이",
        description = "비에 젖은 네온 골목길을 걷는 사이보그 낭인의 미래지향적 아트워크.",
        variableLabels = mapOf("subject" to "사이버 로닌", "lighting" to "네온 빗길 분위기")
      ),
      "ar" to LocalizedPromptData(
        title = "ساموراي سايبربانك في نيو طوكيو",
        description = "رونين معزز سيبرانيًا يسير في زقاق نيون ممطر في نيو طوكيو.",
        variableLabels = mapOf("subject" to "الرونين السيبراني", "lighting" to "أجواء مطر النيون")
      ),
      "ru" to LocalizedPromptData(
        title = "Кибер-самурай в Нео-Токио",
        description = "Кибернетический ронин идет по залитому неоном дождливому переулку.",
        variableLabels = mapOf("subject" to "Кибер-ронин", "lighting" to "Неоновый дождь")
      ),
      "pt" to LocalizedPromptData(
        title = "Samurai Cibernético em Neo-Tóquio",
        description = "Ronin cibernético caminhando por um beco de neon sob chuva torrencial.",
        variableLabels = mapOf("subject" to "Ronin Cibernético", "lighting" to "Atmosfera de Chuva Neon")
      ),
      "it" to LocalizedPromptData(
        title = "Samurai Cibernetico a Neo-Tokyo",
        description = "Ronin cibernetico che cammina in un vicolo al neon bagnato dalla pioggia.",
        variableLabels = mapOf("subject" to "Ronin Cibernetico", "lighting" to "Atmosfera Pioggia Neon")
      ),
      "id" to LocalizedPromptData(
        title = "Samurai Siber di Neo-Tokyo",
        description = "Ronin siber masa depan melangkah di gang neon yang basah oleh hujan.",
        variableLabels = mapOf("subject" to "Ronin Siber", "lighting" to "Atmosfer Hujan Neon")
      ),
      "hi" to LocalizedPromptData(
        title = "नव-टोक्यो में साइबर समुराई",
        description = "बारिश से भीगी नियॉन गली में घूमता हुआ भविष्य का साइबरनेटिक रोनिन।",
        variableLabels = mapOf("subject" to "साइबर रोनिन", "lighting" to "नियॉन वर्षा माहौल")
      ),
      "tr" to LocalizedPromptData(
        title = "Neo-Tokyo'da Siber Samuray",
        description = "Yağmurla ıslanan neon sokakta yürüyen sibernetik ronin figürü.",
        variableLabels = mapOf("subject" to "Siber Ronin", "lighting" to "Neon Yağmur Atmosferi")
      ),
      "vi" to LocalizedPromptData(
        title = "Samurai mạng tại Neo-Tokyo",
        description = "Lãng khách samurai điều khiển học bước đi trong con hẻm neon mưa ướt.",
        variableLabels = mapOf("subject" to "Lãng khách Samurai", "lighting" to "Không khí mưa Neon")
      )
    ),

    "prmt_bioluminescent_panther" to mapOf(
      "zh" to LocalizedPromptData(
        title = "黑曜石之巅生物荧光雪豹伙伴",
        description = "黑曜石峰顶栖息的神话级赛博机械雪豹，体表布满发光青色回路。",
        variableLabels = mapOf("subject" to "神兽伙伴设定", "lighting" to "生物荧光光效")
      ),
      "en" to LocalizedPromptData(
        title = "Bioluminescent Wildlife Companion",
        description = "Mythical cybernetic snow leopard with glowing cyan bioluminescent circuit patterns resting on an obsidian peak.",
        variableLabels = mapOf("subject" to "Mythical Creature", "lighting" to "Bioluminescent Glow")
      ),
      "es" to LocalizedPromptData(
        title = "Compañero Salvaje Bioluminiscente",
        description = "Leopardo de las nieves mítico con circuitos cian brillantes en una cumbre de obsidiana.",
        variableLabels = mapOf("subject" to "Criatura Mítica", "lighting" to "Brillo Bioluminiscente")
      ),
      "fr" to LocalizedPromptData(
        title = "Compagnon Sauvage Bioluminescent",
        description = "Léopard des neiges mythique avec des circuits cyan luminescents sur un pic d'obsidienne.",
        variableLabels = mapOf("subject" to "Créature Mythique", "lighting" to "Éclat Bioluminescent")
      ),
      "de" to LocalizedPromptData(
        title = "Biolumineszenter Wildtier-Begleiter",
        description = "Mythischer Schneeleopard mit leuchtenden Cyan-Schaltkreisen auf einer Obsidian-Spitze.",
        variableLabels = mapOf("subject" to "Mythisches Wesen", "lighting" to "Biolumineszierendes Leuchten")
      ),
      "ja" to LocalizedPromptData(
        title = "生体発光サイバー雪豹の相棒",
        description = "黒曜石の頂で青く光る回路パターンを持つ神秘的なサイバーパンク雪豹。",
        variableLabels = mapOf("subject" to "神秘の生物", "lighting" to "生体発光の輝き")
      ),
      "ko" to LocalizedPromptData(
        title = "생체 발광 사이버 설표 동반자",
        description = "흑요석 봉우리 위에서 푸른빛 회로 패턴으로 빛나는 신비로운 사이버네틱 설표.",
        variableLabels = mapOf("subject" to "신화적 크리처", "lighting" to "생체 발광 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "رفيق بري متوهج حيوياً",
        description = "نمر ثلجي أسطوري مع دوائر سيان متوهجة يستريح على قمة من حجر السج.",
        variableLabels = mapOf("subject" to "المخلوق الأسطوري", "lighting" to "توهج ضوئي حيوي")
      ),
      "ru" to LocalizedPromptData(
        title = "Биолюминесцентный кибер-барс",
        description = "Мифический снежный барс со светящимися схемами на вершине обсидианового пика.",
        variableLabels = mapOf("subject" to "Мифический зверь", "lighting" to "Биолюминесцентное сияние")
      ),
      "pt" to LocalizedPromptData(
        title = "Companheiro Selvagem Bioluminescente",
        description = "Leopardo das neves mítico com circuitos ciano brilhantes em um pico de obsidiana.",
        variableLabels = mapOf("subject" to "Criatura Mítica", "lighting" to "Brilho Bioluminescente")
      ),
      "it" to LocalizedPromptData(
        title = "Compagno Selvaggio Bioluminescente",
        description = "Leopardo delle nevi mitico con circuiti ciano luminescenti su un picco di ossidiana.",
        variableLabels = mapOf("subject" to "Creatura Mitica", "lighting" to "Bagliore Bioluminescente")
      ),
      "id" to LocalizedPromptData(
        title = "Pendamping Hewan Liar Bioluminesens",
        description = "Macan tutul salju mistis dengan sirkuit sian bercahaya di puncak obsidian.",
        variableLabels = mapOf("subject" to "Makhluk Mitologis", "lighting" to "Pendaran Bioluminesens")
      ),
      "hi" to LocalizedPromptData(
        title = "बायोल्यूमिनसेंट वन्यजीव साथी",
        description = "ओब्सीडियन चोटी पर चमकते सियान सर्किट वाला पौराणिक साइबरनेटिक हिम तेंदुआ।",
        variableLabels = mapOf("subject" to "पौराणिक प्राणी", "lighting" to "जैव-दीप्तिमान चमक")
      ),
      "tr" to LocalizedPromptData(
        title = "Biyolüminesan Yabani Hayvan Yoldaşı",
        description = "Obsidiyen zirvede parıldayan camgöbeği devre desenli efsanevi kar leoparı.",
        variableLabels = mapOf("subject" to "Efsanevi Yaratık", "lighting" to "Biyolüminesan Parıltı")
      ),
      "vi" to LocalizedPromptData(
        title = "Bạn đồng hành hoang dã phát quang sinh học",
        description = "Báo tuyết cơ khí thần thoại với các mạch điện phát sáng màu lục lam trên đỉnh đá vỏ chai.",
        variableLabels = mapOf("subject" to "Sinh vật thần thoại", "lighting" to "Ánh sáng phát quang")
      )
    ),

    "prmt_luxury_timepiece" to mapOf(
      "zh" to LocalizedPromptData(
        title = "高级镂空陀飞轮腕表静物棚拍",
        description = "镂空陀飞轮奢华机械腕表的超精细微距棚拍商业广告效果图。",
        variableLabels = mapOf("subject" to "腕表型号与材质", "lighting" to "商业静物布光")
      ),
      "en" to LocalizedPromptData(
        title = "Luxury Timepiece Studio Render",
        description = "Hyper-detailed macro studio product commercial render of an open-worked tourbillon luxury wristwatch.",
        variableLabels = mapOf("subject" to "Timepiece Model", "lighting" to "Commercial Light")
      ),
      "es" to LocalizedPromptData(
        title = "Render de Estudio de Reloj de Lujo",
        description = "Render comercial hiperdetallado de un reloj de lujo con tourbillon esqueletizado.",
        variableLabels = mapOf("subject" to "Modelo de Reloj", "lighting" to "Luz de Estudio")
      ),
      "fr" to LocalizedPromptData(
        title = "Rendu Studio de Montre de Luxe",
        description = "Rendu commercial hyperdétaillé d'une montre de luxe à tourbillon squelette.",
        variableLabels = mapOf("subject" to "Modèle de Montre", "lighting" to "Éclairage Commercial")
      ),
      "de" to LocalizedPromptData(
        title = "Luxusuhr Studio-Rendering",
        description = "Hyperdetailliertes Makro-Rendering einer skelettierten Tourbillon-Luxusuhr.",
        variableLabels = mapOf("subject" to "Uhrenmodell", "lighting" to "Kommerzielles Studio-Licht")
      ),
      "ja" to LocalizedPromptData(
        title = "高級トゥールビヨン機械式腕時計スタジオ撮影",
        description = "スケルトン仕様のトゥールビヨン高級腕時計の超精密マクロスタジオレンダリング。",
        variableLabels = mapOf("subject" to "腕時計モデル", "lighting" to "商品広告ライティング")
      ),
      "ko" to LocalizedPromptData(
        title = "럭셔리 스켈레톤 투르비용 시계 스튜디오 렌더",
        description = "스켈레톤 투르비용 럭셔리 손목시계의 초정밀 매크로 상업용 렌더링.",
        variableLabels = mapOf("subject" to "시계 모델", "lighting" to "상업용 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "رندر استوديو لساعة فاخرة",
        description = "تصميم تجاري فائق الدقة لساعة يد فاخرة ذات توربيون مكشوف.",
        variableLabels = mapOf("subject" to "طراز الساعة", "lighting" to "إضاءة استوديو تجارية")
      ),
      "ru" to LocalizedPromptData(
        title = "Студийный рендер люксовых часов",
        description = "Гипердетализированный коммерческий макро-рендер часов с открытым турбийоном.",
        variableLabels = mapOf("subject" to "Модель часов", "lighting" to "Студийный коммерческий свет")
      ),
      "pt" to LocalizedPromptData(
        title = "Render de Estúdio de Relógio de Luxo",
        description = "Render comercial hiperdetalhado de um relógio de luxo com turbilhão esqueletizado.",
        variableLabels = mapOf("subject" to "Modelo do Relógio", "lighting" to "Iluminação Comercial")
      ),
      "it" to LocalizedPromptData(
        title = "Render da Studio per Orologio di Lusso",
        description = "Render commerciale macro iper-dettagliato di un orologio di lusso con tourbillon scheletrato.",
        variableLabels = mapOf("subject" to "Modello Orologio", "lighting" to "Luce Commerciale")
      ),
      "id" to LocalizedPromptData(
        title = "Render Studio Jam Tangan Mewah",
        description = "Render komersial makro jam tangan mewah dengan tourbillon terbuka yang sangat detail.",
        variableLabels = mapOf("subject" to "Model Jam Tangan", "lighting" to "Pencahayaan Komersial")
      ),
      "hi" to LocalizedPromptData(
        title = "लग्जरी कलाई घड़ी स्टूडियो रेंडर",
        description = "ओपन-वर्क्ड टूरबिलन लक्ज़री कलाई घड़ी का विस्तृत मैक्रो उत्पाद रेंडर।",
        variableLabels = mapOf("subject" to "घड़ी का मॉडल", "lighting" to "वाणिज्यिक प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "Lüks Kol Saati Stüdyo Renderı",
        description = "Açık mekanizmalı tourbillon lüks kol saatinin ultra detaylı makro ticari renderı.",
        variableLabels = mapOf("subject" to "Saat Modeli", "lighting" to "Ticari Işık Düzeni")
      ),
      "vi" to LocalizedPromptData(
        title = "Bản kết xuất đồng hồ sang trọng",
        description = "Bản dựng thương mại siêu chi tiết về đồng hồ đeo tay sang trọng có cơ cấu tourbillon lộ máy.",
        variableLabels = mapOf("subject" to "Mẫu đồng hồ", "lighting" to "Ánh sáng quảng cáo")
      )
    ),

    "prmt_surreal_hourglass" to mapOf(
      "zh" to LocalizedPromptData(
        title = "悬浮荒漠超现实时光水晶沙漏",
        description = "悬浮在外星黑曜石沙丘之上的巨大七彩水晶沙漏丰碑。",
        variableLabels = mapOf("subject" to "沙漏建筑主体", "lighting" to "外星天空光照")
      ),
      "en" to LocalizedPromptData(
        title = "Surreal Chrono-Spire in Floating Desert",
        description = "A massive iridescent crystalline hourglass monument suspended above an alien obsidian dune desert.",
        variableLabels = mapOf("subject" to "Chrono Monument", "lighting" to "Alien Sky Lighting")
      ),
      "es" to LocalizedPromptData(
        title = "Crono-Espira Surrealista en Desierto Flotante",
        description = "Monumento monumental de reloj de arena de cristal suspendido sobre dunas alienígenas.",
        variableLabels = mapOf("subject" to "Monumento Crono", "lighting" to "Cielo Alienígena")
      ),
      "fr" to LocalizedPromptData(
        title = "Chrono-Spire Surréaliste dans le Désert Flottant",
        description = "Sablier géant en cristal irisé suspendu au-dessus d'un désert de dunes extraterrestres.",
        variableLabels = mapOf("subject" to "Monument Chrono", "lighting" to "Lumière Extraterrestre")
      ),
      "de" to LocalizedPromptData(
        title = "Surreale Chrono-Spire in schwebender Wüste",
        description = "Riesiges schillerndes Kristall-Sanduhr-Monument schwebend über außerirdischen Dünen.",
        variableLabels = mapOf("subject" to "Chrono-Monument", "lighting" to "Außerirdisches Licht")
      ),
      "ja" to LocalizedPromptData(
        title = "浮遊砂漠の超現実的クロノ・スパイア",
        description = "異星の黒曜石砂漠の上に浮かぶ巨大な虹色クリスタル砂時計モニュメント。",
        variableLabels = mapOf("subject" to "クロノモニュメント", "lighting" to "異星の光")
      ),
      "ko" to LocalizedPromptData(
        title = "플로팅 사막의 초현실적 모래시계 첨탑",
        description = "외계 사막 모래언덕 위에 떠 있는 거대한 무지갯빛 수정 모래시계 조형물.",
        variableLabels = mapOf("subject" to "크로노 조형물", "lighting" to "외계 하늘 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "برج الساعة السريالي في الصحراء العائمة",
        description = "نصب تذكاري ضخم لساعة رملية بلورية معلقة فوق كثبان صحراوية غريبة.",
        variableLabels = mapOf("subject" to "النصب الزمني", "lighting" to "إضاءة سماء الكواكب")
      ),
      "ru" to LocalizedPromptData(
        title = "Сюрреалистичный хроно-шпиль в пустыне",
        description = "Огромные переливающиеся хрустальные песочные часы над инопланетной пустыней.",
        variableLabels = mapOf("subject" to "Хроно-монумент", "lighting" to "Инопланетное небо")
      ),
      "pt" to LocalizedPromptData(
        title = "Crono-Pináculo Surrealista em Deserto Flutuante",
        description = "Monumento colossal de ampulheta de cristal suspenso sobre dunas alienígenas.",
        variableLabels = mapOf("subject" to "Monumento Crono", "lighting" to "Luz do Céu Alienígena")
      ),
      "it" to LocalizedPromptData(
        title = "Crono-Guglia Surrealista nel Deserto Fluttuante",
        description = "Colossale clessidra di cristallo iridescente sospesa sopra dune aliene.",
        variableLabels = mapOf("subject" to "Monumento Crono", "lighting" to "Luce Cielo Alieno")
      ),
      "id" to LocalizedPromptData(
        title = "Monumen Jam Pasir Surealis di Gurun Melayang",
        description = "Monumen jam pasir kristal raksasa yang melayang di atas bukit pasir obsidian asing.",
        variableLabels = mapOf("subject" to "Monumen Waktu", "lighting" to "Cahaya Langit Asing")
      ),
      "hi" to LocalizedPromptData(
        title = "तैरते रेगिस्तान में असली क्रोनो-स्पायर",
        description = "विदेशी ओब्सीडियन टीलों के ऊपर लटका हुआ एक विशाल इंद्रधनुषी क्रिस्टलीय घंटाघर स्मारक।",
        variableLabels = mapOf("subject" to "समय स्मारक", "lighting" to "विदेशी आकाश प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "Yüzen Çölün Gerçeküstü Kum Saati",
        description = "Yabancı obsidiyen kum tepeleri üzerinde asılı duran devasa yanardöner kristal kum saati.",
        variableLabels = mapOf("subject" to "Zaman Anıtı", "lighting" to "Yabancı Gökyüzü Işığı")
      ),
      "vi" to LocalizedPromptData(
        title = "Tháp đồng hồ cát siêu thực giữa sa mạc nổi",
        description = "Đài tưởng niệm đồng hồ cát pha lê khổng lồ lơ lửng trên cồn cát đen ngoài hành tinh.",
        variableLabels = mapOf("subject" to "Đài tưởng niệm", "lighting" to "Bầu trời ngoài hành tinh")
      )
    ),

    "prmt_viral_landing_hook" to mapOf(
      "zh" to LocalizedPromptData(
        title = "高转化率爆款营销页面文案公式",
        description = "专为实现最高点击与订单转化率设计的顶级直接响应着陆页头条文案。",
        variableLabels = mapOf("product" to "推广产品或服务", "audience" to "目标受众定位")
      ),
      "en" to LocalizedPromptData(
        title = "High-Converting Viral Landing Hook",
        description = "Elite direct-response landing page hero copywriting formula engineered for maximum conversion rate.",
        variableLabels = mapOf("product" to "Product or Offer", "audience" to "Target Audience")
      ),
      "es" to LocalizedPromptData(
        title = "Gancho Viral de Alta Conversión para Landing Page",
        description = "Fórmula de copywriting de respuesta directa para la sección hero con máxima conversión.",
        variableLabels = mapOf("product" to "Producto u Oferta", "audience" to "Público Objetivo")
      ),
      "fr" to LocalizedPromptData(
        title = "Accroche Virale à Forte Conversion pour Landing Page",
        description = "Formule de copywriting à réponse directe pour un taux de conversion maximal.",
        variableLabels = mapOf("product" to "Produit ou Offre", "audience" to "Public Cible")
      ),
      "de" to LocalizedPromptData(
        title = "Hochkonvertierender viraler Landingpage-Hook",
        description = "Elite-Direktmarketing-Formel für die Hero-Section zur Maximierung der Konversionsrate.",
        variableLabels = mapOf("product" to "Produkt oder Angebot", "audience" to "Zielgruppe")
      ),
      "ja" to LocalizedPromptData(
        title = "高CVRバイラルLPヘッドラインコピー",
        description = "コンバージョン率を最大化するために設計されたダイレクトレスポンスLP文案公式。",
        variableLabels = mapOf("product" to "商品またはオファー", "audience" to "ターゲット層")
      ),
      "ko" to LocalizedPromptData(
        title = "고전환율 바이럴 랜딩페이지 헤드라인 카피",
        description = "전환율을 극대화하기 위해 엔지니어링된 엘리트 반응형 랜딩페이지 히어로 카피.",
        variableLabels = mapOf("product" to "제품 및 제안", "audience" to "타깃 고객")
      ),
      "ar" to LocalizedPromptData(
        title = "صيغة نصوص تسويقية سريعة الانتشار عالية التحويل",
        description = "صيغة احترافية لكتابة نصوص صفحات الهبوط لزيادة معدل التحويل إلى أقصى حد.",
        variableLabels = mapOf("product" to "المنتج أو العرض", "audience" to "الجمهور المستهدف")
      ),
      "ru" to LocalizedPromptData(
        title = "Вирусный хук для лендинга с высокой конверсией",
        description = "Элитная формула прямого отклика для максимальной конверсии лендинга.",
        variableLabels = mapOf("product" to "Продукт или оффер", "audience" to "Целевая аудитория")
      ),
      "pt" to LocalizedPromptData(
        title = "Gancho Viral de Alta Conversão para Landing Page",
        description = "Fórmula de copywriting de resposta direta para maximizar taxas de conversão.",
        variableLabels = mapOf("product" to "Produto ou Oferta", "audience" to "Público-Alvo")
      ),
      "it" to LocalizedPromptData(
        title = "Gancio Virale ad Alta Conversione per Landing Page",
        description = "Formula di copywriting a risposta diretta studiata per la massima conversione.",
        variableLabels = mapOf("product" to "Prodotto o Offerta", "audience" to "Pubblico Target")
      ),
      "id" to LocalizedPromptData(
        title = "Formula Copywriting Landing Page Viral Konversi Tinggi",
        description = "Formula copywriting landing page respons langsung untuk konversi penjualan maksimal.",
        variableLabels = mapOf("product" to "Produk atau Penawaran", "audience" to "Target Audiens")
      ),
      "hi" to LocalizedPromptData(
        title = "उच्च-रूपांतरण वायरल लैंडिंग हुक",
        description = "अधिकतम रूपांतरण दर के लिए तैयार किया गया विशिष्ट लैंडिंग पेज कॉपी राइटिंग फॉर्मूला।",
        variableLabels = mapOf("product" to "उत्पाद या प्रस्ताव", "audience" to "लक्षित दर्शक")
      ),
      "tr" to LocalizedPromptData(
        title = "Yüksek Dönüşümlü Viral Açılış Sayfası Başlığı",
        description = "Maksimum dönüşüm oranı için tasarlanmış seçkin doğrudan yanıt metin formülü.",
        variableLabels = mapOf("product" to "Ürün veya Teklif", "audience" to "Hedef Kitle")
      ),
      "vi" to LocalizedPromptData(
        title = "Câu móc hấp dẫn chuyển đổi cao cho trang đích",
        description = "Công thức viết lời chào hàng trang đích ưu tú được tối ưu hóa cho tỷ lệ chuyển đổi cao nhất.",
        variableLabels = mapOf("product" to "Sản phẩm hoặc Ưu đãi", "audience" to "Khán giả mục tiêu")
      )
    ),

    "prmt_clean_architecture_expert" to mapOf(
      "zh" to LocalizedPromptData(
        title = "Kotlin Jetpack Compose 清晰架构设计专家",
        description = "资深 Android 架构师提示词，用于构建严谨规范的生产级 Compose 应用。",
        variableLabels = mapOf("feature" to "功能模块名称", "layer" to "目标架构层级")
      ),
      "en" to LocalizedPromptData(
        title = "Clean Architecture Compose Architect",
        description = "Senior Android staff engineer prompt for structuring production Kotlin Jetpack Compose architecture.",
        variableLabels = mapOf("feature" to "Feature Module", "layer" to "Architectural Layer")
      ),
      "es" to LocalizedPromptData(
        title = "Arquitecto de Clean Architecture para Jetpack Compose",
        description = "Prompt de ingeniero senior para estructurar arquitectura de producción en Kotlin Compose.",
        variableLabels = mapOf("feature" to "Módulo de Función", "layer" to "Capa de Arquitectura")
      ),
      "fr" to LocalizedPromptData(
        title = "Architecte Clean Architecture Kotlin Jetpack Compose",
        description = "Prompt pour ingénieur Android senior structurant une architecture Kotlin Compose robuste.",
        variableLabels = mapOf("feature" to "Module Fonctionnel", "layer" to "Couche Architecturale")
      ),
      "de" to LocalizedPromptData(
        title = "Clean Architecture Compose Architekt",
        description = "Prompt für leitende Android-Entwickler zur Strukturierung von produktionsreifem Kotlin Compose.",
        variableLabels = mapOf("feature" to "Feature-Modul", "layer" to "Architektur-Ebene")
      ),
      "ja" to LocalizedPromptData(
        title = "Kotlin Jetpack Compose クリーンアーキテクチャ設計エキスパート",
        description = "実運用レベルのKotlin Jetpack Composeアーキテクチャを構築するためのシニアプロンプト。",
        variableLabels = mapOf("feature" to "機能モジュール", "layer" to "アーキテクチャ層")
      ),
      "ko" to LocalizedPromptData(
        title = "Kotlin Jetpack Compose 클린 아키텍처 설계 전문가",
        description = "프로덕션급 Kotlin Jetpack Compose 아키텍처 구축을 위한 시니어 엔지니어 프롬프트.",
        variableLabels = mapOf("feature" to "기능 모듈", "layer" to "아키텍처 계층")
      ),
      "ar" to LocalizedPromptData(
        title = "خبير البنية النظيفة لـ Kotlin Jetpack Compose",
        description = "موجه مخصص لمهندسي أندرويد لتصميم بنية تطبيقات حديثة وقابلة للتطوير.",
        variableLabels = mapOf("feature" to "وحدة الميزة", "layer" to "طبقة البنية")
      ),
      "ru" to LocalizedPromptData(
        title = "Архитектор Clean Architecture для Kotlin Jetpack Compose",
        description = "Промпт для старшего инженера Android по построению продакшен-архитектуры на Compose.",
        variableLabels = mapOf("feature" to "Модуль фичи", "layer" to "Слой архитектуры")
      ),
      "pt" to LocalizedPromptData(
        title = "Arquiteto de Clean Architecture Kotlin Jetpack Compose",
        description = "Prompt para engenheiro sênior estruturar arquitetura de produção em Kotlin Compose.",
        variableLabels = mapOf("feature" to "Módulo de Funcionalidade", "layer" to "Camada Arquitetural")
      ),
      "it" to LocalizedPromptData(
        title = "Architetto di Clean Architecture Kotlin Jetpack Compose",
        description = "Prompt per ingegneri Android senior per strutturare un'architettura Compose di produzione.",
        variableLabels = mapOf("feature" to "Modulo Funzionalità", "layer" to "Livello di Architettura")
      ),
      "id" to LocalizedPromptData(
        title = "Arsitek Clean Architecture Kotlin Jetpack Compose",
        description = "Prompt insinyur Android senior untuk menyusun arsitektur Kotlin Compose siap produksi.",
        variableLabels = mapOf("feature" to "Modul Fitur", "layer" to "Lapisan Arsitektur")
      ),
      "hi" to LocalizedPromptData(
        title = "क्लीन आर्किटेक्चर कंपोज़ आर्किटेक्ट",
        description = "उत्पादन कोटलिन जेटपैक कंपोज़ आर्किटेक्चर की संरचना के लिए वरिष्ठ इंजीनियर प्रॉम्प्ट।",
        variableLabels = mapOf("feature" to "फ़ीचर मॉड्यूल", "layer" to "वास्तुकला परत")
      ),
      "tr" to LocalizedPromptData(
        title = "Kotlin Jetpack Compose Temiz Mimari Uzmanı",
        description = "Üretime hazır Kotlin Jetpack Compose mimarisi oluşturmak için kıdemli mühendis istemi.",
        variableLabels = mapOf("feature" to "Özellik Modülü", "layer" to "Mimari Katman")
      ),
      "vi" to LocalizedPromptData(
        title = "Chuyên gia kiến trúc sạch Kotlin Jetpack Compose",
        description = "Lời nhắc kỹ sư Android cấp cao để cấu trúc kiến trúc Kotlin Jetpack Compose chuẩn sản xuất.",
        variableLabels = mapOf("feature" to "Mô-đun tính năng", "layer" to "Tầng kiến trúc")
      )
    ),

    "prmt_retro_3d_avatar" to mapOf(
      "zh" to LocalizedPromptData(
        title = "复古波普风 3D 黏土悬浮虚拟人像",
        description = "漂浮在柔和梦幻空间中的鲜艳 3D 粘土动画角色，佩戴复古全息太阳镜。",
        variableLabels = mapOf("subject" to "黏土角色设计", "lighting" to "梦幻柔光色调")
      ),
      "en" to LocalizedPromptData(
        title = "Retro-Pop Floating 3D Avatar",
        description = "Vibrant 3D claymation avatar floating in pastel space with retro holographic shades.",
        variableLabels = mapOf("subject" to "Clay Character", "lighting" to "Pastel Lighting")
      ),
      "es" to LocalizedPromptData(
        title = "Avatar 3D Flotante Retro-Pop",
        description = "Avatar de plastilina 3D flotando en el espacio con gafas holográficas retro.",
        variableLabels = mapOf("subject" to "Personaje de Plastilina", "lighting" to "Luz Pastel")
      ),
      "fr" to LocalizedPromptData(
        title = "Avatar 3D Flottant Rétro-Pop",
        description = "Avatar en pâte à modeler 3D flottant dans un espace pastel avec lunettes rétro.",
        variableLabels = mapOf("subject" to "Personnage en Pâte", "lighting" to "Lumière Pastel")
      ),
      "de" to LocalizedPromptData(
        title = "Retro-Pop Schwebender 3D-Avatar",
        description = "Lebendiger 3D-Knet-Avatar im Pastellraum mit holografischer Retro-Sonnenbrille.",
        variableLabels = mapOf("subject" to "Knet-Charakter", "lighting" to "Pastell-Licht")
      ),
      "ja" to LocalizedPromptData(
        title = "レトロポップ 3Dクレイ浮遊アバター",
        description = "パステルカラーの空間に浮かぶホログラフィックサングラスをかけた3D粘土アバター。",
        variableLabels = mapOf("subject" to "クレイキャラクター", "lighting" to "パステルライティング")
      ),
      "ko" to LocalizedPromptData(
        title = "레트로 팝 3D 클레이 플로팅 아바타",
        description = "파스텔톤 공간에 떠 있는 레트로 홀로그램 선글라스를 쓴 3D 클레이메이션 아바타.",
        variableLabels = mapOf("subject" to "클레이 캐릭터", "lighting" to "파스텔 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "أفاتار ثلاثي الأبعاد عائم بنمط ريترو بوب",
        description = "شخصية ثلاثية الأبعاد من الصلصال تطفو في فضاء باستيل مع نظارات ثلاثية الأبعاد.",
        variableLabels = mapOf("subject" to "شخصية الصلصال", "lighting" to "إضاءة باستيل")
      ),
      "ru" to LocalizedPromptData(
        title = "Ретро-поп летающий 3D-аватар",
        description = "Яркий глиняный 3D-аватар в пастельном пространстве с голографическими очками.",
        variableLabels = mapOf("subject" to "Глиняный персонаж", "lighting" to "Пастельный свет")
      ),
      "pt" to LocalizedPromptData(
        title = "Avatar 3D Flutuante Retro-Pop",
        description = "Avatar de massinha 3D vibrante flutuando no espaço pastel com óculos holográficos.",
        variableLabels = mapOf("subject" to "Personagem de Massinha", "lighting" to "Iluminação Pastel")
      ),
      "it" to LocalizedPromptData(
        title = "Avatar 3D Fluttuante Retro-Pop",
        description = "Vivace avatar 3D in plastilina che fluttua nello spazio con occhiali olografici retrò.",
        variableLabels = mapOf("subject" to "Personaggio Plastilina", "lighting" to "Luce Pastello")
      ),
      "id" to LocalizedPromptData(
        title = "Avatar 3D Mengapung Retro-Pop",
        description = "Avatar tanah liat 3D yang mengapung di ruang pastel dengan kacamata holografik retro.",
        variableLabels = mapOf("subject" to "Karakter Tanah Liat", "lighting" to "Pencahayaan Pastel")
      ),
      "hi" to LocalizedPromptData(
        title = "रेट्रो-पॉप फ्लोटिंग 3D अवतार",
        description = "रेट्रो होलोग्राफिक चश्मे के साथ अंतरिक्ष में तैरता हुआ जीवंत 3D अवतार।",
        variableLabels = mapOf("subject" to "मिट्टी का चरित्र", "lighting" to "पेस्टल प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "Retro-Pop Yüzen 3D Avatar",
        description = "Retro holografik gözlüklü pastel alanda yüzen canlı 3D killeme avatarı.",
        variableLabels = mapOf("subject" to "Kil Karakter", "lighting" to "Pastel Işık")
      ),
      "vi" to LocalizedPromptData(
        title = "Hình đại diện 3D nổi phong cách Retro-Pop",
        description = "Hình đại diện bằng đất sét 3D rực rỡ lơ lửng trong không gian phấn màu với kính ba chiều retro.",
        variableLabels = mapOf("subject" to "Nhân vật đất sét", "lighting" to "Ánh sáng phấn màu")
      )
    ),

    "prmt_creature_costume" to mapOf(
      "zh" to LocalizedPromptData(
        title = "奇幻生物手作服饰摄影大片",
        description = "穿着手工趣味奇幻生物服饰的人像摄影棚拍，充满童趣与艺术张力。",
        variableLabels = mapOf("subject" to "服饰造型", "lighting" to "摄影棚柔光")
      ),
      "en" to LocalizedPromptData(
        title = "2D Creature Costume Portrait",
        description = "Playful studio editorial photo of a subject in a handmade whimsical creature costume.",
        variableLabels = mapOf("subject" to "Costume Style", "lighting" to "Soft Studio Light")
      ),
      "es" to LocalizedPromptData(
        title = "Retrato con Traje de Criatura 2D",
        description = "Foto editorial de estudio de un sujeto con un disfraz artesanal de criatura fantástica.",
        variableLabels = mapOf("subject" to "Estilo de Disfraz", "lighting" to "Luz Suave de Estudio")
      ),
      "fr" to LocalizedPromptData(
        title = "Portrait en Costume de Créature 2D",
        description = "Photo éditoriale de studio d'un sujet dans un costume artisanal de créature féerique.",
        variableLabels = mapOf("subject" to "Style de Costume", "lighting" to "Lumière Douce Studio")
      ),
      "de" to LocalizedPromptData(
        title = "2D-Kreaturen-Kostümporträt",
        description = "Verspieltes Studio-Foto eines Subjekts in einem handgemachten Fabelwesen-Kostüm.",
        variableLabels = mapOf("subject" to "Kostümstil", "lighting" to "Sanftes Studio-Licht")
      ),
      "ja" to LocalizedPromptData(
        title = "ファンタジー生物コスチュームポートレート",
        description = "手作りの風変わりな生き物の衣装を着た被写体のスタジオエディトリアル写真。",
        variableLabels = mapOf("subject" to "衣装スタイル", "lighting" to "ソフトスタジオ光")
      ),
      "ko" to LocalizedPromptData(
        title = "판타지 크리처 수제 코스튬 포트레이트",
        description = "핸드메이드 크리처 코스튬을 착용한 피사체의 유쾌한 스튜디오 에디토리얼 사진.",
        variableLabels = mapOf("subject" to "코스튬 스타일", "lighting" to "소프트 스튜디오 조명")
      ),
      "ar" to LocalizedPromptData(
        title = "بورتريه لشخصية في زي كائن أسطوري",
        description = "صورة استوديو مرحة لشخص يرتدي زي مخلوق غريب مصنوع يدويًا.",
        variableLabels = mapOf("subject" to "نمط الزي", "lighting" to "إضاءة ناعمة")
      ),
      "ru" to LocalizedPromptData(
        title = "Портрет в костюме фантастического существа",
        description = "Студийное фото модели в забавном костюме волшебного создания ручной работы.",
        variableLabels = mapOf("subject" to "Стиль костюма", "lighting" to "Мягкий свет")
      ),
      "pt" to LocalizedPromptData(
        title = "Retrato em Traje de Criatura 2D",
        description = "Foto editorial de estúdio com traje artesanal de criatura fantástica.",
        variableLabels = mapOf("subject" to "Estilo de Fantasia", "lighting" to "Luz Suave")
      ),
      "it" to LocalizedPromptData(
        title = "Ritratto in Costume da Creatura 2D",
        description = "Foto editoriale in studio di un soggetto con un bizzarro costume da creatura artigianale.",
        variableLabels = mapOf("subject" to "Stile Costume", "lighting" to "Luce Morbida Studio")
      ),
      "id" to LocalizedPromptData(
        title = "Potret Kostum Makhluk Fantasi",
        description = "Foto studio editorial yang ceria dari subjek dalam kostum makhluk buatan tangan.",
        variableLabels = mapOf("subject" to "Gaya Kostum", "lighting" to "Pencahayaan Lembut")
      ),
      "hi" to LocalizedPromptData(
        title = "2D प्राणी पोशाक पोर्ट्रेट",
        description = "हाथ से बनी विचित्र प्राणी पोशाक में एक मॉडल का चंचल स्टूडियो फोटो।",
        variableLabels = mapOf("subject" to "पोशाक शैली", "lighting" to "नरम स्टूडियो प्रकाश")
      ),
      "tr" to LocalizedPromptData(
        title = "Yaratık Kostümlü Eğlenceli Portre",
        description = "El yapımı sevimli canavar kostümlü bir modelin eğlenceli stüdyo fotoğrafı.",
        variableLabels = mapOf("subject" to "Kostüm Tarzı", "lighting" to "Yumuşak Stüdyo Işığı")
      ),
      "vi" to LocalizedPromptData(
        title = "Chân dung trang phục sinh vật huyền ảo",
        description = "Ảnh thời trang trong phòng thu của nhân vật trong bộ trang phục sinh vật thủ công độc đáo.",
        variableLabels = mapOf("subject" to "Phong cách trang phục", "lighting" to "Ánh sáng studio mềm")
      )
    ),

    "prmt_minimalist_vector_badge" to mapOf(
      "zh" to LocalizedPromptData(
        title = "黄金比例极简主义品牌矢量徽标",
        description = "经典极简几何标志设计，融合黄金分割比例与瑞士排版美学。",
        variableLabels = mapOf("subject" to "图形徽标构想", "lighting" to "配色方案")
      ),
      "en" to LocalizedPromptData(
        title = "Clean Minimalist Brand Mark",
        description = "Timeless minimalist geometric logo mark design with golden ratio proportions and Swiss typography.",
        variableLabels = mapOf("subject" to "Emblem Concept", "lighting" to "Color Palette")
      ),
      "es" to LocalizedPromptData(
        title = "Marca de Identidad Minimalista y Limpia",
        description = "Diseño de logotipo geométrico minimalista con proporciones áureas y estilo suizo.",
        variableLabels = mapOf("subject" to "Concepto de Emblema", "lighting" to "Paleta de Color")
      ),
      "fr" to LocalizedPromptData(
        title = "Marque d'Identité Minimaliste Épurée",
        description = "Logo géométrique minimaliste intemporel aux proportions du nombre d'or et style suisse.",
        variableLabels = mapOf("subject" to "Concept d'Emblème", "lighting" to "Palette de Couleurs")
      ),
      "de" to LocalizedPromptData(
        title = "Klares minimalistisches Markenlogo",
        description = "Zeitloses minimalistisches geometrisches Logo mit Goldenem Schnitt und Schweizer Typografie.",
        variableLabels = mapOf("subject" to "Emblem-Konzept", "lighting" to "Farbpalette")
      ),
      "ja" to LocalizedPromptData(
        title = "黄金比 ミニマリストブランドロゴマーク",
        description = "黄金比のプロポーションとスイスタイポグラフィによるタイムレスな幾何学ロゴマーク。",
        variableLabels = mapOf("subject" to "シンボルマークの構想", "lighting" to "カラーパレット")
      ),
      "ko" to LocalizedPromptData(
        title = "황금비 미니멀리스트 브랜드 로고 마크",
        description = "황금비 비율과 스위스 타이포그래피 미학을 결합한 유행을 타지 않는 기하학적 로고.",
        variableLabels = mapOf("subject" to "엠블럼 콘셉트", "lighting" to "컬러 팔레트")
      ),
      "ar" to LocalizedPromptData(
        title = "شعار علامة تجارية بسيط وأنيق",
        description = "تصميم شعار هندسي بسيط وخالد مع نسب النسبة الذهبية والطباعة السويسرية.",
        variableLabels = mapOf("subject" to "مفهوم الشعار", "lighting" to "لوحة الألوان")
      ),
      "ru" to LocalizedPromptData(
        title = "Минималистичный логотип с золотым сечением",
        description = "Геометрический логотип в швейцарском стиле с идеальными пропорциями золотого сечения.",
        variableLabels = mapOf("subject" to "Концепция знака", "lighting" to "Цветовая гамма")
      ),
      "pt" to LocalizedPromptData(
        title = "Marca Minimalista e Limpa",
        description = "Design de logotipo geométrico minimalista e atemporal com proporção áurea e estilo suíço.",
        variableLabels = mapOf("subject" to "Conceito de Emblema", "lighting" to "Paleta de Cores")
      ),
      "it" to LocalizedPromptData(
        title = "Marchio di Brand Minimalista e Pulito",
        description = "Design di logo geometrico minimalista con proporzioni auree e tipografia svizzera.",
        variableLabels = mapOf("subject" to "Concetto Emblema", "lighting" to "Tavolozza Colori")
      ),
      "id" to LocalizedPromptData(
        title = "Logo Merek Minimalis Bersih",
        description = "Desain logo geometris minimalis abadi dengan proporsi rasio emas dan tipografi Swiss.",
        variableLabels = mapOf("subject" to "Konsep Lambang", "lighting" to "Palet Warna")
      ),
      "hi" to LocalizedPromptData(
        title = "स्वच्छ न्यूनतम ब्रांड मार्क",
        description = "स्वर्ण अनुपात अनुपात और स्विस टाइपोग्राफी के साथ कालातीत ज्यामितीय लोगो डिजाइन।",
        variableLabels = mapOf("subject" to "प्रतीक अवधारणा", "lighting" to "रंग पैलेट")
      ),
      "tr" to LocalizedPromptData(
        title = "Temiz Minimalist Marka Logosu",
        description = "Altın oran ve İsviçre tipografisine sahip zamansız minimalist geometrik marka amblemi.",
        variableLabels = mapOf("subject" to "Amblem Konsepti", "lighting" to "Renk Paleti")
      ),
      "vi" to LocalizedPromptData(
        title = "Biểu trưng thương hiệu tối giản thanh lịch",
        description = "Thiết kế logo hình học tối giản vượt thời gian với tỷ lệ vàng và kiểu chữ Thụy Sĩ.",
        variableLabels = mapOf("subject" to "Ý tưởng biểu trưng", "lighting" to "Bảng màu")
      )
    )
  )

  /**
   * Localizes a PromptItem to the given language code.
   * If an explicit translation exists, it will be used.
   * Otherwise, if language is not English, it applies phrase-level translation.
   */
  fun localize(item: PromptItem, languageCode: String): PromptItem {
    if (languageCode == "en") return item

    val promptMap = promptTranslations[item.id]
    val explicit = promptMap?.get(languageCode)

    if (explicit != null) {
      val updatedVars = item.variables.map { v ->
        val localizedLabel = explicit.variableLabels[v.key] ?: v.label
        v.copy(label = localizedLabel)
      }
      return item.copy(
        title = explicit.title,
        description = explicit.description,
        variables = updatedVars
      )
    }

    // Heuristic translation for dynamic / custom prompts
    return heuristicTranslate(item, languageCode)
  }

  private fun heuristicTranslate(item: PromptItem, lang: String): PromptItem {
    val translatedTitle = translateText(item.title, lang)
    val translatedDesc = translateText(item.description, lang)
    val updatedVars = item.variables.map { v ->
      v.copy(label = translateText(v.label, lang))
    }
    return item.copy(
      title = translatedTitle,
      description = translatedDesc,
      variables = updatedVars
    )
  }

  private fun translateText(text: String, lang: String): String {
    if (text.isBlank()) return text
    var result = text

    // Common terminology dictionary across target languages
    val replacements: Map<String, String> = when (lang) {
      "zh" -> mapOf(
        "Photo to" to "照片转",
        "Sports Poster" to "体育海报",
        "Sports" to "体育",
        "Poster" to "海报",
        "AI Prompt" to "AI提示词",
        "Prompt" to "提示词",
        "Portrait" to "人物肖像",
        "Ultra-Realistic" to "超写实",
        "Realistic" to "写实",
        "Cinematic" to "电影级",
        "Motion Blur" to "动态拖影",
        "Shutter" to "快门",
        "Studio" to "摄影棚",
        "3D" to "3D",
        "Hypercar" to "超跑",
        "Luxury" to "豪华",
        "Watch" to "腕表",
        "Timepiece" to "机械表",
        "Cyberpunk" to "赛博朋克",
        "Cyber" to "赛博",
        "Anime" to "动漫",
        "Diorama" to "微缩景观",
        "Avatar" to "虚拟人像",
        "Minimalist" to "极简主义",
        "Brand Mark" to "品牌标志",
        "Wildlife" to "野生动物",
        "Landing Hook" to "着陆页文案",
        "Clean Architecture" to "清晰架构",
        "Transform uploaded" to "转换上传的",
        "photos into" to "照片为"
      )
      "es" -> mapOf(
        "Photo to" to "Foto a",
        "Sports Poster" to "Póster Deportivo",
        "AI Prompt" to "Prompt IA",
        "Prompt" to "Prompt",
        "Portrait" to "Retrato",
        "Ultra-Realistic" to "Ultrarrealista",
        "Realistic" to "Realista",
        "Cinematic" to "Cinematográfico",
        "Motion Blur" to "Desenfoque de Movimiento",
        "Luxury" to "de Lujo",
        "Cyberpunk" to "Cyberpunk",
        "Anime" to "Anime",
        "Avatar" to "Avatar",
        "Minimalist" to "Minimalista"
      )
      "fr" -> mapOf(
        "Photo to" to "Photo vers",
        "Sports Poster" to "Affiche Sportive",
        "AI Prompt" to "Prompt IA",
        "Portrait" to "Portrait",
        "Ultra-Realistic" to "Ultra-Réaliste",
        "Cinematic" to "Cinématographique",
        "Motion Blur" to "Flou de Bougé",
        "Luxury" to "de Luxe",
        "Minimalist" to "Minimaliste"
      )
      "de" -> mapOf(
        "Photo to" to "Foto zu",
        "Sports Poster" to "Sport-Poster",
        "AI Prompt" to "KI-Prompt",
        "Portrait" to "Porträt",
        "Ultra-Realistic" to "Ultrarealistisch",
        "Cinematic" to "Filmisch",
        "Motion Blur" to "Bewegungsunschärfe",
        "Luxury" to "Luxus"
      )
      "ja" -> mapOf(
        "Photo to" to "写真から",
        "Sports Poster" to "スポーツポスター",
        "AI Prompt" to "AIプロンプト",
        "Portrait" to "ポートレート",
        "Ultra-Realistic" to "超リアル",
        "Cinematic" to "シネマティック",
        "Motion Blur" to "モーションブラー",
        "Luxury" to "高級"
      )
      "ko" -> mapOf(
        "Photo to" to "사진을",
        "Sports Poster" to "스포츠 포스터",
        "AI Prompt" to "AI 프롬프트",
        "Portrait" to "인물 사진",
        "Ultra-Realistic" to "초현실주의",
        "Cinematic" to "시네마틱",
        "Motion Blur" to "모션 블러",
        "Luxury" to "럭셔리"
      )
      "ar" -> mapOf(
        "Photo to" to "تحويل الصورة إلى",
        "Sports Poster" to "ملصق رياضي",
        "AI Prompt" to "موجه ذكاء اصطناعي",
        "Portrait" to "بورتريه",
        "Ultra-Realistic" to "فائق الواقعية",
        "Cinematic" to "سينمائي",
        "Luxury" to "فاخر"
      )
      "ru" -> mapOf(
        "Photo to" to "Фото в",
        "Sports Poster" to "Спортивный постер",
        "AI Prompt" to "ИИ-промпт",
        "Portrait" to "Портрет",
        "Ultra-Realistic" to "Ультрареалистичный",
        "Cinematic" to "Кинематографичный",
        "Luxury" to "Люксовый"
      )
      "pt" -> mapOf(
        "Photo to" to "Foto para",
        "Sports Poster" to "Pôster Esportivo",
        "AI Prompt" to "Prompt de IA",
        "Portrait" to "Retrato",
        "Ultra-Realistic" to "Ultrarrealista",
        "Cinematic" to "Cinematográfico",
        "Luxury" to "de Luxo"
      )
      "it" -> mapOf(
        "Photo to" to "Foto in",
        "Sports Poster" to "Poster Sportivo",
        "AI Prompt" to "Prompt IA",
        "Portrait" to "Ritratto",
        "Ultra-Realistic" to "Ultra-Realistico",
        "Cinematic" to "Cinematografico",
        "Luxury" to "di Lusso"
      )
      "id" -> mapOf(
        "Photo to" to "Foto ke",
        "Sports Poster" to "Poster Olahraga",
        "AI Prompt" to "Prompt AI",
        "Portrait" to "Potret",
        "Ultra-Realistic" to "Sangat Realistis",
        "Cinematic" to "Sinematik",
        "Luxury" to "Mewah"
      )
      "hi" -> mapOf(
        "Photo to" to "फ़ोटो से",
        "Sports Poster" to "स्पोर्ट्स पोस्टर",
        "AI Prompt" to "AI प्रॉम्प्ट",
        "Portrait" to "पोर्ट्रेट",
        "Ultra-Realistic" to "अल्ट्रा-रियलिस्टिक",
        "Cinematic" to "सिनेमाई"
      )
      "tr" -> mapOf(
        "Photo to" to "Fotoğraftan",
        "Sports Poster" to "Spor Posteri",
        "AI Prompt" to "Yapay Zeka İstemi",
        "Portrait" to "Portre",
        "Ultra-Realistic" to "Ultra Gerçekçi",
        "Cinematic" to "Sinematik"
      )
      "vi" -> mapOf(
        "Photo to" to "Ảnh thành",
        "Sports Poster" to "Áp phích thể thao",
        "AI Prompt" to "Lời nhắc AI",
        "Portrait" to "Chân dung",
        "Ultra-Realistic" to "Siêu thực",
        "Cinematic" to "Điện ảnh"
      )
      else -> emptyMap()
    }

    for ((src, dest) in replacements) {
      result = result.replace(src, dest, ignoreCase = true)
    }
    return result
  }
}
