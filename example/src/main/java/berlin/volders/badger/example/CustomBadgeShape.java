/*
 * Copyright (C) 2017 volders GmbH with <3 in Berlin
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

package berlin.volders.badger.example;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import berlin.volders.badger.BadgeShape;

class CustomBadgeShape extends BadgeShape {

    private final RectF region;
    private final Paint paint;

    CustomBadgeShape(Context context, @FloatRange(from = 0, to = 1) float scale, int gravity) {
        super(scale, 1, gravity);
        region = new RectF();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getBorderColor(context));
    }

    private static int getBorderColor(Context context) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        if (theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)) {
            return typedValue.data;
        }
        return Color.TRANSPARENT;
    }

    /**
     * @see BadgeShape#circle(float, int)
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint) {
        float extX = region.width() * 0.1f;
        float extY = region.height() * 0.1f;
        this.region.set(region);
        this.region.left -= extX;
        this.region.top -= extY;
        this.region.right += extX;
        this.region.bottom += extY;
        canvas.drawOval(this.region, this.paint);
        this.region.set(region);
        canvas.drawOval(this.region, paint);
    }
}
