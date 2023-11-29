package io.quantumone.cardinalkit.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import io.quantumone.cardinalkit.presentation.RegisterScreen
import io.quantumone.cardinalkit.presentation.contacts.ContactsScreen
import io.quantumone.cardinalkit.presentation.home.HomeScreen
import io.quantumone.cardinalkit.presentation.login.ForgotPassword
import io.quantumone.cardinalkit.presentation.login.LoginScreen
import io.quantumone.cardinalkit.presentation.login.RegisterMethod
import io.quantumone.cardinalkit.presentation.login.SignInMethod
import io.quantumone.cardinalkit.presentation.main.MainScreen
import io.quantumone.cardinalkit.presentation.onboarding.OnboardingScreen
import io.quantumone.cardinalkit.presentation.onboarding.Review
import io.quantumone.cardinalkit.presentation.onboarding.Signature
import io.quantumone.cardinalkit.presentation.onboarding.WelcomeScreen
import io.quantumone.cardinalkit.presentation.profile.ProfileScreen
import io.quantumone.cardinalkit.presentation.profile.ReviewConsent
import io.quantumone.cardinalkit.presentation.tasks.TasksScreen
import io.quantumone.cardinalkit.presentation.welcome.JoinStudyScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun CKNavHost(navController: NavHostController, startDestination: String) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screens.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screens.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screens.Review.route) {
            Review(navController = navController)
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screens.RegisterMethod.route) {
            RegisterMethod(navController = navController)
        }
        composable(Screens.MainScreen.route) {
            MainScreen()
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.TasksScreen.route) {
            TasksScreen()
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screens.ContactsScreen.route) {
            ContactsScreen()
        }
        composable(Screens.ReviewConsent.route) {
            ReviewConsent(navController = navController)
        }
        composable(Screens.SignatureScreen.route) {
            Signature(navController = navController)
        }
        composable(Screens.SignInMethod.route) {
            SignInMethod(navController = navController)
        }
        composable(Screens.JoinStudyScreen.route) {
            JoinStudyScreen(navController = navController)
        }
        composable(Screens.ForgotPasswordScreen.route) {
            ForgotPassword(navController = navController)
        }
    }
}
