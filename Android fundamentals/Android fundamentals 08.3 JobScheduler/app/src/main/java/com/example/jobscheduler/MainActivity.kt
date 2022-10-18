package com.example.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobscheduler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mScheduler: JobScheduler? = null

    companion object {
        const val JOB_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, i: Int, b: Boolean) {
                if (i > 0) {
                    binding.seekBarProgress.text = "$i s"
                } else {
                    binding.seekBarProgress.text = getString(R.string.not_set)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    fun cancelJobs(view: View) {
        if (mScheduler != null) {
            mScheduler!!.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    fun scheduleJob(view: View) {

        val seekBarInteger: Int = binding.seekBar.getProgress()
        val seekBarSet = seekBarInteger > 0

        val selectedNetWorkID = binding.networkOptions.checkedRadioButtonId
        var selectedNetworkOptions = JobInfo.NETWORK_TYPE_NONE

        when (selectedNetWorkID) {
            R.id.noNetwork -> selectedNetworkOptions = JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> selectedNetworkOptions = JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> selectedNetworkOptions = JobInfo.NETWORK_TYPE_UNMETERED
        }
        val mScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val serviceName = ComponentName(
            packageName,
            NotificationJobService::class.java.name
        )
        val builder = JobInfo.Builder(JOB_ID, serviceName)
            .setRequiredNetworkType(selectedNetworkOptions)
            .setRequiresDeviceIdle(binding.idleSwitch.isChecked())
            .setRequiresCharging(binding.chargingSwitch.isChecked());
        if (seekBarSet) {
            builder.setOverrideDeadline((seekBarInteger * 1000).toLong());
        }

        val constraintSet = (selectedNetworkOptions != JobInfo.NETWORK_TYPE_NONE)
                || binding.chargingSwitch.isChecked() || binding.idleSwitch.isChecked() || seekBarSet

        if (constraintSet) {
            // Schedule the job and notify the user.
            val myJobInfo = builder.build()
            mScheduler.schedule(myJobInfo)
            Toast.makeText(
                this, "Job Scheduled, job will run when " +
                        "the constraints are met.", Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this, "Please set at least one constraint",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}