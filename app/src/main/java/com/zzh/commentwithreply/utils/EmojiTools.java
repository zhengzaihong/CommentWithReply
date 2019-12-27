package com.zzh.commentwithreply.utils;

import com.zzh.commentwithreply.R;
import com.zzh.corelib.utils.EmojiUtils;

import java.util.LinkedHashMap;

public class EmojiTools {


    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> emoJiMap = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> xiaohua = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> qqHashMap = new LinkedHashMap<>();


    public static void initEmoji() {
        EmojiUtils.appendEmoji(emojiPack);
    }


    static {

        emojiPack.put("0", emoJiMap);
        emojiPack.put("1", emoJiMap);
        emojiPack.put("2", emoJiMap);

/**************************************************微信表情包**************************************************/
        emoJiMap.put("[呵呵]", R.drawable.d_hehe);
        emoJiMap.put("[可爱]", R.drawable.d_keai);
        emoJiMap.put("[太开心]", R.drawable.d_taikaixin);
        emoJiMap.put("[鼓掌]", R.drawable.d_guzhang);
        emoJiMap.put("[嘻嘻]", R.drawable.d_xixi);
        emoJiMap.put("[哈哈]", R.drawable.d_haha);
        emoJiMap.put("[笑哭]", R.drawable.d_xiaoku);
        emoJiMap.put("[急眼]", R.drawable.d_jiyan);
        emoJiMap.put("[大馋嘴]", R.drawable.d_chanzui);
        emoJiMap.put("[懒得理你]", R.drawable.d_landelini);
        emoJiMap.put("[黑线]", R.drawable.d_heixian);
        emoJiMap.put("[挖鼻屎]", R.drawable.d_wabishi);
        emoJiMap.put("[哼]", R.drawable.d_heng);
        emoJiMap.put("[怒]", R.drawable.d_nu);
        emoJiMap.put("[抓狂]", R.drawable.d_zhuakuang);
        emoJiMap.put("[委屈]", R.drawable.d_weiqu);
        emoJiMap.put("[可怜]", R.drawable.d_kelian);
        emoJiMap.put("[失望]", R.drawable.d_shiwang);
        emoJiMap.put("[悲伤]", R.drawable.d_beishang);
        emoJiMap.put("[累]", R.drawable.d_lei);
        emoJiMap.put("[害羞]", R.drawable.d_haixiu);
        emoJiMap.put("[捂]", R.drawable.d_wu);
        emoJiMap.put("[爱你]", R.drawable.d_aini);
        emoJiMap.put("[亲亲]", R.drawable.d_qinqin);
        emoJiMap.put("[花心]", R.drawable.d_huaxin);
        emoJiMap.put("[舔]", R.drawable.d_tian);
        emoJiMap.put("[钱]", R.drawable.d_qian);
        emoJiMap.put("[神烦狗]", R.drawable.d_doge);
        emoJiMap.put("[喵]", R.drawable.d_miao);
        emoJiMap.put("[二哈]", R.drawable.d_erha);
        emoJiMap.put("[哭]", R.drawable.d_ku);
        emoJiMap.put("[坏笑]", R.drawable.d_huaixiao);
        emoJiMap.put("[阴险]", R.drawable.d_yinxian);
        emoJiMap.put("[偷笑]", R.drawable.d_touxiao);
        emoJiMap.put("[思考]", R.drawable.d_sikao);
        emoJiMap.put("[疑问]", R.drawable.d_yiwen);
        emoJiMap.put("[晕]", R.drawable.d_yun);
        emoJiMap.put("[傻眼]", R.drawable.d_shayan);
        emoJiMap.put("[衰]", R.drawable.d_shuai);
        emoJiMap.put("[骷髅]", R.drawable.d_kulou);
        emoJiMap.put("[嘘]", R.drawable.d_xu);
        emoJiMap.put("[闭嘴]", R.drawable.d_bizui);
        emoJiMap.put("[汗]", R.drawable.d_han);
        emoJiMap.put("[吃惊]", R.drawable.d_chijing);
        emoJiMap.put("[感冒]", R.drawable.d_ganmao);
        emoJiMap.put("[生病]", R.drawable.d_shengbing);
        emoJiMap.put("[吐]", R.drawable.d_tu);
        emoJiMap.put("[拜拜]", R.drawable.d_baibai);
        emoJiMap.put("[鄙视]", R.drawable.d_bishi);
        emoJiMap.put("[左哼哼]", R.drawable.d_zuohengheng);
        emoJiMap.put("[右哼哼]", R.drawable.d_youhengheng);
        emoJiMap.put("[怒骂]", R.drawable.d_numa);
        emoJiMap.put("[打脸]", R.drawable.d_dalian);
        emoJiMap.put("[敲头]", R.drawable.d_ding);
        emoJiMap.put("[打哈气]", R.drawable.d_dahaqi);
        emoJiMap.put("[困]", R.drawable.d_kun);
        emoJiMap.put("[互粉]", R.drawable.f_hufen);
        emoJiMap.put("[抱抱]", R.drawable.d_baobao);
        emoJiMap.put("[摊手]", R.drawable.d_tanshou);
        emoJiMap.put("[心]", R.drawable.l_xin);
        emoJiMap.put("[伤心]", R.drawable.l_shangxin);
        emoJiMap.put("[鲜花]", R.drawable.w_xianhua);
        emoJiMap.put("[男孩儿]", R.drawable.d_nanhaier);
        emoJiMap.put("[女孩儿]", R.drawable.d_nvhaier);
        emoJiMap.put("[握手]", R.drawable.h_woshou);
        emoJiMap.put("[作揖]", R.drawable.h_zuoyi);
        emoJiMap.put("[赞]", R.drawable.h_zan);
        emoJiMap.put("[耶]", R.drawable.h_ye);
        emoJiMap.put("[好]", R.drawable.h_good);
        emoJiMap.put("[弱]", R.drawable.h_ruo);
        emoJiMap.put("[不要]", R.drawable.h_buyao);
        emoJiMap.put("[好的]", R.drawable.h_ok);
        emoJiMap.put("[哈哈]", R.drawable.h_haha);
        emoJiMap.put("[来]", R.drawable.h_lai);//
        emoJiMap.put("[熊猫]", R.drawable.d_xiongmao);
        emoJiMap.put("[兔子]", R.drawable.d_tuzi);
        emoJiMap.put("[猪头]", R.drawable.d_zhutou);
        emoJiMap.put("[神兽]", R.drawable.d_shenshou);
        emoJiMap.put("[奥特曼]", R.drawable.d_aoteman);
        emoJiMap.put("[太阳]", R.drawable.w_taiyang);
        emoJiMap.put("[月亮]", R.drawable.w_yueliang);
        emoJiMap.put("[浮云]", R.drawable.w_fuyun);
        emoJiMap.put("[下雨]", R.drawable.w_xiayu);
        emoJiMap.put("[沙尘暴]", R.drawable.w_shachenbao);
        emoJiMap.put("[微风]", R.drawable.w_weifeng);
        emoJiMap.put("[飞机]", R.drawable.o_feiji);
        emoJiMap.put("[照相机]", R.drawable.o_zhaoxiangji);
        emoJiMap.put("[话筒]", R.drawable.o_huatong);
        emoJiMap.put("[音乐]", R.drawable.o_yinyue);
        emoJiMap.put("[给力]", R.drawable.f_geili);
        emoJiMap.put("[囧]", R.drawable.f_jiong);
        emoJiMap.put("[萌]", R.drawable.f_meng);
        emoJiMap.put("[神马]", R.drawable.f_shenma);
        emoJiMap.put("[织]", R.drawable.f_zhi);
        emoJiMap.put("[最右]", R.drawable.d_zuiyou);
        emoJiMap.put("[蜡烛]", R.drawable.o_lazhu);
        emoJiMap.put("[围观]", R.drawable.o_weiguan);
        emoJiMap.put("[干杯]", R.drawable.o_ganbei);
        emoJiMap.put("[蛋糕]", R.drawable.o_dangao);
        emoJiMap.put("[礼物]", R.drawable.o_liwu);
        emoJiMap.put("[囍]", R.drawable.f_xi);
        emoJiMap.put("[钟]", R.drawable.o_zhong);
        emoJiMap.put("[肥皂]", R.drawable.d_feizao);
        emoJiMap.put("[绿丝带]", R.drawable.o_lvsidai);
        emoJiMap.put("[围脖]", R.drawable.o_weibo);
        emoJiMap.put("[删除]", R.drawable.compose_emotion_delete_highlighted);


        xiaohua.put("[悲催]", R.drawable.lxh_beicui);
        xiaohua.put("[被电]", R.drawable.lxh_beidian);
        xiaohua.put("[奔溃]", R.drawable.lxh_bengkui);
        xiaohua.put("[别烦我]", R.drawable.lxh_biefanwo);
        xiaohua.put("[不好意思]", R.drawable.lxh_buhaoyisi);
        xiaohua.put("[不想上班]", R.drawable.lxh_buxiangshangban);
        xiaohua.put("[得意地笑]", R.drawable.lxh_deyidexiao);
        xiaohua.put("[费劲]", R.drawable.lxh_feijin);
        xiaohua.put("[好爱哦]", R.drawable.lxh_haoaio);
        xiaohua.put("[好棒]", R.drawable.lxh_haobang);
        xiaohua.put("[好囧]", R.drawable.lxh_haojiong);
        xiaohua.put("[好喜欢]", R.drawable.lxh_haoxihuan);
        xiaohua.put("[坚持住]", R.drawable.lxh_holdzhu);
        xiaohua.put("[杰克逊]", R.drawable.lxh_jiekexun);
        xiaohua.put("[纠结]", R.drawable.lxh_jiujie);
        xiaohua.put("[巨汗]", R.drawable.lxh_juhan);
        xiaohua.put("[抠鼻屎]", R.drawable.lxh_koubishi);
        xiaohua.put("[困死了]", R.drawable.lxh_kunsile);
        xiaohua.put("[雷锋]", R.drawable.lxh_leifeng);
        xiaohua.put("[泪流满面]", R.drawable.lxh_leiliumanmian);
        xiaohua.put("[玫瑰]", R.drawable.lxh_meigui);
        xiaohua.put("[欧耶]", R.drawable.lxh_oye);
        xiaohua.put("[霹雳]", R.drawable.lxh_pili);
        xiaohua.put("[悄悄]", R.drawable.lxh_qiaoqiao);
        xiaohua.put("[丘比特]", R.drawable.lxh_qiubite);
        xiaohua.put("[求关注]", R.drawable.lxh_qiuguanzhu);
        xiaohua.put("[群体围观]", R.drawable.lxh_quntiweiguan);
        xiaohua.put("[甩甩手]", R.drawable.lxh_shuaishuaishou);
        xiaohua.put("[偷乐]", R.drawable.lxh_toule);
        xiaohua.put("[推荐]", R.drawable.lxh_tuijian);
        xiaohua.put("[相互膜拜]", R.drawable.lxh_xianghumobai);
        xiaohua.put("[想一想]", R.drawable.lxh_xiangyixiang);
        xiaohua.put("[笑哈哈]", R.drawable.lxh_xiaohaha);
        xiaohua.put("[羞嗒嗒]", R.drawable.lxh_xiudada);
        xiaohua.put("[许愿]", R.drawable.lxh_xuyuan);
        xiaohua.put("[有压力]", R.drawable.lxh_youyali);
        xiaohua.put("[狂躁症]", R.drawable.kuangzaozheng);
        xiaohua.put("[删除]", R.drawable.compose_emotion_delete_highlighted);


/**************************************************qq表情包**************************************************/

        qqHashMap.put("[ecf]", R.drawable.ecf);
        qqHashMap.put("[ecv]", R.drawable.ecv);
        qqHashMap.put("[ecb]", R.drawable.ecb);
        qqHashMap.put("[ecy]", R.drawable.ecy);
        qqHashMap.put("[ebu]", R.drawable.ebu);
        qqHashMap.put("[ebr]", R.drawable.ebr);
        qqHashMap.put("[ecc]", R.drawable.ecc);
        qqHashMap.put("[eft]", R.drawable.eft);
        qqHashMap.put("[ecr]", R.drawable.ecr);
        qqHashMap.put("[ebs]", R.drawable.ebs);
        qqHashMap.put("[ech]", R.drawable.ech);
        qqHashMap.put("[ecg]", R.drawable.ecg);
        qqHashMap.put("[ebh]", R.drawable.ebh);
        qqHashMap.put("[ebg]", R.drawable.ebg);
        qqHashMap.put("[ecp]", R.drawable.ecp);
        qqHashMap.put("[deg]", R.drawable.deg);
        qqHashMap.put("[ecd]", R.drawable.ecd);
        qqHashMap.put("[ecj]", R.drawable.ecj);
        qqHashMap.put("[ebv]", R.drawable.ebv);
        qqHashMap.put("[ece]", R.drawable.ece);
        qqHashMap.put("[ebl]", R.drawable.ebl);
        qqHashMap.put("[eca]", R.drawable.eca);
        qqHashMap.put("[ecn]", R.drawable.ecn);
        qqHashMap.put("[eco]", R.drawable.eco);
        qqHashMap.put("[eeo]", R.drawable.eeo);
        qqHashMap.put("[eep]", R.drawable.eep);
        qqHashMap.put("[eci]", R.drawable.eci);
        qqHashMap.put("[ebj]", R.drawable.ebj);
        qqHashMap.put("[eer]", R.drawable.eer);
        qqHashMap.put("[edi]", R.drawable.edi);
        qqHashMap.put("[ebq]", R.drawable.ebq);
        qqHashMap.put("[eeq]", R.drawable.eeq);
        qqHashMap.put("[ecq]", R.drawable.ecq);
        qqHashMap.put("[ebt]", R.drawable.ebt);
        qqHashMap.put("[ede]", R.drawable.ede);
        qqHashMap.put("[eew]", R.drawable.eew);
        qqHashMap.put("[eex]", R.drawable.eex);
        qqHashMap.put("[dga]", R.drawable.dga);
        qqHashMap.put("[ebp]", R.drawable.ebp);
        qqHashMap.put("[ebo]", R.drawable.ebo);
        qqHashMap.put("[删除]", R.drawable.compose_emotion_delete_highlighted);

    }


}
