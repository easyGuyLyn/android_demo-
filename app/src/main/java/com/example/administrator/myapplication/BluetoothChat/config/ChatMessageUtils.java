package com.example.administrator.myapplication.BluetoothChat.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;


/**
 * 文本聊天工具类，设计这个类的目的主要是实现文字和表情的混排
 * 实现的方式就是将普通的String类型字符串转换为安卓中的SpannableString
 * 在聊天时混合有表情的一个普通字符串是这样的：哈哈哈\ue056\ue056\ue056
 * 如果要把\ue056变成笑脸符号，就需要使用SpannableString，将需要替换的部分找到并替换成图片
 */

public class ChatMessageUtils {
    public static List<TextChatMessage> textChatMessages = new ArrayList<>();

    static {
        textChatMessages.add(new TextChatMessage("\\ue056"));
        textChatMessages.add(new TextChatMessage("\\ue057"));
        textChatMessages.add(new TextChatMessage("\\ue058"));
        textChatMessages.add(new TextChatMessage("\\ue059"));
        textChatMessages.add(new TextChatMessage("\\ue105"));
        textChatMessages.add(new TextChatMessage("\\ue106"));
        textChatMessages.add(new TextChatMessage("\\ue107"));
        textChatMessages.add(new TextChatMessage("\\ue108"));
        textChatMessages.add(new TextChatMessage("\\ue401"));
        textChatMessages.add(new TextChatMessage("\\ue402"));
        textChatMessages.add(new TextChatMessage("\\ue403"));
        textChatMessages.add(new TextChatMessage("\\ue404"));
        textChatMessages.add(new TextChatMessage("\\ue405"));
        textChatMessages.add(new TextChatMessage("\\ue406"));
        textChatMessages.add(new TextChatMessage("\\ue407"));
        textChatMessages.add(new TextChatMessage("\\ue408"));
        textChatMessages.add(new TextChatMessage("\\ue409"));
        textChatMessages.add(new TextChatMessage("\\ue40a"));
        textChatMessages.add(new TextChatMessage("\\ue40b"));
        textChatMessages.add(new TextChatMessage("\\ue40d"));
        textChatMessages.add(new TextChatMessage("\\ue40e"));
        textChatMessages.add(new TextChatMessage("\\ue40f"));
        textChatMessages.add(new TextChatMessage("\\ue410"));
        textChatMessages.add(new TextChatMessage("\\ue411"));
        textChatMessages.add(new TextChatMessage("\\ue412"));
        textChatMessages.add(new TextChatMessage("\\ue413"));
        textChatMessages.add(new TextChatMessage("\\ue414"));
        textChatMessages.add(new TextChatMessage("\\ue415"));
        textChatMessages.add(new TextChatMessage("\\ue416"));
        textChatMessages.add(new TextChatMessage("\\ue417"));
        textChatMessages.add(new TextChatMessage("\\ue418"));
        textChatMessages.add(new TextChatMessage("\\ue41f"));
        textChatMessages.add(new TextChatMessage("\\ue00e"));
        textChatMessages.add(new TextChatMessage("\\ue421"));
    }

    /**
     * 解决带有表情的文本内容的显示
     * 这里必须使用SpannableStirng才可以
     * 这里需要注意的是，我实际需要的字符串为\ue421,但是在java中，如果直接写会被当作一个unicode字符来处理
     * 所以必须对第一个反斜杠进行转义，即写成\\ue421,java在显示的时候才会认为\ue421是一个字符串，即第一个反斜杠是字符串的一部分
     * 匹配\\ue421格式字符串的正则表达式要写成\\\\ue421
     * 将\ue421形式的内容转化时:
     * 1)要先转为对应的R.drawable.ue421
     * 2)利用BitmapFactory将指定id的图像转为Bitmap图像
     * 3)使用ImageSpan显示图像
     * 4) 将带有图像的ImageSpan混到SpannableString中
     *
     * @param context
     * @param text
     * @return
     */
    public static SpannableString toSpannableString(Context context, String text) {
        if (!TextUtils.isEmpty(text)) {
            SpannableString spannableString = new SpannableString(text);
            int start = 0;
            //注意，代表表情的格式是\\uexxx，但是前两个\\分别需要转义，因此是\\\\uexxx
            Pattern pattern = Pattern.compile("\\\\ue[a-z0-9]{3}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {

                //将符合\\ue[a-z0-9]{3}这个正则表达式的内容取出(注意\\代表的是一个\)
                String faceMessage = matcher.group();

                //所以这里substring(1)取出的直接是uexxx部分，取出的部分是表情图像文件在drawable_xhdpi中的文件名称
                String key = faceMessage.substring(1);

                //1)从名称获得resId
                //context.getResources().getIdentifier(key, "drawable", context.getPackageName())
                //也就是获得com.tarena.catmessage.R.drawable.uexxx

                int resId = getResId(context, key);
                //2)从resId获得图像
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);


                //3)将图像放入ImageSpan中显示
                ImageSpan imageSpan = new ImageSpan(context, bitmap);

                //4)将ImageSpan混入SpannableString
                //每一次都要从start位置之后，去取faceMessage的位置

                int startIndex = text.indexOf(faceMessage, start);
                int endIndex = startIndex + faceMessage.length();

                if (startIndex >= 0) {
                    //最后一个参数是指明是否需要在插入后，在起始位置和结束位置显示原来该位置的文本内容
                    //一共有四个可选值
                    //SPAN_INCLUSIVE_EXCLUSIVE 起始位置插入文本，终止位置不插入文本
                    //SPAN_INCLUSIVE_INCLUSIVE 起始位置插入文本，终止位置插入文本
                    //SPAN_EXCLUSIVE_EXCLUSIVE 起始位置不插入文本，终止位置不插入文本
                    //SPAN_EXCLUSIVE_INCLUSIVE 起始位置不插入文本，终止位置插入文本
                    spannableString.setSpan(imageSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //修改start的位置，每一次取faceMessage的id都要从start位置（也就是上一个表情结束的位置）之后继续找
                    start = (endIndex - 1);
                }
            }
            return spannableString;
        } else {
            return new SpannableString("");
        }
    }

    public static SpannableString toSpannableString(Context context, String text, SpannableString spannableString) {

        int start = 0;

        Pattern pattern = Pattern.compile("\\\\ue[a-z0-9]{3}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            String TextChatMessage = matcher.group();

            String key = TextChatMessage.substring(1);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), context.getResources()
                    .getIdentifier(key, "drawable", context.getPackageName()));

            ImageSpan imageSpan = new ImageSpan(context, bitmap);

            int startIndex = text.indexOf(TextChatMessage, start);

            int endIndex = startIndex + TextChatMessage.length();

            if (startIndex >= 0)
                spannableString.setSpan(imageSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = (endIndex - 1);
        }
        return spannableString;
    }

    /**
     * 根据res/drawable_xxxx中图像文件的名称，获得它的resId值
     * 例如文件名称为ic_launcher，可以返回R.drawable.ic_launcher对应的值
     *
     * @param context 获取Resources对象需要一个Context对象
     * @param name    drawable_xxxx文件夹中图像的名称
     * @return 返回图像的资源id值
     */
    public static int getResId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
