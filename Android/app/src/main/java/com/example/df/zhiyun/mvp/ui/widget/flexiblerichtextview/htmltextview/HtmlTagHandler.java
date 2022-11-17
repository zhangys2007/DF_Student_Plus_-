/*
 * Copyright (C) 2013-2015 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2013-2015 Juha Kuitunen
 * Copyright (C) 2013 Mohammed Lakkadshaw
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.CenteredImageSpan;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;

import org.scilab.forge.jlatexmath.core.AjLatexMath;
import org.scilab.forge.jlatexmath.core.Insets;
import org.scilab.forge.jlatexmath.core.TeXConstants;
import org.scilab.forge.jlatexmath.core.TeXFormula;
import org.scilab.forge.jlatexmath.core.TeXIcon;
import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some parts of this code are based on android.text.Html
 */
public class HtmlTagHandler implements Html.TagHandler {
    static final String TAG = "HtmlTagHandler";
    static final int MAX_IMAGE_WIDTH = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.8);
    public static final String UNORDERED_LIST = "HTML_TEXTVIEW_ESCAPED_UL_TAG";
    public static final String ORDERED_LIST = "HTML_TEXTVIEW_ESCAPED_OL_TAG";
    public static final String LIST_ITEM = "HTML_TEXTVIEW_ESCAPED_LI_TAG";
    public static final String LATEX_TAG = "code";
    public static final String IMG_TAG = "HTML_TEXTVIEW_ESCAPED_IMG";
    public static final String P_TAG = "HTML_TEXTVIEW_ESCAPED_P";
    public static final String SPAN_TAG = "HTML_TEXTVIEW_ESCAPED_SPAN";
    private final TextPaint mTextPaint;
    private final Context mContext;
    private ColorStateList mOriginColors;
    private Html.ImageGetter mImageGetter;

    private static Pattern sTextAlignPattern;
    private static Pattern sTextIndentPattern;
    private static Pattern sFontSizePattern;
    private static Pattern sFontFamilyPattern;
    private static Pattern sForegroundColorPattern;
    private static Pattern sBackgroundColorPattern;
    private static Pattern sTextDecorationPattern;
    private int mFlags;

    public HtmlTagHandler(Context context, TextPaint textPaint, Html.ImageGetter imageGetter) {
        mTextPaint = textPaint;
        mContext = context;
        mImageGetter = imageGetter;
    }

    /**
     * Newer versions of the Android SDK's {@link Html.TagHandler} handles &lt;ul&gt; and &lt;li&gt;
     * tags itself which means they never get delegated to this class. We want to handle the tags
     * ourselves so before passing the string html into Html.fromHtml(), we can use this method to
     * replace the &lt;ul&gt; and &lt;li&gt; tags with tags of our own.
     *
     * @see <a href="https://github.com/android/platform_frameworks_base/commit/8b36c0bbd1503c61c111feac939193c47f812190">Specific Android SDK Commit</a>
     *
     * @param html        String containing HTML, for example: "<b>Hello world!</b>"
     * @return html with replaced <ul> and <li> tags
     */
    String overrideTags(@Nullable String html){

        if (html == null) return null;

        html = html.replace("<ul", "<" + UNORDERED_LIST);
        html = html.replace("</ul>", "</" + UNORDERED_LIST + ">");
        html = html.replace("<ol", "<" + ORDERED_LIST);
        html = html.replace("</ol>", "</" + ORDERED_LIST + ">");
        html = html.replace("<li", "<" + LIST_ITEM);
        html = html.replace("</li>", "</" + LIST_ITEM + ">");
        html = html.replace("<latex", "<" + LATEX_TAG);
        html = html.replace("</latex>", "</" + LATEX_TAG + ">");

        html = html.replace("<img", "<" + IMG_TAG);
        html = html.replace("</img>", "</" + IMG_TAG + ">");

        html = html.replace("<p", "<" + P_TAG);
        html = html.replace("</p>", "</" + P_TAG + ">");

        html = html.replace("<span", "<" + SPAN_TAG);
        html = html.replace("</span>", "</" + SPAN_TAG + ">");
        return html;
    }

    /**
     * Keeps track of lists (ol, ul). On bottom of Stack is the outermost list
     * and on top of Stack is the most nested list
     */
    Stack<String> lists = new Stack<>();
    /**
     * Tracks indexes of ordered lists so that after a nested list ends
     * we can continue with correct index of outer list
     */
    Stack<Integer> olNextIndex = new Stack<>();
    /**
     * List indentation in pixels. Nested lists use multiple of this.
     */
    /**
     * Running HTML table string based off of the root table tag. Root table tag being the tag which
     * isn't embedded within any other table tag. Example:
     * <!-- This is the root level opening table tag. This is where we keep track of tables. -->
     * <table>
     * ...
     * <table> <!-- Non-root table tags -->
     * ...
     * </table>
     * ...
     * </table>
     * <!-- This is the root level closing table tag and the end of the string we track. -->
     */
    StringBuilder tableHtmlBuilder = new StringBuilder();
    /**
     * Tells us which level of table tag we're on; ultimately used to find the root table tag.
     */
    int tableTagLevel = 0;

    private static int userGivenIndent = -1;
    private static final int defaultIndent = 10;
    private static final int defaultListItemIndent = defaultIndent * 2;
    private static final BulletSpan defaultBullet = new BulletSpan(defaultIndent);
    private ClickableTableSpan clickableTableSpan;
    private DrawTableLinkSpan drawTableLinkSpan;

    private static class Strikethrough { }

    private static class FontFamily{
        Typeface fontName;
        FontFamily(Typeface name) {
            fontName = name;
        }

        public Typeface getFontName() {
            return fontName;
        }
    }

    private static class FontSizes{
        int size;

        FontSizes(int s){
            size = s;
        }

        public int getSize() {
            return size;
        }
    }

    private static class Ul {
    }

    private static class Ol {
    }

    private static class Code {
    }

    private static class Center {
    }

    private static class Right {
    }

    private static class Strike {
    }

    private static class Table {
    }

    private static class Tr {
    }

    private static class Th {
    }

    private static class Td {
    }

    private static class LaText {
    }


    @Override
    public void handleTag(final boolean opening, final String tag, Editable output, final XMLReader xmlReader) {
        if (opening) {
            // opening tag
            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "opening, output: " + output.toString());
            }

            if (tag.equalsIgnoreCase(UNORDERED_LIST)) {
                lists.push(tag);
            } else if (tag.equalsIgnoreCase(ORDERED_LIST)) {
                lists.push(tag);
                olNextIndex.push(1);
            } else if (tag.equalsIgnoreCase(LIST_ITEM)) {
                if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                    output.append("\n");
                }
                if (!lists.isEmpty()) {
                    String parentList = lists.peek();
                    if (parentList.equalsIgnoreCase(ORDERED_LIST)) {
                        start(output, new Ol());
                        olNextIndex.push(olNextIndex.pop() + 1);
                    } else if (parentList.equalsIgnoreCase(UNORDERED_LIST)) {
                        start(output, new Ul());
                    }
                }
            } else if (tag.equalsIgnoreCase(LATEX_TAG)) {
                start(output, new LaText());
            } else if (tag.equalsIgnoreCase("code")) {
                start(output, new Code());
            } else if (tag.equalsIgnoreCase("center")) {
                start(output, new Center());
            }else if (tag.equalsIgnoreCase("right")) {
                start(output, new Right());
            } else if (tag.equalsIgnoreCase("s") || tag.equalsIgnoreCase("strike")) {
                start(output, new Strike());
            } else if (tag.equalsIgnoreCase("table")) {
                start(output, new Table());
                if (tableTagLevel == 0) {
                    tableHtmlBuilder = new StringBuilder();
                    // We need some text for the table to be replaced by the span because
                    // the other tags will remove their text when their text is extracted
//                    output.append("table placeholder");
                }

                tableTagLevel++;
            } else if (tag.equalsIgnoreCase("tr")) {
                startBlockElement(output, getProperty(xmlReader,"style"), getMarginParagraph());
                start(output, new Tr());
            } else if (tag.equalsIgnoreCase("th")) {
                start(output, new Th());
            } else if (tag.equalsIgnoreCase("td")) {
                start(output, new Td());
            }else if (tag.equalsIgnoreCase(IMG_TAG)) {
                startImg(output, xmlReader);
            }else if (tag.equalsIgnoreCase(P_TAG)) {
                if(tableTagLevel <= 0){
                    startBlockElement(output, getProperty(xmlReader,"style"), getMarginParagraph());
                }

                startCssStyle(output, getProperty(xmlReader,"style"));
            }else if (tag.equalsIgnoreCase(SPAN_TAG)) {
                startCssStyle(output, getProperty(xmlReader,"style"));
            }
        } else {
            // closing tag
            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "closing, output: " + output.toString());
            }

            if (tag.equalsIgnoreCase(UNORDERED_LIST)) {
                lists.pop();
            } else if (tag.equalsIgnoreCase(ORDERED_LIST)) {
                lists.pop();
                olNextIndex.pop();
            } else if (tag.equalsIgnoreCase(LIST_ITEM)) {
                if (!lists.isEmpty()) {
                    int listItemIndent = (userGivenIndent > -1) ? (userGivenIndent * 2) : defaultListItemIndent;
                    if (lists.peek().equalsIgnoreCase(UNORDERED_LIST)) {
                        if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                            output.append("\n");
                        }
                        // Nested BulletSpans increases distance between bullet and text, so we must prevent it.
                        int indent = (userGivenIndent > -1) ? userGivenIndent : defaultIndent;
                        BulletSpan bullet = (userGivenIndent > -1) ? new BulletSpan(userGivenIndent) : defaultBullet;
                        if (lists.size() > 1) {
                            indent = indent - bullet.getLeadingMargin(true);
                            if (lists.size() > 2) {
                                // This get's more complicated when we add a LeadingMarginSpan into the same line:
                                // we have also counter it's effect to BulletSpan
                                indent -= (lists.size() - 2) * listItemIndent;
                            }
                        }
                        BulletSpan newBullet = new BulletSpan(indent);
                        end(output, Ul.class, false,
                                new LeadingMarginSpan.Standard(listItemIndent * (lists.size() - 1)),
                                newBullet);
                    } else if (lists.peek().equalsIgnoreCase(ORDERED_LIST)) {
                        if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
                            output.append("\n");
                        }

                        // Nested NumberSpans increases distance between number and text, so we must prevent it.
                        int indent = (userGivenIndent > -1) ? userGivenIndent : defaultIndent;
                        NumberSpan span = new NumberSpan(indent, olNextIndex.lastElement() - 1);
                        if (lists.size() > 1) {
                            indent = indent - span.getLeadingMargin(true);
                            if (lists.size() > 2) {
                                // As with BulletSpan, we need to compensate for the spacing after the number.
                                indent -= (lists.size() - 2) * listItemIndent;
                            }
                        }
                        NumberSpan numberSpan = new NumberSpan(indent, olNextIndex.lastElement() - 1);
                        end(output, Ol.class, false,
                                new LeadingMarginSpan.Standard(listItemIndent * (lists.size() - 1)),
                                numberSpan);
                    }
                }
            } else if (tag.equalsIgnoreCase(LATEX_TAG)) {
                Object span = new TypefaceSpan("monospace");

                Object obj = getLast(output, LaText.class);
                int where = output.getSpanStart(obj);
                int len = output.length();
                TeXFormula teXFormula = TeXFormula.getPartialTeXFormula(output.subSequence(where,len).toString());

                try {
                    Bitmap bitmap = getBitmap(teXFormula);
                    if (bitmap.getWidth() > MAX_IMAGE_WIDTH) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, MAX_IMAGE_WIDTH,
                                bitmap.getHeight() * MAX_IMAGE_WIDTH / bitmap.getWidth(),
                                false);
                    }

                    span =  new CenteredImageSpan(mContext, bitmap);
                } catch (Exception e) {

                }
                end(output, LaText.class, false, span);
            } else if (tag.equalsIgnoreCase("code")) {
                Object span = new TypefaceSpan("monospace");

                Object obj = getLast(output, Code.class);
                int where = output.getSpanStart(obj);
                int len = output.length();
                TeXFormula teXFormula = TeXFormula.getPartialTeXFormula(output.subSequence(where,len).toString());

                try {
                    Bitmap bitmap = getBitmap(teXFormula);
                    if (bitmap.getWidth() > MAX_IMAGE_WIDTH) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, MAX_IMAGE_WIDTH,
                                bitmap.getHeight() * MAX_IMAGE_WIDTH / bitmap.getWidth(),
                                false);
                    }

                    span =  new CenteredImageSpan(mContext, bitmap);
                } catch (Exception e) {

                }
                end(output, Code.class, false, span);
