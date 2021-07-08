package com.github.ivbaranov.mli;

import ohos.agp.components.AttrHelper;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * MaterialLetterIcon.
 */
public class MaterialLetterIcon extends Component implements Component.DrawTask {
    @Deprecated public static final int SHAPE_CIRCLE = 0;
    @Deprecated public static final int SHAPE_RECT = 1;
    @Deprecated public static final int SHAPE_ROUND_RECT = 2;
    @Deprecated public static final int SHAPE_TRIANGLE = 3;

    /**
     * Shape of icon.
     */
    public enum Shape { CIRCLE, RECT, ROUND_RECT, TRIANGLE }
    private static final Shape DEFAULT_SHAPE = Shape.CIRCLE;
    private static final Color DEFAULT_SHAPE_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_SIZE = 2;
    private static final boolean DEFAULT_BORDER = false;
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_LETTER_SIZE = 26;
    private static final int DEFAULT_LETTERS_NUMBER = 1;
    private static final Color DEFAULT_LETTER_COLOR = Color.WHITE;
    private static final String DEFAULT_FONT_PATH = "resources/rawfile/fonts/Roboto-Light.ttf";
    private static final int DEFAULT_INITIALS_NUMBER = 2;
    private static final boolean DEFAULT_INITIALS_STATE = false;
    private static final float DEFAULT_ROUND_RECT_RADIUS = 2;
    private static final String MLI_BORDER = "mli_border";
    private static final String MLI_BORDER_COLOR = "mli_border_color";
    private static final String MLI_BORDER_SIZE = "mli_border_size";
    private static final String MLI_INITIALS = "mli_initials";
    private static final String MLI_INITIALS_NUMBER = "mli_initials_number";
    private static final String MLI_SHAPE_COLOR = "mli_shape_color";
    private static final String MLI_SHAPE_TYPE = "mli_shape_type";
    private static final String MLI_LETTER_SIZE = "mli_letter_size";
    private static final String MLI_LETTER_COLOR = "mli_letter_color";
    private static final String MLI_LETTERS_NUMBER = "mli_letters_number";
    private static final String MLI_ROUND_RECT_RX = "mli_round_rect_rx";
    private static final String MLI_ROUND_RECT_RY = "mli_round_rect_ry";
    private static final String MLI_LETTER = "mli_letter";

    private Context context;
    private Paint mShapePaint;
    private Paint mBorderPaint;
    private Paint mLetterPaint;
    private Color mShapeColor;
    private boolean mBorder;
    private Color mBorderColor;
    private int mBorderSize;
    private Shape mShapeType;
    private String mLetter;
    private Color mLetterColor;
    private int mLetterSize;
    private int mLettersNumber;
    private boolean mInitials;
    private int mInitialsNumber;
    private String mOriginalLetter;
    private float mRoundRectRx;
    private float mRoundRectRy;

