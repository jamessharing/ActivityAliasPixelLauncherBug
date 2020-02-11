package com.airwatch.activityaliasexample.ui.main

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.airwatch.activityaliasexample.R
import com.airwatch.activityaliasexample.SeparateActivity

class MainFragment : Fragment() {

    lateinit var componentName : ComponentName

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        componentName = ComponentName(context!!, "com.airwatch.activityaliasexample.AliasThang")

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        var button = activity!!.findViewById<Button>(R.id.toggleButton)
        button.setOnClickListener { onToggle(it) }
    }

    fun onToggle(view: View?) {

        val x = object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg p0: Void?): Boolean {
                var enabledStatus = context!!.packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                if (enabledStatus) {
                    context!!.packageManager.setComponentEnabledSetting(
                        componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
                    )
                } else {
                    context!!.packageManager.setComponentEnabledSetting(
                        componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                    )
                }
                return !enabledStatus
            }

        }
        x.execute()
    }


}