//                end(output, Code.class, false, new TypefaceSpan("monospace"));
            } else if (tag.equalsIgnoreCase("center")) {
                end(output, Center.class, true, new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER));
            } else if (tag.equalsIgnoreCase("right")) {
                end(output, Right.class, true, new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE));
            }else if (tag.equalsIgnoreCase("s") || tag.equalsIgnoreCase("strike")) {
                end(output, Strike.class, false, new StrikethroughSpan());
            } else if (tag.equalsIgnoreCase("table")) {
                tableTagLevel--;

                // When we're back at the root-level table
                if (tableTagLevel == 0) {
                    final String tableHtml = tableHtmlBuilder.toString();

                    ClickableTableSpan myClickableTableSpan = null;
                    if (clickableTableSpan != null) {
                        myClickableTableSpan = clickableTableSpan.newInstance();
                        myClickableTableSpan.setTableHtml(tableHtml);
                    }

                    DrawTableLinkSpan myDrawTableLinkSpan = null;
                    if (drawTableLinkSpan != null) {
                        myDrawTableLinkSpan = drawTableLinkSpan.newInstance();
                    }

                    end(output, Table.class, false);
                } else {
                    end(output, Table.class, false);
                }
            } else if (tag.equalsIgnoreCase("tr")) {
                endBlockElement(output);
                end(output, Tr.class, false);
            } else if (tag.equalsIgnoreCase("th")) {
                end(output, Th.class, false);
            } else if (tag.equalsIgnoreCase("td")) {
                end(output, Td.class, false);
            }else if (tag.equalsIgnoreCase(P_TAG)) {
                endCssStyle(mContext,output);
                if(tableTagLevel <= 0) {
                    endBlockElement(output);
                }
            }else if (tag.equalsIgnoreCase(SPAN_TAG)) {
                endCssStyle(mContext,output);
            }
        }

