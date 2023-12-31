package com.example.githubrepoviewer.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.ui.theme.MediumGray
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
     navController: NavController
) {
    val scale = remember{
         Animatable(0f)
    }
     LaunchedEffect(key1 = true){
          scale.animateTo(
               targetValue = 0.3f,
               animationSpec = tween(
                   durationMillis = 500,
                   easing = {
                       OvershootInterpolator(2f).getInterpolation(it)
                   }
               )
          )

         delay(2000L)
         navController.navigate(Screen.GitReposListScreen.route){
             popUpTo(Screen.SplashScreen.route){
                 inclusive = true
             }
         }
     }
     Box(contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
              .background(if (isSystemInDarkTheme()) MediumGray else Color.White))
     {
        Image(painter = painterResource(id = R.drawable.github)
            , contentDescription ="Logo",
            modifier = Modifier.scale(scale.value))

     }


}



