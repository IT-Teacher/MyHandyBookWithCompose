package uz.itteacher.myhandybookwithcompose.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.myhandybookwithcompose.data.model.CommentExtended

@Composable
fun CommentItem(comment: CommentExtended) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD8EEFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFF1B87C7),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = comment.username, style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold, fontSize = 17.sp
                        )
                    )

                    Text(
                        text = comment.created_at, style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray, fontSize = 14.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFF4CB3FF),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${comment.reyting}.0",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium, fontSize = 14.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = comment.text, style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp
            ), maxLines = 6, overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
    }
}
