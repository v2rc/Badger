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

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Implementation of {@link TextBadge} displaying a positive count on the badge.
 */
public class CountBadge extends TextBadge {

    private int count = 0;

    /**
     * @param context to read themed colors from
     * @param shape   of the badge
     * @see #CountBadge(BadgeShape, int, int)
     */
    public CountBadge(@NonNull Context context, @NonNull BadgeShape shape) {
        super(context, shape);
    }

    /**
     * @param shape      of the badge
     * @param badgeColor to paint the badge shape with
     * @param textColor  to paint the {@code count} with
     */
    public CountBadge(@NonNull BadgeShape shape, @ColorInt int badgeColor, @ColorInt int textColor) {
        super(shape, badgeColor, textColor);
    }

    /**
     * Sets the {@code count} for this badge
     *
     * @param count to display
     * @throws IllegalArgumentException if the {@code count} is smaller than zero
     */
    public final void setCount(@IntRange(from = 0) int count) {
        if (count < 0) {
            throw new IllegalArgumentException("must be 0 <= count");
        }
        if (this.count != count) {
            this.count = count;
            setText(count == 0 ? null : String.valueOf(count));
        }
    }

    /**
     * @return the count to display
     */
    public final int getCount() {
        return count;
    }

    /**
     * Implementation of {@link TextBadge.Factory} to create {@code CountBadge} instances.
     */
    public final static class Factory extends TextBadge.Factory<CountBadge> {

        /**
         * @param context to read themed colors from
         * @param shape   of the badge
         */
        public Factory(@NonNull Context context, @NonNull BadgeShape shape) {
            super(context, shape);
        }

        /**
         * @param shape      of the badge
         * @param badgeColor to paint the badge shape with
         * @param textColor  to paint the {@code count} with
         */
        public Factory(@NonNull BadgeShape shape, @ColorInt int badgeColor, @ColorInt int textColor) {
            super(shape, badgeColor, textColor);
        }

        @Override
        public CountBadge createBadge() {
            return new CountBadge(shape, badgeColor, textColor);
        }
    }
}