    public MaterialLetterIcon(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MaterialLetterIcon(Context context, AttrSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MaterialLetterIcon(Context context, AttrSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public MaterialLetterIcon(Context context, AttrSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Sets color to shape.
     *
     * @param color a color integer associated with a particular resource id
     */
    public void setShapeColor(Color color) {
        this.mShapeColor = color;
        invalidate();
    }

    /**
     * Sets border to shape.
     *
     * @param border if true, draws shape with border
     */
    public void setBorder(boolean border) {
        this.mBorder = border;
        invalidate();
    }

    /**
     * Sets border color.
     *
     * @param color a color integer associated with a particular resource id
     */
    public void setBorderColor(Color color) {
        this.mBorderColor = color;
        invalidate();
    }

    /**
     * Set size of border.
     *
     * @param borderSize size of border in DP
     */
    public void setBorderSize(int borderSize) {
        this.mBorderSize = borderSize;
        invalidate();
    }

    /**
     * Sets shape type. Please use {@code setShapeType(Shape shapeType)} instead.
     *
     * @param type one of shapes to draw: {@code MaterialLetterIcon.SHAPE_CIRCLE}, {@code
     * MaterialLetterIcon.SHAPE_RECT}, {@code MaterialLetterIcon.SHAPE_ROUND_RECT}, {@code
     * MaterialLetterIcon.SHAPE_TRIANGLE}
     */
    @Deprecated public void setShapeType(int type) {
        this.mShapeType = Shape.values()[0];
        invalidate();
    }

    /**
     * Sets shape type.
     *
     * @param shapeType one of shapes to draw: {@code MaterialLetterIcon.Shape.CIRCLE}, {@code
     * MaterialLetterIcon.Shape.RECT}, {@code MaterialLetterIcon.Shape.ROUND_RECT}, {@code
     * MaterialLetterIcon.Shape.TRIANGLE}
     */
    public void setShapeType(Shape shapeType) {
        this.mShapeType = shapeType;
        invalidate();
    }

    /**
     * Set letters.
     *
     * @param string a string to take letters from
     */
    public void setLetter(String string) {
        if (string == null) {
            return;
        }
        string = string.trim();
        if (string.isEmpty()) {
            return;
        }
        this.mOriginalLetter = string;
        int desireLength;
        if (mInitials) {
            String initials[] = string.split("\\s+");
            StringBuilder initialsPlain = new StringBuilder(mLettersNumber);
            for (String initial : initials) {
                initialsPlain.append(initial.substring(0, 1));
            }
            this.mLetter = initialsPlain.toString();
            desireLength = mInitialsNumber;
        } else {
            this.mLetter = String.valueOf(string.replaceAll("\\s+", ""));
            desireLength = mLettersNumber;
        }
        int stringLength =  desireLength > mLetter.length() ? mLetter.length() : desireLength;
        this.mLetter = mLetter.substring(0, stringLength).toUpperCase();
        invalidate();
    }

    /**
     * Set letters color.
     *
     * @param color a color integer associated with a particular resource id
     */
    public void setLetterColor(Color color) {
        this.mLetterColor = color;
        invalidate();
    }

    /**
     * Set letters size.
     *
     * @param size size of letter in SP
     */
    public void setLetterSize(int size) {
        this.mLetterSize = size;
        invalidate();
    }

    /**
     * Set number of letters to be displayed.
     *
     * @param num number of letters
     */
    public void setLettersNumber(int num) {
        this.mLettersNumber = num;
        invalidate();
    }

    /**
     * Set letters typeface.
     *
     * @param typeface a typeface to apply to letter
     */
    public void setLetterTypeface(Font typeface) {
        this.mLetterPaint.setFont(typeface);
        invalidate();
    }

    /**
     * Set initials state.
     *
     * @param state if true, gets first letter of {@code initialsNumber} words in provided string
     */
    public void setInitials(boolean state) {
        this.mInitials = state;
        setLetter(mOriginalLetter);
    }

    /**
     * Set number of initials to show.
     *
     * @param initialsNumber number of initials to show
     */
    public void setInitialsNumber(int initialsNumber) {
        this.mInitialsNumber = initialsNumber;
        setLetter(mOriginalLetter);
    }

    /**
     * Set the x-radius of the oval used to round the corners.
     *
     * @param rx x-radius of the oval
     */
    public void setRoundRectRx(float rx) {
        this.mRoundRectRx = rx;
        invalidate();
    }

    /**
     * Set the y-radius of the oval used to round the corners.
     *
     * @param ry y-radius of the oval
     */
    public void setRoundRectRy(float ry) {
        this.mRoundRectRy = ry;
        invalidate();
    }

    public Paint getShapePaint() {
        return mShapePaint;
    }

    public Paint getBorderPaint() {
        return mBorderPaint;
    }

    public boolean hasBorder() {
        return mBorder;
    }

    public Color getBorderColor() {
        return mBorderColor;
    }

    public int getBorderSize() {
        return mBorderSize;
    }

    public Paint getLetterPaint() {
        return mLetterPaint;
    }

    public Color getShapeColor() {
        return mShapeColor;
    }

    public Shape getShapeType() {
        return mShapeType;
    }

    public String getLetter() {
        return mLetter;
    }

    public Color getLetterColor() {
        return mLetterColor;
    }

    public int getLetterSize() {
        return mLetterSize;
    }

    public int getLettersNumber() {
        return mLettersNumber;
    }

    public boolean isInitials() {
        return mInitials;
    }

    public int getInitialsNumber() {
        return mInitialsNumber;
    }

    public float getRoundRectRx() {
        return mRoundRectRx;
    }

    public float getRoundRectRy() {
        return mRoundRectRy;
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private final Context context;
        private Color mShapeColor = DEFAULT_SHAPE_COLOR;
        private Shape mShapeType = DEFAULT_SHAPE;
        private boolean mBorder = DEFAULT_BORDER;
        private Color mBorderColor = DEFAULT_BORDER_COLOR;
        private int mBorderSize = DEFAULT_BORDER_SIZE;
        private String mLetter;
        private Color mLetterColor = DEFAULT_LETTER_COLOR;
        private int mLetterSize = DEFAULT_LETTER_SIZE;
        private int mLettersNumber = DEFAULT_LETTERS_NUMBER;
        private Font mLetterTypeface;
        private boolean mInitials = DEFAULT_INITIALS_STATE;
        private int mInitialsNumber = DEFAULT_INITIALS_NUMBER;
        private float mRoundRectRx = DEFAULT_ROUND_RECT_RADIUS;
        private float mRoundRectRy = DEFAULT_ROUND_RECT_RADIUS;

        public Builder(Context context) {
            this.context = context;
            this.mLetterTypeface = getFont(DEFAULT_FONT_PATH);
        }

        private Font getFont(String fontfamily) {
            byte[] buffer = null;
            int bytesRead = 0;
            FileOutputStream fileOutputStream = null;
            File file = new File(context.getCacheDir(), fontfamily);
            RawFileEntry rawFileEntry = context.getResourceManager().getRawFileEntry(fontfamily);
            try (Resource resource = rawFileEntry.openRawFile()) {
                buffer = new byte[(int) rawFileEntry.openRawFileDescriptor().getFileSize()];
                bytesRead = resource.read(buffer);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(buffer, 0, bytesRead);
                fileOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return new Font.Builder(file).build();
        }

        public Builder shapeColor(Color color) {
            this.mShapeColor = color;
            return this;
        }

        @Deprecated public Builder shapeType(int type) {
            this.mShapeType = Shape.values()[0];
            return this;
        }

        public Builder shapeType(Shape shapeType) {
            this.mShapeType = shapeType;
            return this;
        }

        public Builder border(boolean border) {
            this.mBorder = border;
            return this;
        }

        public Builder borderColor(Color borderColor) {
            this.mBorderColor = borderColor;
            return this;
        }

        public Builder borderSize(int borderSize) {
            this.mBorderSize = borderSize;
            return this;
        }

        public Builder letter(String letter) {
            this.mLetter = letter;
            return this;
        }

        public Builder letterColor(Color color) {
            this.mLetterColor = color;
            return this;
        }

        public Builder letterSize(int size) {
            this.mLetterSize = size;
            return this;
        }

        public Builder lettersNumber(int num) {
            this.mLettersNumber = num;
            return this;
        }

        public Builder letterTypeface(Font typeface) {
            this.mLetterTypeface = typeface;
            return this;
        }

        public Builder initials(boolean state) {
            this.mInitials = state;
            return this;
        }

        public Builder initialsNumber(int num) {
            this.mInitialsNumber = num;
            return this;
        }

        public Builder roundRectRx(float rx) {
            this.mRoundRectRx = rx;
            return this;
        }

        public Builder roundRectRy(float ry) {
            this.mRoundRectRy = ry;
            return this;
        }

        /**
         * Returns MaterialLetterIcon object based on params set by Builder.
         *
         * @return MaterialLetterIcon.
         */
        public MaterialLetterIcon create() {
            MaterialLetterIcon icon = new MaterialLetterIcon(context);
            icon.setShapeColor(mShapeColor);
            icon.setShapeType(mShapeType);
            icon.setBorder(mBorder);
            icon.setBorderColor(mBorderColor);
            icon.setBorderSize(mBorderSize);
            icon.setLetter(mLetter);
            icon.setLetterColor(mLetterColor);
            icon.setLetterSize(mLetterSize);
            icon.setLettersNumber(mLettersNumber);
            icon.setLetterTypeface(mLetterTypeface);
            icon.setInitials(mInitials);
            icon.setInitialsNumber(mInitialsNumber);
            icon.setRoundRectRx(mRoundRectRx);
            icon.setRoundRectRy(mRoundRectRy);
            return icon;
        }
    }

    /**
     * Initialize the default values.
     * <ul>
     * <li>shape color = black</li>
     * <li>border = false</li>
     * <li>border color = black</li>
     * <li>border size = 2 dp/li>
     * <li>shape type = circle</li>
     * <li>letter color = white</li>
     * <li>letter size = 26 sp</li>
     * <li>number of letters = 1</li>
     * <li>typeface = Roboto Light</li>
     * <li>initials = false</li>
     * <li>initials number = 2</li>
     * <li>round-rect x radius = 2 dp</li>
     * <li>round-rect y radius = 2 dp</li>
     * </ul>
     */
    private void init(Context context, AttrSet attrs, int defStyleAttr, int defStyleRes) {
        this.context = context;
        mBorder = DEFAULT_BORDER;
        mBorderColor = DEFAULT_BORDER_COLOR;
        mBorderSize = DEFAULT_BORDER_SIZE;
        mShapeType = DEFAULT_SHAPE;
        mShapeColor = DEFAULT_SHAPE_COLOR;
        mLetterSize = DEFAULT_LETTER_SIZE;
        mLetterColor = DEFAULT_LETTER_COLOR;
        mLettersNumber = DEFAULT_LETTERS_NUMBER;
        mInitials = DEFAULT_INITIALS_STATE;
        mInitialsNumber = DEFAULT_INITIALS_NUMBER;
        mRoundRectRx = DEFAULT_ROUND_RECT_RADIUS;
        mRoundRectRy = DEFAULT_ROUND_RECT_RADIUS;
        mShapePaint = new Paint();
        mShapePaint.setStyle(Paint.Style.FILL_STYLE);
        mShapePaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE_STYLE);
        mBorderPaint.setAntiAlias(true);
        mLetterPaint = new Paint();
        mLetterPaint.setAntiAlias(true);
        mLetterPaint.setFont(getFont(DEFAULT_FONT_PATH));
        if (attrs != null) {
            initAttributes(attrs);
        }
        addDrawTask(this::onDraw);
    }

    private Font getFont(String fontfamily) {
        byte[] buffer = null;
        int bytesRead = 0;
        FileOutputStream fileOutputStream = null;
        File file = new File(context.getCacheDir(), fontfamily);
        RawFileEntry rawFileEntry = context.getResourceManager().getRawFileEntry(fontfamily);
        try (Resource resource = rawFileEntry.openRawFile()) {
            buffer = new byte[(int) rawFileEntry.openRawFileDescriptor().getFileSize()];
            bytesRead = resource.read(buffer);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(buffer, 0, bytesRead);
            fileOutputStream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return new Font.Builder(file).build();
    }

    private void checkBorderAttr(AttrSet attrs) {
        if (attrs.getAttr(MLI_BORDER).isPresent() && attrs.getAttr(MLI_BORDER).get() != null) {
            mBorder = attrs.getAttr(MLI_BORDER).get().getBoolValue();
        }
        if (attrs.getAttr(MLI_BORDER_COLOR).isPresent() && attrs.getAttr(MLI_BORDER_COLOR).get() != null) {
            mBorderColor = attrs.getAttr(MLI_BORDER_COLOR).get().getColorValue();
        }
        if (attrs.getAttr(MLI_BORDER_SIZE).isPresent() && attrs.getAttr(MLI_BORDER_SIZE).get() != null) {
            mBorderSize = attrs.getAttr(MLI_BORDER_SIZE).get().getIntegerValue();
        }
    }

    private void checkInitialAttr(AttrSet attrs) {
        if (attrs.getAttr(MLI_INITIALS).isPresent() && attrs.getAttr(MLI_INITIALS).get() != null) {
            mInitials = attrs.getAttr(MLI_INITIALS).get().getBoolValue();
        }
        if (attrs.getAttr(MLI_INITIALS_NUMBER).isPresent() && attrs.getAttr(MLI_INITIALS_NUMBER).get() != null) {
            mInitialsNumber = attrs.getAttr(MLI_INITIALS_NUMBER).get().getIntegerValue();
        }
    }

    private void checkShapeAttr(AttrSet attrs) {
        if (attrs.getAttr(MLI_SHAPE_COLOR).isPresent() && attrs.getAttr(MLI_SHAPE_COLOR).get() != null) {
            mShapeColor = attrs.getAttr(MLI_SHAPE_COLOR).get().getColorValue();
        }
        if (attrs.getAttr(MLI_SHAPE_TYPE).isPresent() && attrs.getAttr(MLI_SHAPE_TYPE).get() != null) {
            mShapeType = Shape.values()[attrs.getAttr(MLI_SHAPE_TYPE).get().getIntegerValue()];
        }
    }

    private void checkLetterAttr(AttrSet attrs) {
        if (attrs.getAttr(MLI_LETTER_SIZE).isPresent() && attrs.getAttr(MLI_LETTER_SIZE).get() != null) {
            mLetterSize = attrs.getAttr(MLI_LETTER_SIZE).get().getIntegerValue();
        }
        if (attrs.getAttr(MLI_LETTER_COLOR).isPresent() && attrs.getAttr(MLI_LETTER_COLOR).get() != null) {
            mLetterColor = attrs.getAttr(MLI_LETTER_COLOR).get().getColorValue();
        }
        if (attrs.getAttr(MLI_LETTERS_NUMBER).isPresent() && attrs.getAttr(MLI_LETTERS_NUMBER).get() != null) {
            mLettersNumber = attrs.getAttr(MLI_LETTERS_NUMBER).get().getIntegerValue();
        }
        if (attrs.getAttr(MLI_LETTER).isPresent() && attrs.getAttr(MLI_LETTER).get() != null) {
            mOriginalLetter = attrs.getAttr(MLI_LETTER).get().getStringValue();
            if (mOriginalLetter != null) {
                setLetter(mOriginalLetter);
            }
        }
    }

    private void checkIconAttr(AttrSet attrs) {
        if (attrs.getAttr(MLI_ROUND_RECT_RX).isPresent() && attrs.getAttr(MLI_ROUND_RECT_RX).get() != null) {
            mRoundRectRx = attrs.getAttr(MLI_ROUND_RECT_RX).get().getFloatValue();
        }
        if (attrs.getAttr(MLI_ROUND_RECT_RY).isPresent() && attrs.getAttr(MLI_ROUND_RECT_RY).get() != null) {
            mRoundRectRy = attrs.getAttr(MLI_ROUND_RECT_RY).get().getFloatValue();
        }
    }


    private void initAttributes(AttrSet attrs) {
        if (attrs != null) {
            checkIconAttr(attrs);
            checkShapeAttr(attrs);
            checkLetterAttr(attrs);
            checkBorderAttr(attrs);
            checkInitialAttr(attrs);
        }
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        int viewWidthHalf = this.getEstimatedWidth() / 2;
        int viewHeightHalf = this.getEstimatedHeight() / 2;
        int radius;
        if (viewWidthHalf > viewHeightHalf) {
            radius = viewHeightHalf;
        } else {
            radius = viewWidthHalf;
        }
        switch (mShapeType) {
            case CIRCLE:
                drawCircle(canvas, radius, viewWidthHalf, viewHeightHalf);
                break;
            case RECT:
                drawRect(canvas, this.getWidth(), this.getWidth());
                break;
            case ROUND_RECT:
                drawRoundRect(canvas, this.getWidth(), this.getWidth());
                break;
            case TRIANGLE:
                drawTriangle(canvas);
                break;
            default:
                drawCircle(canvas, radius, viewWidthHalf, viewHeightHalf);
                break;
        }
        if (mLetter != null) {
            drawLetter(canvas, viewWidthHalf, viewHeightHalf);
        }
    }


    private void drawCircle(Canvas canvas, int radius, int width, int height) {
        mShapePaint.setColor(mShapeColor);
        canvas.drawCircle(width, height, radius, mShapePaint);
        if (mBorder) {
            final int borderPx = AttrHelper.fp2px(mBorderSize, context);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(borderPx);
            canvas.drawCircle(width, height, radius - borderPx / 2, mBorderPaint);
        }
    }

    private void drawRect(Canvas canvas, int width, int height) {
        mShapePaint.setColor(mShapeColor);
        canvas.drawRect(0, 0, width, height, mShapePaint);
        if (mBorder) {
            final int borderPx = AttrHelper.fp2px(mBorderSize, context);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(borderPx);
            canvas.drawRect(borderPx / 2, borderPx / 2, width - borderPx / 2, height - borderPx / 2,
                    mBorderPaint);
        }
    }

    private void drawRoundRect(Canvas canvas, float width, float height) {
        mShapePaint.setColor(mShapeColor);
        int rectRxPx = AttrHelper.fp2px(mRoundRectRx, context);
        int rectRyPx = AttrHelper.fp2px(mRoundRectRy, context);
        if (mBorder) {
            final int borderPx = AttrHelper.fp2px(mBorderSize, context);
            canvas.drawRoundRect(
                    new RectFloat(borderPx / 2, borderPx / 2, width - borderPx / 2, height - borderPx / 2),
                    rectRxPx, rectRyPx, mShapePaint);

            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(borderPx);
            canvas.drawRoundRect(
                    new RectFloat(borderPx / 2, borderPx / 2, width - borderPx / 2, height - borderPx / 2),
                    rectRxPx, rectRyPx, mBorderPaint);
        } else {
            canvas.drawRoundRect(new RectFloat(0, 0, width, height), rectRxPx, rectRyPx, mShapePaint);
        }
    }

    private void drawTriangle(Canvas canvas) {
        Rect bounds = canvas.getLocalClipBounds();
        Path triangle = new Path();
        triangle.moveTo(bounds.left + bounds.right / 10, bounds.bottom - bounds.bottom / 5);
        triangle.lineTo(bounds.left + (bounds.right - bounds.left) / 2, bounds.top);
        triangle.lineTo(bounds.right - bounds.right / 10, bounds.bottom - bounds.bottom / 5);
        triangle.lineTo(bounds.left + bounds.right / 10, bounds.bottom - bounds.bottom / 5);
        mShapePaint.setColor(mShapeColor);
        mShapePaint.setStyle(Paint.Style.FILL_STYLE);
        canvas.drawPath(triangle, mShapePaint);
    }

    private void drawLetter(Canvas canvas, float cx, float cy) {
        mLetterPaint.setColor(mLetterColor);
        mLetterPaint.setTextSize(AttrHelper.fp2px(mLetterSize, context));
        Rect textBounds = mLetterPaint.getTextBounds(mLetter);
        canvas.drawText(mLetterPaint, mLetter, cx - textBounds.getPreciseHorizontalCenter(), cy - textBounds.getPreciseVerticalCenter());
    }

}
