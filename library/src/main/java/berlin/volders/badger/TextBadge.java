/*
 * Copyright (C) 2016 volders GmbH with <3 in Berlin
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

package berlin.volders.badger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

/**
 * Base badge implementation for drawing a colored badge with text on it.#
 * The text is drawn centered on the badge and scaled to fit it.
 * Due to the limited space not all text will fit on it.
 * From Android Marshmallow this drawable also supports layout directions.
 */
public class TextBadge extends BadgeDrawable {

    private static final boolean WHOLO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    private static final boolean WMATE = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    private static final float MAGIC_TEXT_SCALE_FACTOR = 0.6f;

    private final BadgeShape shape;

    private final Paint badgePaint = new Paint();
    private final Paint textPaint = new Paint();
    private boolean paintPreparationNeeded = true;

    private String text = "";

    /**
     * @param context to read themed colors from
     * @param shape   of the badge
     * @see #TextBadge(BadgeShape, int, int)
     */
    public TextBadge(@NonNull Context context, @NonNull BadgeShape shape) {
        this(shape, badgeShapeColor(context), badgeTextColor(context));
    }

    /**
     * @param shape      of the badge
     * @param badgeColor to paint the badge shape with
     * @param textColor  to paint the {@code count} with
     */
    protected TextBadge(@NonNull BadgeShape shape, @ColorInt int badgeColor, @ColorInt int textColor) {
        this.shape = shape;
        badgePaint.setColor(badgeColor);
        textPaint.setColor(textColor);
    }

    /**
     * @param paint to prepare for drawing the badge
     */
    protected void onPrepareBadgePaint(@NonNull Paint paint) {
        paint.setAntiAlias(true);
    }

    /**
     * @param paint to prepare for drawing the text
     */
    protected void onPrepareTextPaint(@NonNull Paint paint) {
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    @SuppressLint("NewApi")
    public void draw(@NonNull Canvas canvas) {
        if (text.length() == 0) {
            return;
        }

        if (paintPreparationNeeded) {
            paintPreparationNeeded = false;
            onPrepareBadgePaint(badgePaint);
            onPrepareTextPaint(textPaint);
        }

        Rect rect = shape.draw(canvas, getBounds(), badgePaint, getLayoutDirection());
        textPaint.setTextSize(rect.height() * MAGIC_TEXT_SCALE_FACTOR);
        float x = rect.exactCenterX();
        float y = rect.exactCenterY() - (textPaint.ascent() + textPaint.descent()) * 0.5f;
        canvas.drawText(text, x, y, textPaint);
    }

    @Override
    public int getLayoutDirection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return super.getLayoutDirection();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.LAYOUT_DIRECTION_LTR;
        }
        //noinspection WrongConstant
        return 0; // LAYOUT_DIRECTION_LTR

    }

    @Override
    public boolean onLayoutDirectionChanged(int layoutDirection) {
        invalidateSelf();
        return true;
    }

    @Override
    @SuppressLint("NewApi")
    public void setAlpha(int alpha) {
        if (getAlpha() != alpha) {
            badgePaint.setAlpha(alpha);
            textPaint.setAlpha(alpha);
            super.setAlpha(alpha);
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (getColorFilter() != colorFilter) {
            badgePaint.setColorFilter(colorFilter);
            textPaint.setColorFilter(colorFilter);
            super.setColorFilter(colorFilter);
        }
    }

    /**
     * @param text to display in badge
     */
    public final void setText(@Nullable CharSequence text) {
        String trimmedText = text == null ? "" : text.toString().trim();
        if (!this.text.equals(trimmedText)) {
            this.text = trimmedText;
            invalidateSelf();
        }
    }

    /**
     * @return the text
     */
    public final String getText() {
        return text;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    static int badgeShapeColor(Context context) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        if (theme.resolveAttribute(R.attr.badgeShapeColor, typedValue, true)) {
            return typedValue.data;
        }
        if (theme.resolveAttribute(R.attr.colorAccent, typedValue, true)) {
            return typedValue.data;
        }
        if (WHOLO && theme.resolveAttribute(android.R.attr.colorAccent, typedValue, true)) {
            return typedValue.data;
        }
        if (WMATE) {
            return context.getResources().getColor(R.color.badgeShapeColor);
        }
        return context.getColor(R.color.badgeShapeColor);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    static int badgeTextColor(Context context) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        if (theme.resolveAttribute(R.attr.badgeTextColor, typedValue, true)) {
            return typedValue.data;
        }
        if (theme.resolveAttribute(R.attr.titleTextColor, typedValue, true)) {
            return typedValue.data;
        }
        if (WMATE) {
            return context.getResources().getColor(R.color.badgeTextColor);
        }
        if (theme.resolveAttribute(android.R.attr.titleTextColor, typedValue, true)) {
            return typedValue.data;
        }
        return context.getColor(R.color.badgeTextColor);
    }

    /**
     * {@link BadgeDrawable.Factory} extension for creating {@code TextBadge} instances.
     *
     * @param <T> the subtype of {@code TextBadge} to create
     */
    public static abstract class Factory<T extends TextBadge> implements BadgeDrawable.Factory<T> {

        /**
         * The badge shape
         */
        @NonNull
        protected final BadgeShape shape;
        /**
         * The badge color
         */
        @ColorInt
        protected final int badgeColor;
        /**
         * The text color
         */
        @ColorInt
        protected final int textColor;

        /**
         * @param context to read themed colors from
         * @param shape   of the badge
         */
        public Factory(@NonNull Context context, @NonNull BadgeShape shape) {
            this(shape, badgeShapeColor(context), badgeTextColor(context));
        }

        /**
         * @param shape      of the badge
         * @param badgeColor to paint the badge shape with
         * @param textColor  to paint the {@code count} with
         */
        public Factory(@NonNull BadgeShape shape, @ColorInt int badgeColor, @ColorInt int textColor) {
            this.shape = shape;
            this.badgeColor = badgeColor;
            this.textColor = textColor;
        }
    }
}
