package me.zhenxin.thisreallylite.ui.fragment

import android.os.Bundle
import androidx.preference.Preference
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.xposed.prefs.ui.ModulePreferenceFragment
import me.zhenxin.thisreallylite.BuildConfig
import me.zhenxin.thisreallylite.R

/**
 * 设置 Fragment
 *
 * @author 真心
 * @since 2022/11/26 11:55
 * @email qgzhenxin@qq.com
 */
class SettingsFragment : ModulePreferenceFragment() {

    override fun onCreatePreferencesInModuleApp(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        val status = findPreference<Preference>("status")
        if (YukiHookAPI.Status.isModuleActive) {
            status?.summary =
                "${getString(R.string.active_title)}(${YukiHookAPI.Status.executorName})"
        }

        val version = findPreference<Preference>("version")
        version?.summary = BuildConfig.VERSION_NAME
    }
}