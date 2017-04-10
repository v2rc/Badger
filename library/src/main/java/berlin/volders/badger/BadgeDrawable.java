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

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base for all badge drawables. Implementing the abstract methods {@link #setAlpha(int)} and
 * {@link #setColorFilter(ColorFilter)} and handling compatibility for {@link #getAlpha()} and
 * {@link #getColorFilter()}.
 */
public abstract class BadgeDrawable extends Drawable {

    private int alpha = 255;
    private ColorFilter colorFilter;

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        if (alpha > 255) {
            alpha = 255;
        } else if (alpha < 0) {
            alpha = 0;
        }
        if (this.alpha != alpha) {
            this.alpha = alpha;
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (this.colorFilter != colorFilter) {
            this.colorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override
    @Nullable
    public ColorFilter getColorFilter() {
        return colorFilter;
    }

    @Override
    public int getOpacity() {
        if (alpha == 255) {
            return PixelFormat.OPAQUE;
        }
        if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        }
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * {@code Factory} to create new badges when needed with
     * {@link Badger#sett(Drawable, BadgeDrawable.Factory)}.
     *
     * @param <T> the subtype of {@code BadgeDrawable} to create
     */
    public interface Factory<T extends BadgeDrawable> {
        T createBadge();
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        return new DummyState();
    }

    private class DummyState extends ConstantState {

        DummyState() {
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return BadgeDrawable.this;
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
