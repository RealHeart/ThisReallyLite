package me.zhenxin.thisreallylite.hook.entity

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.type.java.BooleanClass
import com.highcapable.yukihookapi.hook.type.java.LongClass
import com.highcapable.yukihookapi.hook.type.java.MapClass
import com.highcapable.yukihookapi.hook.type.java.StringClass

/**
 *
 *
 * @author 真心
 * @since 2022/11/25 13:40
 * @email qgzhenxin@qq.com
 */
object WeiboHooker : YukiBaseHooker() {
    // 轻享版/极速版 基础包名一致
    private const val pkgName = "com.weico.international"

    override fun onHook() {
        // 去除开屏广告
        "$pkgName.activity.LogoActivity".hook {
            injectMember {
                method {
                    name = "doWhatNext"
                    emptyParam()
                }
                afterHook {
                    val type = result<String>()
                    if (type == "AD") {
                        result = "main"
                    }
                }
            }
            injectMember {
                method {
                    name = "triggerPermission"
                    param(BooleanClass)
                }
                replaceAny {
                    return@replaceAny true
                }
            }
        }

        // 去除信息流广告
        "$pkgName.api.RxApiKt".hook {
            injectMember {
                method {
                    name = "queryUveAdRequest\$lambda-178"
                    param(MapClass)
                }
                beforeHook {
                    result = ""
                }
            }
        }

        // Utility
        "$pkgName.utility.KotlinExtendKt".hook {
            injectMember {
                method {
                    name = "isWeiboUVEAd"
                    param(findClass("$pkgName.model.sina.Status"))
                }
                beforeHook {
                    result = false
                }
            }
        }
        "$pkgName.utility.KotlinUtilKt".hook {
            injectMember {
                method {
                    name = "findUVEAd"
                    param(findClass("$pkgName.model.sina.PageInfo"))
                }
                beforeHook {
                    result = null
                }
            }
        }

        // ProcessMonitor
        "$pkgName.manager.ProcessMonitor".hook {
            injectMember {
                method {
                    name = "displayAd"
                    param(LongClass)
                    param(ActivityClass)
                    param(BooleanClass)
                }
                replaceAny {
                    return@replaceAny true
                }
            }
        }

        // 其他广告
        "$pkgName.activity.v4.Setting".hook {
            injectMember {
                method {
                    name = "loadBoolean"
                    param(StringClass)
                }
                beforeHook {
                    val key = args[0] as String
                    when {
                        key == "BOOL_UVE_FEED_AD" -> resultFalse()
                        key.startsWith("BOOL_AD_ACTIVITY_BLOCK_") -> resultTrue()
                    }
                }
            }
            injectMember {
                method {
                    name = "loadInt"
                    param(StringClass)
                }
                beforeHook {
                    when (args[0] as String) {
                        "ad_interval" -> result = Int.MAX_VALUE
                        "display_ad" -> result = 0
                    }
                }
            }
            injectMember {
                method {
                    name = "loadStringSet"
                    param(StringClass)
                }
                beforeHook {
                    if (args[0] as String == "CYT_DAYS") {
                        result = setOf<String>()
                    }
                }
            }
            injectMember {
                method {
                    name = "loadString"
                    param(StringClass)
                }
                beforeHook {
                    if (args[0] as String == "video_ad") {
                        result = ""
                    }
                }
            }
        }
    }
}