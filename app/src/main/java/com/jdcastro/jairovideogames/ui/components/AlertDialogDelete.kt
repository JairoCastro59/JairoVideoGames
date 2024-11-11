package com.jdcastro.jairovideogames.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.ui.dashboard.viewModel.DashboardViewModel

@Composable
fun AlertDialogDelete(
    shouldShowDialog: MutableState<Boolean>,
    viewModel: DashboardViewModel,
    item: VideogameObj?
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            // 5
            title = { Text(text = "Eliminar") },
            text = { Text(text = "¿ Deseas eliminar el juego del catalogo ?") },
            dismissButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Cancelar",
                        color = Color.White
                    )
                }
                            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteVideogame(item)
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Eliminar",
                        color = Color.White
                    )
                }
            }
        )
    }
}