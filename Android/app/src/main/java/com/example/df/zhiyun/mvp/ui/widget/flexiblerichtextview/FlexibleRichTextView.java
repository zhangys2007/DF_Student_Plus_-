package com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jianhao on 16-8-26.
 */
public class FlexibleRichTextView extends LinearLayout {
    private final static String TAG = "FlexibleRichTextView";
    static final int MAX_IMAGE_WIDTH = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.8);

    private Context mContext;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private int mConversationId;
    private List<Attachment> mAttachmentList;
    private OnViewClickListener mOnViewClickListener;

    private List<Tokenizer.TOKEN> mTokenList;
    private int mTokenIndex;

    private boolean mCenter;

    private boolean mShowRemainingAtt = true;

    private int mQuoteViewId = com.example.df.zhiyun.R.layout.default_quote_view;

    public FlexibleRichTextView(Context context) {
        this(context, null, true);

    }

    @SuppressWarnings("unused")
    public FlexibleRichTextView(Context context, OnViewClickListener onViewClickListener) {
        this(context, onViewClickListener, true);
    }

    @SuppressWarnings("unused")
    public FlexibleRichTextView(Context context, OnViewClickListener onViewClickListener, boolean showRemainingAtt) {
        super(context);
        init(context, onViewClickListener, showRemainingAtt);
    }

    public FlexibleRichTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public FlexibleRichTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    public void setToken(List<Tokenizer.TOKEN> tokens, List<Attachment> attachmentList) {
        removeAllViews();

        mAttachmentList = attachmentList;
        mTokenList = tokens;

        for (Tokenizer.TOKEN token : tokens) {
            if (token instanceof Tokenizer.ATTACHMENT) {
                mAttachmentList.remove(((Tokenizer.ATTACHMENT) token).attachment);
            }
        }

        resetTokenIndex();
        List<Object> result = until(Tokenizer.END.class);

        if (mShowRemainingAtt) {
            // remaining attachments will show at the bottom of view
            for (Attachment att : mAttachmentList) {
                append(result, attachment(att));
            }
        }

        if (result == null) {
            return;
        }

        for (final Object o : result) {
            if (o instanceof TextWithFormula) {
                final TextWithFormula textWithFormula = (TextWithFormula) o;

                final LaTeXtView textView = new LaTeXtView(mContext);

                textView.setTextWithFormula(textWithFormula);

                textView.setMovementMethod(LinkMovementMethod.getInstance());
                myAddView(textView);
            }  else if (o instanceof ImageView) {
                myAddView((ImageView) o);
            } else if (o instanceof HorizontalScrollView) {
                myAddView((HorizontalScrollView) o);
            } else if (o instanceof QuoteView) {
                myAddView((QuoteView) o);
            }
        }
    }

    public void setText(String text) {
        setText(text, new ArrayList<Attachment>());
    }

    public void setText(String text, List<Attachment> attachmentList) {
        text = text.replaceAll("\u00AD", "");

        mAttachmentList = attachmentList;
        mTokenList = Tokenizer.tokenizer(text, mAttachmentList);

        setToken(mTokenList, attachmentList);
    }

    private void myAddView(View view) {
        if (view instanceof FImageView && ((FImageView) view).centered) {
            RelativeLayout rl = new RelativeLayout(mContext);
            RelativeLayout.LayoutParams rlLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlLp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            rl.addView(view);
            rl.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(rl);
        } else {
            addView(view);
        }
    }

    private void resetTokenIndex() {
        mTokenIndex = 0;
    }

    private final Class[] start = {Tokenizer.CENTER_START.class, Tokenizer.BOLD_START.class, Tokenizer.ITALIC_START.class,
            Tokenizer.UNDERLINE_START.class, Tokenizer.DELETE_START.class, Tokenizer.CURTAIN_START.class, Tokenizer.TITLE_START.class,
            Tokenizer.COLOR_START.class, Tokenizer.URL_START.class};

    private final Class[] end = {Tokenizer.CENTER_END.class, Tokenizer.BOLD_END.class, Tokenizer.ITALIC_END.class,
            Tokenizer.UNDERLINE_END.class, Tokenizer.DELETE_END.class, Tokenizer.CURTAIN_END.class, Tokenizer.TITLE_END.class,
            Tokenizer.COLOR_END.class, Tokenizer.URL_END.class};

    private final String CENTER_OP = "center";
    private final String BOLD_OP = "bold";
    private final String ITALIC_OP = "italic";
    private final String UNDERLINE_OP = "underline";
    private final String DELETE_OP = "delete";
    private final String CURTAIN_OP = "curtain";
    private final String TITLE_OP = "title";
    private final String COLOR_OP = "color";
    private final String URL_OP = "url";

    private final String[] operation = {CENTER_OP, BOLD_OP, ITALIC_OP, UNDERLINE_OP, DELETE_OP, CURTAIN_OP, TITLE_OP, COLOR_OP, URL_OP};

    private <T extends Tokenizer.TOKEN> List<Object> until(Class<T> endClass) {
        List<Object> ret = new ArrayList<>();

        while (!(thisToken() instanceof Tokenizer.END) && !(endClass.isInstance(thisToken()))) {
            boolean flag = false;
            int tmp;

            for (Class anEnd : end) {
                if (anEnd.isInstance(thisToken())) {
                    append(ret, new TextWithFormula(thisToken().value));
                    flag = true;
                    break;
                }
            }

            for (int i = 0; i < start.length; i++) {
                if (start[i].isInstance(thisToken())) {
                    String operand = "";
                    if (thisToken() instanceof Tokenizer.CENTER_START) {
                        mCenter = true;
                    } else if (thisToken() instanceof Tokenizer.COLOR_START) {
                        operand = ((Tokenizer.COLOR_START) thisToken()).color;
                    } else if (thisToken() instanceof Tokenizer.URL_START) {
                        operand = ((Tokenizer.URL_START) thisToken()).url;
                    }

                    tmp = getTokenIndex();
                    next();
                    List<Object> shown = until(end[i]);
                    mCenter = false;
                    if (shown != null) {
                        concat(ret, operate(shown, operation[i], operand));
                    } else {
                        setTokenIndex(tmp);
                        append(ret, new TextWithFormula(thisToken().value));
                    }
                    flag = true;
                }
            }

            if (!flag) {
                if (thisToken() instanceof Tokenizer.PLAIN) {
                    append(ret, new TextWithFormula(thisToken().value));

                } else if (thisToken() instanceof Tokenizer.ICON) {
                    final Tokenizer.ICON thisToken = (Tokenizer.ICON) thisToken();

                    TextWithFormula textWithFormula = new TextWithFormula(thisToken.value);
                    textWithFormula.setSpan(new ImageSpan(mContext, thisToken.iconId), 0,
                            thisToken.value.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                    append(ret, textWithFormula);

                } else if (thisToken() instanceof Tokenizer.FORMULA) {

                    Tokenizer.FORMULA thisToken = (Tokenizer.FORMULA) thisToken();

                    TextWithFormula textWithFormula = new TextWithFormula(thisToken().value);

                    textWithFormula.addFormula(0, thisToken.value.length(),
                            thisToken.content, thisToken.contentStart,
                            thisToken.contentStart + thisToken.content.length());

                    append(ret, textWithFormula);

                }  else if (thisToken() instanceof Tokenizer.IMAGE) {

                    Tokenizer.IMAGE thisToken = (Tokenizer.IMAGE) thisToken();
                    FImageView imageView = loadImage(thisToken.url, thisToken.width, thisToken.height);
                    if (mCenter) {
                        imageView.centered = true;
                    }
                    append(ret, imageView);

                } else if (thisToken() instanceof Tokenizer.TABLE) {

                    View table = table(thisToken().value);
                    append(ret, table);

                } else if (thisToken() instanceof Tokenizer.ATTACHMENT) {

                    final Tokenizer.ATTACHMENT thisToken = (Tokenizer.ATTACHMENT) thisToken();

                    append(ret, attachment(thisToken.attachment));

                } else if (thisToken() instanceof Tokenizer.QUOTE_START) {
                    int i = 1;
                    List<Tokenizer.TOKEN> tokens = new ArrayList<>();
                    next();
                    while (!(thisToken() instanceof Tokenizer.END)) {
                        if (thisToken() instanceof Tokenizer.QUOTE_START) {
                            i++;
                            while (i > 0) {
                                next();
                                if (thisToken() instanceof Tokenizer.QUOTE_START) {
                                    i++;
                                } else if (thisToken() instanceof Tokenizer.QUOTE_END) {
                                    i--;
                                }
                            }
                        } else if (thisToken() instanceof Tokenizer.QUOTE_END) {
                            tokens.add(new Tokenizer.END(thisToken().position));
                            break;
                        } else {
                            tokens.add(thisToken());
                        }
                        next();
                    }

                    if (thisToken() instanceof Tokenizer.QUOTE_END) {
                        final QuoteView quoteView = QuoteView.newInstance(this, mQuoteViewId);
                        quoteView.setAttachmentList(mAttachmentList);
                        quoteView.setPadding(0, 8, 0, 8);
                        quoteView.setTokens(tokens);
                        quoteView.setOnButtonClickListener(mOnViewClickListener);
                        ret.add(quoteView);
                    } else {
                        append(ret, new TextWithFormula(thisToken().value));
                    }
                }
            }
            next();
        }

        if (endClass.isInstance(thisToken())) {
            return ret;
        }

        return null;
    }

    private Object attachment(final Attachment attachment) {
        if (attachment.isImage()) {
            String url = attachment.getUrl();
            FImageView imageView = loadImage(url);
            if (mCenter) {
                imageView.centered = true;
            }

            return imageView;
        } else {
            TextWithFormula builder = new TextWithFormula(attachment.getText());
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    if (mOnViewClickListener != null) {
                        mOnViewClickListener.onAttClick(attachment);
                    }
                }
            }, 0, attachment.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.append("\n\n");

            return builder;
        }
    }
    private void append(List<Object> list, Object element) {
        concat(list, Collections.singletonList(element));
    }

    private FImageView loadImage(String url) {
        return loadImage(url, -1);
    }

    private FImageView loadImage(String url, int size) {
        return loadImage(url, size, size);
    }

    private FImageView loadImage(String url, int width, int height) {
        final FImageView imageView = new FImageView(mContext);

        ViewGroup.LayoutParams layoutParams;

        int phWidth, phHeight, imgWidth, imgHeight;

        if (height != -1 && width != -1) {
            imgHeight = height;
            imgWidth = width;
            phHeight = height;
            phWidth = width;
        } else if (width != -1) {
            imgHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            imgWidth = width;
            phHeight = MAX_IMAGE_WIDTH / 2;
            phWidth = width;
        } else if (height != -1) {
            imgHeight = height;
            imgWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            phHeight = height;
            phWidth = MAX_IMAGE_WIDTH;
        } else {
            imgHeight = imgWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            phHeight = MAX_IMAGE_WIDTH / 2;
            phWidth = MAX_IMAGE_WIDTH;
        }

        if (imageView.centered) {
            layoutParams = new RelativeLayout.LayoutParams(phWidth, phHeight);
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        } else {
            layoutParams = new LayoutParams(phWidth, phHeight);
        }
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(0, 0, 0, 10);

        final int finalWidth = imgWidth;
        final int finalHeight = imgHeight;
        Glide.with(mContext)
                .load(url)
                .placeholder(new ColorDrawable(ContextCompat.getColor(mContext, android.R.color.darker_gray)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imageView.centered) {
                            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(finalWidth, finalHeight);
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                            imageView.setLayoutParams(params);
                        } else {
                            imageView.setLayoutParams(new LayoutParams(finalWidth, finalHeight));
                        }

                        imageView.setImageDrawable(resource);

                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mOnViewClickListener != null) {
                                    mOnViewClickListener.onImgClick(imageView);
                                }
                            }
                        });
                        return false;
                    }
                })
                .into(imageView);
        return imageView;
    }

    private List<Object> operate(List<Object> list, String operation, final String... operand) {
        switch (operation) {
            case BOLD_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new StyleSpan(Typeface.BOLD), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case CENTER_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else if (o instanceof ImageView) {
                        ((ImageView) o).setScaleType(ImageView.ScaleType.CENTER);
                    }
                }
                break;
            case ITALIC_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new StyleSpan(Typeface.ITALIC), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case UNDERLINE_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new UnderlineSpan(), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case DELETE_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new StrikethroughSpan(), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case CURTAIN_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new BackgroundColorSpan(Color.DKGRAY), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case TITLE_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new RelativeSizeSpan(1.3f), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                break;
            case COLOR_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        try {
                            int color = Color.parseColor(operand[0]);
                            textWithFormula.setSpan(new ForegroundColorSpan(color), 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        } catch (IllegalArgumentException e) {
                            // avoid crash caused by illegal color
                        }
                    }
                }
                break;
            case URL_OP:
                for (Object o : list) {
                    if (o instanceof TextWithFormula) {
                        final TextWithFormula textWithFormula = (TextWithFormula) o;
                        textWithFormula.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(operand[0])));
                            }
                        }, 0, textWithFormula.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
        }
        return list;
    }

    private List<Object> operate(List<Object> list, String operation) {
        return operate(list, operation, "");
    }

    private <T> void concat(List<Object> list1, List<T> list2) {
        if (list1.size() == 0) {
            list1.addAll(list2);
        } else {
            if (list2.size() > 0) {
                if (list1.get(list1.size() - 1) instanceof TextWithFormula &&
                        list2.get(0) instanceof TextWithFormula) {

                    TextWithFormula a = (TextWithFormula) list1.get(list1.size() - 1);
                    TextWithFormula b = (TextWithFormula) list2.get(0);
                    for (TextWithFormula.Formula formula : b.getFormulas()) {
                        formula.start += a.length();
                        formula.end += a.length();
                        formula.contentStart += a.length();
                        formula.contentEnd += a.length();
                    }
                    a.getFormulas().addAll(b.getFormulas());
                    a.append(b);

                    list1.addAll(list2.subList(1, list2.size()));
                } else {
                    list1.addAll(list2);
                }
            }
        }
    }

    private Tokenizer.TOKEN thisToken() {
        return mTokenList.get(mTokenIndex);
    }

    private void next() {
        mTokenIndex++;
    }

    public int getTokenIndex() {
        return mTokenIndex;
    }

    public void setTokenIndex(int tokenIndex) {
        this.mTokenIndex = tokenIndex;
    }

    private View table(CharSequence str) {
        final String SPECIAL_CHAR = "\uF487";
        Pattern pattern = Pattern.compile("(?:\\n|^)( *\\|.+\\| *\\n)??( *\\|(?: *:?----*:? *\\|)+ *\\n)((?: *\\|.+\\| *(?:\\n|$))+)");
        Matcher matcher = pattern.matcher(str);
        int[] margins;
        final int LEFT = 0, RIGHT = 1, CENTER = 2;

        if (matcher.find()) {

            List<String> headers = null;
            if (!TextUtils.isEmpty(matcher.group(1))) {
                String wholeHeader = matcher.group(1);

                headers = new ArrayList<>(Arrays.asList(wholeHeader.split("\\|")));
                format(headers);
            }

            List<String> partitions = new ArrayList<>(Arrays.asList(matcher.group(2).split("\\|")));
            format(partitions);
            final int columnNum = partitions.size();
            margins = new int[columnNum];

            for (int i = 0; i < partitions.size(); i++) {
                String partition = partitions.get(i);
                if (partition.startsWith(":") && partition.endsWith(":")) {
                    margins[i] = CENTER;
                } else if (partition.startsWith(":")) {
                    margins[i] = LEFT;
                } else if (partition.endsWith(":")) {
                    margins[i] = RIGHT;
                } else {
                    margins[i] = CENTER;
                }
            }

            String[] rows = matcher.group(3).replace("\\|", SPECIAL_CHAR).split("\n");
            final List<List<String>> content = new ArrayList<>();
            for (String row : rows) {
                content.add(format(new ArrayList<>(Arrays.asList(row.split("\\|")))));
            }

            final List<String[]> whole = new ArrayList<>();
            if (headers != null) {
                whole.add(headers.toArray(new String[columnNum]));
            }
            for (List<String> strings : content) {
                whole.add(strings.toArray(new String[columnNum]));
            }

            // render table
            HorizontalScrollView scrollView = new HorizontalScrollView(getContext());
            TableLayout tableLayout = new TableLayout(mContext);

            tableLayout.addView(getHorizontalDivider());
            for (int i = 0; i < whole.size(); i++) {
                String[] row = whole.get(i);
                TableRow tableRow = new TableRow(mContext);
                final TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(params);

                tableRow.addView(getVerticalDivider());
                for (int j = 0; j < row.length; j++) {
                    String cell = row[j];
                    if (cell != null) {
                        cell = cell.replace(SPECIAL_CHAR, "|");
                    }
                    FlexibleRichTextView flexibleRichTextView = FlexibleRichTextView.newInstance(getContext(), cell, mAttachmentList, mOnViewClickListener, false);
                    TableRow.LayoutParams pcvParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                    switch (margins[j]) {
                        case CENTER:
                            pcvParams.gravity = Gravity.CENTER;
                            break;
                        case LEFT:
                            pcvParams.gravity = Gravity.START;
                            break;
                        case RIGHT:
                            pcvParams.gravity = Gravity.END;
                            break;
                    }
                    flexibleRichTextView.setPadding(10, 10, 10, 10);
                    flexibleRichTextView.setLayoutParams(pcvParams);
                    tableRow.addView(flexibleRichTextView);
                    tableRow.addView(getVerticalDivider());
                }
                tableLayout.addView(tableRow);
                tableLayout.addView(getHorizontalDivider());
            }

            scrollView.addView(tableLayout);

            return scrollView;
        }

        return null;

    }


    private List<String> format(List<String> strings) {
        for (int i = strings.size() - 1; i >= 0; i--) {
            String str = strings.get(i);
            if (TextUtils.isEmpty(str) || str.equals("\n")) {
                strings.remove(i);
            }
        }

        for (int i = 0; i < strings.size(); i++) {
            strings.set(i, strings.get(i).trim());
        }

        return strings;
    }

    public static FlexibleRichTextView newInstance(Context context, String string,
                                                   List<Attachment> attachmentList,
                                                   OnViewClickListener onViewClickListener,
                                                   boolean showRemainingAtt) {

        FlexibleRichTextView flexibleRichTextView = new FlexibleRichTextView(context, onViewClickListener, showRemainingAtt);

        if (!TextUtils.isEmpty(string)) {
            flexibleRichTextView.setText(string, attachmentList);
        }

        return flexibleRichTextView;
    }

    private View getHorizontalDivider() {
        View horizontalDivider = new View(mContext);
        horizontalDivider.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        horizontalDivider.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.black));

        return horizontalDivider;
    }

    private View getVerticalDivider() {
        View verticalDivider = new View(mContext);
        verticalDivider.setLayoutParams(new TableRow.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        verticalDivider.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.black));

        return verticalDivider;
    }

    private void init(Context context) {
        init(context, null);
    }

    private void init(Context context, OnViewClickListener onViewClickListener) {
        init(context, onViewClickListener, true);
    }

    private void init(Context context, OnViewClickListener onViewClickListener, boolean showRemainingAtt) {
        setOrientation(VERTICAL);
        mOnViewClickListener = onViewClickListener;
        mContext = context;
        mShowRemainingAtt = showRemainingAtt;
        removeAllViews();
    }

    public void setOnClickListener(OnViewClickListener onViewClickListener) {
        mOnViewClickListener = onViewClickListener;
    }

    public int getConversationId() {
        return mConversationId;
    }

    public void setConversationId(int mConversationId) {
        this.mConversationId = mConversationId;
    }

    public void setQuoteViewId(int quoteViewId) {
        this.mQuoteViewId = quoteViewId;
    }

    public interface OnViewClickListener {
        void onImgClick(ImageView imageView);
        void onAttClick(Attachment attachment);
        void onQuoteButtonClick(View view, boolean collapsed);
    }
}
