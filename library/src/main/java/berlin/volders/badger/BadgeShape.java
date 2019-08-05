/*
 * Copyright (C) 2016 Christian Schmitz
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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import android.view.Gravity;

/**
 * The {@code BadgeShape} is a simple {@link Shape} like structure to bypass limitation drawing
 * shapes with hardware acceleration and customize its behavior for {@code Badger}.
 */
public abstract class BadgeShape {

    private final Rect rect = new Rect();

    private final float scale;
    private final float aspectRatio;
    private final int gravity;

    /**
     * @param scale       of the badge shape relative to the size of the canvas
     * @param aspectRatio width to height of the badge shape
     * @param gravity     to place the badge shape on the canvas
     */
    protected BadgeShape(@FloatRange(from = 0, to = 1) float scale, float aspectRatio, int gravity) {
        this.scale = scale;
        this.aspectRatio = aspectRatio;
        this.gravity = gravity;
    }

    /**
     * @param canvas          to draw on
     * @param bounds          of the canvas
     * @param paint           to draw with
     * @param layoutDirection for gravity mapping
     * @return the region the badge is drawn in
     */
    public Rect draw(@NonNull Canvas canvas, @NonNull Rect bounds, @NonNull Paint paint, int layoutDirection) {
        float width = bounds.width() * scale;
        float height = bounds.height() * scale;
        if (width < height * aspectRatio) {
            height = width / aspectRatio;
        } else {
            width = height * aspectRatio;
        }
        applyGravity((int) width, (int) height, bounds, layoutDirection);
        onDraw(canvas, rect, paint);
        return rect;
    }

    private void applyGravity(int width, int height, Rect bounds, int layoutDirection) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Gravity.apply(gravity, width, height, bounds, rect);
        } else {
            Gravity.apply(gravity, width, height, bounds, rect, layoutDirection);
        }
    }

    /**
     * @param canvas to draw on
     * @param region to draw in
     * @param paint  to draw with
     */
    protected abstract void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint);

    /**
     * @param scale   of the badge shape relative to the size of the canvas
     * @param gravity to place the badge shape on the canvas
     * @return a {@code BadgeShape} drawing a circle
     * @see #oval(float, float, int)
     */
    public static BadgeShape circle(@FloatRange(from = 0, to = 1) float scale, int gravity) {
        return oval(scale, 1, gravity);
    }

    /**
     * @param scale       of the badge shape relative to the size of the canvas
     * @param aspectRatio width to height of the badge shape
     * @param gravity     to place the badge shape on the canvas
     * @return a {@code BadgeShape} drawing a circle
     */
    public static BadgeShape oval(@FloatRange(from = 0, to = 1) float scale, float aspectRatio, int gravity) {
        return new BadgeShape(scale, aspectRatio, gravity) {
            private final RectF region = new RectF();

            @Override
            protected void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint) {
                this.region.set(region);
                canvas.drawOval(this.region, paint);
            }
        };
    }

    /**
     * @param scale       of the badge shape relative to the size of the canvas
     * @param aspectRatio width to height of the badge shape
     * @param gravity     to place the badge shape on the canvas
     * @return a {@code BadgeShape} drawing a rect
     */
    public static BadgeShape rect(@FloatRange(from = 0, to = 1) float scale, float aspectRatio, int gravity) {
        return new BadgeShape(scale, aspectRatio, gravity) {
            @Override
            protected void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint) {
                canvas.drawRect(region, paint);
            }
        };
    }

    /**
     * @param scale        of the badge shape relative to the size of the canvas
     * @param aspectRatio  width to height of the badge shape
     * @param gravity      to place the badge shape on the canvas
     * @param radiusFactor of the oval used to round the corners
     * @return a {@code BadgeShape} drawing a round-rect
     */
    public static BadgeShape rect(@FloatRange(from = 0, to = 1) float scale, float aspectRatio, int gravity,
                                  @FloatRange(from = 0, to = 1) final float radiusFactor) {
        if (radiusFactor == 0) {
            return rect(scale, aspectRatio, gravity);
        }
        return new BadgeShape(scale, aspectRatio, gravity) {
            private final RectF region = new RectF();

            @Override
            protected void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint) {
                this.region.set(region);
                float r = 0.5f * Math.min(region.width(), region.height()) * radiusFactor;
                canvas.drawRoundRect(this.region, r, r, paint);
            }
        };
    }

    /**
     * @param scale   of the badge shape relative to the size of the canvas
     * @param gravity to place the badge shape on the canvas
     * @return a {@code BadgeShape} drawing a square
     * @see #rect(float, float, int)
     */
    public static BadgeShape square(@FloatRange(from = 0, to = 1) float scale, int gravity) {
        return rect(scale, 1, gravity);
    }

    /**
     * @param scale        of the badge shape relative to the size of the canvas
     * @param gravity      to place the badge shape on the canvas
     * @param radiusFactor of the oval used to round the corners
     * @return a {@code BadgeShape} drawing a round-square
     * @see #rect(float, float, int, float)
     */
    public static BadgeShape square(@FloatRange(from = 0, to = 1) float scale, int gravity,
                                    @FloatRange(from = 0, to = 1) float radiusFactor) {
        return rect(scale, 1, gravity, radiusFactor);
    }
}