//        storeTableTags(opening, tag);
    }

    /**
     * If we're arriving at a table tag or are already within a table tag, then we should store it
     * the raw HTML for our ClickableTableSpan
     */
    private void storeTableTags(boolean opening, String tag) {
        if (tableTagLevel > 0 || tag.equalsIgnoreCase("table")) {
            tableHtmlBuilder.append("<");
            if (!opening) {
                tableHtmlBuilder.append("/");
            }
            tableHtmlBuilder
                    .append(tag.toLowerCase())
                    .append(">");
        }
    }

    /**
     * Mark the opening tag by using private classes
     */
    private void start(Editable output, Object mark) {
        int len = output.length();
        output.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);

        if (HtmlTextView.DEBUG) {
            Log.d(HtmlTextView.TAG, "len: " + len);
        }
    }

    /**
     * Modified from {@link Html}
     */
    private void end(Editable output, Class kind, boolean paragraphStyle, Object... replaces) {
        Object obj = getLast(output, kind);
        // start of the tag
        int where = output.getSpanStart(obj);
        where = where<0?0:where;  //todo
        // end of the tag
        int len = output.length();

        // If we're in a table, then we need to store the raw HTML for later
//        if (tableTagLevel > 0) {
//            final CharSequence extractedSpanText = extractSpanText(output, kind);
//            tableHtmlBuilder.append(extractedSpanText);
//        }

        output.removeSpan(obj);

        if (where != len) {
            int thisLen = len;
            // paragraph styles like AlignmentSpan need to end with a new line!
            if (paragraphStyle) {
                output.append("\n");
                thisLen++;
            }
            for (Object replace : replaces) {
                output.setSpan(replace, where, thisLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (HtmlTextView.DEBUG) {
                Log.d(HtmlTextView.TAG, "where: " + where);
                Log.d(HtmlTextView.TAG, "thisLen: " + thisLen);
            }
        }
    }

    /**
     * Returns the text contained within a span and deletes it from the output string
     */
    private CharSequence extractSpanText(Editable output, Class kind) {
        final Object obj = getLast(output, kind);
        // start of the tag
        final int where = output.getSpanStart(obj);
        // end of the tag
        final int len = output.length();

        final CharSequence extractedSpanText = output.subSequence(where, len);
        output.delete(where, len);
        return extractedSpanText;
    }

    /**
     * Get last marked position of a specific tag kind (private class)
     */
    private static <T> T getLast(Editable text, Class<T> kind) {
        T[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        } else {
            for (int i = objs.length; i > 0; i--) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1];
                }
            }
            return null;
        }
    }

    // Util method for setting pixels.
    public void setListIndentPx(float px) {
        userGivenIndent = Math.round(px);
    }

    public void setClickableTableSpan(ClickableTableSpan clickableTableSpan) {
        this.clickableTableSpan = clickableTableSpan;
    }

    public void setDrawTableLinkSpan(DrawTableLinkSpan drawTableLinkSpan) {
        this.drawTableLinkSpan = drawTableLinkSpan;
    }


    private Bitmap getBitmap(TeXFormula formula) {
        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(TeXConstants.STYLE_DISPLAY)
                .setSize(mTextPaint.getTextSize() / mTextPaint.density)
                .setWidth(TeXConstants.UNIT_SP, mTextPaint.getTextSize() / mTextPaint.density, TeXConstants.ALIGN_LEFT)
                .setIsMaxWidth(true)
                .setInterLineSpacing(TeXConstants.UNIT_SP,
                        AjLatexMath.getLeading(mTextPaint.getTextSize() / mTextPaint.density))
                .build();
        icon.setInsets(new Insets(5, 5, 5, 5));

        Bitmap image = Bitmap.createBitmap(icon.getIconWidth(), icon.getIconHeight(),
                Bitmap.Config.ARGB_4444);

        Canvas g2 = new Canvas(image);
        g2.drawColor(Color.TRANSPARENT);
        icon.paintIcon(g2, 0, 0);
        return image;
    }

    //根据img标签里的大小给drawable设置大小，
    private void startImg(Editable text, XMLReader xmlReader) {
        String src = getProperty(xmlReader, "src");
        Drawable d = null;

        if (mImageGetter != null) {
            d = mImageGetter.getDrawable(src);
        }

        if (d == null) {
            d = mContext.getResources().
                    getDrawable(R.mipmap.ic_pic_error);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        }

        setPreferBounds(getProperty(xmlReader, "width")
                ,getProperty(xmlReader, "height"),d);

        int len = text.length();
        text.append("\uFFFC");

        text.setSpan(new CenteredImageSpan(d, src), len, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableImageSpan clickableImageSpan = new ClickableImageSpan(src);
        text.setSpan(clickableImageSpan, len, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    //从 7px 这种类型的文字中解析出尺寸数值,defaultSize 在imagegetter那边已经做了最大尺寸现在
    private void setPreferBounds(String strWidth, String strHeight, Drawable d){
        if(TextUtils.isEmpty(strWidth) || TextUtils.isEmpty(strHeight)){
            return;
        }

        String strW = strWidth.trim().split("px")[0];
        String strH = strHeight.trim().split("px")[0];
        try{
            int sizeW = Integer.parseInt(strW);
            int sizeH = Integer.parseInt(strH);
            float widthLimit = DeviceUtils.getScreenWidth(mContext) - 3.5f* ArmsUtils.dip2px(mContext,25);

            float scale = Resources.getSystem().getDisplayMetrics().density;
            if(scale * sizeW > widthLimit){
                scale = widthLimit/sizeW;
            }
            d.setBounds(0,0,(int)(sizeW*scale),(int)(sizeH*scale));
        }catch (NumberFormatException e){

        }
    }

    /**
     * 利用反射获取html标签的属性值
     *
     * @param xmlReader
     * @param property
     * @return
     */
    private String getProperty(XMLReader xmlReader, String property) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);

            for (int i = 0; i < len; i++) {
                // 这边的property换成你自己的属性名就可以了
                if (property.equals(data[i * 5 + 1])) {
                    return data[i * 5 + 4];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void startBlockElement(Editable text, String style, int margin) {
        final int len = text.length();
        if (margin > 0) {
            appendNewlines(text, margin);
            blstart(text, new Newline(margin));
        }

//        String style = attributes.getValue("", "style");
        if (style != null) {
            Matcher m = getTextAlignPattern().matcher(style);
            if (m.find()) {
                String alignment = m.group(1);
                if (alignment.equalsIgnoreCase("start")) {
                    blstart(text, new Alignment(Layout.Alignment.ALIGN_NORMAL));
                } else if (alignment.equalsIgnoreCase("center")) {
                    blstart(text, new Alignment(Layout.Alignment.ALIGN_CENTER));
                } else if (alignment.equalsIgnoreCase("end")) {
                    blstart(text, new Alignment(Layout.Alignment.ALIGN_OPPOSITE));
                }
            }
        }
    }

    private static void endBlockElement(Editable text) {
        Newline n = getLast(text, Newline.class);
        if (n != null) {
            appendNewlines(text, n.mNumNewlines);
            text.removeSpan(n);
        }

        Alignment a = getLast(text, Alignment.class);
        if (a != null) {
            setSpanFromMark(text, a, new AlignmentSpan.Standard(a.mAlignment));
        }
    }

    private static void setSpanFromMark(Spannable text, Object mark, Object... spans) {
        int where = text.getSpanStart(mark);
        text.removeSpan(mark);
        int len = text.length();
        if (where != len) {
            for (Object span : spans) {
                text.setSpan(span, where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }


    private static Pattern getTextAlignPattern() {
        if (sTextAlignPattern == null) {
            sTextAlignPattern = Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b");
        }
        return sTextAlignPattern;
    }

    private static void appendNewlines(Editable text, int minNewline) {
        final int len = text.length();

        if (len == 0) {
            return;
        }

        int existingNewlines = 0;
        for (int i = len - 1; i >= 0 && text.charAt(i) == '\n'; i--) {
            existingNewlines++;
        }

        for (int j = existingNewlines; j < minNewline; j++) {
            text.append("\n");
        }
    }

    private static void blstart(Editable text, Object mark) {
        int len = text.length();
        text.setSpan(mark, len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private static class Alignment {
        private Layout.Alignment mAlignment;

        public Alignment(Layout.Alignment alignment) {
            mAlignment = alignment;
        }
    }

    private static class Newline {
        private int mNumNewlines;

        public Newline(int numNewlines) {
            mNumNewlines = numNewlines;
        }
    }
    private int getHtmlColor(String color) {
        try {
            return Color.parseColor(color);
        }catch (IllegalArgumentException e){
            return -1;
        }
    }

    private void startCssStyle(Editable text, String style) {
        if (style != null) {

            Matcher m = getTextIndentPattern().matcher(style);
            if (m.find()) {
                try {
                    Integer indentSize = Integer.parseInt(m.group(1).split("px")[0]);
                    if (indentSize > 0) {
                        for(int k=0;k< indentSize/8;k++){
                            text.append(" ");
                        }
                    }
                }catch (NumberFormatException e){}
            }

            m = getForegroundColorPattern().matcher(style);
            if (m.find()) {
                int c = getHtmlColor(m.group(1));
                if (c != -1) {
                    start(text, new Foreground(c | 0xFF000000));
                }
            }

            m = getsFontSizePattern().matcher(style);
            if (m.find()) {
                try {
                    Integer fontSize = Integer.parseInt(m.group(1).split("px")[0]);
                    if (fontSize > 0) {
                        start(text, new FontSizes(fontSize));
                    }
                }catch (NumberFormatException e){

                }
            }

            m = getsFontFamilyPattern().matcher(style);
            if (m.find()) {
                String fontFamily = m.group(1);
//                Timber.tag(TAG).d("fontFamily: "+fontFamily);
                Typeface tf = TypefaceHolder.getInstance().getSpecialTypeface(fontFamily);
                if(tf != null){
                    start(text, new FontFamily(tf));
                }
            }

            m = getBackgroundColorPattern().matcher(style);
            if (m.find()) {
                int c = getHtmlColor(m.group(1));
                if (c != -1) {
                    start(text, new Background(c | 0xFF000000));
                }
            }

            m = getTextDecorationPattern().matcher(style);
            if (m.find()) {
                String textDecoration = m.group(1);
                if (textDecoration.equalsIgnoreCase("line-through")) {
                    start(text, new Strikethrough());
                }
            }
        }
    }

    private static Pattern getTextIndentPattern() {
        if (sTextIndentPattern == null) {
            sTextIndentPattern = Pattern.compile(
                    "(?:\\s+|\\A)text-indent\\s*:\\s*(\\S*)\\b");
        }
        return sTextIndentPattern;
    }

    private static Pattern getsFontFamilyPattern() {
        if (sFontFamilyPattern == null) {
            sFontFamilyPattern = Pattern.compile(
                    "(?:\\s+|\\A)font-family\\s*:\\s*(\\S*)\\b");
        }
        return sFontFamilyPattern;
    }

    private static Pattern getsFontSizePattern() {
        if (sFontSizePattern == null) {
            sFontSizePattern = Pattern.compile(
                    "(?:\\s+|\\A)font-size\\s*:\\s*(\\S*)\\b");
        }
        return sFontSizePattern;
    }

    private static Pattern getForegroundColorPattern() {
        if (sForegroundColorPattern == null) {
            sForegroundColorPattern = Pattern.compile(
                    "(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b");
        }
        return sForegroundColorPattern;
    }

    private static Pattern getBackgroundColorPattern() {
        if (sBackgroundColorPattern == null) {
            sBackgroundColorPattern = Pattern.compile(
                    "(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b");
        }
        return sBackgroundColorPattern;
    }

    private static Pattern getTextDecorationPattern() {
        if (sTextDecorationPattern == null) {
            sTextDecorationPattern = Pattern.compile(
                    "(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b");
        }
        return sTextDecorationPattern;
    }

    private static void endCssStyle(Context context,Editable text) {
        Strikethrough s = getLast(text, Strikethrough.class);
        if (s != null) {
            setSpanFromMark(text, s, new StrikethroughSpan());
        }

        Background b = getLast(text, Background.class);
        if (b != null) {
            setSpanFromMark(text, b, new BackgroundColorSpan(b.mBackgroundColor));
        }

        Foreground f = getLast(text, Foreground.class);
        if (f != null) {
            setSpanFromMark(text, f, new ForegroundColorSpan(f.mForegroundColor));
        }

        FontSizes fs = getLast(text, FontSizes.class);
        if (fs != null) {
            setSpanFromMark(text, fs, new AbsoluteSizeSpan(ArmsUtils.dip2px(context,fs.getSize())));
        }

        FontFamily fontFamily = getLast(text, FontFamily.class);
        if (fontFamily != null) {
            setSpanFromMark(text, fontFamily, new CusFontFamilySpan(fontFamily.getFontName()));
        }
    }

    private static class Foreground {
        private int mForegroundColor;

        public Foreground(int foregroundColor) {
            mForegroundColor = foregroundColor;
        }
    }

    private static class Background {
        private int mBackgroundColor;

        public Background(int backgroundColor) {
            mBackgroundColor = backgroundColor;
        }
    }

    private int getMarginParagraph() {
        return getMargin(Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH);
    }

    private int getMargin(int flag) {
        if ((flag & mFlags) != 0) {
            return 1;
        }
        return 2;
    }
} 
