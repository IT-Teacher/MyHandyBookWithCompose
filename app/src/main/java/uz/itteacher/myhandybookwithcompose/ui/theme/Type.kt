package uz.itteacher.myhandybookwithcompose.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    // 🔹 Заголовки
    titleLarge = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    // 🔹 Основной текст
    bodyLarge = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratFont,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )
)
