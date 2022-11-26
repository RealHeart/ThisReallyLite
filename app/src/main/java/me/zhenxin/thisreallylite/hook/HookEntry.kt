package me.zhenxin.thisreallylite.hook

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import me.zhenxin.thisreallylite.BuildConfig
import me.zhenxin.thisreallylite.hook.entity.WeiboHooker

/**
 * Hook入口
 *
 * @author 真心
 * @since 2022/11/25 12:26
 * @email qgzhenxin@qq.com
 */
@InjectYukiHookWithXposed(isUsingResourcesHook = false)
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        debugLog { tag = "ThisReallyLite" }
        isDebug = BuildConfig.DEBUG
        isEnableDataChannel = false
    }

    override fun onHook() = encase {
        // 微博轻享版
        loadApp(name = "com.weico.international", hooker = WeiboHooker)
        // 微博极速版
        loadApp(name = "com.sina.weibolite", hooker = WeiboHooker)
    }
}