package com.example.tabnavigation

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView?.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_camera -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_gallery))
                        return true
                    }
                    R.id.nav_gallery -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_gallery))
                        return true
                    }
                    R.id.nav_manage -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_tools))
                        return true
                    }
                    R.id.nav_send -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_send))
                        return true
                    }
                    R.id.nav_share -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_share))
                        return true
                    }
                    R.id.nav_slideshow -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_slideshow))
                        return true
                    }
                    R.id.nav_share -> {
                        drawer.closeDrawer(GravityCompat.START)
                        displayToast(getString(R.string.chose_share))
                        return true
                    }
                    else -> return false
                }
            }

        })

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL;

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val adapter = PageAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter


        viewPager.addOnPageChangeListener(object :
            TabLayout.TabLayoutOnPageChangeListener(tabLayout) {

        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.setCurrentItem(tab!!.getPosition())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun displayToast(message: String) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show()
    }
}