package com.zzh.corelib.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zzh.corelib.R;

import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/25
 * creat_time: 14:38
 * describe 每个表情的显示
 **/
public class EmojiAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> emojiName;
    private List<Integer> emojiId;

    public EmojiAdapter(Context mContext, List<String> emojiName, List<Integer> emojiId) {

        this.mContext = mContext;
        this.emojiName = emojiName;
        this.emojiId = emojiId;
    }


    @Override
    public int getCount() {
        return emojiId.size();
    }

    @Override
    public Integer getItem(int position) {
        return emojiId.get(position);
    }

    public String getItemEmojiName(int position) {
        return emojiName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_row_emoji, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int resId = getItem(position);
        Drawable drawable = mContext.getResources().getDrawable(resId);
        if (null != drawable) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
            holder.imageView.setImageResource(resId);
        }
        return convertView;
    }


    class ViewHolder {

        private ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.iv_emoji);
        }
    }
}
