package com.geekster.preskeep

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.geekster.preskeep.databinding.ActivityHomeBinding
import com.geekster.preskeep.utils.Constants.TAG
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private lateinit var navController: NavController
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Handle window insets (for edge-to-edge display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Main code starts -

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_nav_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(PRES_FRAG,"Prescription", R.drawable.prescription_icon),
            CurvedBottomNavigation.Model(REPORT_FRAG,"Reports", R.drawable.report_icon),
            CurvedBottomNavigation.Model(PROFILE_FRAG,"Profile", R.drawable.user_icon)

        )
        binding.bottomNavigation.apply {
            titleTextSize = 0
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                navController.navigate(it.id)
            }
            show(PRES_FRAG)
            // optional
            setupNavController(navController)
        }


    }

    companion object {
        val PRES_FRAG = R.id.prescriptionFragment2
        val REPORT_FRAG = R.id.reportFragment
        val PROFILE_FRAG = R.id.userFragment
    }

//    private fun initNavHost() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_nav_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//    }

//    private fun ActivityHomeBinding.setUpBottomNavigation() {
//        val bottomNavigationItems = mutableListOf(
//            CurvedBottomNavigation.Model(PRES_FRAG,"Prescription", R.drawable.user_icon),
//            CurvedBottomNavigation.Model(REPORT_FRAG,"Reports", R.drawable.report_icon),
//            CurvedBottomNavigation.Model(PROFILE_FRAG,"Profile", R.drawable.prescription_icon)
//
//        )
//        bottomNavigation.apply {
//            bottomNavigationItems.forEach { add(it) }
//            setOnClickMenuListener {
//                navController.navigate(it.id)
//            }
//            show(PRES_FRAG)
//            // optional
//            setupNavController(navController)
//        }
//
//    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController.graph.startDestinationId != null && navController.currentDestination?.id != navController.graph.startDestinationId) {
            // Pop all destinations up to, but not including, the start destination
            navController.popBackStack(navController.graph.startDestinationId, false)
        } else {
            // If current destination is the start destination, perform default back action
            super.onBackPressed()
        }
//        if (navController.currentDestination!!.id == PRES_FRAG)
//            super.onBackPressed()
//        else {
//            when (navController.currentDestination!!.id) {
//                REPORT_FRAG -> {
//                    navController.popBackStack()
////                    navController.popBackStack(R.id.prescriptionFragment2, false)
//                }
//                PROFILE_FRAG -> {
//                    navController.popBackStack()
////                    navController.popBackStack(R.id.prescriptionFragment2, false)
//                }
//                else -> {
//                    navController.popBackStack()
////                    navController.navigateUp()
//                }
//            }
//        }
    }


}