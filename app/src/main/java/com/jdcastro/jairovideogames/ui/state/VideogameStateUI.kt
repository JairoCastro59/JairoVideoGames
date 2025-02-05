package com.jdcastro.jairovideogames.ui.state

import com.jdcastro.jairovideogames.domain.models.VideogameObj
import java.util.ArrayList

sealed class VideogameStateUI {
    object Loading : VideogameStateUI()
    data class Success (val videogames: ArrayList<VideogameObj>) : VideogameStateUI()
    data class Error (val msg: String): VideogameStateUI()
}