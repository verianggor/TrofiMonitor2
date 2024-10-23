package com.example.nim234311055.trofimonitor

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nim234311055.trofimonitor.ui.theme.TrofiMonitorTheme
import com.example.nim234311055.trofimonitor.R // Pastikan package ini benar

class MainActivity() : ComponentActivity(), Parcelable {
    constructor(parcel: Parcel) : this()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrofiMonitorTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ClubScreen()
                }
            }
        }
    }

    data class Club(val name: String, val totalTrophies: Int, val internationalTrophies: Int, val imageRes: Int)

    @Composable
    fun ClubScreen() {
        var clubs by remember {
            mutableStateOf(
                listOf(
                    Club("Liverpool", 95, 10, R.drawable.liverpool),
                    Club("Chelsea", 91, 7, R.drawable.chelsea),
                    Club("Manchester United", 68, 8, R.drawable.manchester_united_removebg_preview),
                    Club("Manchester City", 100, 5, R.drawable.manchester_city_removebg_preview),
                    Club("Arsenal", 70, 3, R.drawable.arsenal),
                    Club("Tottenham Hotspur", 50, 1, R.drawable.totenham),
                    Club("Real Madrid", 97, 8, R.drawable.real_madrid_removebg_preview),
                    Club("Bayern Munchen", 83, 15, R.drawable.bayern_munich)
                )
            )
        }

        var showAddDialog by remember { mutableStateOf(false) }

        // Tambahkan Box dengan background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xC1FFFFFF),
                            Color(0xFF00FFCF)


                        )
                    )
                )
                .padding(20.dp)
        ) {
            LazyColumn {
                item {
                    StudentInfoScreen()
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = " # Trofi Klub Sepak Bola Internasional ",
                        fontSize = 28.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                // Tampilkan daftar klub
                items(clubs) { club ->
                    ClubItem(club, onRemove = { clubs = clubs - club })
                }
                // Keterangan untuk klub yang memiliki lebih dari 45 trofi
                val clubsWithMoreThan45Trophies = clubs.filter { it.totalTrophies > 45 }
                if (clubsWithMoreThan45Trophies.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Klub Internasional yang memiliki lebih dari 45 trofi:",
                            fontSize = 26.sp,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(clubsWithMoreThan45Trophies) { club ->
                        Text(
                            text = "${club.name}: ${club.totalTrophies} trofi",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    AddClubButton(onClick = { showAddDialog = true })
                }
            }

            if (showAddDialog) {
                AddMultipleClubsDialog(
                    onDismiss = { showAddDialog = false },
                    onAdd = { newClubs ->
                        clubs = clubs + newClubs
                        showAddDialog = false
                    }
                )
            }
        }
    }

    @Composable
    fun ClubItem(club: Club, onRemove: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Cyan)
                .shadow(3.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = club.imageRes),
                        contentDescription = "${club.name} logo",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = club.name,
                            fontSize = 24.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "${club.totalTrophies} total trofi, ${club.internationalTrophies} trofi internasional",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
                Button(
                    onClick = onRemove,
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Hapus", color = Color.DarkGray)
                }
            }
        }
    }

    @Composable
    fun StudentInfoScreen() {
        val studentName = "Veri Tri Anggoro"
        val studentID = "234311055"
        val studentKelas = "TRPL 3B"

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nama: $studentName",
                fontSize = 20.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 7.dp)
            )
            Text(
                text = "NIM : $studentID",
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 7.dp)
            )
            Text(
                text = "Kelas : $studentKelas",
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 7.dp)
            )
        }
    }

    @Composable
    fun AddClubButton(onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xE800FFE2))
        ) {
            Text(" + Add Club ", color = Color.DarkGray)
        }
    }

    @Composable
    fun AddMultipleClubsDialog(onDismiss: () -> Unit, onAdd: (List<Club>) -> Unit) {
        var clubData by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = {
                        if (clubData.isNotEmpty()) {
                            val newClubs = clubData.split(";").mapNotNull { entry ->
                                val parts = entry.split(",")
                                if (parts.size == 3) {
                                    val name = parts[0].trim()
                                    val totalTrophies = parts[1].trim().toIntOrNull() ?: 0
                                    val internationalTrophies = parts[2].trim().toIntOrNull() ?: 0
                                    Club(name, totalTrophies, internationalTrophies, R.drawable.default_club2)
                                } else {
                                    null
                                }
                            }
                            onAdd(newClubs)
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            title = { Text("Add New Club") },
            text = {
                Column {
                    OutlinedTextField(
                        value = clubData,
                        onValueChange = { clubData = it },
                        label = { Text("Data Klub (Nama, Total Trofi, Trofi Internasional;...)") },
                        placeholder = { Text("Contoh: Klub A, 10, 2; Klub B, 20, 5") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
