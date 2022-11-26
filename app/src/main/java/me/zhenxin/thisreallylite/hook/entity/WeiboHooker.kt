package me.zhenxin.thisreallylite.hook.entity

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.type.java.LongType

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
        }

        // 去除后台切回的全屏广告
        "$pkgName.ui.ad.AdActivity".hook {
            injectMember {
                method {
                    name = "onBackgroundToForeground"
                    param(LongType, LongType, ActivityClass)
                }
                replaceAny { }
            }

        }

        // 去除信息流广告
        "$pkgName.utility.KotlinUtilKt".hook {
            injectMember {
                method {
                    name = "findUVEAd"
                    param("$pkgName.model.sina.PageInfo")
                }
                afterHook {
                    result = null
                }
            }
        }
    }
}