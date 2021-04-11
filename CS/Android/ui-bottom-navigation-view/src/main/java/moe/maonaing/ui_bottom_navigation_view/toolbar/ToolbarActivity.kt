package moe.maonaing.ui_bottom_navigation_view.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import moe.maonaing.ui_bottom_navigation_view.R

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navController = findNavController(R.id.toolbarFragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavView)

        val appConfiguration = AppBarConfiguration.Builder(
                bottomNavigation.menu
        ).build()

        NavigationUI.setupWithNavController(
                bottomNavigation,
                navController
        )

        NavigationUI.setupActionBarWithNavController(
                this,
                navController,
                appConfiguration
        )

    }

}