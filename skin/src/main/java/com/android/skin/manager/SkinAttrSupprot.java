package com.android.skin.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.android.skin.contacts.SkinContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luhao on 2016/9/13.
 * 4，皮肤属性兼容类
 * 因为每个activity都会使用到该方法所以直接将它设置成静态的，以免多次实例化和释放造成内存压力过大
 */
public class SkinAttrSupprot {
    /**
     * 通过资源截取,暂时用不到
     *
     * @param attrs
     * @param context
     * @return
     */
    public List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        SkinAttr skinAttr;
        //在这里循环遍历出这个activity中所包含的所有资源
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);//属性名
            String attrValue = attrs.getAttributeValue(i);//属性值
            //通过属性名获得到属性类型，这个属性类型枚举中已经包含了被查找到的枚举属性：例如包含了backage
            SkinAttrType attrType = getSupprotAttrType(attrName);
            if (attrType == null) continue;
            if (attrValue.startsWith("@")) {
                //通过属性值获得属性id，以@开头验证
                int id = Integer.parseInt(attrValue.substring(1));
                //通过属性id获得这个属性的名称，例如R文件id是0x7f050000；可以通过这个id得到它的命名：例如：skin_index_bg
                String entryName = context.getResources().getResourceEntryName(id);
                if (entryName.startsWith(SkinContact.ATTR_PREFIX)) {
                    //我们验证skin以后的资源名称加上带有资源类型的枚举，例如：color类型的枚举
                    skinAttr = new SkinAttr(attrType, entryName);
                    skinAttrs.add(skinAttr);
                    //LogUtils.i("添加资源SkinAttr：" + entryName);
                }
            }
        }
        return skinAttrs;
    }

    /**
     * tag的样式，我们可以根据tag来截取
     *
     * @param tagStr 样式：skin_left_menu_icon:src|skin_color_red:textColor
     * @return
     */
    public static List<SkinAttr> getSkinTags(String tagStr) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        if (TextUtils.isEmpty(tagStr)) return skinAttrs;
        //将string截取成一|分隔符的字符串数组
        String[] items = tagStr.split("\\|");
        for (String item : items) {
            //如果不包含标识换肤的前缀，则表示它不是需要换肤的
            if (!tagStr.startsWith(SkinContact.ATTR_PREFIX))
                return skinAttrs;
            //截取出资源名和资源类型
            String[] resItems = item.split(":");
            String resName = resItems[1];
            String resType = resItems[2];

            //通过属性名获得到属性类型，这个属性类型枚举中已经包含了被查找到的枚举属性：例如包含了backage
            SkinAttrType attrType = getSupprotAttrType(resType);
            if (attrType == null) continue;
            SkinAttr attr = new SkinAttr(attrType, resName);
            skinAttrs.add(attr);
        }
        return skinAttrs;
    }

    //传入资源名得到资源类型实例
    private static SkinAttrType getSupprotAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            //如果枚举中有一个资源类型匹配了，则返回这个枚举所带有的资源类型实例
            if (attrType.getAttrType().equals(attrName))
                return attrType;
        }
        return null;
    }
}
